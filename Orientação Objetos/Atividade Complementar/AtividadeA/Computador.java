package AtividadeA;

public abstract class Computador {
    private String modelo;
    private int numeroSerie;
    private double custoBase;
    private double custosAdicionais;

    private static final double LIMITE_MAXIMO = 15000.0;

    public Computador(String modelo, int numeroSerie, double custoBase) throws ViolacaoOrcamentariaException {
        this.modelo = modelo;
        this.numeroSerie = numeroSerie;
        validarCustoBase(custoBase);
        this.custoBase = custoBase;
        this.custosAdicionais = 0.0;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(int numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public double getCustoBase() {
        return custoBase;
    }

    public void setCustoBase(double custoBase) throws ViolacaoOrcamentariaException {
        validarCustoBase(custoBase);
        this.custoBase = custoBase;
    }

    public double getCustosAdicionais() {
        return custosAdicionais;
    }

    protected void adicionarCustosAdicionais(double valor) {
        if (valor > 0) {
            this.custosAdicionais += valor;
        }
    }

    private void validarCustoBase(double custo) throws ViolacaoOrcamentariaException {
        if (custo < 0) {
            throw new ViolacaoOrcamentariaException("Erro: O custo base não pode ser negativo (recebido: R$ " + custo + ").");
        }
        if (custo > LIMITE_MAXIMO) {
            throw new ViolacaoOrcamentariaException("Erro: O custo base (R$ " + custo + ") excede o limite orçamentário máximo de R$ " + LIMITE_MAXIMO + ".");
        }
    }

    public abstract void receberUpgrade(int quantidade);

    public double calcularCustoFinal() {
        return this.custoBase + this.custosAdicionais;
    }

    @Override
    public String toString() {
        return String.format(
            "Ficha Técnica - Computador\n" +
            "---------------------------\n" +
            "Modelo: %s\n" +
            "Número de Série: %d\n" +
            "Custo Base: R$ %.2f\n" +
            "Custos Adicionais: R$ %.2f\n" +
            "Custo Final: R$ %.2f",
            modelo, numeroSerie, custoBase, custosAdicionais, calcularCustoFinal()
        );
    }
}
