#include <stdio.h>
#include <stdlib.h>

int main()
{
    int hora_cinema = 20;
    int hora_atual = 20;

    if (hora_atual > hora_cinema + 30){
        printf("Passou o tempo !!");
    }
    else if(hora_atual < hora_cinema - 30){
        printf("Ainda n�o est� na hora do seu filme!!");
    }
    else{
        printf("Voc� consegue entrar amigo!!");
    }


    return 0;
}
