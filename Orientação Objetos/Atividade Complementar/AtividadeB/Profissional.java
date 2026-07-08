package AtividadeB;

public abstract class Profissional {
    private String nome;
    private int identificacao;
    private double salarioBase;
    private double bonusAcumulado;

    private static final double SALARIO_MAXIMO = 50000.0;

    public Profissional(String nome, int identificacao, double salarioBase) throws ViolacaoSalarialException {
        this.nome = nome;
        this.identificacao = identificacao;
        validarSalarioBase(salarioBase);
        this.salarioBase = salarioBase;
        this.bonusAcumulado = 0.0;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(int identificacao) {
        this.identificacao = identificacao;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) throws ViolacaoSalarialException {
        validarSalarioBase(salarioBase);
        this.salarioBase = salarioBase;
    }

    public double getBonusAcumulado() {
        return bonusAcumulado;
    }

    protected void adicionarBonus(double valor) {
        if (valor > 0) {
            this.bonusAcumulado += valor;
        }
    }

    private void validarSalarioBase(double salario) throws ViolacaoSalarialException {
        if (salario < 0) {
            throw new ViolacaoSalarialException("Erro: O salário base não pode ser negativo (recebido: R$ " + salario + ").");
        }
        if (salario > SALARIO_MAXIMO) {
            throw new ViolacaoSalarialException("Erro: O salário base (R$ " + salario + ") excede o teto salarial máximo permitido de R$ " + SALARIO_MAXIMO + ".");
        }
    }

    public abstract void registrarDesempenho(int quantidade);

    public double calcularPagamentoMensal() {
        return this.salarioBase + this.bonusAcumulado;
    }

    @Override
    public String toString() {
        return String.format(
            "Ficha Cadastral - Profissional\n" +
            "------------------------------\n" +
            "Nome: %s\n" +
            "ID Identificação: %d\n" +
            "Salário Base: R$ %.2f\n" +
            "Bônus Acumulado: R$ %.2f\n" +
            "Pagamento Mensal: R$ %.2f",
            nome, identificacao, salarioBase, bonusAcumulado, calcularPagamentoMensal()
        );
    }
}
