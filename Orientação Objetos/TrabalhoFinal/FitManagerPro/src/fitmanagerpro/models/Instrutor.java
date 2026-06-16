package fitmanagerpro.models;

/**
 * Representa um instrutor da academia.
 * Requisito 4 — Herança: Instrutor É UMA Pessoa (extends Pessoa).
 * Requisito 5 — Polimorfismo: sobrescreve toString() com informações específicas.
 */
public class Instrutor extends Pessoa {

    // Requisito 3 — atributos específicos de Instrutor, privados
    private int    id;
    private String especialidade;  // Ex.: Musculação, CrossFit, Pilates
    private String cref;           // Registro profissional

    // Contador estático para geração de IDs únicos
    private static int contadorId = 1;

    // -----------------------------------------------------------------------
    // Construtores
    // -----------------------------------------------------------------------

    /**
     * Construtor padrão.
     */
    public Instrutor() {
        super();
        this.id            = contadorId++;
        this.especialidade = "Não informada";
        this.cref          = "Não informado";
    }

    /**
     * Construtor parametrizado completo.
     *
     * @param nome          nome completo do instrutor
     * @param cpf           CPF do instrutor
     * @param telefone      telefone para contato
     * @param idade         idade do instrutor
     * @param especialidade área de especialização
     * @param cref          número de registro no CREF
     */
    public Instrutor(String nome, String cpf, String telefone, int idade,
                     String especialidade, String cref) {
        super(nome, cpf, telefone, idade); // reaproveitamento — Requisito 4
        this.id            = contadorId++;
        this.especialidade = especialidade;
        this.cref          = cref;
    }

    // -----------------------------------------------------------------------
    // Getters e Setters — Requisito 3 (Encapsulamento)
    // -----------------------------------------------------------------------

    public int getId() {
        return id;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getCref() {
        return cref;
    }

    public void setCref(String cref) {
        this.cref = cref;
    }

    // -----------------------------------------------------------------------
    // toString
    // -----------------------------------------------------------------------

    @Override
    public String toString() {
        return String.format("ID: %-3d | %s | Especialidade: %s | CREF: %s",
                id, super.toString(), especialidade, cref);
    }
}
