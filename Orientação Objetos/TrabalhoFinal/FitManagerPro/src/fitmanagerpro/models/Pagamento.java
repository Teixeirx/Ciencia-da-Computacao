package fitmanagerpro.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Representa o registro de um pagamento realizado por um aluno.
 * Requisito 1 — Entidade com atributos bem definidos para controle financeiro.
 * Requisito 3 — Encapsulamento: atributos privados com getters/setters.
 */
public class Pagamento {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Requisito 3 — atributos privados
    private int       id;
    private Matricula matricula;
    private double    valor;
    private LocalDate dataPagamento;
    private String    formaPagamento; // Pix, Cartão, Dinheiro

    private static int contadorId = 1;

    // -----------------------------------------------------------------------
    // Construtor
    // -----------------------------------------------------------------------

    /**
     * Cria um registro de pagamento.
     *
     * @param matricula      matrícula referente ao pagamento
     * @param valor          valor pago em reais
     * @param formaPagamento forma de pagamento utilizada
     */
    public Pagamento(Matricula matricula, double valor, String formaPagamento) {
        this.id             = contadorId++;
        this.matricula      = matricula;
        this.valor          = valor;
        this.dataPagamento  = LocalDate.now();
        this.formaPagamento = formaPagamento;

        // Marca o pagamento da matrícula como em dia ao registrar
        matricula.setPagamentoEmDia(true);
    }

    // -----------------------------------------------------------------------
    // Métodos de negócio
    // -----------------------------------------------------------------------

    /**
     * Exibe os detalhes do pagamento no console.
     */
    public void exibirInfo() {
        System.out.println("┌──────────────────────────── PAGAMENTO ─────────────────────────┐");
        System.out.printf ("│ Pagamento Nº %-4d                                               │%n", id);
        System.out.printf ("│ Aluno        : %-46s│%n", matricula.getAluno().getNome());
        System.out.printf ("│ Matrícula Nº : %-46s│%n", matricula.getId());
        System.out.printf ("│ Valor        : R$ %-43s│%n", String.format("%.2f", valor));
        System.out.printf ("│ Forma        : %-46s│%n", formaPagamento);
        System.out.printf ("│ Data         : %-46s│%n", dataPagamento.format(FORMATTER));
        System.out.println("└─────────────────────────────────────────────────────────────────┘");
    }

    // -----------------------------------------------------------------------
    // Getters e Setters — Requisito 3 (Encapsulamento)
    // -----------------------------------------------------------------------

    public int getId() { return id; }

    public Matricula getMatricula() { return matricula; }

    public double getValor() { return valor; }

    public void setValor(double valor) { this.valor = valor; }

    public LocalDate getDataPagamento() { return dataPagamento; }

    public String getFormaPagamento() { return formaPagamento; }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    // -----------------------------------------------------------------------
    // toString
    // -----------------------------------------------------------------------

    @Override
    public String toString() {
        return String.format(
                "Pagamento #%-3d | Aluno: %-20s | R$ %7.2f | %s | %s",
                id,
                matricula.getAluno().getNome(),
                valor,
                formaPagamento,
                dataPagamento.format(FORMATTER));
    }
}
