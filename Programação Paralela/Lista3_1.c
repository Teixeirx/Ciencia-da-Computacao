// Lista 3 - Exercicio 1
// Enunciado: Com o uso de Comunicacao Ponto-a-Ponto, no MPI, desenvolva o somatorio dos elementos
// de um vetor com N elementos e considerando a existencia de P processos na aplicacao.
//
// Etapas de computacao e comunicacao:
// a. O processo Zero gera os elementos do vetor.
// b. O processo Zero faz o particionamento do vetor, entre os P processos da aplicacao.
// c. Cada processo Pi participante da aplicacao executa a soma parcial de seus elementos.
// d. O processo Um recebe os somatorios parciais, calcula e mostra o somatorio final dos elementos do vetor.
//
// Obs.: Paralelismo de Dados.

#include <mpi.h>
#include <stdio.h>
#include <stdlib.h>

#define N 16 // Tamanho do vetor (ajustavel, deve ser divisivel por p)

int main(int argc, char *argv[])
{
    int p, id, tag = 1;
    int destino, origem, i;
    MPI_Status status;

    MPI_Init(&argc, &argv);

    MPI_Comm_rank(MPI_COMM_WORLD, &id);
    MPI_Comm_size(MPI_COMM_WORLD, &p);

    // Como o Processo 1 tem o papel especifico de receber as somas parciais e exibir o total,
    // a aplicacao necessita de no minimo 2 processos (Processo 0 e Processo 1).
    if (p < 2)
    {
        if (id == 0)
        {
            printf("Erro: A aplicacao requer no minimo 2 processos (Processo 0 particiona e Processo 1 consolida).\n");
        }
        MPI_Finalize();
        return 1;
    }

    // Valida se o vetor de tamanho N pode ser dividido igualmente entre os P processos
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

    // =========================================================================
    // Etapa a: O processo Zero gera os elementos do vetor.
    // Etapa b: O processo Zero faz o particionamento do vetor entre os P processos.
    // =========================================================================
    if (id == 0)
    {
        int vetor[N];

        // Etapa a: gerando os elementos do vetor
        printf("===============================================================\n");
        printf("ETAPA A: Processo 0 gerando vetor com N = %d elementos\n", N);
        printf("===============================================================\n");
        for (i = 0; i < N; i++)
        {
            vetor[i] = i + 1; // Exemplo: 1, 2, 3, ..., N
            printf("%d ", vetor[i]);
        }
        printf("\n\n");

        // Etapa b: particionamento entre os P processos usando Comunicacao Ponto-a-Ponto
        // Guarda sua propria fatia
        for (i = 0; i < tam_fatia; i++)
        {
            vetor_local[i] = vetor[i];
        }

        // Envia as fatias para os demais processos (1 a p-1)
        for (destino = 1; destino < p; destino++)
        {
            MPI_Send(&vetor[destino * tam_fatia], tam_fatia, MPI_INT, destino, tag, MPI_COMM_WORLD);
        }
    }
    else
    {
        // Processos 1 a p-1 recebem sua fatia do Processo 0
        MPI_Recv(vetor_local, tam_fatia, MPI_INT, 0, tag, MPI_COMM_WORLD, &status);
    }

    // =========================================================================
    // Etapa c: Cada processo Pi participante da aplicacao executa a soma parcial
    //          de seus elementos.
    // =========================================================================
    int soma_parcial = 0;
    for (i = 0; i < tam_fatia; i++)
    {
        soma_parcial += vetor_local[i];
    }
    printf("Processo %d: fatia recebida, soma parcial calculada = %d\n", id, soma_parcial);

    // =========================================================================
    // Etapa d: O processo Um recebe os somatorios parciais, calcula e mostra
    //          o somatorio final dos elementos do vetor.
    // =========================================================================
    if (id != 1)
    {
        // Todos os processos (inclusive o processo 0) enviam seu somatorio parcial ao processo 1
        MPI_Send(&soma_parcial, 1, MPI_INT, 1, tag, MPI_COMM_WORLD);
    }
    else
    {
        // O Processo 1 inicializa o somatorio com a sua propria soma parcial
        int soma_total = soma_parcial;
        int soma_recebida;

        printf("\n===============================================================\n");
        printf("ETAPA D: Processo 1 coletando os somatorios parciais\n");
        printf("===============================================================\n");
        printf("Processo 1: soma parcial propria = %d\n", soma_parcial);

        for (origem = 0; origem < p; origem++)
        {
            if (origem != 1)
            {
                MPI_Recv(&soma_recebida, 1, MPI_INT, origem, tag, MPI_COMM_WORLD, &status);
                printf("Processo 1: recebeu soma parcial %d do Processo %d\n", soma_recebida, origem);
                soma_total += soma_recebida;
            }
        }

        printf("\n---------------------------------------------------------------\n");
        printf("SOMATORIO FINAL DOS ELEMENTOS DO VETOR = %d\n", soma_total);
        printf("---------------------------------------------------------------\n\n");
    }

    MPI_Finalize();

    return 0;
}
