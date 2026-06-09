package ex3;

public class Professor extends MembroAcademico implements Pesquisador, Avaliador {
    public Professor(String nome, String matricula) {
        super(nome, matricula);
    }

    @Override
    public void mostrarFuncao() {
        System.out.println("Sou o Professor " + getNome());
    }

    @Override
    public void publicarArtigo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new DadosAcademicosInvalidosException("Título do artigo não pode ser vazio para professor.");
        }
        System.out.println("Professor publicou artigo: " + titulo);
    }

    @Override
    public void lancarNotas(double nota) {
        if (nota < 0 || nota > 10) {
            throw new DadosAcademicosInvalidosException("Nota inválida lançada pelo professor: " + nota);
        }
        System.out.println("Nota " + nota + " lançada com sucesso pelo professor.");
    }
}
