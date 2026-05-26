package ex3;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<MembroAcademico> lista = new ArrayList<>();
        lista.add(new Professor("Carlos Silva", "P001"));
        lista.add(new AlunoPosGraduacao("Ana Souza", "A001"));

        for (MembroAcademico membro : lista) {
            membro.mostrarFuncao();

            if (membro instanceof Pesquisador) {
                try {
                    System.out.println("Tentando publicar artigo vazio...");
                    ((Pesquisador) membro).publicarArtigo("");
                } catch (DadosAcademicosInvalidosException e) {
                    System.out.println("Erro Capturado: " + e.getMessage());
                }
            }

            if (membro instanceof Avaliador) {
                try {
                    System.out.println("Tentando lançar nota 11.0...");
                    ((Avaliador) membro).lancarNotas(11.0);
                } catch (DadosAcademicosInvalidosException e) {
                    System.out.println("Erro Capturado: " + e.getMessage());
                }
            }
            System.out.println("---");
        }
    }
}
