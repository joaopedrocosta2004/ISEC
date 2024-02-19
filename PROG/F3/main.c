// Programação 2023/24
// Aula Prática 3 - Ponteiros e Endereços: Comunicação entre funções e manipulação de tabelas

#include <stdio.h>

// Recebe: Endereços/ponteiros para 3 variáveis do tipo float
// Deve efetuar a rotação de valores entre essas variáveis
void rotacao(float *p1, float *p2, float *p3){
    int temp;
    temp = *p1;
    *p1 = *p2;
    *p2 = *p3;
    *p3 = temp;

}

// Recebe: Endereço inicial de uma tabela de inteiros, tamanho da tabela e endereços de 4 variáveis inteiras
// Deve colocar nas variáveis referenciadas pelos 4 ponteiros:
// número de pares, de impares, maior valor e posição do maior valor
void conta(int *t, int tam, int *np, int *ni, int *maior, int *pos){
    int valor;
    for (int i = 0; i < tam; i++) {
        valor = *(t + i) % 2;
        if (valor == 0) {
            (*np)++;
        } else {
            (*ni)++;
        }
        if (*maior < *(t + i)) {
            *maior = *(t + i);
            *pos = i;
        }
    }

}

// Recebe: Endereço inicial e tamnaho de uma tabela de inteiros e endereços de 2 variáveis inteiras
// Deve colocar nas variáveis referenciadas pelos 2 ponteiros o maior e segundo maior elementos existentes na tabela
void procuraDupla(int *tab, int tam, int *prim, int *seg){
    for (int i = 0; i < tam; i++) {
        if (*(tab + i) > *prim || i == 0) {
            *prim = *(tab + i);
        }
        if (*(tab + i) > *seg && *(tab + i) < *prim || i == 0) {
            *seg = *(tab + i);
        }
    }
}

// Exercicio 6
void alteraTabela(int *tab, int tam) {
    int soma, media;
    for (int i = 0; i < tam; i++) {
        soma += tab[i];
    }
    media = soma / tam;
    for (int i = 0; i < tam; i++) {
        if (tab[i] < media) {
            tab[i] = 0;
        }
    }
}

// Exercicio 7
int comparaTabelas (int *tab1, int *tab2, int tam1, int tam2) {
    if (tam1 != tam2) {
        return 0;
    }
    for (int i = 0; i < tam1; i++) {
        if(tab1[i] != tab2[i]) {
            return 0;
        }
    }
    return 1;
}

// Exercicio 8
int comuns(int *tabA, int tamA, int *tabB, int tamB) {
    int count = 0;
    for (int i = 0; i < tamA; i++) {
        for (int j = 0; j < tamB; j++) {
            if (tabA[i] == tabB[j]) {
                count++;
            }
        }
    }
    return count;
}

// Exercicio 9
void deslocaTabelas (int *v, int tam, int shift) {
    if (tam >= shift) {
        return;
    }
    int temp[shift];
    int i;

    // Armazena os últimos "shift" elementos em temp
    for (i = tam - shift; i < tam; i++) {
        temp[i - (tam - shift)] = v[i];
    }

    // Move os elementos restantes "shift" posições para a direita
    for (i = tam - 1; i >= shift; i--) {
        v[i] = v[i - shift];
    }

    // Copia os elementos de temp de volta para o início do vetor
    for (i = 0; i < shift; i++) {
        v[i] = temp[i];
    }
}

int main(){
    float x=1.2, y=4.9, z=-2.3;

    int tab1[10] = {12, 7, 9, 4, 1, 4, 41, 7, 21, 14};
    int tab2[5] = {-2, -7, -8, -9, -1};
    int tab3[8] = {12, 10, 11, 5, 8, 3, -4, -1};

    int pares=0, impares=0, maior=0, posMaior=0;
    int prim=0, seg=0;

    printf("Antes: X=%.1f\tY=%.1f\tZ=%.1f\n", x, y, z);

    rotacao(&x, &y, &z);

    printf("Depois: X=%.1f\tY=%.1f\tZ=%.1f\n", x, y, z);

    // Colocar chamada da funcao conta()
    conta(tab1, 10,&pares, &impares, &maior, &posMaior);

    printf("Pares: %d\tImpares: %d\t, Maior: %d\t, Posicao: %d\n", pares, impares, maior, posMaior);

    // Colocar chamada da funcao procuraDupla()

    procuraDupla(tab2, 5, &prim, &seg);

    printf("Maior: %d\t, Segundo Maior: %d\n", prim, seg);

    // Exercicio 6
    alteraTabela(tab1, 10);
    // Exercicio 7
    comparaTabelas(tab2, tab3, 5, 8);
    // Exercicio 8
    comuns(tab1, 10, tab2, 5);
    // Exercicio 9
    deslocaTabelas(tab1, 10, 5);
    return 0;
}