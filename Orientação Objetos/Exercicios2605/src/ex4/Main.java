package ex4;

public class Main {
    public static void main(String[] args) {
        Guerreiro guerreiro = new Guerreiro("Thor", 20);
        Mago mago = new Mago("Gandalf", 15);

        System.out.println("Início da Batalha!");
        
        while (mago.getPontosVida() > 0) {
            try {
                guerreiro.atacar(mago);
                System.out.println("Vida de " + mago.getNome() + ": " + mago.getPontosVida());
            } catch (AlvoInvalidoException e) {
                System.out.println("Erro Capturado: " + e.getMessage());
                break;
            }
        }

        // Tentar atacar o personagem morto para disparar a exceção
        try {
            System.out.println("\nTentando atacar " + mago.getNome() + " que já está derrotado...");
            guerreiro.atacar(mago);
        } catch (AlvoInvalidoException e) {
            System.out.println("Exceção Disparada e Capturada: " + e.getMessage());
        }
    }
}
