package Ex5;

import java.io.IOException;

public class ProjetoMarcenaria {

    public static void lerArquivo() throws IOException {
        throw new IOException("Erro ao ler o arquivo projeto_mesa.txt");
    }

    public static void main(String[] args) {
        try {
            lerArquivo();
        } catch (IOException e) {
            System.out.println("Falha: " + e.getMessage());
        } finally {
            System.out.println("Limpando a bancada e desligando as máquinas...");
        }
    }
}