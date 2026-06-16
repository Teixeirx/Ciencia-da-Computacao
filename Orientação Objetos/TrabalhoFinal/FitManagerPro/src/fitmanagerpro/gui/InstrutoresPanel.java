package fitmanagerpro.gui;

import fitmanagerpro.database.DatabaseManager;
import fitmanagerpro.models.Instrutor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/** Aba de gerenciamento de instrutores. */
public class InstrutoresPanel extends JPanel {

    private final DatabaseManager db;
    private DefaultTableModel tableModel;
    private JTable tabela;

    private JTextField txtNome, txtCpf, txtTelefone, txtIdade, txtEspecialidade, txtCref;

    public InstrutoresPanel(DatabaseManager db) {
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
        JLabel lbl = new JLabel("🏋 Instrutores Cadastrados");
        lbl.setFont(Cores.FONTE_TITULO);
        lbl.setForeground(Cores.TEXTO_CLARO);
        lbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        return lbl;
    }

    private JScrollPane criarTabela() {
        String[] colunas = {"ID", "Nome", "CPF", "Telefone", "Idade", "Especialidade", "CREF"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabela = new JTable(tableModel);
        Cores.estilizarTabela(tabela);
        tabela.getColumnModel().getColumn(0).setMaxWidth(50);
        tabela.getColumnModel().getColumn(4).setMaxWidth(60);

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

        Cores.addLabel(painel, "Novo Instrutor", gbc, 0, 0, true);

        txtNome          = Cores.addCampo(painel, "Nome *",          gbc, 0, 1);
        txtCpf           = Cores.addCampo(painel, "CPF",             gbc, 0, 3);
        txtTelefone      = Cores.addCampo(painel, "Telefone",        gbc, 0, 5);
        txtIdade         = Cores.addCampo(painel, "Idade *",         gbc, 0, 7);
        txtEspecialidade = Cores.addCampo(painel, "Especialidade *", gbc, 0, 9);
        txtCref          = Cores.addCampo(painel, "CREF",            gbc, 0, 11);

        JButton btnSalvar  = Cores.botaoPrimario("💾  Cadastrar Instrutor");
        btnSalvar.addActionListener(e -> salvarInstrutor());

        JButton btnExcluir = Cores.botaoPerigo("🗑  Excluir Selecionado");
        btnExcluir.addActionListener(e -> excluirInstrutor());

        gbc.gridx = 0; gbc.gridy = 13; gbc.insets = new Insets(16, 0, 4, 0);
        painel.add(btnSalvar, gbc);
        gbc.gridy = 14; gbc.insets = new Insets(4, 0, 0, 0);
        painel.add(btnExcluir, gbc);

        gbc.gridy = 15; gbc.weighty = 1;
        painel.add(new JLabel(), gbc);

        return painel;
    }

    private void salvarInstrutor() {
        String nome = txtNome.getText().trim();
        String idadeStr = txtIdade.getText().trim();
        String espec = txtEspecialidade.getText().trim();

        if (nome.isEmpty() || idadeStr.isEmpty() || espec.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome, Idade e Especialidade são obrigatórios.",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int idade;
        try { idade = Integer.parseInt(idadeStr); }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Idade deve ser um número inteiro.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Instrutor inst = new Instrutor(nome, txtCpf.getText().trim(),
                txtTelefone.getText().trim(), idade, espec,
                txtCref.getText().trim().isEmpty() ? "Não informado" : txtCref.getText().trim());

        db.salvarInstrutor(inst);
        limparFormulario();
        carregarDados();
        JOptionPane.showMessageDialog(this, "Instrutor \"" + nome + "\" cadastrado!", "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void excluirInstrutor() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um instrutor na tabela.", "Atenção",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        int dbId = (int) tableModel.getValueAt(linha, 0);
        String nome = (String) tableModel.getValueAt(linha, 1);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Excluir o instrutor \"" + nome + "\"?", "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            db.excluirInstrutor(dbId);
            carregarDados();
        }
    }

    public void carregarDados() {
        tableModel.setRowCount(0);
        List<Instrutor> lista = db.carregarInstrutores();
        int id = 1;
        for (Instrutor i : lista) {
            tableModel.addRow(new Object[]{
                id++, i.getNome(), i.getCpf(), i.getTelefone(),
                i.getIdade(), i.getEspecialidade(), i.getCref()
            });
        }
    }

    private void limparFormulario() {
        txtNome.setText(""); txtCpf.setText(""); txtTelefone.setText("");
        txtIdade.setText(""); txtEspecialidade.setText(""); txtCref.setText("");
    }
}
