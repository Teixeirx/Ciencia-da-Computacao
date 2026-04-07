package ex9;

class Pessoa {
    String nome;
    int idade;
}

class Aluno extends Pessoa {
    String matricula;

    @Override
    public String toString() {
        return "Nome: " + nome +
                ", Idade: " + idade +
                ", Matrícula: " + matricula;
    }
}
