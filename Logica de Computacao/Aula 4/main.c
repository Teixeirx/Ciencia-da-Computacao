#include <stdio.h>
#include <stdlib.h>
#include <locale.h>

//exemplo 1
/*int main()
{
    int contador;
    int limite = 10;

    for(contador=1; contador <= limite; contador++){
        printf("Hello World\n");
        printf("%d", contador);
        printf("------\n");

    }

    return 0;
}*/

//lista de exercícios
//exercício 1

/*int main()
{
    setlocale(LC_ALL, "Portuguese");

    int i;
    int limite = 100;
    for (i=1; i<=limite; i++){
        if (i%2==0){
            printf("%d - Par\n", i);
        }
        else{
            printf("%d - Ímpar\n", i);
        }
    }

    return 0;
}*/


int main(){
    setlocale(LC_ALL, "Portuguese");
    int limite = 10;
    int contador = 1;

    while (contador <= limite){
        printf("Estamos no primeiro Loop!\nContador = %d\n", contador);
        contador++;
        if(contador==3){
            int contador_2 = 5;
            while(contador_2 > 0){
                printf("Estamos no segundo Loop!\nContador_2= %d\n", contador_2);
                contador_2--;
            }
        }
    }


    return 0;
}
