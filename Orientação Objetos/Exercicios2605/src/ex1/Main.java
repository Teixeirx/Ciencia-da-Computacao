package ex1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Produto> carrinho = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        try {
            ProdutoFisico pf = new ProdutoFisico("Livro de POO", 50.0);
            ProdutoDigital pd = new ProdutoDigital("Curso de Java", 150.0);
            
            carrinho.add(pf);
            carrinho.add(pd);

            System.out.print("Digite uma avaliação (1 a 5) para o Produto Físico: ");
            int notaPf = scanner.nextInt();
            pf.adicionarAvaliacao(notaPf);
            
            System.out.print("Digite uma avaliação (1 a 5) para o Produto Digital: ");
            int notaPd = scanner.nextInt();
            pd.adicionarAvaliacao(notaPd);

        } catch (NotaInvalidaException e) {
            System.out.println("Erro de Avaliação: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Erro de Preço: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        } finally {
            scanner.close();
        }

        System.out.println("\n--- Resumo da Compra ---");
        double totalCompra = 0;
        for (Produto p : carrinho) {
            double custoProduto = p.getPreco() + p.calcularFrete();
            System.out.println(p.getNome() + " - Preço: R$" + p.getPreco() + " | Frete: R$" + p.calcularFrete());
            totalCompra += custoProduto;
        }
        
        System.out.println("Total da Compra (Produtos + Frete): R$" + totalCompra);
    }
}
