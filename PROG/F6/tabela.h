
#ifndef P2324_AULA5_TABELA_H
#define P2324_AULA5_TABELA_H

#include "retangulo.h"

void printV(ret a[], int total);

void printQuadrados (ret a[], int total);

int addRet(ret a[], int *total);

void duplicaAltLarg(ret a[], int total);

int quadrante1(ret a[], int total);

int encontraMenor(ret a[], int *total);

int eliminarElemento(ret a[],int *total,int pos);

void eliminaMenor(ret a[], int *total);

void inverteOrdem(ret a[], int total);

void eliminaVarios(ret a[], int *total, int lim);

#endif //P2324_AULA5_TABELA_H
