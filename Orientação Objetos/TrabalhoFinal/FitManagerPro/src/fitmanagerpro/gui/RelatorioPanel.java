package fitmanagerpro.gui;

import fitmanagerpro.database.DatabaseManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/** Aba de relatório geral da academia. */
public class RelatorioPanel extends JPanel {

    private final DatabaseManager db;

    // Cards de KPI
    private JLabel lblAlunos, lblInstrutores, lblMatriculas, lblPlanos, lblAtraso, lblReceita;

    // Tabela de inadimplentes
    private DefaultTableModel tableModel;

    public RelatorioPanel(DatabaseManager db) {
        this.db = db;
        setBackground(Cores.FUNDO);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel titulo = new JLabel("📊 Relatório Geral da Academia");
        titulo.setFont(Cores.FONTE_TITULO);
        titulo.setForeground(Cores.TEXTO_CLARO);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));
        add(titulo, BorderLayout.NORTH);

        add(criarCards(), BorderLayout.CENTER);
        add(criarInadimplentes(), BorderLayout.SOUTH);

        atualizar();
    }

    private JPanel criarCards() {
        JPanel grid = new JPanel(new GridLayout(2, 3, 12, 12));
        grid.setBackground(Cores.FUNDO);

        lblAlunos      = criarCard(grid, "Total de Alunos",      "0", new Color(0x3A86FF));
        lblInstrutores = criarCard(grid, "Instrutores",           "0", new Color(0x8338EC));
        lblMatriculas  = criarCard(grid, "Matrículas",            "0", new Color(0x06D6A0));
        lblPlanos      = criarCard(grid, "Planos no Catálogo",    "0", new Color(0xFFBE0B));
        lblAtraso      = criarCard(grid, "Pgtos em Atraso",       "0", new Color(0xFF006E));
        lblReceita     = criarCard(grid, "Receita Mensal (R$)",   "0,00", new Color(0x2DC653));

        return grid;
    }

    private JLabel criarCard(JPanel parent, String titulo, String valor, Color cor) {
        JPanel card = new JPanel(new BorderLayout(0, 8));
        card.setBackground(Cores.FUNDO_PAINEL);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(cor.darker(), 1),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setForeground(Cores.TEXTO_SECUNDARIO);
        lblTitulo.setFont(Cores.FONTE_NORMAL.deriveFont(12f));

        JLabel lblValor = new JLabel(valor, SwingConstants.RIGHT);
        lblValor.setForeground(cor);
        lblValor.setFont(Cores.FONTE_NORMAL.deriveFont(Font.BOLD, 28f));

        card.add(lblTitulo, BorderLayout.NORTH);
        card.add(lblValor,  BorderLayout.CENTER);

        parent.add(card);
        return lblValor;
    }

    private JPanel criarInadimplentes() {
        JPanel painel = new JPanel(new BorderLayout(0, 8));
        painel.setBackground(Cores.FUNDO);
        painel.setPreferredSize(new Dimension(0, 200));

        JLabel titulo = new JLabel("⚠ Alunos com Pagamento em Atraso");
        titulo.setForeground(new Color(0xFF006E));
        titulo.setFont(Cores.FONTE_NORMAL.deriveFont(Font.BOLD, 14f));
        titulo.setBorder(BorderFactory.createEmptyBorder(12, 0, 4, 0));
        painel.add(titulo, BorderLayout.NORTH);

        String[] colunas = {"Aluno", "Plano", "Vencimento"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabela = new JTable(tableModel);
        Cores.estilizarTabela(tabela);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.getViewport().setBackground(Cores.FUNDO_CAMPO);
        scroll.setBorder(BorderFactory.createLineBorder(Cores.BORDA));
        painel.add(scroll, BorderLayout.CENTER);

        JButton btnAtualizar = Cores.botaoSecundario("🔄 Atualizar Relatório");
        btnAtualizar.addActionListener(e -> atualizar());
        btnAtualizar.setPreferredSize(new Dimension(200, 36));
        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        south.setBackground(Cores.FUNDO);
        south.add(btnAtualizar);
        painel.add(south, BorderLayout.SOUTH);

        return painel;
    }

    public void atualizar() {
        lblAlunos.setText(String.valueOf(db.totalAlunos()));
        lblInstrutores.setText(String.valueOf(db.totalInstrutores()));
        lblMatriculas.setText(String.valueOf(db.totalMatriculas()));
        lblPlanos.setText(String.valueOf(db.totalPlanos()));
        lblAtraso.setText(String.valueOf(db.matriculasAtraso()));
        lblReceita.setText(String.format("%.2f", db.receitaMensal()).replace(".", ","));

        tableModel.setRowCount(0);
        List<Object[]> inads = db.inadimplentes();
        if (inads.isEmpty()) {
            tableModel.addRow(new Object[]{"Nenhum inadimplente", "—", "—"});
        } else {
            for (Object[] row : inads) {
                tableModel.addRow(row);
            }
        }
    }
}
