package fitmanagerpro.services;

import fitmanagerpro.exceptions.MatriculaInvalidaException;
import fitmanagerpro.exceptions.PlanoInvalidoException;
import fitmanagerpro.models.*;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Responsável pela interface de menu no console.
 * Centraliza toda a interação com o usuário, separando a lógica de apresentação
 * da lógica de negócio (que fica em AcademiaService).
 *
 * Requisito 7 — Tratamento de Exceções: blocos try-catch-finally em operações
 *               de leitura e em chamadas que podem lançar exceções customizadas.
 */
public class MenuService {

    private final AcademiaService academia;
    private final Scanner         scanner;

    // -----------------------------------------------------------------------
    // Construtor
    // -----------------------------------------------------------------------

    public MenuService(AcademiaService academia) {
        this.academia = academia;
        this.scanner  = new Scanner(System.in);
    }

    // -----------------------------------------------------------------------
    // Menu principal
    // -----------------------------------------------------------------------

    /**
     * Exibe o menu e processa a opção escolhida pelo usuário em loop.
     */
    public void iniciar() {
        int opcao = -1;

        do {
            exibirMenuPrincipal();

            try {
                opcao = scanner.nextInt();
                scanner.nextLine(); // consumir nova linha
            } catch (InputMismatchException e) {
                // Requisito 7 — try-catch para entrada inválida
                System.out.println("\n⚠ Entrada inválida! Digite um número inteiro.");
                scanner.nextLine();
                continue;
            }

            switch (opcao) {
                case 1  -> menuCadastrarAluno();
                case 2  -> menuCadastrarInstrutor();
                case 3  -> menuCriarPlano();
                case 4  -> menuRealizarMatricula();
                case 5  -> menuRegistrarPagamento();
                case 6  -> menuListarAlunos();
                case 7  -> menuListarInstrutores();
                case 8  -> menuListarMatriculas();
                case 9  -> academia.gerarRelatorioGeral();
                case 10 -> menuRelatorioAluno();
                case 0  -> System.out.println("\n👋 Saindo do FitManager Pro. Até logo!");
                default -> System.out.println("\n⚠ Opção inválida. Tente novamente.");
            }

        } while (opcao != 0);
    }

    // -----------------------------------------------------------------------
    // Exibição do menu
    // -----------------------------------------------------------------------

    private void exibirMenuPrincipal() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         FIT MANAGER PRO  🏋              ║");
        System.out.println("║      Sistema de Gestão de Academia      ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║  1  │ Cadastrar Aluno                  ║");
        System.out.println("║  2  │ Cadastrar Instrutor              ║");
        System.out.println("║  3  │ Criar Plano de Treinamento       ║");
        System.out.println("║  4  │ Realizar Matrícula               ║");
        System.out.println("║  5  │ Registrar Pagamento              ║");
        System.out.println("║─────────────────────────────────────── ║");
        System.out.println("║  6  │ Listar Alunos                   ║");
        System.out.println("║  7  │ Listar Instrutores               ║");
        System.out.println("║  8  │ Listar Matrículas                ║");
        System.out.println("║  9  │ Relatório Geral                  ║");
        System.out.println("║  10 │ Relatório por Aluno              ║");
        System.out.println("║─────────────────────────────────────── ║");
        System.out.println("║  0  │ Sair                             ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.print("Escolha uma opção: ");
    }

    // -----------------------------------------------------------------------
    // Sub-menus
    // -----------------------------------------------------------------------

    /** Coleta dados e cadastra um novo aluno. */
    private void menuCadastrarAluno() {
        System.out.println("\n─── CADASTRAR ALUNO ───");
        try {
            System.out.print("Nome     : ");
            String nome = scanner.nextLine().trim();

            System.out.print("CPF      : ");
            String cpf = scanner.nextLine().trim();

            System.out.print("Telefone : ");
            String telefone = scanner.nextLine().trim();

            System.out.print("Idade    : ");
            int idade = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Objetivo : ");
            String objetivo = scanner.nextLine().trim();

            Aluno aluno = new Aluno(nome, cpf, telefone, idade, objetivo);
            academia.cadastrarAluno(aluno);

        } catch (InputMismatchException e) {
            // Requisito 7 — captura de erro de conversão de tipo
            System.out.println("⚠ Idade inválida! Cadastro cancelado.");
            scanner.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println("⚠ Erro: " + e.getMessage());
        }
    }

    /** Coleta dados e cadastra um novo instrutor. */
    private void menuCadastrarInstrutor() {
        System.out.println("\n─── CADASTRAR INSTRUTOR ───");
        try {
            System.out.print("Nome          : ");
            String nome = scanner.nextLine().trim();

            System.out.print("CPF           : ");
            String cpf = scanner.nextLine().trim();

            System.out.print("Telefone      : ");
            String telefone = scanner.nextLine().trim();

            System.out.print("Idade         : ");
            int idade = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Especialidade : ");
            String especialidade = scanner.nextLine().trim();

            System.out.print("CREF          : ");
            String cref = scanner.nextLine().trim();

            Instrutor instrutor = new Instrutor(nome, cpf, telefone, idade, especialidade, cref);
            academia.cadastrarInstrutor(instrutor);

        } catch (InputMismatchException e) {
            System.out.println("⚠ Idade inválida! Cadastro cancelado.");
            scanner.nextLine();
        }
    }

    /** Cria um plano no catálogo da academia. */
    private void menuCriarPlano() {
        System.out.println("\n─── CRIAR PLANO ───");
        System.out.println("Tipos disponíveis: Básico | Premium | VIP");
        System.out.print("Tipo do plano: ");
        String tipo = scanner.nextLine().trim();

        try {
            // Requisito 7 — try-catch para PlanoInvalidoException (customizada)
            Plano plano = new Plano(tipo);
            academia.adicionarPlano(plano);
            System.out.println(plano);
        } catch (PlanoInvalidoException e) {
            System.out.println("⚠ " + e.getMessage());
        }
    }

    /** Realiza a matrícula de um aluno em um plano. */
    private void menuRealizarMatricula() {
        System.out.println("\n─── REALIZAR MATRÍCULA ───");

        menuListarAlunos();
        System.out.print("ID do aluno    : ");
        int idAluno = lerInteiro();
        Aluno aluno = academia.buscarAlunoPorId(idAluno);
        if (aluno == null) {
            System.out.println("⚠ Aluno não encontrado.");
            return;
        }

        menuListarPlanos();
        System.out.print("Nome do plano  : ");
        String nomePlano = scanner.nextLine().trim();
        Plano plano = academia.buscarPlanoPorNome(nomePlano);
        if (plano == null) {
            System.out.println("⚠ Plano não encontrado.");
            return;
        }

        Instrutor instrutor = null;
        List<Instrutor> instrutores = academia.listarInstrutores();
        if (!instrutores.isEmpty()) {
            menuListarInstrutores();
            System.out.print("ID do instrutor (0 = sem instrutor): ");
            int idInstrutor = lerInteiro();
            if (idInstrutor != 0) {
                instrutor = academia.buscarInstrutorPorId(idInstrutor);
                if (instrutor == null) {
                    System.out.println("⚠ Instrutor não encontrado. Prosseguindo sem instrutor.");
                }
            }
        }

        try {
            // Requisito 7 — try-catch para MatriculaInvalidaException (customizada)
            academia.realizarMatricula(aluno, plano, instrutor);
        } catch (MatriculaInvalidaException e) {
            System.out.println("⚠ " + e.getMessage());
        }
    }

    /** Registra o pagamento de uma matrícula. */
    private void menuRegistrarPagamento() {
        System.out.println("\n─── REGISTRAR PAGAMENTO ───");

        menuListarMatriculas();
        System.out.print("ID da matrícula : ");
        int idMatricula = lerInteiro();
        Matricula matricula = academia.buscarMatriculaPorId(idMatricula);
        if (matricula == null) {
            System.out.println("⚠ Matrícula não encontrada.");
            return;
        }

        System.out.printf("Valor do plano  : R$ %.2f%n", matricula.getPlano().getPreco());
        System.out.print("Valor pago      : R$ ");
        double valor = 0;
        try {
            valor = scanner.nextDouble();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            // Requisito 7 — captura erro de conversão
            System.out.println("⚠ Valor inválido.");
            scanner.nextLine();
            return;
        }

        System.out.println("Forma de pagamento:");
        System.out.println("  1 - Pix");
        System.out.println("  2 - Cartão");
        System.out.println("  3 - Dinheiro");
        System.out.print("Escolha: ");
        int opcaoForma = lerInteiro();
        String forma = switch (opcaoForma) {
            case 1 -> "Pix";
            case 2 -> "Cartão";
            case 3 -> "Dinheiro";
            default -> "Outro";
        };

        academia.registrarPagamento(matricula, valor, forma);
    }

    /** Lista todos os alunos cadastrados. */
    private void menuListarAlunos() {
        List<Aluno> alunos = academia.listarAlunos();
        if (alunos.isEmpty()) {
            System.out.println("\nNenhum aluno cadastrado.");
            return;
        }
        System.out.println("\n─── ALUNOS CADASTRADOS ───");
        for (Aluno a : alunos) {
            System.out.println(a);
        }
    }

    /** Lista todos os instrutores cadastrados. */
    private void menuListarInstrutores() {
        List<Instrutor> instrutores = academia.listarInstrutores();
        if (instrutores.isEmpty()) {
            System.out.println("\nNenhum instrutor cadastrado.");
            return;
        }
        System.out.println("\n─── INSTRUTORES CADASTRADOS ───");
        for (Instrutor i : instrutores) {
            System.out.println(i);
        }
    }

    /** Lista todos os planos do catálogo. */
    private void menuListarPlanos() {
        List<Plano> planos = academia.listarPlanos();
        if (planos.isEmpty()) {
            System.out.println("\nNenhum plano cadastrado.");
            return;
        }
        System.out.println("\n─── PLANOS DISPONÍVEIS ───");
        for (Plano p : planos) {
            System.out.println(p);
        }
    }

    /** Lista todas as matrículas registradas. */
    private void menuListarMatriculas() {
        List<Matricula> matriculas = academia.listarMatriculas();
        if (matriculas.isEmpty()) {
            System.out.println("\nNenhuma matrícula registrada.");
            return;
        }
        System.out.println("\n─── MATRÍCULAS REGISTRADAS ───");
        for (Matricula m : matriculas) {
            System.out.println(m);
        }
    }

    /** Solicita nome ou ID do aluno e exibe o relatório individual. */
    private void menuRelatorioAluno() {
        System.out.print("\nDigite o nome ou ID do aluno: ");
        String identificador = scanner.nextLine().trim();
        academia.gerarRelatorioItem(identificador);
    }

    // -----------------------------------------------------------------------
    // Utilitário de leitura segura
    // -----------------------------------------------------------------------

    /**
     * Lê um inteiro do console com tratamento de erro.
     * Requisito 7 — try-catch-finally para entrada de dados.
     *
     * @return inteiro lido ou -1 em caso de erro
     */
    private int lerInteiro() {
        int valor = -1;
        try {
            valor = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("⚠ Valor inválido. Usando padrão 0.");
        } finally {
            scanner.nextLine(); // Requisito 7 — finally: sempre limpa o buffer
        }
        return valor;
    }
}
