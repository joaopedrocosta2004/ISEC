#include "utils.h"

//-------------------------------------------------------------------
// Inicializacao de variaveis e listas de armazenamento
Topic storedTopics[MAX_TOPICS];
int numStoredTopics = 0;
DataUser storedUsers[MAX_USERS];
int numStoredUsers = 0;
int stop_thread = 0;

// Funcao para terminar o servidor
void closeManager()
{
    unlink(SERVER_FIFO);
    printf("\nManager Terminado\n");
    exit(1);
}

// Funcao para terminar o servidor por sinal
void handler_sigalrm(int s, siginfo_t *i, void *v)
{
    closeManager();
}

void savePMessagesFile(Topic topics[], int *numTopics)
{

    char *fileName = getenv("MSG_FICH");
    if (fileName == NULL)
    {
        fprintf(stderr, "Erro: Variável de ambiente MSG_FICH não definida.\n");
        return;
    }

    // Abrir o arquivo para escrita
    FILE *file = fopen(fileName, "w");
    if (file == NULL)
    {
        perror("Erro ao abrir o arquivo");
        return;
    }

    // Iterar pelas mensagens e salvar as válidas
    for (int i = 0; i < *numTopics; i++)
    {
        for (int j = 0; j < topics[i].topicMessagesP.num_msgP; j++)
        {
            if (topics[i].topicMessagesP.messagesP[j].duration > 0)
            {
                fprintf(file, "%s %s %d %s\n",
                        topics[i].dataTopic.name,
                        topics[i].topicMessagesP.messagesP[j].name,
                        topics[i].topicMessagesP.messagesP[j].duration,
                        topics[i].topicMessagesP.messagesP[j].msg);
            }
        }
    }

    // Fechar o arquivo
    fclose(file);
    printf("Mensagens salvas no arquivo: %s\n", fileName);
}

void loadPMessagesFile(Topic topics[], int *numTopics)
{
    char *fileName = getenv("MSG_FICH");
    if (fileName == NULL)
    {
        fprintf(stderr, "Erro: Variável de ambiente MSG_FICH não definida.\n");
        return;
    }

    // Abrir o arquivo para leitura
    FILE *file = fopen(fileName, "r");
    if (file == NULL)
    {
        perror("Erro ao abrir o arquivo");
        return;
    }

    // Variáveis auxiliares para leitura
    char line[600]; // Para armazenar uma linha inteira
    char topicName[100];
    char userName[100];
    int duration;
    char msg[500];

    // Limpar a contagem de tópicos
    *numTopics = 0;

    // Ler linha por linha do arquivo
    while (fgets(line, sizeof(line), file) != NULL)
    {
        // Tentar extrair os dados da linha
        if (sscanf(line, "%s %s %d %[^\n]", topicName, userName, &duration, msg) == 4)
        {
            int found = 0;
            // Procurar pelo tópico
            for (int i = 0; i < *numTopics; i++)
            {
                if (strcmp(topics[i].dataTopic.name, topicName) == 0)
                {
                    found = 1;
                    if (topics[i].topicMessagesP.num_msgP < MAX_MSG_P)
                    {
                        // Adicionar nova mensagem ao tópico encontrado
                        strcpy(topics[i].topicMessagesP.messagesP[topics[i].topicMessagesP.num_msgP].msg, msg);
                        strcpy(topics[i].topicMessagesP.messagesP[topics[i].topicMessagesP.num_msgP].name, userName);
                        topics[i].topicMessagesP.messagesP[topics[i].topicMessagesP.num_msgP].duration = duration;
                        topics[i].topicMessagesP.num_msgP++;
                    }
                    else
                    {
                        printf("Número máximo de mensagens persistentes atingido no tópico '%s'.\n", topicName);
                    }
                    break;
                }
            }

            // Se o tópico não for encontrado, criar um novo tópico
            if (!found)
            {
                if (*numTopics < MAX_TOPICS) // Verificar se há espaço para mais tópicos
                {
                    strcpy(topics[*numTopics].dataTopic.name, topicName);
                    strcpy(topics[*numTopics].dataTopic.status, "Desbloqueado");
                    strcpy(topics[*numTopics].topicMessagesP.messagesP[0].name, userName);
                    topics[*numTopics].topicMessagesP.messagesP[0].duration = duration;
                    strcpy(topics[*numTopics].topicMessagesP.messagesP[0].msg, msg);
                    topics[*numTopics].topicMessagesP.num_msgP = 1;
                    (*numTopics)++; // Incrementar o número de tópicos
                }
                else
                {
                    printf("Número máximo de tópicos atingido.\n");
                }
            }
        }
        else
        {
            printf("Formato de linha inválido: %s", line);
        }
    }

    fclose(file);
    printf("Mensagens carregadas do arquivo: %s\n", fileName);
}

void *tempo(void *dados)
{
    Topic *pdados = (Topic *)dados;
    time_t current_time;

    while (!stop_thread)
    {
        // Captura o tempo atual
        current_time = time(NULL);

        // Verifica cada tópico e suas mensagens
        for (int i = 0; i < numStoredTopics; i++) // Usando numStoredTopics para limitar os tópicos
        {
            pthread_mutex_lock(pdados[i].m);

            for (int j = 0; j < pdados[i].topicMessagesP.num_msgP; j++)
            {
                // Verifica se a duração da mensagem é maior que zero
                if (pdados[i].topicMessagesP.messagesP[j].duration > 0)
                {
                    // A duração é maior que zero, então diminui o tempo
                    pdados[i].topicMessagesP.messagesP[j].duration--;

                    // Quando a duração chega a 0, limpa a mensagem
                    if (pdados[i].topicMessagesP.messagesP[j].duration <= 0)
                    {
                        // Limpa a mensagem, ou seja, coloca uma string vazia
                        strcpy(pdados[i].topicMessagesP.messagesP[j].msg, "\0");  // Limpa a mensagem
                        strcpy(pdados[i].topicMessagesP.messagesP[j].name, "\0"); // Limpa o nome também (opcional)
                    }
                }
            }

            pthread_mutex_unlock(pdados[i].m);
        }

        // Aguardar um segundo antes de verificar novamente
        sleep(1);
    }

    pthread_exit(NULL);
}

int main(int argc, char *argv[])
{
    pthread_t t;
    pthread_mutex_t mutexes[MAX_TOPICS];

    for (int i = 0; i < MAX_TOPICS; i++)
    {
        pthread_mutex_init(&mutexes[i], NULL); // Inicializa o mutex para cada tópico
        storedTopics[i].m = mutexes + i;       // Atribui o mutex correspondente ao tópico
    }

    int fd_request, fd_response, size, nfd;
    char managerCommand[100];
    DataDados dados;
    fd_set read_fds;

    // Cria as estruturas tanto para o tempo como para o sinal
    struct sigaction sa;
    sa.sa_sigaction = handler_sigalrm;
    sa.sa_flags = SA_RESTART | SA_SIGINFO;
    sigaction(SIGINT, &sa, NULL);

    loadPMessagesFile(storedTopics, &numStoredTopics);

    pthread_create(&t, NULL, &tempo, (void *)storedTopics);

    // Cria fifo e verifica se ja se encontra algum criado com o mesmo nome
    if (mkfifo(SERVER_FIFO, 0666) == -1)
    {
        if (errno == EEXIST)
        {
            printf("<Server_Info> O programa ja se encontra em execucao!");
        }
        printf("Erro abrir FIFO");
        return 1;
    }

    printf("<Server_Info> Manager iniciado!\n");

    // Abre fifo e verifica se deu erro na abertura
    fd_request = open(SERVER_FIFO, O_RDWR);
    if (fd_request == -1)
    {
        printf("<Server_Info> Erro abrir o servidor");
        return 1;
    }

    do
    {
        FD_ZERO(&read_fds);
        FD_SET(0, &read_fds);
        FD_SET(fd_request, &read_fds);

        nfd = select(fd_request + 1, &read_fds, NULL, NULL, NULL);

        if (FD_ISSET(0, &read_fds))
        {
            // Apanha o que vem do teclado
            fgets(managerCommand, 100, stdin);
            managerCommand[strcspn(managerCommand, "\n")] = '\0';

            // Lógica do comando close
            if (strcmp(managerCommand, "close") == 0)
            {
                savePMessagesFile(storedTopics, &numStoredTopics);
                int countUsers = 0;
                dados.topicInfos.id = 0;
                dados.topicMessagesP.id = 0;
                dados.feedCommands.id = 6;
                for (int i = 0; i < numStoredUsers; i++)
                {
                    if (storedUsers[i].pid > 0)
                    {
                        // Percorre o array com o pid de todos os users subscritos neste topico
                        sprintf(CLIENT_FIFO_FINAL, CLIENT_FIFO, storedUsers[i].pid);
                        fd_response = open(CLIENT_FIFO_FINAL, O_WRONLY);
                        write(fd_response, &dados, sizeof(dados));
                        close(fd_response);
                    }
                }

                stop_thread = 1;

                if (pthread_join(t, NULL) != 0)
                {
                    exit(-1);
                }

                // Destruir todos os mutexes ao final
                for (int i = 0; i < MAX_TOPICS; i++)
                {
                    pthread_mutex_destroy(&mutexes[i]);
                }
                closeManager();
            }
            // Lógica do comando users
            else if (strcmp(managerCommand, "users") == 0)
            {
                if (numStoredUsers > 0)
                {
                    printf("\n<Server> Users ativos: \n");
                    for (int i = 0; i < numStoredUsers; i++)
                    {
                        if (storedUsers[i].pid != 0)
                        {
                            printf("User: %s\n", storedUsers[i].name);
                        }
                    }
                }
                else
                {
                    printf("<Server> Não existe nenhum usuário conectado ao Manager.\n");
                }
            }
            // Lógica do comando remove
            else if (strncmp(managerCommand, "remove ", 7) == 0)
            {
                int userFound = 0;

                for (int i = 0; i < numStoredUsers; i++)
                {
                    if (strcmp(storedUsers[i].name, managerCommand + 7) == 0 && storedUsers[i].pid > 0)
                    { 
                        userFound = 1;
                        dados.feedCommands.id = 6;
                        dados.feedCommands.duration = -20;
                        // Envia os dados ao cliente.
                        sprintf(CLIENT_FIFO_FINAL, CLIENT_FIFO, storedUsers[i].pid);
                        fd_response = open(CLIENT_FIFO_FINAL, O_WRONLY);
                        if (fd_response != -1)
                        {
                            write(fd_response, &dados, sizeof(dados));
                            close(fd_response);
                        }
                        else
                        {
                            perror("<Server_Info> Erro ao abrir FIFO para resposta.");
                        }
                        close(fd_response);

                        strcpy(storedUsers[i].name, "\0");
                        storedUsers[i].pid = 0;
                        printf("User %s removido!\n", managerCommand + 7);
                    }
                }
                if (userFound == 0)
                    printf("\nUser não existe!\n");
            }
            // Lógica do comando topics
            else if (strcmp(managerCommand, "topics") == 0)
            {
                if (numStoredTopics > 0)
                {
                    printf("\n<Server> Tópicos ativos: \n");
                    for (int i = 0; i < numStoredTopics; i++)
                    {
                        printf("Tópico: %s\n", storedTopics[i].dataTopic.name);
                    }
                }
                else
                {
                    printf("\n<Server> Não existe nenhum tópico criado.\n");
                }
            }
            // Lógica do comando Show
            else if (strncmp(managerCommand, "show ", 5) == 0)
            {
                printf("<Server> Mensagens Persistente do tópico %s.\n", managerCommand + 5);
                for (int i = 0; i < numStoredTopics; i++)
                {
                    if (strcmp(managerCommand + 5, storedTopics[i].dataTopic.name) == 0)
                    {
                        if (storedTopics[i].topicMessagesP.num_msgP > 0)
                        {
                            for (int j = 0; j < storedTopics[i].topicMessagesP.num_msgP; j++)
                            {
                                printf("Mensagem: %s\n", storedTopics[i].topicMessagesP.messagesP[j].msg);
                            }
                        }
                        else
                        {
                            printf("<Server> Não existe nenhuma mensagem persistente no tópico %s.\n", managerCommand + 5);
                        }
                    }
                }
            }
            // Lógica do comando lock
            else if (strncmp(managerCommand, "lock ", 5) == 0)
            {
                if (numStoredTopics > 0)
                {
                    for (int i = 0; i < numStoredTopics; i++)
                    {
                        if (strcmp(managerCommand + 5, storedTopics[i].dataTopic.name) == 0)
                        {
                            strcpy(storedTopics[i].dataTopic.status, "Bloqueado");
                            dados.feedCommands.id = 7;
                            strcpy(dados.feedCommands.topic, storedTopics[i].dataTopic.name);
                            for (int j = 0; j < storedTopics[i].topicUsers.numUsers; j++)
                            {
                                sprintf(CLIENT_FIFO_FINAL, CLIENT_FIFO, storedTopics[i].topicUsers.users[j].pid);
                                fd_response = open(CLIENT_FIFO_FINAL, O_WRONLY);
                                write(fd_response, &dados, sizeof(dados));
                                close(fd_response);
                            }
                        }
                    }
                }
                else
                {
                    printf("\n<Server> Não existe nenhum tópico criado.\n");
                }
            }
            // Lógica do comando unlock
            else if (strncmp(managerCommand, "unlock ", 7) == 0)
            {
                if (numStoredTopics > 0)
                {
                    for (int i = 0; i < numStoredTopics; i++)
                    {
                        if (strcmp(managerCommand + 7, storedTopics[i].dataTopic.name) == 0)
                        {
                            strcpy(storedTopics[i].dataTopic.status, "Desbloqueado");
                            dados.feedCommands.id = 8;
                            strcpy(dados.feedCommands.topic, storedTopics[i].dataTopic.name);
                            for (int j = 0; j < storedTopics[i].topicUsers.numUsers; j++)
                            {
                                sprintf(CLIENT_FIFO_FINAL, CLIENT_FIFO, storedTopics[i].topicUsers.users[j].pid);
                                fd_response = open(CLIENT_FIFO_FINAL, O_WRONLY);
                                write(fd_response, &dados, sizeof(dados));
                                close(fd_response);
                            }
                        }
                    }
                }
                else
                {
                    printf("\n<Server> Não existe nenhum tópico criado.\n");
                }
            }
            // Comando Invalido
            else
            {
                printf("<Server_Commad> Comando Invalido!\n");
            }
        }
        else if (FD_ISSET(fd_request, &read_fds))
        {
            // Recebe a estrutura com os dados
            size = read(fd_request, &dados, sizeof(dados));

            if (size > 0)
            {
                // Verifica os users que se querem conectar
                if (dados.user.id == 1)
                {
                    int found = 0;
                    int emptyUserSlotIndex = -1;

                    for (int i = 0; i < numStoredUsers; i++)
                    {
                        if (strcmp(storedUsers[i].name, dados.user.name) == 0)
                        {
                            found = 1;
                            break;
                        }

                        // Verifica se há um slot vazio (pid == 0 e name == '\0')
                        if (storedUsers[i].pid == 0 && strcmp(storedUsers[i].name, "\0") == 0 && emptyUserSlotIndex == -1)
                        {
                            emptyUserSlotIndex = i;
                        }
                    }

                    if (found == 1 || numStoredUsers >= MAX_USERS)
                    {
                        // Envia ao usuario a flag=1 | rejeitado
                        dados.user.flag = 1;
                        printf("\n<Server_Info> Usuário %s rejeitado.\n", dados.user.name);
                        sprintf(CLIENT_FIFO_FINAL, CLIENT_FIFO, dados.user.pid);
                        fd_response = open(CLIENT_FIFO_FINAL, O_WRONLY);
                        write(fd_response, &dados, sizeof(dados));
                        close(fd_response);
                    }
                    else
                    {
                        if (emptyUserSlotIndex != -1)
                        {
                            // Substitui o usuário no slot vazio
                            strcpy(storedUsers[emptyUserSlotIndex].name, dados.user.name);
                            storedUsers[emptyUserSlotIndex].pid = dados.user.pid;
                        }
                        else
                        {
                            // Armazena o novo usuário na próxima posição disponível
                            strcpy(storedUsers[numStoredUsers].name, dados.user.name);
                            storedUsers[numStoredUsers].pid = dados.user.pid;
                            numStoredUsers++;
                        }

                        printf("\n<Server_Info> Usuário %s conectado.\n", dados.user.name);
                        int countUsers = 0;
                        for (int i = 0; i < numStoredUsers; i++)
                        {
                            if (strcmp(storedUsers[i].name, "\0") != 0)
                            {
                                countUsers++;
                            }
                        }
                        printf("<Server_Info> %d Usuários conectados.\n", countUsers);

                        // Altera variáveis para indicar aceitação
                        dados.user.flag = 0;
                        dados.user.id = 0;
                        sprintf(CLIENT_FIFO_FINAL, CLIENT_FIFO, dados.user.pid);
                        fd_response = open(CLIENT_FIFO_FINAL, O_WRONLY);
                        write(fd_response, &dados, sizeof(dados));
                        close(fd_response);
                    }
                }
                // Lógica do comando Topics
                else if (dados.feedCommands.id == 2)
                {
                    dados.topicInfos.id = 2;
                    int validTopicCount = 0;
                    for (int i = 0; i < numStoredTopics; i++)
                    {
                        if (strcmp(storedTopics[i].dataTopic.name, "\0") != 0)
                        { // Verifica se o tópico é válido.
                            strcpy(dados.topicInfos.topics[validTopicCount].name, storedTopics[i].dataTopic.name);
                            strcpy(dados.topicInfos.topics[validTopicCount].status, storedTopics[i].dataTopic.status);
                            if (storedTopics[i].topicMessagesP.num_msgP > 0)
                            {
                                int validMsgCount = 0;
                                for (int j = 0; j < storedTopics[i].topicMessagesP.num_msgP; j++)
                                {
                                    if (storedTopics[i].topicMessagesP.messagesP[j].duration > 0)
                                    {
                                        validMsgCount++;
                                    }
                                }
                                dados.topicInfos.topics[validTopicCount].num_msgP = validMsgCount;
                            }
                            else
                            {
                                dados.topicInfos.topics[validTopicCount].num_msgP = 0;
                            }

                            validTopicCount++; // Incrementa apenas para tópicos válidos.
                        }
                    }

                    // Define o número real de tópicos enviados.
                    dados.topicInfos.numTopics = validTopicCount;

                    // Envia os dados ao cliente.
                    sprintf(CLIENT_FIFO_FINAL, CLIENT_FIFO, dados.feedCommands.user.pid);
                    fd_response = open(CLIENT_FIFO_FINAL, O_WRONLY);
                    if (fd_response != -1)
                    {
                        write(fd_response, &dados, sizeof(dados));
                        close(fd_response);
                    }
                    else
                    {
                        perror("<Server_Info> Erro ao abrir FIFO para resposta.");
                    }
                    close(fd_response);
                }
                // Lógica do comando Subscribe
                else if (dados.feedCommands.id == 3)
                {
                    dados.topicInfos.id = 0;
                    dados.topicMessagesP.id = 3;
                    if (numStoredTopics == 0)
                    {
                        // -- Cria topico --
                        strcpy(storedTopics[numStoredTopics].dataTopic.name, dados.feedCommands.topic);
                        // Inicializa a variavel status do topico
                        strcpy(storedTopics[numStoredTopics].dataTopic.status, "Desbloqueado");
                        // Inicializa a variavel de numero de mensagens permanentes neste topico
                        storedTopics[numStoredTopics].dataTopic.num_msgP = 0;
                        storedTopics[numStoredTopics].topicMessagesP.num_msgP = 0;

                        // -- Users inscritos no Topico --
                        // Adicionar o user ao topico criado
                        storedTopics[numStoredTopics].topicUsers.numUsers = 1;
                        strcpy(storedTopics[numStoredTopics].topicUsers.users[0].name, dados.feedCommands.user.name);
                        storedTopics[numStoredTopics].topicUsers.users[0].pid = dados.feedCommands.user.pid;

                        // Envia o numero de mensagens permanentes para desbloquear o feed
                        dados.topicMessagesP.num_msgP = storedTopics[numStoredTopics].topicMessagesP.num_msgP;

                        // Incrementa o numero de topicos
                        numStoredTopics++;

                        printf("\n<Server_Info> Novo topico criado: %s\n", dados.feedCommands.topic);

                        dados.topicMessagesP.num_msgP = -3;

                        // Desbloquear o feed
                        sprintf(CLIENT_FIFO_FINAL, CLIENT_FIFO, dados.feedCommands.user.pid);
                        fd_response = open(CLIENT_FIFO_FINAL, O_WRONLY);
                        write(fd_response, &dados, sizeof(dados));
                        close(fd_response);
                    }
                    else if (numStoredTopics > 0)
                    {
                        int topicExist = 0;
                        for (int i = 0; i < numStoredTopics; i++)
                        {
                            // Verifica se o tópico já existe
                            if (strcmp(storedTopics[i].dataTopic.name, dados.feedCommands.topic) == 0)
                            {
                                topicExist = 1;
                                int userExists = 0;
                                int foundEmptySlot = -1;

                                // Verifica se o usuário já está inscrito ou se há slots disponíveis (pid == 0)
                                for (int j = 0; j < storedTopics[i].topicUsers.numUsers; j++)
                                {
                                    if (storedTopics[i].topicUsers.users[j].pid == dados.feedCommands.user.pid)
                                    {
                                        userExists = 1;
                                        break;
                                    }
                                    if (storedTopics[i].topicUsers.users[j].pid == 0 && foundEmptySlot == -1)
                                    {
                                        foundEmptySlot = j;
                                    }
                                }

                                // Se o usuário não existe
                                if (userExists == 1)
                                {

                                    dados.topicMessagesP.num_msgP = -1;

                                    sprintf(CLIENT_FIFO_FINAL, CLIENT_FIFO, dados.feedCommands.user.pid);
                                    fd_response = open(CLIENT_FIFO_FINAL, O_WRONLY);
                                    write(fd_response, &dados, sizeof(dados));
                                    close(fd_response);

                                    break;
                                }

                                // Se encontrou um slot vazio (pid == 0), substitui o slot
                                if (foundEmptySlot != -1)
                                {
                                    storedTopics[i].topicUsers.users[foundEmptySlot].pid = dados.feedCommands.user.pid;
                                    strcpy(storedTopics[i].topicUsers.users[foundEmptySlot].name, dados.feedCommands.user.name);
                                }
                                else
                                {
                                    // Adiciona o usuário em uma nova posição
                                    int newIndex = storedTopics[i].topicUsers.numUsers;
                                    storedTopics[i].topicUsers.users[newIndex].pid = dados.feedCommands.user.pid;
                                    strcpy(storedTopics[i].topicUsers.users[newIndex].name, dados.feedCommands.user.name);
                                    storedTopics[i].topicUsers.numUsers++;
                                }

                                printf("\n<Server_Info> Usuário %d - %s subscreveu tópico %s.\n", dados.feedCommands.user.pid, dados.feedCommands.user.name, dados.feedCommands.topic);

                                if (storedTopics[i].topicMessagesP.num_msgP > 0)
                                {
                                    int validMsgCount = 0;
                                    for (int k = 0; k < storedTopics[i].topicMessagesP.num_msgP; k++)
                                    {
                                        if (storedTopics[i].topicMessagesP.messagesP[k].duration > 0)
                                        {
                                            validMsgCount++;
                                            strcpy(dados.topicMessagesP.messagesP[k].name, storedTopics[i].topicMessagesP.messagesP[k].name);
                                            strcpy(dados.topicMessagesP.messagesP[k].msg, storedTopics[i].topicMessagesP.messagesP[k].msg);
                                        }
                                    }
                                    dados.topicMessagesP.num_msgP = validMsgCount;
                                }
                                sprintf(CLIENT_FIFO_FINAL, CLIENT_FIFO, dados.feedCommands.user.pid);
                                fd_response = open(CLIENT_FIFO_FINAL, O_WRONLY);
                                write(fd_response, &dados, sizeof(dados));
                                close(fd_response);
                            }
                        }
                        if (topicExist == 0)
                        {
                            int reusedIndex = -1;

                            // Verifica se existe algum tópico com o nome '\0' para reutilizar
                            for (int i = 0; i < numStoredTopics; i++)
                            {
                                if (strcmp(storedTopics[i].dataTopic.name, "\0") == 0)
                                {
                                    reusedIndex = i;
                                    break;
                                }
                            }

                            if (reusedIndex != -1)
                            {
                                // Reutiliza a posição encontrada
                                strcpy(storedTopics[reusedIndex].dataTopic.name, dados.feedCommands.topic);
                                strcpy(storedTopics[reusedIndex].dataTopic.status, "Desbloqueado");
                                storedTopics[reusedIndex].dataTopic.num_msgP = 0;
                                storedTopics[reusedIndex].topicMessagesP.num_msgP = 0;

                                // Adiciona o usuário ao tópico reutilizado
                                storedTopics[reusedIndex].topicUsers.users[0].pid = dados.feedCommands.user.pid;
                                strcpy(storedTopics[reusedIndex].topicUsers.users[0].name, dados.feedCommands.user.name);
                                storedTopics[reusedIndex].topicUsers.numUsers = 1;

                                printf("\n<Server_Info> Novo tópico criado (reutilizando posição): %s\n", dados.feedCommands.topic);

                                // Envia dados ao usuário
                                dados.topicMessagesP.num_msgP = -3;
                                sprintf(CLIENT_FIFO_FINAL, CLIENT_FIFO, dados.feedCommands.user.pid);
                                fd_response = open(CLIENT_FIFO_FINAL, O_WRONLY);
                                write(fd_response, &dados, sizeof(dados));
                                close(fd_response);
                            }
                            else if (numStoredTopics < (MAX_TOPICS)-1)
                            {
                                // Caso não exista posição reutilizável, cria um novo tópico
                                strcpy(storedTopics[numStoredTopics].dataTopic.name, dados.feedCommands.topic);
                                strcpy(storedTopics[numStoredTopics].dataTopic.status, "Desbloqueado");
                                storedTopics[numStoredTopics].dataTopic.num_msgP = 0;
                                storedTopics[numStoredTopics].topicMessagesP.num_msgP = 0;

                                // Adiciona o usuário ao novo tópico
                                storedTopics[numStoredTopics].topicUsers.users[0].pid = dados.feedCommands.user.pid;
                                strcpy(storedTopics[numStoredTopics].topicUsers.users[0].name, dados.feedCommands.user.name);
                                storedTopics[numStoredTopics].topicUsers.numUsers = 1;

                                // Atualiza o número de tópicos armazenados
                                numStoredTopics++;

                                printf("\n<Server_Info> Novo tópico criado: %s\n", dados.feedCommands.topic);

                                // Envia dados ao usuário
                                dados.topicMessagesP.num_msgP = -3;
                                sprintf(CLIENT_FIFO_FINAL, CLIENT_FIFO, dados.feedCommands.user.pid);
                                fd_response = open(CLIENT_FIFO_FINAL, O_WRONLY);
                                write(fd_response, &dados, sizeof(dados));
                                close(fd_response);
                            }
                            else
                            {

                                dados.topicMessagesP.num_msgP = -2;

                                sprintf(CLIENT_FIFO_FINAL, CLIENT_FIFO, dados.feedCommands.user.pid);
                                fd_response = open(CLIENT_FIFO_FINAL, O_WRONLY);
                                write(fd_response, &dados, sizeof(dados));
                                close(fd_response);
                            }
                        }
                    }
                }
                // Lógica do comando Unsubscribe
                else if (dados.feedCommands.id == 4)
                {
                    dados.topicInfos.id = 0;
                    dados.topicMessagesP.id = 0;
                    dados.feedCommands.id = 4;
                    int userTopicExists = 0;
                    for (int i = 0; i < numStoredTopics; i++)
                    {
                        // Verifica se o tópico existe
                        if (strcmp(storedTopics[i].dataTopic.name, dados.feedCommands.topic) == 0)
                        {
                            // Remove o usuário do tópico (definindo pid como 0)
                            for (int j = 0; j < storedTopics[i].topicUsers.numUsers; j++)
                            {
                                if (storedTopics[i].topicUsers.users[j].pid == dados.feedCommands.user.pid)
                                {
                                    storedTopics[i].topicUsers.users[j].pid = 0;
                                    printf("\n<Server_Info> Usuário '%s' removido do tópico '%s'.\n", dados.feedCommands.user.name, dados.feedCommands.topic);
                                }
                            }

                            // Verifica se ainda há algum usuário com pid != 0
                            userTopicExists = 0;
                            for (int j = 0; j < storedTopics[i].topicUsers.numUsers; j++)
                            {
                                if (storedTopics[i].topicUsers.users[j].pid != 0)
                                {
                                    userTopicExists = 1;
                                }
                            }
                            int validMsgCount = 0;
                            for (int j = 0; j < storedTopics[i].topicMessagesP.num_msgP; j++)
                            {
                                if (storedTopics[i].topicMessagesP.messagesP[j].duration > 0)
                                {
                                    validMsgCount++;
                                }
                            }

                            // Se não há usuários inscritos e não há mensagens permanentes, apaga o nome do tópico
                            if (userTopicExists == 0 && validMsgCount == 0)
                            {
                                strcpy(storedTopics[i].dataTopic.name, "\0");
                                printf("<Server_Info> Tópico '%s' foi removido.\n", dados.feedCommands.topic);

                                dados.feedCommands.duration = -2;

                                sprintf(CLIENT_FIFO_FINAL, CLIENT_FIFO, dados.feedCommands.user.pid);
                                fd_response = open(CLIENT_FIFO_FINAL, O_WRONLY);
                                write(fd_response, &dados, sizeof(dados));
                                close(fd_response);
                            }

                            break;
                        }
                    }
                    dados.feedCommands.duration = -1;

                    sprintf(CLIENT_FIFO_FINAL, CLIENT_FIFO, dados.feedCommands.user.pid);
                    fd_response = open(CLIENT_FIFO_FINAL, O_WRONLY);
                    write(fd_response, &dados, sizeof(dados));
                    close(fd_response);
                }
                // Lógica do comando msg
                else if (dados.feedCommands.id == 5)
                {
                    dados.feedCommands.id = 5;
                    dados.topicInfos.id = 0;
                    dados.topicMessagesP.id = 0;
                    int userFound = 0;
                    // Verifica se a mensagem é ou não persistente
                    if (dados.feedCommands.duration > 0)
                    {
                        for (int i = 0; i < numStoredTopics; i++)
                        {
                            // Identifica qual o topico que a mensagem será enviada
                            if (strcmp(storedTopics[i].dataTopic.name, dados.feedCommands.topic) == 0)
                            {
                                for (int g = 0; g < storedTopics[i].topicUsers.numUsers; g++)
                                {
                                    if (storedTopics[i].topicUsers.users[g].pid == dados.feedCommands.user.pid)
                                    {
                                        userFound = 1;
                                    }
                                }
                                if (strcmp(storedTopics[i].dataTopic.status, "Desbloqueado") == 0 && userFound == 1)
                                {
                                    if (storedTopics[i].topicMessagesP.num_msgP == 0)
                                    {
                                        storedTopics[i].topicMessagesP.num_msgP = 1;
                                        pthread_mutex_lock(storedTopics[i].m);
                                        storedTopics[i].topicMessagesP.messagesP[0].duration = dados.feedCommands.duration;
                                        pthread_mutex_unlock(storedTopics[i].m);
                                        strcpy(storedTopics[i].topicMessagesP.messagesP[0].msg, dados.feedCommands.message);
                                        strcpy(storedTopics[i].topicMessagesP.messagesP[0].name, dados.feedCommands.user.name);
                                    }
                                    else
                                    {
                                        int messageAdded = 0;

                                        for (int k = 0; k < storedTopics[i].topicMessagesP.num_msgP; k++)
                                        {
                                            if (storedTopics[i].topicMessagesP.messagesP[k].duration == 0)
                                            {
                                                // Reutilizar espaço da mensagem com duração 0
                                                pthread_mutex_lock(storedTopics[i].m);
                                                storedTopics[i].topicMessagesP.messagesP[k].duration = dados.feedCommands.duration;
                                                pthread_mutex_unlock(storedTopics[i].m);
                                                strcpy(storedTopics[i].topicMessagesP.messagesP[k].msg, dados.feedCommands.message);
                                                strcpy(storedTopics[i].topicMessagesP.messagesP[k].name, dados.feedCommands.user.name);
                                                messageAdded = 1;
                                                break;
                                            }
                                        }
                                        if (messageAdded == 0)
                                        {
                                            if (storedTopics[i].topicMessagesP.num_msgP < MAX_MSG_P)
                                            { // Limite de tópicos não atingido
                                                pthread_mutex_lock(storedTopics[i].m);
                                                storedTopics[i].topicMessagesP.messagesP[storedTopics[i].topicMessagesP.num_msgP].duration = dados.feedCommands.duration;
                                                pthread_mutex_unlock(storedTopics[i].m);
                                                strcpy(storedTopics[i].topicMessagesP.messagesP[storedTopics[i].topicMessagesP.num_msgP].msg, dados.feedCommands.message);
                                                strcpy(storedTopics[i].topicMessagesP.messagesP[storedTopics[i].topicMessagesP.num_msgP].name, dados.feedCommands.user.name);
                                                storedTopics[i].topicMessagesP.num_msgP++;
                                            }
                                            else
                                            {
                                                dados.feedCommands.duration = -1;
                                                sprintf(CLIENT_FIFO_FINAL, CLIENT_FIFO, dados.feedCommands.user.pid);
                                                fd_response = open(CLIENT_FIFO_FINAL, O_WRONLY);
                                                write(fd_response, &dados, sizeof(dados));
                                                close(fd_response);
                                            }
                                        }
                                    }

                                    for (int j = 0; j < storedTopics[i].topicUsers.numUsers; j++)
                                    {
                                        if (storedTopics[i].topicUsers.users[j].pid > 0)
                                        {
                                            // Percorre o array com o pid de todos os users subscritos neste topico
                                            sprintf(CLIENT_FIFO_FINAL, CLIENT_FIFO, storedTopics[i].topicUsers.users[j].pid);
                                            fd_response = open(CLIENT_FIFO_FINAL, O_WRONLY);
                                            write(fd_response, &dados, sizeof(dados));
                                            close(fd_response);
                                        }
                                    }
                                }
                                else
                                {
                                    if (userFound == 0)
                                    {
                                        dados.feedCommands.duration = -3;
                                        sprintf(CLIENT_FIFO_FINAL, CLIENT_FIFO, dados.feedCommands.user.pid);
                                        fd_response = open(CLIENT_FIFO_FINAL, O_WRONLY);
                                        write(fd_response, &dados, sizeof(dados));
                                        close(fd_response);
                                        break;
                                    }
                                    else
                                    {
                                        dados.feedCommands.duration = -2;
                                        sprintf(CLIENT_FIFO_FINAL, CLIENT_FIFO, dados.feedCommands.user.pid);
                                        fd_response = open(CLIENT_FIFO_FINAL, O_WRONLY);
                                        write(fd_response, &dados, sizeof(dados));
                                        close(fd_response);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    else
                    {
                        for (int i = 0; i < numStoredTopics; i++)
                        {
                            // Identifica qual o topico que a mensagem será enviada
                            if (strcmp(storedTopics[i].dataTopic.name, dados.feedCommands.topic) == 0)
                            {
                                for (int g = 0; g < storedTopics[i].topicUsers.numUsers; g++)
                                {
                                    if (storedTopics[i].topicUsers.users[g].pid == dados.feedCommands.user.pid)
                                    {
                                        userFound = 1;
                                    }
                                }
                                if (strcmp(storedTopics[i].dataTopic.status, "Desbloqueado") == 0 && userFound == 1)
                                {
                                    for (int j = 0; j < storedTopics[i].topicUsers.numUsers; j++)
                                    {
                                        if (storedTopics[i].topicUsers.users[j].pid > 0)
                                        {
                                            // Percorre o array com o pid de todos os users subscritos neste topico
                                            sprintf(CLIENT_FIFO_FINAL, CLIENT_FIFO, storedTopics[i].topicUsers.users[j].pid);
                                            fd_response = open(CLIENT_FIFO_FINAL, O_WRONLY);
                                            write(fd_response, &dados, sizeof(dados));
                                            close(fd_response);
                                            // break;
                                        }
                                    }
                                }
                                else
                                {
                                    if (userFound == 0)
                                    {
                                        dados.feedCommands.duration = -3;
                                        sprintf(CLIENT_FIFO_FINAL, CLIENT_FIFO, dados.feedCommands.user.pid);
                                        fd_response = open(CLIENT_FIFO_FINAL, O_WRONLY);
                                        write(fd_response, &dados, sizeof(dados));
                                        close(fd_response);
                                        break;
                                    }
                                    else
                                    {
                                        dados.feedCommands.duration = -2;
                                        sprintf(CLIENT_FIFO_FINAL, CLIENT_FIFO, dados.feedCommands.user.pid);
                                        fd_response = open(CLIENT_FIFO_FINAL, O_WRONLY);
                                        write(fd_response, &dados, sizeof(dados));
                                        close(fd_response);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                // Lógica do comando exit do feed
                else if (dados.feedCommands.id == 6)
                {
                    int userTopicExists = 0;
                    for (int i = 0; i < numStoredTopics; i++)
                    {
                        for (int j = 0; j < storedTopics[i].topicUsers.numUsers; j++)
                        {
                            if (storedTopics[i].topicUsers.users[j].pid == dados.feedCommands.user.pid)
                            {
                                printf("<Server_Info> User %s foi retirado do topico %s\n", dados.feedCommands.user.name, storedTopics[i].dataTopic.name);
                                storedTopics[i].topicUsers.users[j].pid = 0;
                            }
                        }
                        // Verifica se ainda há algum usuário com pid != 0
                        userTopicExists = 0;
                        for (int j = 0; j < storedTopics[i].topicUsers.numUsers; j++)
                        {
                            if (storedTopics[i].topicUsers.users[j].pid != 0)
                            {
                                userTopicExists = 1;
                            }
                        }
                        int validMsgCount = 0;
                        for (int j = 0; j < storedTopics[i].topicMessagesP.num_msgP; j++)
                        {
                            if (storedTopics[i].topicMessagesP.messagesP[j].duration > 0)
                            {
                                validMsgCount++;
                            }
                        }

                        // Se não há usuários inscritos e não há mensagens permanentes, apaga o nome do tópico
                        if (userTopicExists == 0 && validMsgCount == 0)
                        {
                            strcpy(storedTopics[i].dataTopic.name, "\0");
                            printf("<Server_Info> Tópico '%s' foi removido.\n", dados.feedCommands.topic);
                        }
                    }
                    for (int i = 0; i < numStoredUsers; i++)
                    {
                        if (storedUsers[i].pid == dados.feedCommands.user.pid)
                        {
                            storedUsers[i].pid = 0;
                            strcpy(storedUsers[i].name, "\0");
                            printf("User %s com pid %d desconectou-se!\n", dados.feedCommands.user.name, dados.feedCommands.user.pid);
                        }
                    }
                }
            }
        }
    } while (1);
    closeManager();
}