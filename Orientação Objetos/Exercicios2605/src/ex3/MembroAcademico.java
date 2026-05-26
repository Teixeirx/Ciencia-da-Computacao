package ex3;

public abstract class MembroAcademico {
    private String nome;
    private String matricula;

    public MembroAcademico(String nome, String matricula) {
        this.nome = nome;
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public abstract void mostrarFuncao();
}
