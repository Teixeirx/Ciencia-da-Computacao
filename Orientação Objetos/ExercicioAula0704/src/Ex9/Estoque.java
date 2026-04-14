package Ex9;

class MadeiraInsuficienteException extends RuntimeException {
    public MadeiraInsuficienteException(String msg) {
        super(msg);
    }
}

class Estoque {
    double quantidadeMdf = 10;
    double quantidadeMacaudiba = 5;

    public void cortarPeca(String tipo, double metros) {
        if (tipo.equalsIgnoreCase("MDF")) {
            if (metros > quantidadeMdf) {
                throw new MadeiraInsuficienteException("MDF insuficiente!");
            }
        } else if (tipo.equalsIgnoreCase("Macaudiba")) {
            if (metros > quantidadeMacaudiba) {
                throw new MadeiraInsuficienteException("Madeira insuficiente!");
            }
        }

        System.out.println("Peça cortada!");
    }

    public static void main(String[] args) {
        Estoque e = new Estoque();
        e.cortarPeca("MDF", 20);
    }
}