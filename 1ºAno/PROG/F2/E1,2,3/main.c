#include <stdio.h>

// Recebe: Matriz de inteiros mat com 3 colunas e nLin linhas
// Mostra na consola os valores armazenados na matriz
void printMat(int mat[][3], int nLin){
    int i, j;

    for(i=0; i<nLin; i++){
        for(j=0; j<3; j++)
            printf("%d\t", mat[i][j]);
        putchar('\n');
    }
}

// Recebe: Matriz de inteiros mat com 3 colunas e nLin linhas
// Preenche a matriz de acordo com as regras definidas nos exercícios 2 e 3 da ficha prática 2
void preencheMat(int mat[][3], int nLin){
    // Exercicio 2
    int num, flag;
    for (int i = 0; i < nLin; i++) {
        do{
            // Exercicio 3
            printf("Introduza um numero: ");
            scanf("%d", &num);
            flag = 0;
            for (int j = 0; j < i; j++) {
                if (mat[j][0] == num) {
                    flag = 1;
                }
            }

        } while (num < 0 || num > 100 || flag == 1);
        mat[i][0] = num;
        mat[i][1] = num * num;
        mat[i][2] = num * num * num;
    }
}

int main(){

    int m1[4][3] = {{1,2,3},{6,7,8},{10,11,12},{20,30,40}};
    int m2[10][3] = {0};

    printMat(m1, 4);

    // Chamada da função do exercicio 2
    preencheMat(m2, 10);
    printf("\nMatriz preenchida:\n");
    printMat(m2, 10);

    return 0;
}
