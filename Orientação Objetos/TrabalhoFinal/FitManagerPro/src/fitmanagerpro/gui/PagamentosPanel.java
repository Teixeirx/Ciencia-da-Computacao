package fitmanagerpro.gui;

import fitmanagerpro.database.DatabaseManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/** Aba de registro e listagem de pagamentos. */
public class PagamentosPanel extends JPanel {

    private final DatabaseManager db;
    private DefaultTableModel tableModel;

    private JComboBox<String> cmbMatricula;
    private JTextField txtValor;
    private JComboBox<String> cmbForma;

    // Guarda os IDs das matrículas carregadas no combo, na mesma ordem
    private List<Object[]> matriculasRaw;

    public PagamentosPanel(DatabaseManager db) {
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
        JLabel lbl = new JLabel("💰 Pagamentos");
        lbl.setFont(Cores.FONTE_TITULO);
        lbl.setForeground(Cores.TEXTO_CLARO);
        lbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        return lbl;
    }

    private JScrollPane criarTabela() {
        String[] colunas = {"#", "Aluno", "Valor", "Forma", "Data", "Matrícula"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabela = new JTable(tableModel);
        Cores.estilizarTabela(tabela);
        tabela.getColumnModel().getColumn(0).setMaxWidth(40);
        tabela.getColumnModel().getColumn(5).setMaxWidth(80);

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

        Cores.addLabel(painel, "Registrar Pagamento", gbc, 0, 0, true);

        // Combo de matrículas
        gbc.gridx = 0; gbc.gridy = 1; gbc.insets = new Insets(8, 0, 2, 0);
        JLabel lbl = new JLabel("Matrícula (Aluno — Plano) *");
        lbl.setForeground(Cores.TEXTO_SECUNDARIO);
        lbl.setFont(Cores.FONTE_NORMAL.deriveFont(12f));
        painel.add(lbl, gbc);

        cmbMatricula = new JComboBox<>();
        cmbMatricula.setBackground(Cores.FUNDO_CAMPO);
        cmbMatricula.setForeground(Cores.TEXTO_CLARO);
        cmbMatricula.setFont(Cores.FONTE_NORMAL);
        gbc.gridy = 2; gbc.insets = new Insets(0, 0, 0, 0);
        painel.add(cmbMatricula, gbc);

        txtValor = Cores.addCampo(painel, "Valor (R$) *", gbc, 0, 3);

        // Combo forma de pagamento
        gbc.gridy = 5; gbc.insets = new Insets(8, 0, 2, 0);
        JLabel lblForma = new JLabel("Forma de Pagamento *");
        lblForma.setForeground(Cores.TEXTO_SECUNDARIO);
        lblForma.setFont(Cores.FONTE_NORMAL.deriveFont(12f));
        painel.add(lblForma, gbc);

        cmbForma = new JComboBox<>(new String[]{"Pix", "Cartão", "Dinheiro"});
        cmbForma.setBackground(Cores.FUNDO_CAMPO);
        cmbForma.setForeground(Cores.TEXTO_CLARO);
        cmbForma.setFont(Cores.FONTE_NORMAL);
        gbc.gridy = 6; gbc.insets = new Insets(0, 0, 0, 0);
        painel.add(cmbForma, gbc);

        JButton btnSalvar = Cores.botaoPrimario("💾  Registrar Pagamento");
        btnSalvar.addActionListener(e -> registrarPagamento());

        JButton btnAtualizar = Cores.botaoSecundario("🔄 Atualizar Lista");
        btnAtualizar.addActionListener(e -> atualizarComboMatriculas());

        gbc.gridy = 7; gbc.insets = new Insets(16, 0, 4, 0);
        painel.add(btnSalvar, gbc);
        gbc.gridy = 8; gbc.insets = new Insets(4, 0, 0, 0);
        painel.add(btnAtualizar, gbc);

        gbc.gridy = 9; gbc.weighty = 1;
        painel.add(new JLabel(), gbc);

        atualizarComboMatriculas();
        return painel;
    }

    public void atualizarComboMatriculas() {
        cmbMatricula.removeAllItems();
        matriculasRaw = db.carregarMatriculasRaw();
        for (Object[] row : matriculasRaw) {
            // row: [id, aluno, plano, instrutor, data_inicio, data_vencimento, pgto]
            cmbMatricula.addItem("#" + row[0] + " — " + row[1] + " (" + row[2] + ")");
        }
    }

    private void registrarPagamento() {
        if (cmbMatricula.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this, "Nenhuma matrícula disponível.", "Atenção",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (txtValor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o valor pago.", "Atenção",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        double valor;
        try {
            valor = Double.parseDouble(txtValor.getText().trim().replace(",", "."));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Valor inválido.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idx = cmbMatricula.getSelectedIndex();
        if (idx < 0 || idx >= matriculasRaw.size()) return;

        int matriculaDbId = (int) matriculasRaw.get(idx)[0];
        String forma = (String) cmbForma.getSelectedItem();

        db.salvarPagamento(matriculaDbId, valor, forma);
        txtValor.setText("");
        carregarDados();
        JOptionPane.showMessageDialog(this, "Pagamento registrado com sucesso!", "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void carregarDados() {
        tableModel.setRowCount(0);
        List<Object[]> dados = db.carregarPagamentosRaw();
        int idx = 1;
        for (Object[] row : dados) {
            tableModel.addRow(new Object[]{
                idx++, row[1], row[2], row[3], row[4], "Matrícula #" + row[5]
            });
        }
    }
}
