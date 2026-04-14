package Ex8;

class AcordeInvalidoException extends Exception {
    public AcordeInvalidoException(String msg) {
        super(msg);
    }
}

class Partitura {
    public void registrarAcorde(String acorde) throws AcordeInvalidoException {
        if (!acorde.matches("[CDEFGAB].*")) {
            throw new AcordeInvalidoException("Acorde inválido!");
        }
        System.out.println("Acorde registrado: " + acorde);
    }
}

public class TestePartitura {
    public static void main(String[] args) {
        Partitura p = new Partitura();

        try {
            p.registrarAcorde("H#m");
        } catch (AcordeInvalidoException e) {
            System.out.println(e.getMessage());
        }
    }
}