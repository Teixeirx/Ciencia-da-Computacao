package ex8;

class Conta {
    double saldo;

    public void render() {
        // padrão
    }
}

class ContaPoupanca extends Conta {
    @Override
    public void render() {
        saldo *= 1.01;
    }
}