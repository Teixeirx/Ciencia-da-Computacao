#include <stdio.h>
#include <stdlib.h>
#include <locale.h>

int main()
{
    setlocale(LC_ALL, "Portuguese");
    char nome[256];
    char sobrenome[256];
    int ano_nascimento, dia_nascimento, mes_nascimento, idade;
    int ano_atual = 2025;

    printf("Olá, por favor digite seu nome: ");
    scanf("%s", nome);
    printf("\nO seu sobrenome agora: ");
    scanf("%s", sobrenome);
    printf("\nO dia do seu nascimento: ");
    scanf("%d", &dia_nascimento);
    printf("\nO mês do seu nascimento: ");
    scanf("%d", &mes_nascimento);
    printf("\nO ano do seu nascimento: ");
    scanf("%d", &ano_nascimento);

    idade = ano_atual - ano_nascimento;


    printf("Muito bemm!! Seu nome é %s %s, você nasceu no dia %d/%d/%d e tem %d anos de idade", nome, sobrenome, dia_nascimento, mes_nascimento, ano_nascimento, idade);
    printf("\nA primeira letra do seu nome é %c e a primeira letra do seu sobrenome é %c", nome[0], sobrenome[0]);
    if (idade < 12){
         printf(" e você é uma criança");
    } else if (idade < 18){
         printf(" e você é um adolescente");
    } else if (idade < 60){
         printf(" e você é um adulto");
    } else{
        printf(" e você é um idoso");
    }


    return 0;
}
