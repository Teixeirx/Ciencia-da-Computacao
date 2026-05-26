package ex1;

public class ProdutoDigital extends Produto implements Avaliavel {
    public ProdutoDigital(String nome, double preco) {
        super(nome, preco);
    }

    @Override
    public double calcularFrete() {
        return 0.0;
    }

    @Override
    public void adicionarAvaliacao(int nota) throws NotaInvalidaException {
        if (nota < 1 || nota > 5) {
            throw new NotaInvalidaException("Nota inválida para o produto digital: " + nota);
        }
        System.out.println("Avaliação " + nota + " adicionada com sucesso ao produto digital " + getNome());
    }
}
