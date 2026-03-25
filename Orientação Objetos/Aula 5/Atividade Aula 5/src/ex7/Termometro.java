package ex7;

public class Termometro {
    private double celsius;

    public double getCelsius() {
        return celsius;
    }

    public double getFahrenheit() {
        return (celsius * 9/5) + 32;
    }

    public void setFahrenheit(double f) {
        this.celsius = (f - 32) * 5/9;
    }
}
