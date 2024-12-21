#include <stdio.h>
#include <stdlib.h>
#include <time.h>

// Recebe: Dimensões e endereço de uma matriz de inteiros
// A ordem dos parâmetros é crucial: o número de colunas tem que surgir antes do parâmetro que apresenta a matriz à função.
// A função imprime o conteúdo da matriz na consola
void printMat(int nLin, int nCol, int m[][nCol]){
    int i, j;

    for(i=0; i<nLin; i++){
        for(j=0; j<nCol; j++)
            printf("%d|", m[i][j]);
        putchar('\n');
    }
}

// Recebe: Dimensões e endereço de uma matriz de inteiros
// Recebe: Endereço de 2 variáveis inteiras onde deve colocar os indices das colunas com menor e maior média
// Escreve na consola as médias dos valores de cada coluna
void calcMediaCol(int nLin, int nCol, int m[][nCol], int* iMin, int* iMax){
    int sum;
    float media;
    for (int i = 0; i < nCol; i++ ){
        media = 0;
        sum = 0;
        for (int j = 0; j < nLin; j++) {
            sum += m[i][j];
            printf("%d\n", m[i][j]);
        }
        media = sum / nLin;
        printf("%.2f", media);
        if (media < *iMin || i == 0) {
            *iMin = media;
        } else if (media > *iMax || i == 0) {
            *iMax = media;
        }
    }
}

// Recebe: Dimensões e endereço de uma matriz de inteiros quadrada
// Efetua a transposicao dos valores na matriz
void tMat(int n, int mat[][n]){
    int temp[n][n];
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            temp[i][j] = mat[j][i];
        }
    }
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            mat[i][j] = temp[i][j];
        }
    }
}

// Exercicio do professor
// Só é possivel fazer isto com as linhas, pois é passado o ponteiro para
// o inicio de cada uma das linhas. Já nas colunas nao pode ser assim
// pois teria que ser passado todos os ponteiros de cada uma das linhas.

float media_array(int v[], int n) {
    float media = 0;
    for (int i = 0; i < n; i++) {
        media += v[i];
    }
    if (n > 0) {
        return media / n;
    }
    return 0;
}

void calcMediaLin(int nLin, int nCol, int m[][nCol]) {
    float mediaMaior = media_array(m[0], nCol);
    printf("%.2f\n", mediaMaior);
    for (int i = 1; i < nLin; i++) {
        float media = media_array(m[i], nCol);
        printf("%.2f\n", media);
        if (media > mediaMaior) {
            mediaMaior = media;
        }
    }
    printf("Media Maior: %.2f\n", mediaMaior);

}

// Exercicio 4
int unicaMat(int nLin, int nCol, int mat[][nCol]) {
    int i, j;

    for (i = 0; i < nLin; i++) {
        for (j = 0; j < nCol; j++) {
            int current = mat[i][j];

            for (int k = 0; k < nLin; k++) {
                for (int l = 0; l < nCol; l++) {
                    if ((i != k || j != l) && mat[k][l] == current) {
                        return 0;
                    }
                }
            }
        }
    }

    return 1;
}

// Exercicio 5

void reduzMatriz (int M, int matInicial[][M], int matReduzida[][M/2]) {
    for (int i = 0; i < M; i += 2) {
        for (int j = 0; j < M; j += 2) {
            int soma = matInicial[i][j] + matInicial[i][j+1] + matInicial[i+1][j] + matInicial[i+1][j+1];
            matReduzida[i/2][j/2] = soma / 4;
        }
    }
}

void preencherMatrizAleatoria(int tam, int matriz[][tam]) {
    srand(time(NULL));

    for (int i = 0; i < tam; i++) {
        for (int j = 0; j < tam; j++) {
            matriz[i][j] = rand() % 10;
        }
    }
}

// Exercicio 6
void espiral(int dim, int mat[][dim]) {
    int i, k = 0, l = 0;

    // k - índice da linha inicial
    // l - índice da coluna inicial

    while (k < dim && l < dim) {
        // Imprime a linha superior da matriz
        for (i = l; i < dim; ++i) {
            printf("%d ", mat[k][i]);
        }
        k++; // Move para a próxima linha

        // Imprime a coluna direita da matriz
        for (i = k; i < dim; ++i) {
            printf("%d ", mat[i][dim - 1]);
        }
        dim--; // Move para a coluna anterior

        // Imprime a linha inferior da matriz
        if (k < dim) {
            for (i = dim - 1; i >= l; --i) {
                printf("%d ", mat[dim][i]);
            }
            dim--; // Move para a linha anterior
        }

        // Imprime a coluna esquerda da matriz
        if (l < dim) {
            for (i = dim; i >= k; --i) {
                printf("%d ", mat[i][l]);
            }
            l++; // Move para a próxima coluna
        }
    }
}


int main() {

    int tam = 8;

    int mat1[3][3] = {{1,2,3},{7,8,9},{12,13,14}};
    int mat2[2][5] = {{10,2,13,4,8},{5, 9, 12, 1, 0}};

    // Exercicio 5 ----------------
    int matInicial[tam][tam];
    int matReduzida[tam/2][tam/2];
    //------------------------------

    int a=-1,b=-1;


    //printf("Mat 1:\n");
    //printMat(3, 3, mat1);
    //printf("\nMat 2:\n");
    //printMat(2, 5, mat2);

    //calcMediaCol(2, 5, mat2, &a, &b);
    //printf("\n\nCol. com menor media: %d\nCol. com maior media: %d\n", a, b);

    //tMat(3, mat1);
    //printf("\nMat 1 Transposta:\n");
    //printMat(3, 3, mat1);

    // Exercicio do professor
    //calcMediaLin(3, 3, mat1);

    // Exercicio 4
    //printf("%d", unicaMat(2, 5, mat2));

    // Exercicio 5
    /*
    preencherMatrizAleatoria(tam, matInicial);
    printMat(tam, tam, matInicial);
    reduzMatriz(tam, matInicial, matReduzida);
    printf("\n");
    printMat(tam/2, tam/2, matReduzida);
    */

    // Exercicio 6
    preencherMatrizAleatoria(tam/2, matReduzida);
    printMat(tam/2, tam/2, matReduzida);
    espiral(tam/2, matReduzida);
    return 0;
}
