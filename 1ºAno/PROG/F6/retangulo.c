#include "retangulo.h"
#include <stdio.h>

void printRet(ret r){
    printf("\n (%3d,%3d)", (r.canto.x), (r.canto.y) + r.alt);
    printf("\t\t (%3d,%3d)", (r.canto.x) + r.larg, (r.canto.y) + r.alt);
    printf("\n (%3d,%3d)", (r.canto.x), (r.canto.y));
    printf("\n (%3d,%3d)", (r.canto.x) + r.larg, (r.canto.y));
}

void initRet(ret* p){
    printf("Introduza o canto inferior esquerdo do retangulo (x,y) -> ");
    scanf("%d %d", &(p->canto.x), &(p->canto.y));

    printf("Introduza altura do retangulo: ");
    scanf("%d", &(p->alt));
    printf("Introduza a largura do retangulo: ");
    scanf("%d", &(p->larg));
}

int areaR(ret r){
    return (r.larg * r.alt);
}

int dentroR(ret r, ponto2D a){
    if ((a.x > r.canto.x) && (a.x < r.canto.y + r.larg)
        && (a.y > r.canto.y) && (a.y < r.canto.y + r.alt))
        return 1;

    return 0;
}

void moveR(ret* p, int dx, int dy){
    p->canto.x += dx;
    p->canto.y += dy;

    printf("\n (%3d,%3d)", (p->canto.x), (p->canto.y) + p->alt);
    printf("\t\t (%3d,%3d)", (p->canto.x) + p->larg, (p->canto.y) + p->alt);
    printf("\n (%3d,%3d)", (p->canto.x), (p->canto.y));
    printf("\n (%3d,%3d)", (p->canto.x) + p->larg, (p->canto.y));

}