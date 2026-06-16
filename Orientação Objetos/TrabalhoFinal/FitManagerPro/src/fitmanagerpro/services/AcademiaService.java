package fitmanagerpro.services;

import fitmanagerpro.exceptions.MatriculaInvalidaException;
import fitmanagerpro.exceptions.PlanoInvalidoException;
import fitmanagerpro.models.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Serviço central da academia: armazena e gerencia todas as entidades do sistema.
 *
 * Requisito 5 — Polimorfismo de Inclusão: usa ArrayList<Pessoa> para tratar
 *               Alunos e Instrutores de forma uniforme pelo tipo da superclasse.
 */
public class AcademiaService {

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
    }

    public List<Pagamento> listarPagamentos() { return pagamentos; }

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
}
