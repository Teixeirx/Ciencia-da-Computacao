package AtividadeB;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainB {
    private static final List<Profissional> quadroFuncionarios = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao = -1;

        while (opcao != 4) {
            exibirMenu();
            opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    cadastrarProfissional();
                    break;
                case 2:
                    registrarDesempenho();
                    break;
                case 3:
                    imprimirFolhaPagamento();
                    break;
                case 4:
                    System.out.println("\n[Saindo] Sistema de Gestão de RH Esportivo encerrado. Até mais!");
                    break;
                default:
                    System.out.println("\n>>> Opção inválida! Escolha um número entre 1 e 4.");
            }
        }
    }

    private static void exibirMenu() {
        System.out.println("\n=============================================");
        System.out.println("          Gestão de RH - Clube Esportivo     ");
        System.out.println("=============================================");
        System.out.println("1. Cadastrar Novo Profissional");
        System.out.println("2. Registrar Desempenho (Gols/Vitórias)");
        System.out.println("3. Imprimir Folha de Pagamento Completa");
        System.out.println("4. Sair");
        System.out.println("=============================================");
    }

    private static void cadastrarProfissional() {
        System.out.println("\n--- Cadastro de Profissional ---");
        System.out.println("1. Atleta");
        System.out.println("2. Membro da Comissão Técnica");
        int cargo = lerInteiro("Selecione o cargo: ");

        if (cargo != 1 && cargo != 2) {
            System.out.println(">>> Erro: Opção de cargo inválida! Voltando ao menu principal.");
            return;
        }

        System.out.print("Digite o nome do profissional: ");
        String nome = scanner.nextLine();

        int identificacao = lerInteiro("Digite o número de ID de identificação (único): ");

        if (buscarProfissionalPorId(identificacao) != null) {
            System.out.println(">>> Erro: Já existe um profissional cadastrado com este ID!");
            return;
        }

        double salarioBase = lerDouble("Digite o salário base do profissional (R$): ");

        try {
            Profissional novoProfissional;
            if (cargo == 1) {
                novoProfissional = new Atleta(nome, identificacao, salarioBase);
            } else {
                novoProfissional = new MembroComissaoTecnica(nome, identificacao, salarioBase);
            }
            quadroFuncionarios.add(novoProfissional);
            System.out.println("\n[Sucesso] Profissional cadastrado com sucesso no clube!");
        } catch (ViolacaoSalarialException e) {
            System.out.println("\n>>> " + e.getMessage());
            System.out.println(">>> Alerta: O profissional NÃO foi cadastrado devido a violação de teto salarial.");
        }
    }

    private static void registrarDesempenho() {
        System.out.println("\n--- Registrar Desempenho no Mês ---");
        if (quadroFuncionarios.isEmpty()) {
            System.out.println(">>> Nenhum profissional cadastrado no clube.");
            return;
        }

        int identificacao = lerInteiro("Digite o ID de identificação do profissional: ");
        Profissional profissional = buscarProfissionalPorId(identificacao);

        if (profissional == null) {
            System.out.println(">>> Erro: Profissional com ID " + identificacao + " não foi localizado.");
            return;
        }

        System.out.println("\nProfissional localizado:");
        System.out.println("Nome: " + profissional.getNome());

        if (profissional instanceof Atleta) {
            System.out.println("Desempenho de Atleta: gols marcados (R$ 1.000,00 de bônus por gol)");
            int gols = lerInteiro("Digite a quantidade de gols marcados nesta rodada: ");
            if (gols <= 0) {
                System.out.println(">>> Quantidade inválida! A quantidade de gols deve ser maior que zero.");
                return;
            }
            profissional.registrarDesempenho(gols);
            System.out.println("\n[Sucesso] Gols registrados. Bônus creditado com sucesso!");
        } else if (profissional instanceof MembroComissaoTecnica) {
            System.out.println("Desempenho da Comissão Técnica: vitórias conquistadas (R$ 2.500,00 de bônus por vitória)");
            int vitorias = lerInteiro("Digite a quantidade de vitórias conquistadas: ");
            if (vitorias <= 0) {
                System.out.println(">>> Quantidade inválida! A quantidade de vitórias deve ser maior que zero.");
                return;
            }
            profissional.registrarDesempenho(vitorias);
            System.out.println("\n[Sucesso] Vitórias registradas. Bônus creditado com sucesso!");
        }
    }

    private static void imprimirFolhaPagamento() {
        System.out.println("\n=============================================");
        System.out.println("          FOLHA DE PAGAMENTO DO CLUBE        ");
        System.out.println("=============================================");

        if (quadroFuncionarios.isEmpty()) {
            System.out.println("Nenhum profissional cadastrado no quadro de funcionários.");
            System.out.println("=============================================");
            return;
        }

        double custoTotalFolha = 0.0;
        for (Profissional prof : quadroFuncionarios) {
            System.out.println(prof);
            System.out.println("---------------------------------------------");
            custoTotalFolha += prof.calcularPagamentoMensal();
        }

        System.out.printf("Custo Total da Folha Mensal: R$ %.2f\n", custoTotalFolha);
        System.out.printf("Total de Profissionais Ativos: %d\n", quadroFuncionarios.size());
        System.out.println("=============================================");
    }

    private static Profissional buscarProfissionalPorId(int identificacao) {
        for (Profissional prof : quadroFuncionarios) {
            if (prof.getIdentificacao() == identificacao) {
                return prof;
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
