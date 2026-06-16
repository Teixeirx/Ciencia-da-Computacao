package fitmanagerpro.models;

import fitmanagerpro.exceptions.PlanoInvalidoException;

/**
 * Representa um plano de treinamento oferecido pela academia.
 * Requisito 1 — Entidade bem definida com atributos coerentes com o domínio.
 * Requisito 2 — Demonstra sobrecarga de construtores (três formas de criar um Plano).
 * Requisito 3 — Atributos encapsulados com getters/setters e validação de negócio.
 */
public class Plano {

    // Tipos de plano aceitos pelo sistema
    public static final String BASICO   = "Básico";
    public static final String PREMIUM  = "Premium";
    public static final String VIP      = "VIP";

    // Requisito 3 — atributos privados
    private String nome;
    private String descricao;
    private double preco;
    private int    duracaoMeses;

    // -----------------------------------------------------------------------
    // Construtores — Requisito 2 (sobrecarga)
    // -----------------------------------------------------------------------

    /**
     * Construtor padrão: cria um plano Básico com configurações pré-definidas.
     */
    public Plano() {
        this.nome         = BASICO;
        this.descricao    = "Acesso à musculação e área cardio.";
        this.preco        = 80.00;
        this.duracaoMeses = 1;
    }

    /**
     * Construtor com nome do tipo de plano — usa valores padrão do tipo escolhido.
     * Requisito 2 — Sobrecarga de construtor.
     *
     * @param nome tipo do plano (Básico, Premium ou VIP)
     * @throws PlanoInvalidoException se o tipo informado não existir
     */
    public Plano(String nome) throws PlanoInvalidoException {
        switch (nome) {
            case BASICO:
                this.nome         = BASICO;
                this.descricao    = "Acesso à musculação e área cardio.";
                this.preco        = 80.00;
                this.duracaoMeses = 1;
                break;
            case PREMIUM:
                this.nome         = PREMIUM;
                this.descricao    = "Musculação + aulas coletivas + avaliação física mensal.";
                this.preco        = 150.00;
                this.duracaoMeses = 1;
                break;
            case VIP:
                this.nome         = VIP;
                this.descricao    = "Acesso total + personal trainer + nutricionista.";
                this.preco        = 280.00;
                this.duracaoMeses = 1;
                break;
            default:
                // Requisito 7 — lança exceção customizada para tipo inválido
                throw new PlanoInvalidoException(
                    "Tipo de plano inválido: \"" + nome + "\". Use: Básico, Premium ou VIP.");
        }
    }

    /**
     * Construtor completo com todos os parâmetros.
     * Requisito 2 — Sobrecarga de construtor.
     *
     * @param nome         nome do plano
     * @param descricao    descrição dos benefícios
     * @param preco        valor mensal em reais
     * @param duracaoMeses duração do contrato em meses
     * @throws PlanoInvalidoException se o preço for negativo ou duração inválida
     */
    public Plano(String nome, String descricao, double preco, int duracaoMeses)
            throws PlanoInvalidoException {
        if (preco < 0) {
            throw new PlanoInvalidoException("O preço do plano não pode ser negativo.");
        }
        if (duracaoMeses <= 0) {
            throw new PlanoInvalidoException("A duração do plano deve ser de pelo menos 1 mês.");
        }
        this.nome         = nome;
        this.descricao    = descricao;
        this.preco        = preco;
        this.duracaoMeses = duracaoMeses;
    }

    // -----------------------------------------------------------------------
    // Getters e Setters — Requisito 3 (Encapsulamento)
    // -----------------------------------------------------------------------

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) throws PlanoInvalidoException {
        if (preco < 0) {
            throw new PlanoInvalidoException("O preço do plano não pode ser negativo.");
        }
        this.preco = preco;
    }

    public int getDuracaoMeses() {
        return duracaoMeses;
    }

    public void setDuracaoMeses(int duracaoMeses) {
        this.duracaoMeses = duracaoMeses;
    }

    // -----------------------------------------------------------------------
    // toString
    // -----------------------------------------------------------------------

    @Override
    public String toString() {
        return String.format("Plano: %-8s | R$ %7.2f/mês | Duração: %d mês(es) | %s",
                nome, preco, duracaoMeses, descricao);
    }
}
