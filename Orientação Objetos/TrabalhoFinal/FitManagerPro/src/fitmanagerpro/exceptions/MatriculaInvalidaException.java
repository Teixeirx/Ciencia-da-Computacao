package fitmanagerpro.exceptions;

/**
 * Exceção customizada lançada quando uma matrícula não pode ser realizada.
 * Requisito 7 — Exceção customizada para proteger as regras de negócio de matrículas
 * (ex.: aluno já matriculado, plano não encontrado).
 */
public class MatriculaInvalidaException extends Exception {

    /**
     * Cria a exceção com uma mensagem descritiva do problema.
     *
     * @param mensagem descrição do motivo pelo qual a matrícula é inválida
     */
    public MatriculaInvalidaException(String mensagem) {
        super(mensagem);
    }
}
