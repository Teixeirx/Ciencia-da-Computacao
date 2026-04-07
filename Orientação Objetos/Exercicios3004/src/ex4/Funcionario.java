package ex4;

class Funcionario {
    public double calcularSalario() {
        return 1000;
    }
}

class Gerente extends Funcionario {
    @Override
    public double calcularSalario() {
        return 3000;
    }
}

class Vendedor extends Funcionario {
    @Override
    public double calcularSalario() {
        return 2000;
    }
}