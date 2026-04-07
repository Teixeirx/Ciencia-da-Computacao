package ex6;

class Forma {
    String cor;

    public Forma(String cor) {
        this.cor = cor;
    }
}

class Circulo extends Forma {
    double raio;

    public Circulo(String cor, double raio) {
        super(cor);
        this.raio = raio;
    }
}