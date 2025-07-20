#include <stdio.h>
#include <stdlib.h>
#include <locale.h>

int main(){

    setlocale(LC_ALL, "Portuguese");

    int num_1, num_2, operacao;
    float resultado;


    printf("Digite o primeiro n�mero: ");
    scanf("%d", &num_1);

    printf("Digite o segundo n�mero: ");
    scanf("%d", &num_2);

    do{
        printf("Digite: ");
        printf("1(multiplica��o), 2(divis�o), 3(adi��o), 4(subtra��o) ou 0 para sair\n");
        scanf("%d", &operacao);

        if(operacao == 1){
            resultado = num_1 * num_2;
            printf("O resultado da sua multiplica��o �: %.2f \n", resultado);
        }
        else if(operacao == 2){
            if(num_2 != 0){
                resultado = (float)num_1 / num_2;
                printf("O resultado da sua divis�o � aproximadamente: %.2f \n", resultado);
            }
            else{
                printf("N�o � poss�vel dividir por zero!\n");
            }
        }
        else if(operacao == 3){
            resultado = num_1 + num_2;
            printf("O resultado da sua adi��o �: %.2f \n", resultado);
        }
        else if(operacao == 4 ){
            resultado = num_1 - num_2;
            printf("O resultado da sua subtra��o �: %.2f \n", resultado);
        }
        else{
            printf("Programa Finalizado!");
        }
    } while(operacao != 0);
    return 0;
}
