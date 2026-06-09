package ex2;

public class Main {
    public static void main(String[] args) {
        CaminhaoPadrao caminhao = new CaminhaoPadrao("ABC-1234", 15000);
        VanRefrigerada van = new VanRefrigerada("XYZ-9876", 3000);

        caminhao.registrarManutencao();
        van.registrarManutencao();

        System.out.println("Localização Caminhão: " + caminhao.obterLocalizacao());
        System.out.println("Localização Van: " + van.obterLocalizacao());

        caminhao.iniciarViagem();
        van.iniciarViagem();

        System.out.println("\n--- Teste de Temperatura ---");
        
        try {
            System.out.println("Tentando ajustar temperatura para 15.0...");
            van.ajustarTemperatura(15.0);
        } catch (TemperaturaCriticaException e) {
            System.out.println("Erro Capturado: " + e.getMessage());
        }

        try {
            System.out.println("\nTentando ajustar temperatura para 2.0...");
            van.ajustarTemperatura(2.0);
        } catch (TemperaturaCriticaException e) {
            System.out.println("Erro Capturado: " + e.getMessage());
        }
    }
}
