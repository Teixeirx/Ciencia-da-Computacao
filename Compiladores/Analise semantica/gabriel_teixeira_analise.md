Análise da Quantidade de Memória Utilizada em um Código C
1. Enunciado

Utilizando um código fonte em linguagem C, verifique quantos elementos são alocados em memória. Identifique seus tipos e, de acordo com a informação (coletada na Internet) sobre quanto cada tipo consome de memória, calcule quanto o trecho de código utiliza de memória para DADOS.

2. Tabela de tamanhos dos tipos de dados

Para realizar os cálculos, será utilizada a seguinte tabela de referência:

Tipo de dado	Tamanho
char	1 byte
bool	1 byte
short	2 bytes
int	4 bytes
float	4 bytes
long	4 bytes
double	8 bytes

Observação: os tamanhos acima são os valores adotados para este exercício. Na linguagem C, o tamanho de alguns tipos pode variar conforme a implementação e a plataforma.

3. Código-fonte escolhido

O código escolhido verifica se uma palavra digitada pelo usuário é um palíndromo, ou seja, se pode ser lida da mesma forma da esquerda para a direita e da direita para a esquerda.

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

4. Identificação dos elementos alocados

No código, as variáveis são declaradas da seguinte maneira:

char palavra[100];
int i, tamanho, eh_palindromo = 1;


Essas declarações representam os seguintes elementos:

palavra[100]: vetor com 100 elementos do tipo char.
i: uma variável do tipo int.
tamanho: uma variável do tipo int.
eh_palindromo: uma variável do tipo int, inicializada com o valor 1.

Portanto, são considerados 103 elementos declarados: 100 posições do vetor e 3 variáveis inteiras.

5. Cálculo da memória utilizada
5.1. Vetor palavra

O vetor possui 100 posições, e cada elemento do tipo char ocupa 1 byte, conforme a tabela adotada.

Cálculo:

100 × 1 = 100 bytes

5.2. Variável i

A variável i é do tipo int e ocupa 4 bytes.

Cálculo:

1 × 4 = 4 bytes

5.3. Variável tamanho

A variável tamanho também é do tipo int e ocupa 4 bytes.

Cálculo:

1 × 4 = 4 bytes

5.4. Variável eh_palindromo

A variável eh_palindromo é do tipo int e ocupa 4 bytes. Embora armazene os valores 0 ou 1, seu tipo declarado continua sendo int.

Cálculo:

1 × 4 = 4 bytes

5.5. Tabela do cálculo
Variável	Tipo	Quantidade de elementos	Tamanho por elemento	Memória total
palavra	char	100	1 byte	100 bytes
i	int	1	4 bytes	4 bytes
tamanho	int	1	4 bytes	4 bytes
eh_palindromo	int	1	4 bytes	4 bytes
Total		103		112 bytes
6. Resultado final

Para calcular a quantidade total de memória utilizada pelas variáveis, somam-se os valores encontrados:

Memória total = 100 + 4 + 4 + 4

Memória total = 112 bytes

Assim, considerando a tabela de tamanhos fornecida, o código utiliza 112 bytes de memória para armazenar as variáveis locais declaradas.

Esse cálculo considera somente os dados das variáveis locais. Não inclui o espaço utilizado internamente pelas funções da biblioteca, a memória de execução do programa ou outros possíveis custos adicionais.

O vetor palavra já possui 100 posições, incluindo espaço para o caractere terminador \0 da string. A função strlen() apenas retorna o comprimento da palavra e não cria outro vetor.

7. Fontes de pesquisa
GNU C Language Manual — Type Size: https://www.gnu.org/software/c-intro-and-ref/manual/html_node/Type-Size.html
cppreference — Operador sizeof em C: https://en.cppreference.com/w/c/language/sizeof

As fontes explicam como o tamanho dos tipos e dos objetos pode ser consultado em C por meio do operador sizeof. Os valores utilizados nos cálculos deste relatório seguem a tabela de referência indicada no enunciado.