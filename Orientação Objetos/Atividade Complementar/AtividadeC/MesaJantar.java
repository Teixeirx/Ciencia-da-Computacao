package AtividadeC;

public class MesaJantar extends Movel {
    private int demaosVernizExtras;
    private static final double CUSTO_DEMAO_VERNIZ = 80.0;

    public MesaJantar(String modelo, int codigoIdentificacao, double custoBaseFabricacao) throws ViolacaoOrcamentariaMovelException {
        super(modelo, codigoIdentificacao, custoBaseFabricacao);
        this.demaosVernizExtras = 0;
    }

    public int getDemaosVernizExtras() {
        return demaosVernizExtras;
    }

    @Override
    public void receberUpgrade(int quantidade) {
        if (quantidade > 0) {
            this.demaosVernizExtras += quantidade;
            double custoAdicional = quantidade * CUSTO_DEMAO_VERNIZ;
            adicionarCustoAcabamento(custoAdicional);
        }
    }

    @Override
    public String toString() {
        return super.toString() + String.format(
            "\nTipo: Mesa de Jantar\n" +
            "Demãos Extras de Verniz: %d (Custo: R$ %.2f por demão)",
            demaosVernizExtras, CUSTO_DEMAO_VERNIZ
        );
    }
}
