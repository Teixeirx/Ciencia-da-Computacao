package ex5;

public class Usuario {
    private String nome;
    private String senha;

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSenha(String senha) {
        if (senha != null && senha.length() >= 8) {
            this.senha = senha;
        }
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return "********";
    }
}
