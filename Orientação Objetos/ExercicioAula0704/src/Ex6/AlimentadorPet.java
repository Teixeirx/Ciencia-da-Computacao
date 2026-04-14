package Ex6;

public class AlimentadorPet {

    public void liberarRacao(int quantidadeGramas) {
        if (quantidadeGramas < 0) {
            throw new IllegalArgumentException("Quantidade negativa!");
        }
        if (quantidadeGramas > 100) {
            throw new IllegalStateException("A tigela vai transbordar!");
        }

        System.out.println("Ração liberada: " + quantidadeGramas + "g");
    }

    public static void main(String[] args) {
        AlimentadorPet a = new AlimentadorPet();

        try {
            a.liberarRacao(150);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }
}
