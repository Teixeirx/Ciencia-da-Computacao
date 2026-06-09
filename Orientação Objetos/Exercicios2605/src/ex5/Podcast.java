package ex5;

public class Podcast extends Midia implements Compartilhavel {
    public Podcast(String titulo, int duracaoSegundos) {
        super(titulo, duracaoSegundos);
    }

    @Override
    public void reproduzir() {
        System.out.println("Reproduzindo podcast: " + getTitulo());
    }

    @Override
    public String gerarLink() {
        return "https://streaming.com/podcast/" + getTitulo().replace(" ", "");
    }
}
