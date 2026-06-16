package fitmanagerpro.models;

/**
 * Representa um aluno da academia.
 * Requisito 4 — Herança: Aluno É UMA Pessoa (extends Pessoa).
 * Requisito 5 — Polimorfismo: sobrescreve exibirInfo() da superclasse.
 * Requisito 2 — Sobrecarga de construtores: construtor padrão e parametrizado.
 */
public class Aluno extends Pessoa {

    // Requisito 3 — atributos específicos de Aluno, privados
    private int    id;
    private String objetivo;    // Ex.: ganho de massa, emagrecimento
    private boolean ativo;      // indica se a matrícula está vigente

    // Contador estático para geração de IDs únicos
    private static int contadorId = 1;

    // -----------------------------------------------------------------------
    // Construtores — Requisito 2 (sobrecarga)
    // -----------------------------------------------------------------------

    /**
     * Construtor padrão: cria um aluno com valores genéricos.
     * Requisito 2 — Construtor padrão.
     */
    public Aluno() {
        super(); // chama o construtor padrão de Pessoa
        this.id       = contadorId++;
        this.objetivo = "Não informado";
        this.ativo    = true;
    }

    /**
     * Construtor parametrizado completo.
     * Requisito 2 — Construtor parametrizado (sobrecarga junto com o padrão).
     *
     * @param nome     nome completo do aluno
     * @param cpf      CPF do aluno
     * @param telefone telefone para contato
     * @param idade    idade do aluno
     * @param objetivo objetivo de treinamento
     */
    public Aluno(String nome, String cpf, String telefone, int idade, String objetivo) {
        super(nome, cpf, telefone, idade); // reaproveitamento — Requisito 4
        this.id       = contadorId++;
        this.objetivo = objetivo;
        this.ativo    = true;
    }

    // -----------------------------------------------------------------------
    // Método abstrato implementado — Requisito 5 (Polimorfismo / Override)
    // -----------------------------------------------------------------------

    /**
     * Exibe as informações detalhadas do aluno no console.
     * Requisito 5 — Sobrescrita (@Override) do método abstrato de Pessoa.
     */
    @Override
    public void exibirInfo() {
        System.out.println("┌─────────────────────────── ALUNO ──────────────────────────────┐");
        System.out.printf ("│ ID: %-3d                                                         │%n", id);
        System.out.printf ("│ %s%n", toString());
        System.out.printf ("│ Objetivo : %-50s│%n", objetivo);
        System.out.printf ("│ Status   : %-50s│%n", ativo ? "Ativo" : "Inativo");
        System.out.println("└─────────────────────────────────────────────────────────────────┘");
    }

    // -----------------------------------------------------------------------
    // Getters e Setters — Requisito 3 (Encapsulamento)
    // -----------------------------------------------------------------------

    public int getId() {
        return id;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    // -----------------------------------------------------------------------
    // toString
    // -----------------------------------------------------------------------

    @Override
    public String toString() {
        return String.format("ID: %-3d | %s | Objetivo: %s | Status: %s",
                id, super.toString(), objetivo, ativo ? "Ativo" : "Inativo");
    }
}
