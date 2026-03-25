package ex1;

public class ContaBancaria {
    private double saldo;
    private double limite;

    public void setLimite(double limite) {
        if (limite >= 0) {
            this.limite = limite;
        }
    }

    public void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
        }
    }

    public boolean sacar(double valor) {
        if (valor > 0 && valor <= saldo + limite) {
            saldo -= valor;
            return true;
        }
        return false;
    }

    public double getSaldo() {
        return saldo;
    }
}