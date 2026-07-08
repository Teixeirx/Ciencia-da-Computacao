package AtividadeA;

public class Desktop extends Computador {
    private int quantidadePentesRamExtras;
    private static final double CUSTO_PENTE_RAM = 150.0;

    public Desktop(String modelo, int numeroSerie, double custoBase) throws ViolacaoOrcamentariaException {
        super(modelo, numeroSerie, custoBase);
        this.quantidadePentesRamExtras = 0;
    }

    public int getQuantidadePentesRamExtras() {
        return quantidadePentesRamExtras;
    }

    @Override
    public void receberUpgrade(int quantidade) {
        if (quantidade > 0) {
            this.quantidadePentesRamExtras += quantidade;
            double custoAdicional = quantidade * CUSTO_PENTE_RAM;
            adicionarCustosAdicionais(custoAdicional);
        }
    }

    @Override
    public String toString() {
        return super.toString() + String.format(
            "\nTipo: Desktop\n" +
            "Pentes de RAM Extras: %d (Acabamento/Upgrade: R$ %.2f por unidade)",
            quantidadePentesRamExtras, CUSTO_PENTE_RAM
        );
    }
}
