package ex5;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Midia> playlist = new ArrayList<>();
        playlist.add(new Musica("Bohemian Rhapsody", 354));
        playlist.add(new Filme("Inception", 8880));
        playlist.add(new Podcast("DevTalks", 3600));

        System.out.println("--- Iniciando a Playlist ---");

        for (Midia midia : playlist) {
            System.out.println("\nProcessando: " + midia.getTitulo());
            
            if (midia instanceof Baixavel) {
                try {
                    System.out.println("Tentando realizar download deliberadamente falho...");
                    ((Baixavel) midia).realizarDownload(false);
                } catch (FalhaNoDownloadException e) {
                    System.out.println("Aviso para o usuário: " + e.getMessage());
                }
            }

            if (midia instanceof Compartilhavel) {
                System.out.println("Link de compartilhamento: " + ((Compartilhavel) midia).gerarLink());
            }

            midia.reproduzir();
        }
        
        System.out.println("\n--- Fim da Playlist ---");
    }
}
