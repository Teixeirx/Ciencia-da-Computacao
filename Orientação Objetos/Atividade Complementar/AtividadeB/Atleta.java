package AtividadeB;

public class Atleta extends Profissional {
    private int golsMarcados;
    private static final double BONUS_POR_GOL = 1000.0;

    public Atleta(String nome, int identificacao, double salarioBase) throws ViolacaoSalarialException {
        super(nome, identificacao, salarioBase);
        this.golsMarcados = 0;
    }

    public int getGolsMarcados() {
        return golsMarcados;
    }

    @Override
    public void registrarDesempenho(int quantidade) {
        if (quantidade > 0) {
            this.golsMarcados += quantidade;
            double valorBonus = quantidade * BONUS_POR_GOL;
            adicionarBonus(valorBonus);
        }
    }

    @Override
    public String toString() {
        return super.toString() + String.format(
            "\nCargo: Atleta\n" +
            "Gols Marcados no Mês: %d (Bônus: R$ %.2f por gol)",
            golsMarcados, BONUS_POR_GOL
        );
    }
}
