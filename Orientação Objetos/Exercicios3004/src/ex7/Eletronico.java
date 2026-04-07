package ex7;

class Eletronico {
    public void ligar() {
        System.out.println("Ligando eletrônico...");
    }
}

class Smartphone extends Eletronico {
    @Override
    public void ligar() {
        System.out.println("Ligando smartphone...");
    }

    public void conectar4G() {
        System.out.println("Conectado ao 4G");
    }
}