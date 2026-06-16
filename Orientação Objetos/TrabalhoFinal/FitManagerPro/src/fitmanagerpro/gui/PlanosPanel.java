package fitmanagerpro.gui;

import fitmanagerpro.database.DatabaseManager;
import fitmanagerpro.exceptions.PlanoInvalidoException;
import fitmanagerpro.models.Plano;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/** Aba de gerenciamento de planos de treinamento. */
public class PlanosPanel extends JPanel {

    private final DatabaseManager db;
    private DefaultTableModel tableModel;

    public PlanosPanel(DatabaseManager db) {
        this.db = db;
        setBackground(Cores.FUNDO);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        add(criarTitulo(), BorderLayout.NORTH);
        add(criarTabela(), BorderLayout.CENTER);
        add(criarPainel(), BorderLayout.EAST);

        carregarDados();
    }

    private JLabel criarTitulo() {
        JLabel lbl = new JLabel("📋 Planos de Treinamento");
        lbl.setFont(Cores.FONTE_TITULO);
        lbl.setForeground(Cores.TEXTO_CLARO);
        lbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        return lbl;
    }

    private JScrollPane criarTabela() {
        String[] colunas = {"#", "Nome", "Preço/mês", "Duração (meses)", "Descrição"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabela = new JTable(tableModel);
        Cores.estilizarTabela(tabela);
        tabela.getColumnModel().getColumn(0).setMaxWidth(40);
        tabela.getColumnModel().getColumn(2).setMaxWidth(100);
        tabela.getColumnModel().getColumn(3).setMaxWidth(130);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.getViewport().setBackground(Cores.FUNDO_CAMPO);
        scroll.setBorder(BorderFactory.createLineBorder(Cores.BORDA));
        return scroll;
    }

    private JPanel criarPainel() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(Cores.FUNDO_PAINEL);
        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Cores.BORDA),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)));
        painel.setPreferredSize(new Dimension(260, 0));

        GridBagConstraints gbc = Cores.gbc();

        Cores.addLabel(painel, "Adicionar Plano Padrão", gbc, 0, 0, true);

        // Descrição dos planos
        String[] infos = {
            "Básico  — R$ 80,00/mês",
            "Musculação e cardio",
            "",
            "Premium — R$ 150,00/mês",
            "Musculação + aulas + avaliação",
            "",
            "VIP     — R$ 280,00/mês",
            "Tudo + personal + nutricionista"
        };
        for (int i = 0; i < infos.length; i++) {
            JLabel l = new JLabel(infos[i]);
            l.setForeground(infos[i].startsWith(" ") || infos[i].isEmpty()
                    ? Cores.TEXTO_SECUNDARIO : Cores.TEXTO_CLARO);
            l.setFont(Cores.FONTE_NORMAL.deriveFont(12f));
            gbc.gridx = 0; gbc.gridy = 1 + i;
            gbc.insets = new Insets(1, 0, 1, 0);
            painel.add(l, gbc);
        }

        String[] tipos = {Plano.BASICO, Plano.PREMIUM, Plano.VIP};
        for (int i = 0; i < tipos.length; i++) {
            String tipo = tipos[i];
            JButton btn = Cores.botaoPrimario("➕ Adicionar " + tipo);
            btn.addActionListener(e -> adicionarPlano(tipo));
            gbc.gridy = 10 + i; gbc.insets = new Insets(6, 0, 4, 0);
            painel.add(btn, gbc);
        }

        gbc.gridy = 14; gbc.weighty = 1;
        painel.add(new JLabel(), gbc);

        return painel;
    }

    private void adicionarPlano(String tipo) {
        try {
            Plano plano = new Plano(tipo);
            db.salvarPlano(plano);
            carregarDados();
            JOptionPane.showMessageDialog(this, "Plano \"" + tipo + "\" adicionado!", "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (PlanoInvalidoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void carregarDados() {
        tableModel.setRowCount(0);
        List<Plano> planos = db.carregarPlanos();
        int idx = 1;
        for (Plano p : planos) {
            tableModel.addRow(new Object[]{
                idx++,
                p.getNome(),
                String.format("R$ %.2f", p.getPreco()),
                p.getDuracaoMeses(),
                p.getDescricao()
            });
        }
    }
}
