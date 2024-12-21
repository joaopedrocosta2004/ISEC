
#include <stdio.h>
#include "ponto.h"

// Escreve as coordenadas do ponto recebido como parâmetro
void printPonto(ponto2D a){
    printf("Ponto: (%d,%d)\n", a.x, a.y);
}

// Inicializa as coordenadas do ponto referenciado pelo parâmetro recebido. O utilizador indica os valores
void initPonto(ponto2D* p){
    int x1, y1;

    printf("Introduza uma coordenada x: ");
    scanf("%d", &x1);
    printf("Introduza uma coordenada y: ");
    scanf("%d", &y1);

    p->x = x1;
    p->y = y1;
}

// Recebe endereço de um ponto e valores para o deslocamento ao longo dos eixos
// Atualiza as coordenadas do ponto
void movePonto(ponto2D* p, int dx, int dy){

    p->x += dx;
    p->y += dy;

}

// Devolve o quadrante a que pertence o ponto recebido por parâmetro
int quadrante(ponto2D a){
    if (a.x < 0 && a.y < 0) {
        return 3;
    } else if (a.x > 0 && a.y < 0) {
        return 4;
    } else if (a.x < 0 && a.y > 0) {
        return 2;
    } else if (a.x > 0 && a.y > 0) {
        return 1;
    } else if (a.x == 0 && a.y == 0) {
        printf("Esta no ponto de origem!\n");
        return 0;
    }
}

// Recebe 3 pontos
// Devolve 1 se estiverem na mesma reta, 0 se não estiverem
int eReta(ponto2D a, ponto2D b, ponto2D c){
    int M1;
    float k;

    M1 = (b.y-a.y)/(b.x-a.x);

    k = (float)a.y - M1*a.x;

    if ((c.y) == (M1 * c.x + k)) {
        return 1;
    } else {
        return 0;
    }
}