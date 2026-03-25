package ex3;

public class Retangulo {
    private double largura;
    private double altura;

    public void setLargura(double largura) {
        if (largura > 0) {
            this.largura = largura;
        }
    }

    public void setAltura(double altura) {
        if (altura > 0) {
            this.altura = altura;
        }
    }

    public double getArea() {
        return largura * altura;
    }
}