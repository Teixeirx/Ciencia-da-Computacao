package fitmanagerpro.services;

import fitmanagerpro.exceptions.MatriculaInvalidaException;
import fitmanagerpro.exceptions.PlanoInvalidoException;
import fitmanagerpro.interfaces.Relatorio;
import fitmanagerpro.models.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Serviço central da academia: armazena e gerencia todas as entidades do sistema.
 *
 * Requisito 5 — Polimorfismo de Inclusão: usa ArrayList<Pessoa> para tratar
 *               Alunos e Instrutores de forma uniforme pelo tipo da superclasse.
 * Requisito 6 — Implementa a interface Relatorio, cumprindo o contrato definido.
 */
public class AcademiaService implements Relatorio {

    // Requisito 5 — coleção polimórfica: armazena Aluno e Instrutor como Pessoa
    private final List<Pessoa>    pessoas;

    private final List<Plano>     planos;
    private final List<Matricula> matriculas;
    private final List<Pagamento> pagamentos;

    // -----------------------------------------------------------------------
    // Construtor
    // -----------------------------------------------------------------------

    public AcademiaService() {
        this.pessoas    = new ArrayList<>();
        this.planos     = new ArrayList<>();
        this.matriculas = new ArrayList<>();
        this.pagamentos = new ArrayList<>();
    }

    // -----------------------------------------------------------------------
    // Métodos de Aluno
    // -----------------------------------------------------------------------

    /**
     * Cadastra um aluno no sistema.
     *
     * @param aluno aluno a ser cadastrado
     */
    public void cadastrarAluno(Aluno aluno) {
        pessoas.add(aluno); // ArrayList<Pessoa> recebe Aluno — Requisito 5
        System.out.println("\n✔ Aluno \"" + aluno.getNome() + "\" cadastrado com sucesso!");
    }

    /**
     * Retorna lista de apenas os alunos cadastrados.
     *
     * @return lista de Aluno
     */
    public List<Aluno> listarAlunos() {
        List<Aluno> alunos = new ArrayList<>();
        for (Pessoa p : pessoas) {          // percorre ArrayList<Pessoa> — Req 5
            if (p instanceof Aluno) {
                alunos.add((Aluno) p);
            }
        }
        return alunos;
    }

    /**
     * Busca um aluno pelo ID.
     *
     * @param id identificador único do aluno
     * @return Aluno encontrado ou null
     */
    public Aluno buscarAlunoPorId(int id) {
        for (Aluno a : listarAlunos()) {
            if (a.getId() == id) return a;
        }
        return null;
    }

    // -----------------------------------------------------------------------
    // Métodos de Instrutor
    // -----------------------------------------------------------------------

    /**
     * Cadastra um instrutor no sistema.
     *
     * @param instrutor instrutor a ser cadastrado
     */
    public void cadastrarInstrutor(Instrutor instrutor) {
        pessoas.add(instrutor); // ArrayList<Pessoa> recebe Instrutor — Requisito 5
        System.out.println("\n✔ Instrutor \"" + instrutor.getNome() + "\" cadastrado com sucesso!");
    }

    /**
     * Retorna lista de apenas os instrutores cadastrados.
     *
     * @return lista de Instrutor
     */
    public List<Instrutor> listarInstrutores() {
        List<Instrutor> instrutores = new ArrayList<>();
        for (Pessoa p : pessoas) {
            if (p instanceof Instrutor) {
                instrutores.add((Instrutor) p);
            }
        }
        return instrutores;
    }

    /**
     * Busca um instrutor pelo ID.
     *
     * @param id identificador único do instrutor
     * @return Instrutor encontrado ou null
     */
    public Instrutor buscarInstrutorPorId(int id) {
        for (Instrutor i : listarInstrutores()) {
            if (i.getId() == id) return i;
        }
        return null;
    }

    // -----------------------------------------------------------------------
    // Métodos de Plano
    // -----------------------------------------------------------------------

    /**
     * Adiciona um plano ao catálogo da academia.
     *
     * @param plano plano a ser adicionado
     */
    public void adicionarPlano(Plano plano) {
        planos.add(plano);
        System.out.println("\n✔ Plano \"" + plano.getNome() + "\" adicionado ao catálogo!");
    }

    /**
     * Busca um plano pelo nome (Básico, Premium, VIP).
     *
     * @param nome nome do plano
     * @return Plano encontrado ou null
     */
    public Plano buscarPlanoPorNome(String nome) {
        for (Plano p : planos) {
            if (p.getNome().equalsIgnoreCase(nome)) return p;
        }
        return null;
    }

    public List<Plano> listarPlanos() { return planos; }

    // -----------------------------------------------------------------------
    // Métodos de Matrícula
    // -----------------------------------------------------------------------

    /**
     * Realiza a matrícula de um aluno em um plano.
     *
     * @param aluno      aluno a matricular
     * @param plano      plano escolhido
     * @param instrutor  instrutor responsável (pode ser null)
     * @throws MatriculaInvalidaException se o aluno já estiver matriculado no plano
     *                                    — Requisito 7 (exceção customizada)
     */
    public void realizarMatricula(Aluno aluno, Plano plano, Instrutor instrutor)
            throws MatriculaInvalidaException {

        // Verifica se o aluno já possui matrícula ativa neste plano
        for (Matricula m : matriculas) {
            if (m.getAluno().getId() == aluno.getId()
                    && m.getPlano().getNome().equals(plano.getNome())
                    && m.estaVigente()) {
                // Requisito 7 — lança exceção customizada
                throw new MatriculaInvalidaException(
                    "O aluno \"" + aluno.getNome() + "\" já possui matrícula ativa no plano " + plano.getNome() + ".");
            }
        }

        Matricula matricula = (instrutor != null)
                ? new Matricula(aluno, plano, instrutor)
                : new Matricula(aluno, plano);

        matriculas.add(matricula);
        aluno.setAtivo(true);
        System.out.println("\n✔ Matrícula realizada com sucesso! (ID: " + matricula.getId() + ")");
    }

    public List<Matricula> listarMatriculas() { return matriculas; }

    /**
     * Busca uma matrícula pelo ID.
     *
     * @param id ID da matrícula
     * @return Matricula encontrada ou null
     */
    public Matricula buscarMatriculaPorId(int id) {
        for (Matricula m : matriculas) {
            if (m.getId() == id) return m;
        }
        return null;
    }

    // -----------------------------------------------------------------------
    // Métodos de Pagamento
    // -----------------------------------------------------------------------

    /**
     * Registra um pagamento para uma matrícula.
     *
     * @param matricula      matrícula referente
     * @param valor          valor pago
     * @param formaPagamento forma de pagamento (Pix, Cartão, Dinheiro)
     */
    public void registrarPagamento(Matricula matricula, double valor, String formaPagamento) {
        Pagamento pagamento = new Pagamento(matricula, valor, formaPagamento);
        pagamentos.add(pagamento);
        System.out.println("\n✔ Pagamento registrado! (ID: " + pagamento.getId() + ")");
    }

    public List<Pagamento> listarPagamentos() { return pagamentos; }

    // -----------------------------------------------------------------------
    // Implementação da Interface Relatorio — Requisito 6
    // -----------------------------------------------------------------------

    /**
     * Gera e exibe o relatório geral da academia no console.
     * Requisito 6 — Implementação do método da interface Relatorio.
     */
    @Override
    public void gerarRelatorioGeral() {
        List<Aluno>    alunos     = listarAlunos();
        List<Instrutor> instrutores = listarInstrutores();

        long ativos       = alunos.stream().filter(Aluno::isAtivo).count();
        long inadimplentes = matriculas.stream().filter(m -> !m.isPagamentoEmDia()).count();
        double receitaEstimada = planos.stream().mapToDouble(Plano::getPreco).sum()
                * ativos / Math.max(planos.size(), 1);

        System.out.println("\n╔═══════════════════════════════════════════════════════════════════╗");
        System.out.println("║              RELATÓRIO GERAL — FIT MANAGER PRO                   ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════╣");
        System.out.printf ("║  Total de alunos cadastrados : %-35d║%n", alunos.size());
        System.out.printf ("║  Alunos ativos               : %-35d║%n", ativos);
        System.out.printf ("║  Total de instrutores        : %-35d║%n", instrutores.size());
        System.out.printf ("║  Total de matrículas         : %-35d║%n", matriculas.size());
        System.out.printf ("║  Matrículas com pgto em atraso: %-34d║%n", inadimplentes);
        System.out.printf ("║  Planos no catálogo          : %-35d║%n", planos.size());
        System.out.printf ("║  Receita mensal estimada     : R$ %-32s║%n",
                String.format("%.2f", calcularReceitaMensal()));
        System.out.println("╚═══════════════════════════════════════════════════════════════════╝");
    }

    /**
     * Gera relatório de um aluno específico por nome ou ID.
     * Requisito 6 — Implementação do método da interface Relatorio.
     */
    @Override
    public void gerarRelatorioItem(String identificador) {
        System.out.println("\n─── Matrícula(s) do aluno: " + identificador + " ───");
        boolean encontrou = false;
        for (Matricula m : matriculas) {
            if (m.getAluno().getNome().equalsIgnoreCase(identificador)
                    || String.valueOf(m.getAluno().getId()).equals(identificador)) {
                m.exibirInfo();
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Nenhuma matrícula encontrada para: " + identificador);
        }
    }

    // -----------------------------------------------------------------------
    // Utilitários
    // -----------------------------------------------------------------------

    /**
     * Calcula a receita mensal com base nas matrículas vigentes e com pagamento em dia.
     *
     * @return soma dos preços dos planos das matrículas vigentes e pagas
     */
    public double calcularReceitaMensal() {
        double total = 0;
        for (Matricula m : matriculas) {
            if (m.estaVigente() && m.isPagamentoEmDia()) {
                total += m.getPlano().getPreco();
            }
        }
        return total;
    }

    /**
     * Exibe todas as pessoas do sistema usando polimorfismo.
     * Requisito 5 — chama exibirInfo() sobre ArrayList<Pessoa>; Java decide em
     *               tempo de execução qual implementação usar (Aluno ou Instrutor).
     */
    public void exibirTodasAsPessoas() {
        if (pessoas.isEmpty()) {
            System.out.println("Nenhuma pessoa cadastrada.");
            return;
        }
        for (Pessoa p : pessoas) {
            p.exibirInfo(); // despacho polimórfico — Requisito 5
            System.out.println();
        }
    }
}
