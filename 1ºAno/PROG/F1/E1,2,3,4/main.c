// Programação 2023/24
// Aula Prática 1

#include <stdio.h>

// Recebe: Tabela de inteiros a com tamanho tam
// Mostra na consola os valores armazenados na tabela
void mostraTab(int a[], int tam){
    int i;

    for(i=0; i<tam; i++)
        printf("%d\t", a[i]);
    putchar('\n');
}

// Exercicio 1
// Recebe: Tabela de inteiros a com tamanho tam
// Devolve maior valor armazenado na tabela
int maior(int a[], int tam){
    int i, m = a[0];
    for(i=1; i<tam; i++)
        if(a[i] > m)
            m = a[i];
    return m;
}

// Exercicio 2
// Recebe: Tabela de inteiros a com tamanho tam
// Devolve posição do maior valor armazenado na tabela
int posMaior(int a[], int tam){
    int i, max = 0, pos = 0;
    for(i = 0; i < tam; i++){
        if (a[i] > max){
            max = a[i];
            pos = i;
        }
    }

    return pos;
}

// Exercicio 3
// Recebe: Tabela de inteiros a com tamanho tam
// Devolve número de ocorrências do maior valor na tabela
int contaMaior(int a[], int tam){
    int count = 0;
    int max = 0;

    for (int i = 0; i < tam; i++){
        if (a[i] > max){
            max = a[i];
        }
    }

    for (int i = 0; i < tam; i++){
        if (a[i] == max){
            count += 1;
        }
    }

    return count;
}

// Exercicio 4
int maisComum(int a[], int tam) {
    int count = 0;
    int maxCount = 0;
    int num;
    int maxNum;

    for (int i = 0; i < tam; i++){
        num = a[i];
        count = 0;
        for (int j = 0; j < tam; j++){
            if (num == a[j]){
                count += 1;
            }
            if (count > maxCount) {
                maxCount = count;
                maxNum = num;
            }
        }
    }
    return maxNum;
}



int main(){

    int tab1[8] = {3, 6, 8, 8, 10, 1, 4, 2};
    int tab2[6] = {5, 5, 5, 9, 1, 9};

    printf("Tabela 1:\n");
    mostraTab(tab1, 8);
    printf("Maior: %d\n", maior(tab1, 8));
    printf("Pos Maior: %d\n", posMaior(tab1, 8));
    printf("Conta Maior: %d\n", contaMaior(tab1, 8));
    printf("Mais Comum: %d\n\n", maisComum(tab1, 8));

    printf("Tabela 2:\n");
    mostraTab(tab2, 6);
    printf("Maior: %d\n", maior(tab2, 6));
    printf("Pos Maior: %d\n", posMaior(tab2, 6));
    printf("Conta Maior: %d\n", contaMaior(tab2, 6));
    printf("Mais Comum: %d\n\n", maisComum(tab2, 8));

    return 0;
}
