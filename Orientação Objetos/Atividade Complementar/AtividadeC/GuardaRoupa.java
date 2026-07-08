package AtividadeC;

public class GuardaRoupa extends Movel {
    private int portasComEspelho;
    private static final double CUSTO_PORTA_ESPELHO = 400.0;

    public GuardaRoupa(String modelo, int codigoIdentificacao, double custoBaseFabricacao) throws ViolacaoOrcamentariaMovelException {
        super(modelo, codigoIdentificacao, custoBaseFabricacao);
        this.portasComEspelho = 0;
    }

    public int getPortasComEspelho() {
        return portasComEspelho;
    }

    @Override
    public void receberUpgrade(int quantidade) {
        if (quantidade > 0) {
            this.portasComEspelho += quantidade;
            double custoAdicional = quantidade * CUSTO_PORTA_ESPELHO;
            adicionarCustoAcabamento(custoAdicional);
        }
    }

    @Override
    public String toString() {
        return super.toString() + String.format(
            "\nTipo: Guarda-Roupa\n" +
            "Portas com Espelho: %d (Custo: R$ %.2f por porta)",
            portasComEspelho, CUSTO_PORTA_ESPELHO
        );
    }
}
