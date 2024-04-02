#include <stdio.h>
#include <stdlib.h>

#include "agenda.h"

int main() {

    pct tab = NULL;
    int i, total=0;
    int tel;

    for(i=0; i<3; i++)
        tab = addC(tab, &total);

    listaC(tab, total);

    printf("Alterar numero de telemovel.\n");
    printf("Introduza um novo numero de telemovel: ");
    scanf(" %d", &tel);
    atualizaTel(tab, total, "Joao Pedro", tel);

    listaC(tab, total);

    free(tab);

    return 0;
}
