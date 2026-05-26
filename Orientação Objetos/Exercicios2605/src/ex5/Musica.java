package ex5;

public class Musica extends Midia implements Baixavel, Compartilhavel {
    public Musica(String titulo, int duracaoSegundos) {
        super(titulo, duracaoSegundos);
    }

    @Override
    public void reproduzir() {
        System.out.println("Reproduzindo música: " + getTitulo());
    }

    @Override
    public void realizarDownload(boolean conexaoEstavel) throws FalhaNoDownloadException {
        if (!conexaoEstavel) {
            throw new FalhaNoDownloadException("Erro de rede: Não foi possível baixar " + getTitulo());
        }
        System.out.println("Download da música " + getTitulo() + " concluído.");
    }

    @Override
    public String gerarLink() {
        return "https://streaming.com/musica/" + getTitulo().replace(" ", "");
    }
}
