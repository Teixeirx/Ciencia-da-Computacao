package AtividadeA;

public class Notebook extends Computador {
    private int quantidadeBateriasExtras;
    private static final double CUSTO_BATERIA_EXTRA = 250.0;

    public Notebook(String modelo, int numeroSerie, double custoBase) throws ViolacaoOrcamentariaException {
        super(modelo, numeroSerie, custoBase);
        this.quantidadeBateriasExtras = 0;
    }

    public int getQuantidadeBateriasExtras() {
        return quantidadeBateriasExtras;
    }

    @Override
    public void receberUpgrade(int quantidade) {
        if (quantidade > 0) {
            this.quantidadeBateriasExtras += quantidade;
            double custoAdicional = quantidade * CUSTO_BATERIA_EXTRA;
            adicionarCustosAdicionais(custoAdicional);
        }
    }

    @Override
    public String toString() {
        return super.toString() + String.format(
            "\nTipo: Notebook\n" +
            "Baterias Extras: %d (Acabamento/Upgrade: R$ %.2f por unidade)",
            quantidadeBateriasExtras, CUSTO_BATERIA_EXTRA
        );
    }
}
