package fitmanagerpro.exceptions;

/**
 * Exceção customizada lançada quando se tenta criar ou atribuir um plano inválido.
 * Requisito 7 — Exceção customizada para proteger as regras de negócio dos planos.
 */
public class PlanoInvalidoException extends Exception {

    /**
     * Cria a exceção com uma mensagem descritiva do problema.
     *
     * @param mensagem descrição do motivo pelo qual o plano é inválido
     */
    public PlanoInvalidoException(String mensagem) {
        super(mensagem);
    }
}
