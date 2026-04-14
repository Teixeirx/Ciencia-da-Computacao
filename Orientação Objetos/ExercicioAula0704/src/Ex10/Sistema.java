package Ex10;

import java.sql.SQLException;

// Exceção customizada
class AlunoNaoEncontradoException extends Exception {
    public AlunoNaoEncontradoException(String msg) {
        super(msg);
    }
}

// Camada de Banco
class BancoDados {
    public void buscarAluno(int matricula) throws SQLException {
        throw new SQLException("Falha na conexão com banco!");
    }
}

// Camada de Negócio
class Negocio {
    BancoDados bd = new BancoDados();

    public void gerarBoletim(int matricula) throws AlunoNaoEncontradoException {
        try {
            bd.buscarAluno(matricula);
        } catch (SQLException e) {
            throw new AlunoNaoEncontradoException("Aluno não encontrado!");
        }
    }
}

// Camada de Interface
public class Sistema {
    public static void main(String[] args) {
        Negocio n = new Negocio();

        try {
            n.gerarBoletim(123);
        } catch (AlunoNaoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }
}
