#include <stdio.h>

int verificaQuadrado(int m1[][3], int lin) {
    int somaVerifica = 0, somaLinha = 0, somaColuna = 0, somaDiagonal1 = 0, somaDiagonal2 = 0;
    for (int i = 0; i < lin; i++) {
        somaVerifica += m1[i][0];
    }

    for (int i = 0; i < lin; i++){
        somaLinha = 0;
        somaColuna = 0;
        for (int j = 0; j < lin; j++){
            somaLinha += m1[i][j];
            somaColuna += m1[j][i];
        }
        if (somaLinha != somaVerifica || somaColuna != somaVerifica){
            // Caso uma das linhas nao de a mesma soma que a somaVerifica
            // retorna que nao e uma quadrado magico;
            return 0;
        }
    }
    somaDiagonal1 += m1[0][0] + m1[1][1] + m1[2][2];
    somaDiagonal2 += m1[0][2] + m1[1][1] + m1[2][0];
    if (somaDiagonal1 != somaVerifica || somaDiagonal2 != somaVerifica) {
        // Caso uma das diagonais nao de a mesma soma que a somaVerfica
        // retorna que nao e um quadrado magico;
        return 0;
    }
    return 1;
}
int main() {
    int m1[3][3] = {{6,1,8},{7,5,3},{2,9,4}};


    if (verificaQuadrado(m1, 3) == 1) {
        printf("E um quadrado magico!\n");
    } else {
        printf("Nao e um quadrado magico!\n");
    }
    return 0;
}
