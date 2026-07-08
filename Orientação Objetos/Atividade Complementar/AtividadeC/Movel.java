package AtividadeC;

public abstract class Movel {
    private String modelo;
    private int codigoIdentificacao;
    private double custoBaseFabricacao;
    private double custoAcabamento;

    private static final double LIMITE_MAXIMO = 10000.0;

    public Movel(String modelo, int codigoIdentificacao, double custoBaseFabricacao) throws ViolacaoOrcamentariaMovelException {
        this.modelo = modelo;
        this.codigoIdentificacao = codigoIdentificacao;
        validarCustoBase(custoBaseFabricacao);
        this.custoBaseFabricacao = custoBaseFabricacao;
        this.custoAcabamento = 0.0;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getCodigoIdentificacao() {
        return codigoIdentificacao;
    }

    public void setCodigoIdentificacao(int codigoIdentificacao) {
        this.codigoIdentificacao = codigoIdentificacao;
    }

    public double getCustoBaseFabricacao() {
        return custoBaseFabricacao;
    }

    public void setCustoBaseFabricacao(double custoBaseFabricacao) throws ViolacaoOrcamentariaMovelException {
        validarCustoBase(custoBaseFabricacao);
        this.custoBaseFabricacao = custoBaseFabricacao;
    }

    public double getCustoAcabamento() {
        return custoAcabamento;
    }

    protected void adicionarCustoAcabamento(double valor) {
        if (valor > 0) {
            this.custoAcabamento += valor;
        }
    }

    private void validarCustoBase(double custo) throws ViolacaoOrcamentariaMovelException {
        if (custo < 0) {
            throw new ViolacaoOrcamentariaMovelException("Erro: O custo base de fabricação não pode ser negativo (recebido: R$ " + custo + ").");
        }
        if (custo > LIMITE_MAXIMO) {
            throw new ViolacaoOrcamentariaMovelException("Erro: O custo base de fabricação (R$ " + custo + ") excede o limite orçamentário máximo de R$ " + LIMITE_MAXIMO + " por peça.");
        }
    }

    public abstract void receberUpgrade(int quantidade);

    public double calcularCustoFinal() {
        return this.custoBaseFabricacao + this.custoAcabamento;
    }

    @Override
    public String toString() {
        return String.format(
            "Ficha Técnica - Móvel Planejado\n" +
            "-------------------------------\n" +
            "Modelo: %s\n" +
            "Código de Identificação: %d\n" +
            "Custo Base de Fabricação: R$ %.2f\n" +
            "Custos de Acabamento: R$ %.2f\n" +
            "Custo Final: R$ %.2f",
            modelo, codigoIdentificacao, custoBaseFabricacao, custoAcabamento, calcularCustoFinal()
        );
    }
}
