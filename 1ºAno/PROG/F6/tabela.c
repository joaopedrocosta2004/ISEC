#include <stdio.h>
#include "tabela.h"

void printV(ret a[], int total){
    int i;

    printf("Existem %d retangulos na tabela\n", total);
    for(i=0; i < total; i++) {
        printf("R. %d\n", i);
        printRet(a[i]);
        printf("\n");
    }
}

void printQuadrados(ret a[], int total) {
    ret aux;
    for (int i = 0; i < total; i++) {
        aux = a[i];
        if (aux.larg == aux.alt) {
            printRet(aux);
        }
    }
}

int addRet(ret a[], int *total){
    if (*total >=10)
        return 0;

    initRet(&a[*total]);
    *total += 1;
    return 1;

}

void duplicaAltLarg(ret a[], int total){

    for (int i = 0; i < total; i++) {
        if (areaR(a[i]) % 2 == 0) {
            a[i].alt = a[i].alt * 2;
            a[i].larg = a[i].larg * 2;
        }
    }
}


int quadrante1(ret a[], int total){
    int count = 0;
    for (int i = 0; i < total; i++) {
        if (quadrante(a[i].canto) == 1) {
            count += 1;
        }
    }
    return count;
}

int encontraMenor (ret a[], int *total) {
    ret min = a[0];
    int pos = 0;

    for (int i = 0; i < *total; i++) {
        if (areaR(a[i]) < areaR(min)) {
            min = a[i];
            pos = i;
        }
    }

    return pos;
}

int eliminarElemento(ret a[],int *total,int pos) {
    for (int i = pos; i < *total - 1; i++) {
        a[i] = a[i+1];
    }

    return --(*total);
}

void eliminaMenor(ret a[], int *total){
    int pos;

    if (*total == 0) {
        printf("Nao Existem Retangulos!\n");
        return;
    }

    pos = encontraMenor(a, total);

    *total = eliminarElemento(a,total,pos);

}

void inverteOrdem (ret a[], int total) {
    ret aux, aux1;
    int j = total;

    if (total % 2 == 0) {
        for (int i = 0; i <= total/2; i++) {
            aux = a[i];
            a[i] = a[j];
            a[j] = aux;
            j--;
        }
    } else {
        for (int i = 0; i < total/2; i++) {
            aux = a[i];
            a[i] = a[j];
            a[j] = aux;
            j--;
        }
    }
}

void eliminaVarios(ret a[], int *total, int lim){

    for (int i = 0; i < *total; i++) {
        if (areaR(a[i]) < lim) {
            *total = eliminarElemento(a, total, i);
        }
    }
}