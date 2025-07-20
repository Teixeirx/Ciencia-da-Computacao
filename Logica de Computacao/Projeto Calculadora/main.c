#include <stdio.h>
#include <stdlib.h>
#include <locale.h>

int main(){

    setlocale(LC_ALL, "Portuguese");

    int num_1, num_2, operacao;
    float resultado;


    printf("Digite o primeiro número: ");
    scanf("%d", &num_1);

    printf("Digite o segundo número: ");
    scanf("%d", &num_2);

    do{
        printf("Digite: ");
        printf("1(multiplicação), 2(divisão), 3(adição), 4(subtração) ou 0 para sair\n");
        scanf("%d", &operacao);

        if(operacao == 1){
            resultado = num_1 * num_2;
            printf("O resultado da sua multiplicação é: %.2f \n", resultado);
        }
        else if(operacao == 2){
            if(num_2 != 0){
                resultado = (float)num_1 / num_2;
                printf("O resultado da sua divisão é aproximadamente: %.2f \n", resultado);
            }
            else{
                printf("Não é possível dividir por zero!\n");
            }
        }
        else if(operacao == 3){
            resultado = num_1 + num_2;
            printf("O resultado da sua adição é: %.2f \n", resultado);
        }
        else if(operacao == 4 ){
            resultado = num_1 - num_2;
            printf("O resultado da sua subtração é: %.2f \n", resultado);
        }
        else{
            printf("Programa Finalizado!");
        }
    } while(operacao != 0);
    return 0;
}
