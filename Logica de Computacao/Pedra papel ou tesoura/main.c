#include <stdio.h>
#include <stdlib.h>
#include <locale.h>
#include <string.h>


int main()
{
    setlocale(LC_ALL, "Portuguese");
    char player1[256];
    char player2[256];
    printf("Player 1, o que você quer jogar? (pedra, papel ou testoura): ");
    scanf("%s", player1);

    printf("Player 2, o que você quer jogar? (pedra, papel ou testoura): ");
    scanf("%s", player2);

    if ((strcmp(player1, "tesoura") == 0 && strcmp(player2, "papel") == 0) ||
    (strcmp(player1, "papel") == 0 && strcmp(player2, "pedra") == 0) ||
    (strcmp(player1, "pedra") == 0 && strcmp(player2, "tesoura") == 0)) {
    printf("O vencedor é o player 1!");
}
else if ((strcmp(player2, "tesoura") == 0 && strcmp(player1, "papel") == 0) ||
         (strcmp(player2, "papel") == 0 && strcmp(player1, "pedra") == 0) ||
         (strcmp(player2, "pedra") == 0 && strcmp(player1, "tesoura") == 0)) {
    printf("O vencedor é o player 2!");
}
else if (strcmp(player1, player2) == 0) {
    printf("Deu empate!");
}
else {
    printf("Jogada inválida.");
}

    return 0;
}

