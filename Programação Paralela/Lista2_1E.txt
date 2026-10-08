// Lista 2 - Exercicio 1 e)
// Enunciado: Processo 0 distribui um vetor de tamanho N entre todos os processos da aplicacao

#include <mpi.h>
#include <stdio.h>
#include <stdlib.h>

#define N 12 // Tamanho total do vetor (deve ser divisivel por p)

int main(int argc, char *argv[])
{
    int p, id, tag = 1, destino, i;
    MPI_Status status;

    MPI_Init(&argc, &argv);

    MPI_Comm_rank(MPI_COMM_WORLD, &id);
    MPI_Comm_size(MPI_COMM_WORLD, &p);

    // Valida se N eh divisivel pelo numero de processos
    if (N % p != 0)
    {
        if (id == 0)
        {
            printf("Erro: O tamanho do vetor N (%d) deve ser divisivel pelo numero de processos P (%d).\n", N, p);
        }
        MPI_Finalize();
        return 1;
    }

    int tam_fatia = N / p;
    int vetor_local[tam_fatia];

    if (id == 0)
    {
        int vetor[N];

        // Processo 0 gera e preenche o vetor de tamanho N
        printf("--- Processo 0 gerou o vetor completo (N = %d) ---\n", N);
        for (i = 0; i < N; i++)
        {
            vetor[i] = (i + 1) * 10;
            printf("%d ", vetor[i]);
        }
        printf("\n\n");

        // Processo 0 armazena a sua propria fatia local
        for (i = 0; i < tam_fatia; i++)
        {
            vetor_local[i] = vetor[i];
        }

        // Processo 0 distribui as fatias correspondentes para os demais processos
        for (destino = 1; destino < p; destino++)
        {
            MPI_Send(&vetor[destino * tam_fatia], tam_fatia, MPI_INT, destino, tag, MPI_COMM_WORLD);
        }

        // Exibe os elementos da fatia do Processo 0
        printf("Processo 0 manteve sua fatia (%d elementos): ", tam_fatia);
        for (i = 0; i < tam_fatia; i++)
        {
            printf("%d ", vetor_local[i]);
        }
        printf("\n");
    }
    else
    {
        // Demais processos recebem sua respectiva fatia do Processo 0
        MPI_Recv(vetor_local, tam_fatia, MPI_INT, 0, tag, MPI_COMM_WORLD, &status);

        // Exibe os elementos recebidos
        printf("Processo %d recebeu sua fatia (%d elementos): ", id, tam_fatia);
        for (i = 0; i < tam_fatia; i++)
        {
            printf("%d ", vetor_local[i]);
        }
        printf("\n");
    }

    MPI_Finalize();

    return 0;
}
