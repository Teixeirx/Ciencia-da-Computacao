package ex2;

public class CaminhaoPadrao extends Veiculo implements Rastreador {
    public CaminhaoPadrao(String placa, double capacidadeCarga) {
        super(placa, capacidadeCarga);
    }

    @Override
    public void iniciarViagem() {
        System.out.println("Caminhão Padrão " + getPlaca() + " iniciando viagem.");
    }

    @Override
    public String obterLocalizacao() {
        return "BR-101, Km 200";
    }
}
