package fitmanagerpro.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Representa a matrícula de um Aluno em um Plano de treinamento.
 * Requisito 1 — Entidade que relaciona Aluno e Plano, com atributos e comportamentos próprios.
 * Requisito 3 — Encapsulamento: atributos privados com getters/setters.
 */
public class Matricula {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Requisito 3 — atributos privados
    private int        id;
    private Aluno      aluno;
    private Plano      plano;
    private Instrutor  instrutor;     // instrutor responsável (pode ser null)
    private LocalDate  dataInicio;
    private LocalDate  dataVencimento;
    private boolean    pagamentoEmDia;

    private static int contadorId = 1;

    // -----------------------------------------------------------------------
    // Construtores
    // -----------------------------------------------------------------------

    /**
     * Cria uma matrícula associando aluno e plano a partir da data atual.
     *
     * @param aluno aluno a ser matriculado
     * @param plano plano escolhido
     */
    public Matricula(Aluno aluno, Plano plano) {
        this.id             = contadorId++;
        this.aluno          = aluno;
        this.plano          = plano;
        this.instrutor      = null;
        this.dataInicio     = LocalDate.now();
        this.dataVencimento = dataInicio.plusMonths(plano.getDuracaoMeses());
        this.pagamentoEmDia = true;
    }

    /**
     * Cria uma matrícula com instrutor responsável.
     *
     * @param aluno     aluno a ser matriculado
     * @param plano     plano escolhido
     * @param instrutor instrutor responsável pelo aluno
     */
    public Matricula(Aluno aluno, Plano plano, Instrutor instrutor) {
        this(aluno, plano); // reutiliza o outro construtor
        this.instrutor = instrutor;
    }

    // -----------------------------------------------------------------------
    // Métodos de negócio
    // -----------------------------------------------------------------------

    /**
     * Verifica se a matrícula está dentro do prazo de validade.
     *
     * @return true se não venceu, false caso contrário
     */
    public boolean estaVigente() {
        return !LocalDate.now().isAfter(dataVencimento);
    }

    // -----------------------------------------------------------------------
    // Getters e Setters — Requisito 3 (Encapsulamento)
    // -----------------------------------------------------------------------

    public int getId() { return id; }

    public Aluno getAluno() { return aluno; }

    public Plano getPlano() { return plano; }

    public Instrutor getInstrutor() { return instrutor; }

    public void setInstrutor(Instrutor instrutor) { this.instrutor = instrutor; }

    public LocalDate getDataInicio() { return dataInicio; }

    public LocalDate getDataVencimento() { return dataVencimento; }

    public boolean isPagamentoEmDia() { return pagamentoEmDia; }

    public void setPagamentoEmDia(boolean pagamentoEmDia) {
        this.pagamentoEmDia = pagamentoEmDia;
    }

    // -----------------------------------------------------------------------
    // toString
    // -----------------------------------------------------------------------

    @Override
    public String toString() {
        return String.format(
                "Matrícula #%-3d | Aluno: %-20s | Plano: %-8s | Vencimento: %s | Pgto: %s",
                id,
                aluno.getNome(),
                plano.getNome(),
                dataVencimento.format(FORMATTER),
                pagamentoEmDia ? "Em dia" : "ATRASO");
    }
}
