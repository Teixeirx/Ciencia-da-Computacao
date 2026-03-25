package ex8;

public class Triangulo {
    private double a;
    private double b;
    private double c;

    private boolean ehValido(double a, double b, double c) {
        return (a + b > c) && (a + c > b) && (b + c > a);
    }

    public void setA(double novoA) {
        if (ehValido(novoA, b, c)) {
            this.a = novoA;
        }
    }

    public void setB(double novoB) {
        if (ehValido(a, novoB, c)) {
            this.b = novoB;
        }
    }

    public void setC(double novoC) {
        if (ehValido(a, b, novoC)) {
            this.c = novoC;
        }
    }
}
