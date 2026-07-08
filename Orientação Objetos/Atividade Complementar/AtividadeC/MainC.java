package AtividadeC;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainC {
    private static final List<Movel> oficinaProducao = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao = -1;

        while (opcao != 4) {
            exibirMenu();
            opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    cadastrarMovel();
                    break;
                case 2:
                    aplicarAcabamento();
                    break;
                case 3:
                    imprimirRelatorioLote();
                    break;
                case 4:
                    System.out.println("\n[Saindo] UFNDesign encerrando atividades da oficina. Até mais!");
                    break;
                default:
                    System.out.println("\n>>> Opção inválida! Escolha um número entre 1 e 4.");
            }
        }
    }

    private static void exibirMenu() {
        System.out.println("\n=============================================");
        System.out.println("          UFNDesign - Oficina de Produção    ");
        System.out.println("=============================================");
        System.out.println("1. Cadastrar Novo Móvel Planejado");
        System.out.println("2. Aplicar Acabamento Extra (Upgrade)");
        System.out.println("3. Imprimir Relatório do Lote");
        System.out.println("4. Sair");
        System.out.println("=============================================");
    }

    private static void cadastrarMovel() {
        System.out.println("\n--- Cadastro de Móvel Planejado ---");
        System.out.println("1. Mesa de Jantar");
        System.out.println("2. Guarda-Roupa");
        int tipo = lerInteiro("Selecione o tipo de móvel: ");

        if (tipo != 1 && tipo != 2) {
            System.out.println(">>> Erro: Tipo inválido! Voltando ao menu principal.");
            return;
        }

        System.out.print("Digite o modelo do móvel: ");
        String modelo = scanner.nextLine();

        int codigo = lerInteiro("Digite o código de identificação (único): ");

        if (buscarMovelPorCodigo(codigo) != null) {
            System.out.println(">>> Erro: Já existe um móvel cadastrado com este código!");
            return;
        }

        double custoBase = lerDouble("Digite o custo base de fabricação (R$): ");

        try {
            Movel novoMovel;
            if (tipo == 1) {
                novoMovel = new MesaJantar(modelo, codigo, custoBase);
            } else {
                novoMovel = new GuardaRoupa(modelo, codigo, custoBase);
            }
            oficinaProducao.add(novoMovel);
            System.out.println("\n[Sucesso] Móvel cadastrado com sucesso na oficina!");
        } catch (ViolacaoOrcamentariaMovelException e) {
            System.out.println("\n>>> " + e.getMessage());
            System.out.println(">>> Alerta: O móvel NÃO foi cadastrado devido a violação orçamentária.");
        }
    }

    private static void aplicarAcabamento() {
        System.out.println("\n--- Aplicar Acabamento na Oficina ---");
        if (oficinaProducao.isEmpty()) {
            System.out.println(">>> Nenhuma peça em produção na oficina no momento.");
            return;
        }

        int codigo = lerInteiro("Digite o código de identificação do móvel: ");
        Movel movel = buscarMovelPorCodigo(codigo);

        if (movel == null) {
            System.out.println(">>> Erro: Móvel com o código " + codigo + " não foi encontrado na oficina.");
            return;
        }

        System.out.println("\nMóvel localizado:");
        System.out.println("Modelo: " + movel.getModelo());

        if (movel instanceof MesaJantar) {
            System.out.println("Acabamento disponível para Mesa de Jantar: Demãos Extras de Verniz (R$ 80,00 cada)");
            int quantidade = lerInteiro("Digite a quantidade de demãos extras de verniz a aplicar: ");
            if (quantidade <= 0) {
                System.out.println(">>> Quantidade inválida! A quantidade de demãos deve ser positiva.");
                return;
            }
            movel.receberUpgrade(quantidade);
            System.out.println("\n[Sucesso] Demãos de verniz aplicadas com sucesso!");
        } else if (movel instanceof GuardaRoupa) {
            System.out.println("Acabamento disponível para Guarda-Roupa: Portas com Espelho (R$ 400,00 cada)");
            int quantidade = lerInteiro("Digite a quantidade de portas com espelho a adicionar: ");
            if (quantidade <= 0) {
                System.out.println(">>> Quantidade inválida! A quantidade de portas deve ser positiva.");
                return;
            }
            movel.receberUpgrade(quantidade);
            System.out.println("\n[Sucesso] Portas com espelhos instaladas com sucesso!");
        }
    }

    private static void imprimirRelatorioLote() {
        System.out.println("\n=============================================");
        System.out.println("         RELATÓRIO DO LOTE DE MÓVEIS         ");
        System.out.println("=============================================");

        if (oficinaProducao.isEmpty()) {
            System.out.println("Nenhum móvel planejado em produção.");
            System.out.println("=============================================");
            return;
        }

        double custoTotalLote = 0.0;
        for (Movel mov : oficinaProducao) {
            System.out.println(mov);
            System.out.println("---------------------------------------------");
            custoTotalLote += mov.calcularCustoFinal();
        }

        System.out.printf("Custo Total do Lote: R$ %.2f\n", custoTotalLote);
        System.out.printf("Total de Peças no Lote: %d\n", oficinaProducao.size());
        System.out.println("=============================================");
    }

    private static Movel buscarMovelPorCodigo(int codigo) {
        for (Movel mov : oficinaProducao) {
            if (mov.getCodigoIdentificacao() == codigo) {
                return mov;
            }
        }
        return null;
    }

    private static int lerInteiro(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(">>> Entrada inválida! Digite apenas números inteiros.");
            }
        }
    }

    private static double lerDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String input = scanner.nextLine().trim().replace(',', '.');
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println(">>> Entrada inválida! Digite apenas valores numéricos decimais.");
            }
        }
    }
}
