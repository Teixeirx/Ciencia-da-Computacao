// Lista 2 - Exercicio 2 a)
// Enunciado: Desenvolva um programa MPI onde cada processo i envia uma mensagem para o proximo processo, formando um anel logico.
// a. Em uma versao do algoritmo, utilize a estrutura de desvio if

#include <mpi.h>
#include <stdio.h>

int main(int argc, char *argv[])
{
    int p, id, tag = 1;
    int destino, origem;
    int token;
    MPI_Status status;

    MPI_Init(&argc, &argv);

    MPI_Comm_rank(MPI_COMM_WORLD, &id);
    MPI_Comm_size(MPI_COMM_WORLD, &p);

    // Um anel logico requer no minimo 2 processos
    if (p < 2)
    {
        if (id == 0)
        {
            printf("Erro: O anel logico necessita de pelo menos 2 processos.\n");
        }
        MPI_Finalize();
        return 1;
    }

    // Definicao do proximo processo (destino) usando estrutura de desvio if
    if (id == p - 1)
    {
        destino = 0; // O ultimo processo envia para o processo 0
    }
    else
    {
        destino = id + 1; // Cada processo envia para o processo subsequente
    }

    // Definicao do processo anterior (origem) usando estrutura de desvio if
    if (id == 0)
    {
        origem = p - 1; // O processo 0 recebe do ultimo processo
    }
    else
    {
        origem = id - 1; // Cada processo recebe do anterior
    }

    // Comunicacao no anel logico
    if (id == 0)
    {
        token = 100; // Valor inicial da mensagem a percorrer o anel

        printf("Processo %d: Iniciando o anel. Enviando token %d para o processo %d (proximo)\n", id, token, destino);

        // Processo 0 inicia o anel enviando para o seu sucessor
        MPI_Send(&token, 1, MPI_INT, destino, tag, MPI_COMM_WORLD);

        // Processo 0 aguarda a mensagem dar a volta completa e ser recebida do processo anterior
        MPI_Recv(&token, 1, MPI_INT, origem, tag, MPI_COMM_WORLD, &status);

        printf("Processo %d: Token %d retornou pelo processo %d. Anel completado com sucesso!\n", id, token, origem);
    }
    else
    {
        // Demais processos aguardam receber do anterior e repassam para o proximo
        MPI_Recv(&token, 1, MPI_INT, origem, tag, MPI_COMM_WORLD, &status);
        printf("Processo %d: Recebeu token %d do processo %d\n", id, token, origem);

        token += id; // Incrementa o token para demonstrar a passagem por este processo

        printf("Processo %d: Repassando token %d para o processo %d\n", id, token, destino);
        MPI_Send(&token, 1, MPI_INT, destino, tag, MPI_COMM_WORLD);
    }

    MPI_Finalize();

    return 0;
}
