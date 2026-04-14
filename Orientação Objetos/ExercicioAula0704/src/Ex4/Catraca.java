package Ex4;

public class Catraca {

    public void validarIngresso(int idadeTorcedor, boolean isSocio) {
        if (!isSocio && idadeTorcedor < 18) {
            throw new IllegalArgumentException(
                    "Acesso negado: Menores não-sócios precisam de acompanhante."
            );
        }
        System.out.println("Acesso liberado!");
    }

    public static void main(String[] args) {
        Catraca c = new Catraca();

        try {
            c.validarIngresso(16, false);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}