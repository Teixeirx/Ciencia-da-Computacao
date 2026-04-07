package ex2;

class Pessoa {
    String nome;
    int idade;
}

class Aluno extends Pessoa {
    String matricula;
}

public class Main {
    public static void main(String[] args) {
        Aluno a = new Aluno();
        a.nome = "João";
        a.idade = 20;
        a.matricula = "12345";

        System.out.println("Nome: " + a.nome);
        System.out.println("Idade: " + a.idade);
        System.out.println("Matrícula: " + a.matricula);
    }
}