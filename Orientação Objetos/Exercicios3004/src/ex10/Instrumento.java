package ex10;

class Instrumento {
    public void tocar() {
        System.out.println("Som genérico");
    }
}

class Violao extends Instrumento {
    @Override
    public void tocar() {
        System.out.println("Tocando violão");
    }
}

class Piano extends Instrumento {
    @Override
    public void tocar() {
        System.out.println("Tocando piano");
    }
}
