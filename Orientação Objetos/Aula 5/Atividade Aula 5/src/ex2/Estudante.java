package ex2;

public class Estudante {
    private double nota;

    public void setNota(double nota) {
        if (nota > 0 && nota < 10) {
            this.nota = nota;
        }
    }

    public double getNota() {
        return nota;
    }
}