
#ifndef PONTO_H
#define PONTO_H

// Criação do tipo estruturado ponto2D para armazenar coordenadas inteiras num espaço com 2 dimensões
typedef struct ponto ponto2D;
struct ponto{
    int x, y;
};

// Prototipos das funções definidas no modulo ponto.c

void printPonto(ponto2D a);

void initPonto(ponto2D* p);

void movePonto(ponto2D* p, int dx, int dy);

int quadrante(ponto2D a);

int eReta(ponto2D a, ponto2D b, ponto2D c);

#endif
