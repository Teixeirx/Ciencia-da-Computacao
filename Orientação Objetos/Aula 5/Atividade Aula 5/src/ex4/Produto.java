package ex4;

public class Produto {
    private int quantidadeEmEstoque;

    public void adicionarEstoque(int qtd) {
        if (qtd > 0) {
            quantidadeEmEstoque += qtd;
        }
    }

    public boolean removerEstoque(int qtd) {
        if (qtd > 0 && quantidadeEmEstoque >= qtd) {
            quantidadeEmEstoque -= qtd;
            return true;
        }
        return false;
    }

    public int getQuantidadeEmEstoque() {
        return quantidadeEmEstoque;
    }
}