#include <stdio.h>

void calculaSoma(int tab[], int dim, int valor) {
    for (int i = 0; i < dim; i++) {
        for (int j = 0; j < dim; j++) {
            for (int k = 0; k < dim; k++) {
                if ((tab[i] + tab[j] + tab[k]) == valor && tab[i] != tab[j] && tab[i] != tab[k] && tab[j] != tab[k]) {
                    printf("%d %d %d |", tab[i], tab[j], tab[k]);
                    
                }
            }
        }
    }
}

int main() {

    int tab[] = {1,-2, 3, 4, -5, 6};

    calculaSoma(tab, 6, 7);

    return 0;
}

