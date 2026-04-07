package ex3;

class Veiculo {
    int velocidade;
}

class Carro extends Veiculo {
    public void acelerar() {
        velocidade += 10;
        System.out.println("Velocidade: " + velocidade);
    }
}