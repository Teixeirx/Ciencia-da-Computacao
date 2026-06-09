package ex5;

public class Filme extends Midia implements Baixavel {
    public Filme(String titulo, int duracaoSegundos) {
        super(titulo, duracaoSegundos);
    }

    @Override
    public void reproduzir() {
        System.out.println("Reproduzindo filme: " + getTitulo());
    }

    @Override
    public void realizarDownload(boolean conexaoEstavel) throws FalhaNoDownloadException {
        if (!conexaoEstavel) {
            throw new FalhaNoDownloadException("Erro de rede: Não foi possível baixar " + getTitulo());
        }
        System.out.println("Download do filme " + getTitulo() + " concluído.");
    }
}
