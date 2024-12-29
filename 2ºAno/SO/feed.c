#include "utils.h"
//------------------------------------------------------------

void closeFeed()
{
    unlink(CLIENT_FIFO_FINAL);
    printf("\nFeed Terminado\n");
    exit(1);
}

void handler_sigalrm(int s, siginfo_t *i, void *v)
{
    closeFeed();
}

int main(int argc, char *argv[])
{
    // Faz verificação do numero de argumentos
    // Se faltar o nome o programa nao arranca
    if (argc != 2)
    {
        fprintf(stderr, "Uso: %s <username>\n", argv[0]);
        exit(1);
    }

    // Inicializa as variaveis
    int fd_request, fd_response, size, nfd;
    char userCommand[400];

    // Cria as estrutura de dados e atribui valores base
    fd_set read_fds;
    DataDados dados;
    dados.user.pid = getpid();
    strcpy(dados.user.name, argv[1]);
    dados.user.id = 1;

    // Cria estrutura de sinal
    struct sigaction sa;
    sa.sa_sigaction = handler_sigalrm;
    sa.sa_flags = SA_RESTART | SA_SIGINFO;
    sigaction(SIGINT, &sa, NULL);

    sprintf(CLIENT_FIFO_FINAL, CLIENT_FIFO, getpid());
    // Faz verificação de existencia de outro fifo com o mesmo nome
    if (mkfifo(CLIENT_FIFO_FINAL, 0666) == -1)
    {
        perror("Erro ao criar FIFO do cliente");
        exit(1);
    }

    //--------- Envia o nome para o Manager ---------------
    fd_request = open(SERVER_FIFO, O_WRONLY);
    // Verificação de abertura de fifo MANAGER
    if (fd_request == -1)
    {
        fprintf(stderr, "Manager não está em execução!\n");
        closeFeed();
    }

    // Envia o nome do user para o servidor
    if (write(fd_request, &dados, sizeof(dados)) == -1)
    {
        perror("Erro ao enviar login");
        close(fd_request);
        closeFeed();
    }

    close(fd_request);
    //--------------------------------------------------------

    //--------- Recebe feedback do MANAGER sobre o usuario ---------------
    fd_response = open(CLIENT_FIFO_FINAL, O_RDWR);
    // Verificação de abertura de fifo FEED
    if (fd_response == -1)
    {
        fprintf(stderr, "<Feed_Info> Manager não está em execução!\n");
        closeFeed();
    }

    //--------------------------------------------------------

    printf("<Feed_Info> Bem-vindo, %s! Digite comandos:\n", dados.user.name);

    do
    {
        FD_ZERO(&read_fds);
        FD_SET(0, &read_fds);
        FD_SET(fd_response, &read_fds);

        nfd = select(fd_response + 1, &read_fds, NULL, NULL, NULL);

        if (FD_ISSET(0, &read_fds))
        {
            // printf("\n<Feed_Command> ");
            fgets(userCommand, sizeof(userCommand), stdin);

            userCommand[strcspn(userCommand, "\n")] = '\0';

            // Lógica do comando exit
            if (strcmp(userCommand, "exit") == 0)
            {
                dados.feedCommands.id = 6;
                dados.feedCommands.user.pid = getpid();
                strcpy(dados.feedCommands.user.name, argv[1]);
                fd_request = open(SERVER_FIFO, O_WRONLY);
                // Envia o topico do user para o servidor
                if (write(fd_request, &dados, sizeof(dados)) == -1)
                {
                    perror("<Feed> Erro ao enviar comando!\n");
                    close(fd_request);
                }
                close(fd_request);
                close(fd_response);
                closeFeed();
            }
            // Lógica de envio do comando topics
            else if (strcmp(userCommand, "topics") == 0)
            {
                dados.feedCommands.id = 2;
                dados.feedCommands.user.pid = getpid();
                fd_request = open(SERVER_FIFO, O_WRONLY);
                // Envia o nome do user para o servidor
                if (write(fd_request, &dados, sizeof(dados)) == -1)
                {
                    perror("\n<Feed> Erro ao enviar comando!\n");
                    close(fd_request);
                    break;
                }
                close(fd_request);
            }
            // Lógica de envio do comando subscribe
            else if (strncmp(userCommand, "subscribe ", 10) == 0 && strlen(userCommand) > 11 && strlen(userCommand) <= 30)
            {

                strcpy(dados.feedCommands.topic, userCommand + 10);

                dados.feedCommands.id = 3;
                dados.feedCommands.user.pid = getpid();
                strcpy(dados.feedCommands.user.name, argv[1]);
                fd_request = open(SERVER_FIFO, O_WRONLY);
                // Envia o topico do user para o servidor
                if (write(fd_request, &dados, sizeof(dados)) == -1)
                {
                    perror("<Feed> Erro ao enviar comando!\n");
                    close(fd_request);
                    break;
                }
                close(fd_request);
            }
            // Lógica de envio do comando unsubscribe
            else if (strncmp(userCommand, "unsubscribe ", 12) == 0 && strlen(userCommand) > 13)
            {

                strcpy(dados.feedCommands.topic, userCommand + 12);

                dados.feedCommands.id = 4;
                dados.feedCommands.user.pid = getpid();
                strcpy(dados.feedCommands.user.name, argv[1]);

                fd_request = open(SERVER_FIFO, O_WRONLY);
                if (write(fd_request, &dados, sizeof(dados)) == -1)
                {
                    perror("<Feed> Erro ao enviar comando!\n");
                    close(fd_request);
                    break;
                }
                close(fd_request);
            }
            // Lógica de envio do comando msg
            else if (strncmp(userCommand, "msg ", 4) == 0 && strlen(userCommand) > 5)
            {
                dados.feedCommands.id = 5;
                dados.feedCommands.user.pid = getpid();
                strcpy(dados.feedCommands.user.name, argv[1]);

                sscanf(userCommand, "%s %19s %d %[^\n]", dados.feedCommands.command, dados.feedCommands.topic, &dados.feedCommands.duration, dados.feedCommands.message);

                fd_request = open(SERVER_FIFO, O_WRONLY);
                // Envia o topico do user para o servidor
                if (write(fd_request, &dados, sizeof(dados)) == -1)
                {
                    perror("<Feed> Erro ao enviar comando!\n");
                    close(fd_request);
                    break;
                }
                close(fd_request);
            }
            // Comando invalido
            else
            {
                printf("<Feed> Comando Inválido!\n");
            }
        }
        else if (FD_ISSET(fd_response, &read_fds))
        {
            // Recebe verificação se o nome ja existe ou nao
            size = read(fd_response, &dados, sizeof(dados));

            if (size > 0)
            {
                // Verifica os users que se querem conectar
                if (dados.user.id == 1)
                {
                    // Sendo 1 que ja existe
                    if (size > 0)
                    {
                        if (dados.user.flag == 1)
                        {
                            printf("Usuário %s rejeitado.\n", dados.user.name);
                            closeFeed();
                        }
                    }
                }
                // Lógica do comando Topics
                else if (dados.topicInfos.id == 2)
                {
                    if (dados.topicInfos.numTopics == 0)
                    {
                        printf("<Server> Não existe nenhum topico criado!\n");
                    }
                    else
                    {
                        printf("\n<Server> Tópicos existentes: \n");
                        for (int i = 0; i < dados.topicInfos.numTopics; i++)
                        {
                            printf("<Server> %s - %d - %s\n", dados.topicInfos.topics[i].name, dados.topicInfos.topics[i].num_msgP, dados.topicInfos.topics[i].status);
                        }
                    }
                }
                // Lógica do comando Subscribe
                else if (dados.topicMessagesP.id == 3)
                {
                    if (dados.topicMessagesP.num_msgP > 0)
                    {
                        printf("<Server> Mensagens permanentes do Tópico: %s\n", userCommand + 10);
                        for (int j = 0; j < dados.topicMessagesP.num_msgP; j++)
                        {
                            printf("<Server> %s - %s\n", dados.topicMessagesP.messagesP[j].name, dados.topicMessagesP.messagesP[j].msg);
                        }
                    }
                    else if (dados.topicMessagesP.num_msgP == -1)
                    {
                        printf("\n<Server> Já se encontra inscrito!\n");
                    }
                    else if (dados.topicMessagesP.num_msgP == -2)
                    {
                        printf("\n<Server> Numero de Topicos maximo alcancado!\n");
                    }
                    else if (dados.topicMessagesP.num_msgP == -3)
                    {
                        printf("\n<Server> Tópico %s criado com sucesso.\n", userCommand + 10);
                    }
                }
                // Lógica do comando Unsubscribe
                else if (dados.feedCommands.id == 4)
                {
                    if (dados.feedCommands.duration == -1)
                    {
                        printf("\n<Server> Unsubscribe do tópico %s com sucesso.\n", userCommand + 12);
                    }
                    else if (dados.feedCommands.duration == -2)
                    {
                        printf("\n<Server> Unsubscribe do tópico %s com sucesso.\n", userCommand + 12);
                        printf("<Server> Tópico %s eliminado.\n", userCommand + 12);
                    }
                }
                // Lógica do comando msg
                else if (dados.feedCommands.id == 5)
                {
                    // Logica para receber a mensagem
                    if (dados.feedCommands.duration > 0)
                    {
                        printf("Mensagem Persistente do tópico %s\n", dados.feedCommands.topic);
                        printf("<%s> <%d> <%s>\n", dados.feedCommands.user.name, dados.feedCommands.duration, dados.feedCommands.message);
                    }
                    else if (dados.feedCommands.duration == -1)
                    {
                        printf("Numero máximo de mensagens persistentes atingido!\n");
                    }
                    else if (dados.feedCommands.duration == -2)
                    {
                        printf("Tópico %s bloqueado.\n", dados.feedCommands.topic);
                    }
                    else if (dados.feedCommands.duration == -3)
                    {
                        printf("Não se encontra inscrito no tópico %s.\n", dados.feedCommands.topic);
                    }
                    else
                    {
                        printf("Mensagem do tópico %s\n", dados.feedCommands.topic);
                        printf("<%s> <%s>\n\n", dados.feedCommands.user.name, dados.feedCommands.message);
                    }
                }
                // Lógica do comando exit do manager
                else if (dados.feedCommands.id == 6)
                {
                    if (dados.feedCommands.duration = -20)
                    {
                        printf("\n\nFoi removido pelo administrador.\n");
                    }
                    else
                    {
                        printf("\n\nManager vai terminar...\n");
                    }

                    printf("A terminar feed...\n");
                    printf("Feed terminado...\n");
                    closeFeed();
                }
                else if (dados.feedCommands.id == 7)
                {
                    printf("\nO Tópico %s foi bloqueado!\n", dados.feedCommands.topic);
                }
                else if (dados.feedCommands.id == 8)
                {
                    printf("\nO Tópico %s foi desbloqueado!\n", dados.feedCommands.topic);
                }
            }
        }

    } while (1);
    closeFeed();

    return 0;
}