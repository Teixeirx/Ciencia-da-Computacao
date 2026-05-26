package ex3;

public class AlunoPosGraduacao extends MembroAcademico implements Pesquisador {
    public AlunoPosGraduacao(String nome, String matricula) {
        super(nome, matricula);
    }

    @Override
    public void mostrarFuncao() {
        System.out.println("Sou Aluno de Pós-Graduação " + getNome());
    }

    @Override
    public void publicarArtigo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new DadosAcademicosInvalidosException("Título do artigo não pode ser vazio para aluno de pós.");
        }
        System.out.println("Aluno de pós-graduação publicou artigo: " + titulo);
    }
}
