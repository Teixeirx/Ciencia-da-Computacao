package AtividadeA;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainA {
    private static final List<Computador> esteiraProducao = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao = -1;

        while (opcao != 4) {
            exibirMenu();
            opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    cadastrarComputador();
                    break;
                case 2:
                    aplicarUpgrade();
                    break;
                case 3:
                    imprimirRelatorioLote();
                    break;
                case 4:
                    System.out.println("\n[Saindo] UFNForge encerrando atividades da esteira. Até mais!");
                    break;
                default:
                    System.out.println("\n>>> Opção inválida! Escolha um número entre 1 e 4.");
            }
        }
    }

    private static void exibirMenu() {
        System.out.println("\n=============================================");
        System.out.println("          UFNForge - Linha de Montagem       ");
        System.out.println("=============================================");
        System.out.println("1. Cadastrar Novo Computador");
        System.out.println("2. Aplicar Upgrade");
        System.out.println("3. Imprimir Relatório do Lote");
        System.out.println("4. Sair");
        System.out.println("=============================================");
    }

    private static void cadastrarComputador() {
        System.out.println("\n--- Cadastro de Computador ---");
        System.out.println("1. Notebook");
        System.out.println("2. Desktop");
        int tipo = lerInteiro("Selecione o tipo de computador: ");

        if (tipo != 1 && tipo != 2) {
            System.out.println(">>> Erro: Tipo inválido! Voltando ao menu principal.");
            return;
        }

        System.out.print("Digite o modelo do computador: ");
        String modelo = scanner.nextLine();

        int numeroSerie = lerInteiro("Digite o número de série (único): ");

        // Verifica se o número de série já existe
        if (buscarComputadorPorSerie(numeroSerie) != null) {
            System.out.println(">>> Erro: Já existe um computador com este número de série!");
            return;
        }

        double custoBase = lerDouble("Digite o custo base de montagem (R$): ");

        try {
            Computador novoComputador;
            if (tipo == 1) {
                novoComputador = new Notebook(modelo, numeroSerie, custoBase);
            } else {
                novoComputador = new Desktop(modelo, numeroSerie, custoBase);
            }
            esteiraProducao.add(novoComputador);
            System.out.println("\n[Sucesso] Computador cadastrado com sucesso na esteira!");
        } catch (ViolacaoOrcamentariaException e) {
            System.out.println("\n>>> " + e.getMessage());
            System.out.println(">>> Alerta: A máquina NÃO foi cadastrada devido a violação orçamentária.");
        }
    }

    private static void aplicarUpgrade() {
        System.out.println("\n--- Aplicar Upgrade na Esteira ---");
        if (esteiraProducao.isEmpty()) {
            System.out.println(">>> Nenhuma máquina cadastrada na esteira no momento.");
            return;
        }

        int numeroSerie = lerInteiro("Digite o número de série do equipamento: ");
        Computador computador = buscarComputadorPorSerie(numeroSerie);

        if (computador == null) {
            System.out.println(">>> Erro: Equipamento com o número de série " + numeroSerie + " não foi encontrado na esteira.");
            return;
        }

        System.out.println("\nEquipamento localizado:");
        System.out.println("Modelo: " + computador.getModelo());
        
        if (computador instanceof Notebook) {
            System.out.println("Upgrade disponível para Notebook: Bateria Estendida (R$ 250,00 cada)");
            int quantidade = lerInteiro("Digite a quantidade de módulos de bateria estendida a adicionar: ");
            if (quantidade <= 0) {
                System.out.println(">>> Quantidade inválida! O upgrade deve ser positivo.");
                return;
            }
            computador.receberUpgrade(quantidade);
            System.out.println("\n[Sucesso] Bateria(s) estendida(s) instalada(s)!");
        } else if (computador instanceof Desktop) {
            System.out.println("Upgrade disponível para Desktop: Pentes Extras de RAM (R$ 150,00 cada)");
            int quantidade = lerInteiro("Digite a quantidade de pentes de RAM extras a adicionar: ");
            if (quantidade <= 0) {
                System.out.println(">>> Quantidade inválida! O upgrade deve ser positivo.");
                return;
            }
            computador.receberUpgrade(quantidade);
            System.out.println("\n[Sucesso] Pente(s) de RAM extra(s) instalado(s)!");
        }
    }

    private static void imprimirRelatorioLote() {
        System.out.println("\n=============================================");
        System.out.println("         RELATÓRIO DO LOTE FABRICADO        ");
        System.out.println("=============================================");
        
        if (esteiraProducao.isEmpty()) {
            System.out.println("Nenhum computador cadastrado na esteira.");
            System.out.println("=============================================");
            return;
        }

        double custoTotalLote = 0.0;
        for (Computador comp : esteiraProducao) {
            System.out.println(comp);
            System.out.println("---------------------------------------------");
            custoTotalLote += comp.calcularCustoFinal();
        }

        System.out.printf("Custo Total do Lote: R$ %.2f\n", custoTotalLote);
        System.out.printf("Total de Máquinas no Lote: %d\n", esteiraProducao.size());
        System.out.println("=============================================");
    }

    private static Computador buscarComputadorPorSerie(int numeroSerie) {
        for (Computador comp : esteiraProducao) {
            if (comp.getNumeroSerie() == numeroSerie) {
                return comp;
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
