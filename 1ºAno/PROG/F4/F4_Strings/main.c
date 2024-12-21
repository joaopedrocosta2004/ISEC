#include <stdio.h>
#include <string.h>

// Recebe um mês escrito em português
// Escreve na consola o nome do mês traduzido para ingles
// Exercicio 8
void traduz(char *mes){
    char *pt[12] = {"Janeiro", "Fevereiro", "Marco", "Abril", "Maio", "Junho",
                    "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
    char *eng[12] = {"January", "February", "March", "April", "May", "June",
                     "July", "August", "September", "October", "November", "December"};

    for (int i = 0; i < 12; i++) {
        if (strcmp(mes, pt[i]) == 0) {
            printf("%s\n", eng[i]);
            return;
        }
    }

    printf("Mes nao encontrado!\n");
}

// Exercicio 9
int verificaPlaneta(char *nome) {
    char *planetas[] = {"Mercurio", "Venus", "Terra", "Marte", "Jupiter", "Saturno", "Urano", "Neptuno"};

    int tamanho = sizeof(planetas) / sizeof (planetas[0]);

    for(int i = 0; i < tamanho; i++) {
        if (strcmp(nome, planetas[i]) == 0) {
            printf("O planeta pertence ao Sistema Solar!\n");
            return 1;
        }
    }

    printf("O nome nao corresponde com nenhum planeta do Sistema Solar!\n");
    return 0;
}

// Recebe a matriz de sinonimos e a indicacao do numero de linhas (sabe-se que são 2 colunas)
// Escreve na consola todos os pares de sinonimos
// Exercicio 10 a)
void escreve_sin(char *sin[][2], int tot_lin){

    printf("Sinonimos Armazenados: \n");
    printf("-----------------------\n");
    for (int i = 0; i < tot_lin; i++) {
        printf("%s\n", sin[i][0]);
    }
    printf("-----------------------\n");
}

// Recebe a matriz de sinonimos, a indicacao do numero de linhas e a palavra a pesquisar
// Devolve ponteiro para sinonimo da palavra recebida por parametro (NULL se não existir sinonimo)
// Exercicio 10 b)
char *pesquisa_sinonimo(char *sin[][2], int tot_lin, char *p){

    for (int i = 0; i < tot_lin; i++) {
        if(strcmp(p, sin[i][0]) == 0) {
            return sin[i][1];
        }
    }
    return NULL;
}

// Recebe a matriz de sinonimos e a indicacao do numero de linhas
// Devolve ponteiro para a palavra alfabeticamente mais pequena que se encontra na matriz (NULL se não existirem palavras)
// Exercicio 10 c)
char* alfaMin(char *sin[][2], int tot_lin){

    int menor = 0, x , y;
    for (int i = 0; i < tot_lin; i++) {
        for(int j = 0; j < 2; j++) {
            if(strlen(sin[i][j]) < menor || i == 0 && j == 0) {
                menor = strlen(sin[i][j]);
                x = i;
                y = j;
            }
        }
    }
    return sin[x][y];
}

int contaPalavrasRepetidas(char *sin[][2], int tot_lin) {

    char contador[100];

    int tam = sizeof(contador) / sizeof(contador[0]);

    for (int i = 0; i < tot_lin; i++) {
        for (int j = 0; j < 2; j++) {
            for (int x = 0; x < tam; x++) {
                if(strcmp(contador,sin[i][j] )) {
                    strcpy(contador, sin[i][j]);
                }
            }
        }
    }

}

// Exercicio 10 e)
void verificarSinonimos(char frase[], char *sin[][2], int tot_lin) {
    char *token;
    char copiaFrase[1000];
    strcpy(copiaFrase, frase);

    token = strtok(copiaFrase, " ,.!?");
    while (token != NULL) {
        for (int i = 0; i < tot_lin; i++) {
            if (strcmp(token, sin[i][0]) == 0) {
                printf("Palavra: %s, Sinonimo: %s\n", sin[i][0], sin[i][1]);
                break;
            }
        }
        token = strtok(NULL, " ,.!?");
    }
}

int main(){
    char palavra[50], *p, *q, frase[1000];

    char *s[5][2] = {{"estranho", "bizarro"},
                     {"desconfiar", "suspeitar"},
                     {"vermelho", "encarnado"},
                     {"duvidar", "desconfiar"},
                     {"carro", "automovel"}};


    char st[20];

    // Exercicio 8
    /*printf("Mes: ");
    scanf("%s", st);
    traduz(st);

    // Exercicio 9
    printf("Introduza um nome de um planeta: \n");
    scanf("%s", st);
    verificaPlaneta(st);*/

    //return 0;
    // Exercicio 10 a partir daqui. Retirar a instrução da linha anterior para testar o código

    escreve_sin(s, 5);

    printf("Palavra a pesquisar: ");
    scanf(" %s", palavra);

    p = pesquisa_sinonimo(s, 5, palavra);

    if(p == NULL)
        printf("A palavra %s nao tem sinonimo conhecido\n", palavra);
    else
        printf("A palavra %s e sinonimo de %s\n", p, palavra);

    q = alfaMin(s, 5);

    if(p == NULL)
        printf("Nao existem palavras na tabela\n");
    else
        printf("A palavra alfabeticamente mais pequena e %s\n", q);

    // Escrever e testar as restantes funções

    if(p == NULL)
        printf("Nao existem palavras na tabela\n");
    else
        //printf("A tabela contem %d palavras repetidas\n", contaPalavrasRepetidas(s, 5));
        printf("ola\n");

    if(p == NULL)
        printf("Nao existem palavras na tabela\n");
    else{
        printf("Introduza uma frase: ");
        scanf("%s", frase);
        verificarSinonimos(frase, s, 5);
    }

    return 0;
}