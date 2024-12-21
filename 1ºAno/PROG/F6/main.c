#include <stdio.h>
#include "ponto.h"
#include "retangulo.h"
#include "tabela.h"

int main(){

    ret tab[10] = {{{1,1},7,5}, {{2,3},2,6}, {{-1,4},7,2}};
    int total = 3;

    //addRet(tab, &total);
    //printV(tab, total);

    printQuadrados(tab, total);

    //duplicaAltLarg(tab, total);
    //printV(tab, total);
    eliminaVarios(tab, &total, 15);

    //printf("\nExistem %d retangulos no quadrante 1\n", quadrante1(tab, total));


    return 0;
}
