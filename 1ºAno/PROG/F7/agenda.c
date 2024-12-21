
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "agenda.h"

// Escreve os dados de todos os contactos na agenda
// Recebe endereço do vetor e numero de contactos armazenados
void listaC(pct p, int total){
    int i;

    printf("Existem %d contactos na agenda\n", total);
    for(i=0; i<total; i++)
        printf("%s\t%d\n", p[i].nome, p[i].num);
}

// Adiciona um novo contacto ao vetor dinamico. Os dados são indicados pelo utilizador
// Recebe endereço do vetor e endereço de variavel inteira contento o numero de contactos
// Devolve endereço de vetor depois de efetuada a atualizacao
pct addC(pct p, int *total){
    char nome[200];
    int num, flag;

    do{
        flag = 0;
        printf("Nome do novo contacto: ");
        scanf(" %[^\n]", nome);

        for (int i = 0; i < *total; i++) {
            if (strcmp(nome, p[i].nome) == 0){
                flag = 1;
                break;
            }
        }

        if (flag == 1) {
            printf("Nome ja existente na agenda. Por favor, insira um nome unico.\n");
        }
    }while (flag == 1);

    printf("Numero do novo contacto: ");
    scanf(" %d", &num);

    p = (pct)realloc(p, (*total + 1) * sizeof (ct));

    if (p == NULL) {
        printf("Erro ao realocar memória.\n");
        exit(EXIT_FAILURE);
    }

    int posicao = 0;
    while (posicao < *total && strcmp(nome, p[posicao].nome) > 0) {
        posicao++;
    }

    for (int i = *total; i > posicao; i--) {
        strcpy(p[i].nome, p[i - 1].nome);
        p[i].num = p[i - 1].num;
    }

    strcpy(p[posicao].nome, nome);
    p[posicao].num = num;
    (*total)++;

    return p;
}

// Recebe endereço do vetor, numero de contactos armazenados e nome do contacto a pesquisar
// Devolve o numero de telemovel de um contacto
int getTel(pct p, int total, char *nome){

    for (int i = 0; i < total; i++) {
        if (strcmp(p[i].nome, nome) == 0) {
            return p[i].num;
        }
    }
    printf("Contacto nao existe.\n");
    return -1;
}

// Atualiza numero de telemovel de um contacto
// Recebe endereço do vetor, numero de contactos armazenados, nome do contacto a atualizar e novo numero
// Devolve 1 se a atualizacao for efetuada, ou 0, caso contrario
int atualizaTel(pct p, int total, char *nome, int novoT){

    for (int i = 0; i < total; i++) {
        if(strcmp(p[i].nome, nome) == 0) {
            p[i].num = novoT;
            return 1;
        }
    }
    printf("Não existe ninguem com o nome: %s.\n", nome);
    return 0;
}

// Eliminar um novo contacto do vetor dinamico
// Recebe endereço do vetor, endereço de variavel inteira contento o numero de contactos e nome do contacto a eliminar
// Devolve endereço de vetor depois de efetuada a atualizacao
pct eliminaC(pct p, int *total, char *nome){
    int aux = -1;
    for (int i = 0;i < *total; i++) {
        if (strcmp(p[i].nome, nome) == 0) {
            aux = i;
            break;
        }
    }

    if (aux != -1) {
        for (int i = aux; i < *total - 1; i++) {
            strcpy(p[i].nome, p[i + 1].nome);
            p[i].num = p[i + 1].num;
        }
        (*total)--; // Decrementa o número total de contatos
        // Realoca a memória para ajustar o tamanho do vetor
        p = (pct)realloc(p, (*total) * sizeof(ct));
        if (p == NULL) {
            printf("Erro ao realocar memória.\n");
            exit(EXIT_FAILURE);
        }
        printf("Contacto %s eliminado com sucesso.\n", nome);
    } else {
        printf("Contacto %s não encontrado na agenda.\n", nome);
    }


    return p;
}