package ex5;

public interface Baixavel {
    void realizarDownload(boolean conexaoEstavel) throws FalhaNoDownloadException;
}
