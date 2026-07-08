package AtividadeB;

public class MembroComissaoTecnica extends Profissional {
    private int vitoriasConquistadas;
    private static final double BONUS_POR_VITORIA = 2500.0;

    public MembroComissaoTecnica(String nome, int identificacao, double salarioBase) throws ViolacaoSalarialException {
        super(nome, identificacao, salarioBase);
        this.vitoriasConquistadas = 0;
    }

    public int getVitoriasConquistadas() {
        return vitoriasConquistadas;
    }

    @Override
    public void registrarDesempenho(int quantidade) {
        if (quantidade > 0) {
            this.vitoriasConquistadas += quantidade;
            double valorBonus = quantidade * BONUS_POR_VITORIA;
            adicionarBonus(valorBonus);
        }
    }

    @Override
    public String toString() {
        return super.toString() + String.format(
            "\nCargo: Membro da Comissão Técnica\n" +
            "Vitórias no Mês: %d (Bônus: R$ %.2f por vitória)",
            vitoriasConquistadas, BONUS_POR_VITORIA
        );
    }
}
