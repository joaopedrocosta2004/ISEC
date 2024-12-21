#include <stdio.h>

void unicos(int v[ ], int tam) {
    int flag;

    for (int i = 0; i < tam; i++) {
        flag = 0;
        for (int j = 0; j < tam; j++) {
            if (v[i] == v[j] && i != j) {
                flag = 1;
            }
        }
        if (flag == 0) {
            printf("%d ", v[i]);
        }
    }
}

int main() {
    int tab1[10] = {2, 30, 4, 7, 10, 3, 12, 15, 2, 10};

    unicos(tab1, 10);

    return 0;
}
