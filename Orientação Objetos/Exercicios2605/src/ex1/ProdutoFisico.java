package ex1;

public class ProdutoFisico extends Produto implements Avaliavel {
    public ProdutoFisico(String nome, double preco) {
        super(nome, preco);
    }

    @Override
    public double calcularFrete() {
        return getPreco() * 0.10;
    }

    @Override
    public void adicionarAvaliacao(int nota) throws NotaInvalidaException {
        if (nota < 1 || nota > 5) {
            throw new NotaInvalidaException("Nota inválida para o produto físico: " + nota);
        }
        System.out.println("Avaliação " + nota + " adicionada com sucesso ao produto físico " + getNome());
    }
}
