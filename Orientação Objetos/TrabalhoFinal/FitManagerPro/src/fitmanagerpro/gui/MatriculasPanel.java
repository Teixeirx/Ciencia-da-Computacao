package fitmanagerpro.gui;

import fitmanagerpro.database.DatabaseManager;
import fitmanagerpro.exceptions.MatriculaInvalidaException;
import fitmanagerpro.models.*;
import fitmanagerpro.services.AcademiaService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/** Aba de gerenciamento de matrículas. */
public class MatriculasPanel extends JPanel {

    private final DatabaseManager   db;
    private final AcademiaService   academia;
    private DefaultTableModel       tableModel;

    private JComboBox<String> cmbAluno, cmbPlano, cmbInstrutor;

    public MatriculasPanel(DatabaseManager db, AcademiaService academia) {
        this.db       = db;
        this.academia = academia;
        setBackground(Cores.FUNDO);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        add(criarTitulo(), BorderLayout.NORTH);
        add(criarTabela(), BorderLayout.CENTER);
        add(criarFormulario(), BorderLayout.EAST);

        carregarDados();
    }

    private JLabel criarTitulo() {
        JLabel lbl = new JLabel("📝 Matrículas");
        lbl.setFont(Cores.FONTE_TITULO);
        lbl.setForeground(Cores.TEXTO_CLARO);
        lbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        return lbl;
    }

    private JScrollPane criarTabela() {
        String[] colunas = {"#", "Aluno", "Plano", "Instrutor", "Início", "Vencimento", "Pgto"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabela = new JTable(tableModel);
        Cores.estilizarTabela(tabela);
        tabela.getColumnModel().getColumn(0).setMaxWidth(40);
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

        Cores.addLabel(painel, "Nova Matrícula", gbc, 0, 0, true);

        cmbAluno     = criarCombo(painel, "Aluno *",                gbc, 0, 1);
        cmbPlano     = criarCombo(painel, "Plano *",                gbc, 0, 3);
        cmbInstrutor = criarCombo(painel, "Instrutor (opcional)",   gbc, 0, 5);

        JButton btnSalvar = Cores.botaoPrimario("💾  Matricular");
        btnSalvar.addActionListener(e -> realizarMatricula());

        JButton btnAtualizar = Cores.botaoSecundario("🔄 Atualizar Listas");
        btnAtualizar.addActionListener(e -> atualizarCombos());

        gbc.gridx = 0; gbc.gridy = 7; gbc.insets = new Insets(16, 0, 4, 0);
        painel.add(btnSalvar, gbc);
        gbc.gridy = 8; gbc.insets = new Insets(4, 0, 0, 0);
        painel.add(btnAtualizar, gbc);

        gbc.gridy = 9; gbc.weighty = 1;
        painel.add(new JLabel(), gbc);

        atualizarCombos();
        return painel;
    }

    private JComboBox<String> criarCombo(JPanel painel, String label,
                                          GridBagConstraints gbc, int x, int y) {
        gbc.gridx = x; gbc.gridy = y; gbc.insets = new Insets(8, 0, 2, 0); gbc.weighty = 0;
        JLabel lbl = new JLabel(label);
        lbl.setForeground(Cores.TEXTO_SECUNDARIO);
        lbl.setFont(Cores.FONTE_NORMAL.deriveFont(12f));
        painel.add(lbl, gbc);

        JComboBox<String> cmb = new JComboBox<>();
        cmb.setBackground(Cores.FUNDO_CAMPO);
        cmb.setForeground(Cores.TEXTO_CLARO);
        cmb.setFont(Cores.FONTE_NORMAL);
        gbc.gridy = y + 1; gbc.insets = new Insets(0, 0, 0, 0);
        painel.add(cmb, gbc);
        return cmb;
    }

    public void atualizarCombos() {
        cmbAluno.removeAllItems();
        for (Aluno a : db.carregarAlunos()) cmbAluno.addItem(a.getNome());

        cmbPlano.removeAllItems();
        for (Plano p : db.carregarPlanos()) cmbPlano.addItem(p.getNome());

        cmbInstrutor.removeAllItems();
        cmbInstrutor.addItem("(Sem instrutor)");
        for (Instrutor i : db.carregarInstrutores()) cmbInstrutor.addItem(i.getNome());
    }

    private void realizarMatricula() {
        if (cmbAluno.getItemCount() == 0 || cmbPlano.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Cadastre pelo menos um aluno e um plano antes de matricular.",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nomeAluno    = (String) cmbAluno.getSelectedItem();
        String nomePlano    = (String) cmbPlano.getSelectedItem();
        String nomeInstrutor = (String) cmbInstrutor.getSelectedItem();

        // Monta objetos via serviço/banco
        List<Aluno>    alunos     = db.carregarAlunos();
        List<Plano>    planos     = db.carregarPlanos();
        List<Instrutor> instrutores = db.carregarInstrutores();

        Aluno aluno = alunos.stream()
                .filter(a -> a.getNome().equals(nomeAluno)).findFirst().orElse(null);
        Plano plano = planos.stream()
                .filter(p -> p.getNome().equals(nomePlano)).findFirst().orElse(null);
        Instrutor instrutor = instrutores.stream()
                .filter(i -> i.getNome().equals(nomeInstrutor)).findFirst().orElse(null);

        if (aluno == null || plano == null) {
            JOptionPane.showMessageDialog(this, "Aluno ou plano não encontrado.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            academia.realizarMatricula(aluno, plano, instrutor);
            // Persiste no banco
            Matricula m = academia.listarMatriculas().getLast();
            db.salvarMatricula(m);
            carregarDados();
            JOptionPane.showMessageDialog(this, "Matrícula realizada com sucesso!", "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (MatriculaInvalidaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Matrícula Inválida",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void carregarDados() {
        tableModel.setRowCount(0);
        List<Object[]> dados = db.carregarMatriculasRaw();
        int idx = 1;
        for (Object[] row : dados) {
            tableModel.addRow(new Object[]{
                idx++, row[1], row[2], row[3], row[4], row[5], row[6]
            });
        }
    }
}
