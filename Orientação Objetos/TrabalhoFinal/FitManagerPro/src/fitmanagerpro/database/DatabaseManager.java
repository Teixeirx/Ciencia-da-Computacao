package fitmanagerpro.database;

import fitmanagerpro.exceptions.PlanoInvalidoException;
import fitmanagerpro.models.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Gerencia a conexão com o banco SQLite e fornece métodos de CRUD para todas as entidades.
 * O arquivo fitmanager.db é criado automaticamente na primeira execução.
 */
public class DatabaseManager {

    private static final String URL = "jdbc:sqlite:fitmanager.db";
    private Connection conexao;

    // -----------------------------------------------------------------------
    // Conexão e inicialização
    // -----------------------------------------------------------------------

    public DatabaseManager() {
        try {
            Class.forName("org.sqlite.JDBC");
            conexao = DriverManager.getConnection(URL);
            conexao.createStatement().execute("PRAGMA foreign_keys = ON");
            criarTabelas();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar no banco de dados: " + e.getMessage(), e);
        }
    }

    /** Cria as tabelas se não existirem. */
    private void criarTabelas() throws SQLException {
        Statement st = conexao.createStatement();

        st.execute("""
            CREATE TABLE IF NOT EXISTS planos (
                id              INTEGER PRIMARY KEY AUTOINCREMENT,
                nome            TEXT    NOT NULL UNIQUE,
                descricao       TEXT,
                preco           REAL    NOT NULL,
                duracao_meses   INTEGER NOT NULL
            )
        """);

        st.execute("""
            CREATE TABLE IF NOT EXISTS instrutores (
                id              INTEGER PRIMARY KEY AUTOINCREMENT,
                nome            TEXT    NOT NULL,
                cpf             TEXT,
                telefone        TEXT,
                idade           INTEGER,
                especialidade   TEXT,
                cref            TEXT
            )
        """);

        st.execute("""
            CREATE TABLE IF NOT EXISTS alunos (
                id              INTEGER PRIMARY KEY AUTOINCREMENT,
                nome            TEXT    NOT NULL,
                cpf             TEXT,
                telefone        TEXT,
                idade           INTEGER,
                objetivo        TEXT,
                ativo           INTEGER DEFAULT 1
            )
        """);

        st.execute("""
            CREATE TABLE IF NOT EXISTS matriculas (
                id                  INTEGER PRIMARY KEY AUTOINCREMENT,
                aluno_id            INTEGER NOT NULL,
                plano_id            INTEGER NOT NULL,
                instrutor_id        INTEGER,
                data_inicio         TEXT,
                data_vencimento     TEXT,
                pagamento_em_dia    INTEGER DEFAULT 1,
                FOREIGN KEY (aluno_id)   REFERENCES alunos(id),
                FOREIGN KEY (plano_id)   REFERENCES planos(id),
                FOREIGN KEY (instrutor_id) REFERENCES instrutores(id)
            )
        """);

        st.execute("""
            CREATE TABLE IF NOT EXISTS pagamentos (
                id              INTEGER PRIMARY KEY AUTOINCREMENT,
                matricula_id    INTEGER NOT NULL,
                valor           REAL    NOT NULL,
                forma_pagamento TEXT,
                data_pagamento  TEXT,
                FOREIGN KEY (matricula_id) REFERENCES matriculas(id)
            )
        """);

        st.close();
    }

    public void fechar() {
        try {
            if (conexao != null && !conexao.isClosed()) conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // -----------------------------------------------------------------------
    // PLANOS
    // -----------------------------------------------------------------------

    public void salvarPlano(Plano plano) {
        String sql = "INSERT OR IGNORE INTO planos (nome, descricao, preco, duracao_meses) VALUES (?,?,?,?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, plano.getNome());
            ps.setString(2, plano.getDescricao());
            ps.setDouble(3, plano.getPreco());
            ps.setInt(4, plano.getDuracaoMeses());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Plano> carregarPlanos() {
        List<Plano> lista = new ArrayList<>();
        String sql = "SELECT * FROM planos";
        try (Statement st = conexao.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                try {
                    Plano p = new Plano(rs.getString("nome"), rs.getString("descricao"),
                            rs.getDouble("preco"), rs.getInt("duracao_meses"));
                    lista.add(p);
                } catch (PlanoInvalidoException ignored) {}
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /** Retorna o id do banco para o plano pelo nome. */
    public int idPlano(String nomePlano) {
        try (PreparedStatement ps = conexao.prepareStatement("SELECT id FROM planos WHERE nome=?")) {
            ps.setString(1, nomePlano);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("id");
        } catch (SQLException e) { e.printStackTrace(); }
        return -1;
    }

    // -----------------------------------------------------------------------
    // INSTRUTORES
    // -----------------------------------------------------------------------

    public int salvarInstrutor(Instrutor inst) {
        String sql = "INSERT INTO instrutores (nome, cpf, telefone, idade, especialidade, cref) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, inst.getNome());
            ps.setString(2, inst.getCpf());
            ps.setString(3, inst.getTelefone());
            ps.setInt(4, inst.getIdade());
            ps.setString(5, inst.getEspecialidade());
            ps.setString(6, inst.getCref());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) return keys.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return -1;
    }

    public List<Instrutor> carregarInstrutores() {
        List<Instrutor> lista = new ArrayList<>();
        try (Statement st = conexao.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM instrutores")) {
            while (rs.next()) {
                Instrutor i = new Instrutor(
                        rs.getString("nome"), rs.getString("cpf"),
                        rs.getString("telefone"), rs.getInt("idade"),
                        rs.getString("especialidade"), rs.getString("cref"));
                // Força o ID do banco no objeto (acesso via reflexão seria alternativa)
                lista.add(i);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    /** Retorna id do banco para o instrutor pelo nome. */
    public int idInstrutor(String nome) {
        try (PreparedStatement ps = conexao.prepareStatement("SELECT id FROM instrutores WHERE nome=?")) {
            ps.setString(1, nome);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("id");
        } catch (SQLException e) { e.printStackTrace(); }
        return -1;
    }

    public void excluirInstrutor(int dbId) {
        try (PreparedStatement ps = conexao.prepareStatement("DELETE FROM instrutores WHERE id=?")) {
            ps.setInt(1, dbId);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // -----------------------------------------------------------------------
    // ALUNOS
    // -----------------------------------------------------------------------

    public int salvarAluno(Aluno aluno) {
        String sql = "INSERT INTO alunos (nome, cpf, telefone, idade, objetivo, ativo) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, aluno.getNome());
            ps.setString(2, aluno.getCpf());
            ps.setString(3, aluno.getTelefone());
            ps.setInt(4, aluno.getIdade());
            ps.setString(5, aluno.getObjetivo());
            ps.setInt(6, aluno.isAtivo() ? 1 : 0);
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) return keys.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return -1;
    }

    public List<Aluno> carregarAlunos() {
        List<Aluno> lista = new ArrayList<>();
        try (Statement st = conexao.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM alunos")) {
            while (rs.next()) {
                Aluno a = new Aluno(
                        rs.getString("nome"), rs.getString("cpf"),
                        rs.getString("telefone"), rs.getInt("idade"),
                        rs.getString("objetivo"));
                a.setAtivo(rs.getInt("ativo") == 1);
                lista.add(a);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    /** Retorna o id do banco para o aluno pelo nome. */
    public int idAluno(String nome) {
        try (PreparedStatement ps = conexao.prepareStatement("SELECT id FROM alunos WHERE nome=?")) {
            ps.setString(1, nome);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("id");
        } catch (SQLException e) { e.printStackTrace(); }
        return -1;
    }

    public void excluirAluno(int dbId) {
        try (PreparedStatement ps = conexao.prepareStatement("DELETE FROM alunos WHERE id=?")) {
            ps.setInt(1, dbId);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // -----------------------------------------------------------------------
    // MATRÍCULAS
    // -----------------------------------------------------------------------

    public int salvarMatricula(Matricula m) {
        int idAluno     = idAluno(m.getAluno().getNome());
        int idPlano     = idPlano(m.getPlano().getNome());
        int idInstrutor = m.getInstrutor() != null ? idInstrutor(m.getInstrutor().getNome()) : -1;

        String sql = """
            INSERT INTO matriculas
                (aluno_id, plano_id, instrutor_id, data_inicio, data_vencimento, pagamento_em_dia)
            VALUES (?,?,?,?,?,?)
        """;
        try (PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, idAluno);
            ps.setInt(2, idPlano);
            if (idInstrutor > 0) ps.setInt(3, idInstrutor); else ps.setNull(3, Types.INTEGER);
            ps.setString(4, m.getDataInicio().toString());
            ps.setString(5, m.getDataVencimento().toString());
            ps.setInt(6, m.isPagamentoEmDia() ? 1 : 0);
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) return keys.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return -1;
    }

    public List<Object[]> carregarMatriculasRaw() {
        List<Object[]> lista = new ArrayList<>();
        String sql = """
            SELECT m.id, a.nome AS aluno, p.nome AS plano, i.nome AS instrutor,
                   m.data_inicio, m.data_vencimento, m.pagamento_em_dia
            FROM matriculas m
            JOIN alunos     a ON a.id = m.aluno_id
            JOIN planos     p ON p.id = m.plano_id
            LEFT JOIN instrutores i ON i.id = m.instrutor_id
        """;
        try (Statement st = conexao.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getInt("id"),
                    rs.getString("aluno"),
                    rs.getString("plano"),
                    rs.getString("instrutor") != null ? rs.getString("instrutor") : "—",
                    rs.getString("data_inicio"),
                    rs.getString("data_vencimento"),
                    rs.getInt("pagamento_em_dia") == 1 ? "Em dia" : "ATRASO"
                });
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public void atualizarPagamentoMatricula(int matriculaDbId, boolean emDia) {
        try (PreparedStatement ps = conexao.prepareStatement(
                "UPDATE matriculas SET pagamento_em_dia=? WHERE id=?")) {
            ps.setInt(1, emDia ? 1 : 0);
            ps.setInt(2, matriculaDbId);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // -----------------------------------------------------------------------
    // PAGAMENTOS
    // -----------------------------------------------------------------------

    public void salvarPagamento(int matriculaDbId, double valor, String forma) {
        String sql = "INSERT INTO pagamentos (matricula_id, valor, forma_pagamento, data_pagamento) VALUES (?,?,?,?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, matriculaDbId);
            ps.setDouble(2, valor);
            ps.setString(3, forma);
            ps.setString(4, LocalDate.now().toString());
            ps.executeUpdate();
            // Marca matrícula como paga
            atualizarPagamentoMatricula(matriculaDbId, true);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public List<Object[]> carregarPagamentosRaw() {
        List<Object[]> lista = new ArrayList<>();
        String sql = """
            SELECT pg.id, a.nome AS aluno, pg.valor, pg.forma_pagamento, pg.data_pagamento, m.id AS mat_id
            FROM pagamentos pg
            JOIN matriculas m ON m.id = pg.matricula_id
            JOIN alunos     a ON a.id = m.aluno_id
        """;
        try (Statement st = conexao.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getInt("id"),
                    rs.getString("aluno"),
                    String.format("R$ %.2f", rs.getDouble("valor")),
                    rs.getString("forma_pagamento"),
                    rs.getString("data_pagamento"),
                    rs.getInt("mat_id")
                });
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    // -----------------------------------------------------------------------
    // RELATÓRIO
    // -----------------------------------------------------------------------

    public int totalAlunos()      { return contarTabela("alunos"); }
    public int totalInstrutores() { return contarTabela("instrutores"); }
    public int totalMatriculas()  { return contarTabela("matriculas"); }
    public int totalPlanos()      { return contarTabela("planos"); }

    public int matriculasAtraso() {
        try (Statement st = conexao.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM matriculas WHERE pagamento_em_dia=0")) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public double receitaMensal() {
        String sql = "SELECT SUM(p.preco) FROM matriculas m JOIN planos p ON p.id=m.plano_id WHERE m.pagamento_em_dia=1";
        try (Statement st = conexao.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    private int contarTabela(String tabela) {
        try (Statement st = conexao.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM " + tabela)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    // Retorna lista de [nome_aluno, plano, data_vencimento] dos inadimplentes
    public List<Object[]> inadimplentes() {
        List<Object[]> lista = new ArrayList<>();
        String sql = """
            SELECT a.nome, p.nome AS plano, m.data_vencimento
            FROM matriculas m
            JOIN alunos a ON a.id = m.aluno_id
            JOIN planos p ON p.id = m.plano_id
            WHERE m.pagamento_em_dia = 0
        """;
        try (Statement st = conexao.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next())
                lista.add(new Object[]{ rs.getString(1), rs.getString(2), rs.getString(3) });
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public Connection getConexao() { return conexao; }
}
