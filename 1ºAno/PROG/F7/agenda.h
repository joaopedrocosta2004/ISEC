
#ifndef AGENDA_H
#define AGENDA_H


// Criação do tipo estruturado ct para armazenar os dados de um contacto
typedef struct dados ct, *pct;
struct dados{
    int num;
    char nome[100];
};

// Prototipos das funções definidas no modulo agenda.c

void listaC(pct p, int total);

pct addC(pct p, int *total);

int getTel(pct p, int total, char *nome);

int atualizaTel(pct p, int total, char *nome, int novoT);

pct eliminaC(pct p, int *total, char *nome);


#endif
