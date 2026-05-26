package ex2;

public class VanRefrigerada extends Veiculo implements Rastreador, Refrigerado {
    public VanRefrigerada(String placa, double capacidadeCarga) {
        super(placa, capacidadeCarga);
    }

    @Override
    public void iniciarViagem() {
        System.out.println("Van Refrigerada " + getPlaca() + " iniciando viagem.");
    }

    @Override
    public String obterLocalizacao() {
        return "Depósito Central";
    }

    @Override
    public void ajustarTemperatura(double graus) throws TemperaturaCriticaException {
        if (graus > 10.0 || graus < -20.0) {
            throw new TemperaturaCriticaException("A carga pode ser comprometida! Temperatura fora dos limites seguros: " + graus);
        }
        System.out.println("Temperatura da Van " + getPlaca() + " ajustada para " + graus + " graus.");
    }
}
