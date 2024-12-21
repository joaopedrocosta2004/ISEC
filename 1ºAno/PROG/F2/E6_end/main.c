#include <stdio.h>
#include <string.h>
#include <ctype.h>

// Exercicio 6
void inverteString (char *str) {
    for (int i = strlen(str); i >= 0; i--) {
        printf("%c", str[i]);
    }
}

// Exercicio 7 e 9
// Exercicio 9 foi adicionado mais duas condiçoes uma no inicio da funcao
// e uma no meio do while para caso haja um espaco a seguir a outro o i avanca 1
void separaString (char *str) {
    int i = 0;
    if (str[i] == ' ') {
        i++;
    }
    while (str[i] != '\0') {
        if (str[i] == ' ' && str[i-1] != ' ') {
            printf("\n");
            i++;
        } else if(str[i] == ' ' && str[i-1] == ' ') {
            i++;
        } else {
            printf("%c", str[i]);
            i++;
        }
    }
}

// Exercicio 8
void verificaStrings (char *str0, char *str1, char *str2) {
    if (strcmp(str0, str1) != 0) {
        str2 = "Conteudo Igual!";
        printf("%s", str2);
    } else if (strlen(str0) == strlen(str1)){
        str2 = "Tamanho Igual!";
        printf("%s", str2);
    } else {
        str2 = strcat(str0, str1);
        printf("%s", str2);
    }
}

// Exercicio 10
void contaPalavras (char *str) {

    int count = 0;
    char* token = strtok(str," ");

    while (token != NULL) {
        //printf("%s \n", token);
        count += 1;
        token = strtok(NULL, " - ");
    }

    printf("Numero de palavras: %d", count);

}

// Exercicio 11
void removeEspacos(char *str) {
    for (int i = 0; i < strlen(str); i++){
        if (str[i] == ' ' && str[i+1] == ' ' || str[i] == ' ' && str[i+1] == '\0') {
            for (int j = i; j < strlen(str); j++) {
                str[j] = str[j+1];
            }
        }
    }
    printf("%s", str);
}

// Exercicio 12
void alteraVogais(char *str) {
    char vogais[] = {'a', 'e', 'i', 'o','u'};
    for (int i = 0; i < strlen(str); i++) {
        for (int j = 0; j < strlen(vogais); j++){
            if (str[i] == vogais[j]){
                str[i] = toupper(str[i]);
            }
        }
        printf("%c", str[i]);
    }
}

// Exercicio 13
int analisaFrase(char *str) {
    int nDigit = 0, nChar = 0;
    for (int i = 0; i < strlen(str); i++) {
        if (isdigit(str[i])){
            nDigit += 1;
        } else if (ispunct(str[i])){
            nChar += 1;
        }
    }

    if (nDigit == nChar) {
        return 0;
    } else if (nDigit > nChar) {
        return 1;
    } else {
        return -1;
    }
}

// Exercicio 14
int contaCharUnicos(char *str) {
    int count = 0;
    for (int i = 0; i < strlen(str); i++){
        for (int j = 0; j < i; j++) {
            if (str[i] != str[j]) {
                printf("%c", str[i]);
                count += 1;
            }
        }
    }
    return count;
}

int main() {
    // Exercicio 6 e 7
    char str[] = "Hoje e  Domingo!";
    //inverteString(str);
    //printf("\n");
    //separaString(str);
    //printf("\n");

    // Exercicio 8
    char str0[] = "Amanha e domingo";
    char str1[] = "Amanha e domingo";
    char str2[100];
    //verificaStrings(str0, str1, str2);

    // Exercicio 10
    printf("\n");
    //contaPalavras(str);

    // Exercicio 11
    printf("\n");
    //removeEspacos(str);

    // Exercicio 12
    printf("\n");
    //alteraVogais(str);

    // Exercicio 13
    printf("\n");
    //printf("%d",analisaFrase(str));

    // Exercicio 14
    printf("\n");
    printf("%d", contaCharUnicos(str));


    return 0;
}
