package fitmanagerpro.models;

/**
 * Classe ABSTRATA que representa uma pessoa no sistema.
 * Requisito 4 — Base da hierarquia de herança: Aluno e Instrutor estendem esta classe.
 * Requisito 6 — Classe abstrata: não pode ser instanciada diretamente.
 */
public abstract class Pessoa {

    // Requisito 3 — Encapsulamento: atributos privados protegidos por getters/setters
    private String nome;
    private String cpf;
    private String telefone;
    private int    idade;

    // -----------------------------------------------------------------------
    // Construtores
    // -----------------------------------------------------------------------

    /**
     * Construtor padrão (sem parâmetros).
     * Requisito 2 — Construtor padrão na superclasse.
     */
    public Pessoa() {
        this.nome     = "Não informado";
        this.cpf      = "000.000.000-00";
        this.telefone = "Não informado";
        this.idade    = 0;
    }

    /**
     * Construtor parametrizado.
     * Requisito 2 — Construtor parametrizado na superclasse.
     *
     * @param nome     nome completo da pessoa
     * @param cpf      CPF no formato 000.000.000-00
     * @param telefone número de telefone
     * @param idade    idade em anos
     */
    public Pessoa(String nome, String cpf, String telefone, int idade) {
        this.nome     = nome;
        this.cpf      = cpf;
        this.telefone = telefone;
        this.idade    = idade;
    }


    // Getters e Setters — Requisito 3 (Encapsulamento)
    // -----------------------------------------------------------------------

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome não pode ser vazio.");
        }
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        if (idade < 0 || idade > 120) {
            throw new IllegalArgumentException("Idade inválida: " + idade);
        }
        this.idade = idade;
    }

    // -----------------------------------------------------------------------
    // toString — representação textual padrão
    // -----------------------------------------------------------------------

    @Override
    public String toString() {
        return String.format("Nome: %-25s | CPF: %-14s | Tel: %-15s | Idade: %d",
                nome, cpf, telefone, idade);
    }
}
