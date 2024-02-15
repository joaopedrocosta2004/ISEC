#include <stdio.h>

int verificaMat(int mat[][3], int nLin){
    for (int i = 0; i < nLin; i++) {
        for (int j = 0; j < 3; j++) {
            for (int x = 0; x < nLin; x++) {
                for (int y = 0; y < 3; y++) {
                    if (x == i && y == j){
                        continue;
                    }else if (mat[i][j] == mat[x][y]) {
                        return 0;
                    }
                }
            }
        }
    }
    return 1;
}

int main() {
    int m1[4][3] = {{1,2,3},{6,7,8},{10,11,12},{20,30,40}};


    if (verificaMat(m1, 4) == 0) {
        printf("A tabela contem numeros repetidos!\n");
    } else {
        printf("A tabela contem apenas valores unicos!\n");
    }

    return 0;
}
