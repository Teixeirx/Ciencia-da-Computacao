package fitmanagerpro.gui;

import fitmanagerpro.database.DatabaseManager;
import fitmanagerpro.models.Aluno;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/** Aba de gerenciamento de alunos. */
public class AlunosPanel extends JPanel {

    private final DatabaseManager db;
    private DefaultTableModel tableModel;
    private JTable tabela;

    // Campos do formulário
    private JTextField txtNome, txtCpf, txtTelefone, txtIdade, txtObjetivo;

    public AlunosPanel(DatabaseManager db) {
        this.db = db;
        setBackground(Cores.FUNDO);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        add(criarTitulo(), BorderLayout.NORTH);
        add(criarTabela(), BorderLayout.CENTER);
        add(criarFormulario(), BorderLayout.EAST);

        carregarDados();
    }

    private JLabel criarTitulo() {
        JLabel lbl = new JLabel("🧍 Alunos Cadastrados");
        lbl.setFont(Cores.FONTE_TITULO);
        lbl.setForeground(Cores.TEXTO_CLARO);
        lbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        return lbl;
    }

    private JScrollPane criarTabela() {
        String[] colunas = {"ID", "Nome", "CPF", "Telefone", "Idade", "Objetivo", "Status"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabela = new JTable(tableModel);
        Cores.estilizarTabela(tabela);
        tabela.getColumnModel().getColumn(0).setMaxWidth(50);
        tabela.getColumnModel().getColumn(4).setMaxWidth(60);
        tabela.getColumnModel().getColumn(6).setMaxWidth(80);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.getViewport().setBackground(Cores.FUNDO_CAMPO);
        scroll.setBorder(BorderFactory.createLineBorder(Cores.BORDA));
        return scroll;
    }

    private JPanel criarFormulario() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(Cores.FUNDO_PAINEL);
        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Cores.BORDA),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)));
        painel.setPreferredSize(new Dimension(280, 0));

        GridBagConstraints gbc = Cores.gbc();

        Cores.addLabel(painel, "Novo Aluno", gbc, 0, 0, true);

        txtNome     = Cores.addCampo(painel, "Nome *",     gbc, 0, 1);
        txtCpf      = Cores.addCampo(painel, "CPF",        gbc, 0, 3);
        txtTelefone = Cores.addCampo(painel, "Telefone",   gbc, 0, 5);
        txtIdade    = Cores.addCampo(painel, "Idade *",    gbc, 0, 7);
        txtObjetivo = Cores.addCampo(painel, "Objetivo",   gbc, 0, 9);

        JButton btnSalvar = Cores.botaoPrimario("💾  Cadastrar Aluno");
        btnSalvar.addActionListener(e -> salvarAluno());

        JButton btnExcluir = Cores.botaoPerigo("🗑  Excluir Selecionado");
        btnExcluir.addActionListener(e -> excluirAluno());

        gbc.gridx = 0; gbc.gridy = 11; gbc.insets = new Insets(16, 0, 4, 0);
        painel.add(btnSalvar, gbc);
        gbc.gridy = 12; gbc.insets = new Insets(4, 0, 0, 0);
        painel.add(btnExcluir, gbc);

        // Espaçador
        gbc.gridy = 13; gbc.weighty = 1;
        painel.add(new JLabel(), gbc);

        return painel;
    }

    private void salvarAluno() {
        String nome = txtNome.getText().trim();
        String idadeStr = txtIdade.getText().trim();

        if (nome.isEmpty() || idadeStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome e Idade são obrigatórios.", "Atenção",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        int idade;
        try { idade = Integer.parseInt(idadeStr); }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Idade deve ser um número inteiro.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Aluno aluno = new Aluno(nome,
                txtCpf.getText().trim(),
                txtTelefone.getText().trim(),
                idade,
                txtObjetivo.getText().trim().isEmpty() ? "Não informado" : txtObjetivo.getText().trim());

        db.salvarAluno(aluno);
        limparFormulario();
        carregarDados();
        JOptionPane.showMessageDialog(this, "Aluno \"" + nome + "\" cadastrado!", "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void excluirAluno() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um aluno na tabela.", "Atenção",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        int dbId = (int) tableModel.getValueAt(linha, 0);
        String nome = (String) tableModel.getValueAt(linha, 1);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Excluir o aluno \"" + nome + "\"?", "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            db.excluirAluno(dbId);
            carregarDados();
        }
    }

    public void carregarDados() {
        tableModel.setRowCount(0);
        List<Aluno> alunos = db.carregarAlunos();
        for (int i = 0; i < alunos.size(); i++) {
            Aluno a = alunos.get(i);
            tableModel.addRow(new Object[]{
                i + 1, a.getNome(), a.getCpf(), a.getTelefone(),
                a.getIdade(), a.getObjetivo(), a.isAtivo() ? "Ativo" : "Inativo"
            });
        }
    }

    private void limparFormulario() {
        txtNome.setText(""); txtCpf.setText(""); txtTelefone.setText("");
        txtIdade.setText(""); txtObjetivo.setText("");
    }
}
