#include <stdio.h>
#include <string.h>

int main() {
    char palavra[100];
    int i, tamanho, eh_palindromo = 1;

    printf("Digite uma palavra: ");
    scanf("%99s", palavra);

    tamanho = strlen(palavra);

    for (i = 0; i < tamanho / 2; i++) {
        if (palavra[i] != palavra[tamanho - 1 - i]) {
            eh_palindromo = 0;
            break;
        }
    }

    if (eh_palindromo) {
        printf("A palavra '%s' e um palindromo!\n", palavra);
    } else {
        printf("A palavra '%s' NAO e um palindromo.\n", palavra);
    }

    return 0;
}