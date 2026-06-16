package fitmanagerpro.gui;

import fitmanagerpro.database.DatabaseManager;
import fitmanagerpro.services.AcademiaService;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;

/**
 * Janela principal do FitManager Pro.
 * Usa JTabbedPane com 6 abas e tema escuro premium.
 */
public class MainFrame extends JFrame {

    private final DatabaseManager db;
    private final AcademiaService academia;

    private AlunosPanel      alunosPanel;
    private InstrutoresPanel instrutoresPanel;
    private PlanosPanel      planosPanel;
    private MatriculasPanel  matriculasPanel;
    private PagamentosPanel  pagamentosPanel;
    private RelatorioPanel   relatorioPanel;

    public MainFrame(DatabaseManager db, AcademiaService academia) {
        this.db       = db;
        this.academia = academia;

        aplicarUIManager();
        configurarJanela();
        construirUI();
        setVisible(true);
    }

    /** Aplica as customizações globais de UI antes de criar os componentes. */
    private void aplicarUIManager() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {}

        UIManager.put("Panel.background",               new ColorUIResource(Cores.FUNDO));
        UIManager.put("Label.foreground",               new ColorUIResource(Cores.TEXTO_CLARO));

        // Abas
        UIManager.put("TabbedPane.background",          new ColorUIResource(Cores.FUNDO));
        UIManager.put("TabbedPane.foreground",          new ColorUIResource(Cores.TEXTO_CLARO));
        UIManager.put("TabbedPane.selected",            new ColorUIResource(Cores.FUNDO_PAINEL));
        UIManager.put("TabbedPane.selectedForeground",  new ColorUIResource(Cores.ACENTO));
        UIManager.put("TabbedPane.unselectedBackground",new ColorUIResource(Cores.FUNDO));
        UIManager.put("TabbedPane.tabAreaBackground",   new ColorUIResource(Cores.FUNDO));
        UIManager.put("TabbedPane.contentAreaColor",    new ColorUIResource(Cores.FUNDO_PAINEL));
        UIManager.put("TabbedPane.borderHightlightColor",new ColorUIResource(Cores.BORDA));
        UIManager.put("TabbedPane.darkShadow",          new ColorUIResource(Cores.BORDA));
        UIManager.put("TabbedPane.shadow",              new ColorUIResource(Cores.FUNDO));
        UIManager.put("TabbedPane.light",               new ColorUIResource(Cores.FUNDO_PAINEL));
        UIManager.put("TabbedPane.highlight",           new ColorUIResource(Cores.FUNDO_PAINEL));
        UIManager.put("TabbedPane.font",                Cores.FONTE_BOLD);
        UIManager.put("TabbedPane.tabInsets",           new Insets(0, 0, 0, 0));

        // OptionPane
        UIManager.put("OptionPane.background",          new ColorUIResource(Cores.FUNDO_PAINEL));
        UIManager.put("OptionPane.messageForeground",   new ColorUIResource(Cores.TEXTO_CLARO));
        UIManager.put("Button.background",              new ColorUIResource(Cores.FUNDO_CAMPO));
        UIManager.put("Button.foreground",              new ColorUIResource(Cores.TEXTO_CLARO));
        UIManager.put("Button.font",                    Cores.FONTE_NORMAL);

        // ScrollBar discreta
        UIManager.put("ScrollBar.background",           new ColorUIResource(Cores.FUNDO_PAINEL));
        UIManager.put("ScrollBar.thumb",                new ColorUIResource(Cores.BORDA));
        UIManager.put("ScrollBar.track",                new ColorUIResource(Cores.FUNDO_PAINEL));

        // ComboBox
        UIManager.put("ComboBox.background",            new ColorUIResource(Cores.FUNDO_CAMPO));
        UIManager.put("ComboBox.foreground",            new ColorUIResource(Cores.TEXTO_CLARO));
        UIManager.put("ComboBox.selectionBackground",   new ColorUIResource(Cores.ACENTO));
        UIManager.put("ComboBox.selectionForeground",   new ColorUIResource(Color.WHITE));
        UIManager.put("ComboBox.font",                  Cores.FONTE_NORMAL);
    }

    private void configurarJanela() {
        setTitle("FitManager Pro — Sistema de Gestão de Academia");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1140, 720);
        setMinimumSize(new Dimension(960, 620));
        setLocationRelativeTo(null);
        getContentPane().setBackground(Cores.FUNDO);
    }

    private void construirUI() {
        setLayout(new BorderLayout());

        // ── Cabeçalho ────────────────────────────────────────────────────────
        JPanel header = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Linha de acento no fundo do header
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setPaint(new GradientPaint(
                        0, getHeight() - 2, Cores.ACENTO,
                        300, getHeight() - 2, new Color(0, 0, 0, 0)));
                g2.fillRect(0, getHeight() - 2, getWidth(), 2);
                g2.dispose();
            }
        };
        header.setBackground(Cores.FUNDO_PAINEL);
        header.setPreferredSize(new Dimension(0, 68));
        header.setBorder(BorderFactory.createEmptyBorder(0, 24, 0, 24));

        // Logo
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        logoPanel.setOpaque(false);

        JLabel bullet = new JLabel("◉");
        bullet.setFont(new Font("Segoe UI", Font.BOLD, 24));
        bullet.setForeground(Cores.ACENTO);

        JLabel logoText = new JLabel("<html><font color='white'> FitManager</font> <font color='#0096FF'>Pro</font></html>");
        logoText.setFont(new Font("Segoe UI", Font.BOLD, 22));

        logoPanel.add(bullet);
        logoPanel.add(logoText);

        // Lado direito
        JLabel subLabel = new JLabel("Sistema de Gestão de Academia");
        subLabel.setFont(Cores.FONTE_PEQUENA);
        subLabel.setForeground(Cores.TEXTO_SECUNDARIO);

        header.add(logoPanel, BorderLayout.WEST);
        header.add(subLabel,  BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // ── Abas ─────────────────────────────────────────────────────────────
        JTabbedPane abas = new JTabbedPane(JTabbedPane.LEFT);
        abas.setBackground(Cores.FUNDO);
        abas.setForeground(Cores.TEXTO_CLARO);
        abas.setFont(Cores.FONTE_BOLD);

        alunosPanel      = new AlunosPanel(db);
        instrutoresPanel = new InstrutoresPanel(db);
        planosPanel      = new PlanosPanel(db);
        matriculasPanel  = new MatriculasPanel(db, academia);
        pagamentosPanel  = new PagamentosPanel(db);
        relatorioPanel   = new RelatorioPanel(db);

        abas.addTab("<html><div style='padding: 10px 16px; width: 130px; font-size: 11px;'>🧍  Alunos</div></html>",       alunosPanel);
        abas.addTab("<html><div style='padding: 10px 16px; width: 130px; font-size: 11px;'>🏋  Instrutores</div></html>",  instrutoresPanel);
        abas.addTab("<html><div style='padding: 10px 16px; width: 130px; font-size: 11px;'>📋  Planos</div></html>",       planosPanel);
        abas.addTab("<html><div style='padding: 10px 16px; width: 130px; font-size: 11px;'>📝  Matrículas</div></html>",   matriculasPanel);
        abas.addTab("<html><div style='padding: 10px 16px; width: 130px; font-size: 11px;'>💰  Pagamentos</div></html>",   pagamentosPanel);
        abas.addTab("<html><div style='padding: 10px 16px; width: 130px; font-size: 11px;'>📊  Relatório</div></html>",    relatorioPanel);

        abas.addChangeListener(e -> {
            int idx = abas.getSelectedIndex();
            switch (idx) {
                case 0 -> alunosPanel.carregarDados();
                case 1 -> instrutoresPanel.carregarDados();
                case 2 -> planosPanel.carregarDados();
                case 3 -> { matriculasPanel.atualizarCombos();          matriculasPanel.carregarDados(); }
                case 4 -> { pagamentosPanel.atualizarComboMatriculas(); pagamentosPanel.carregarDados(); }
                case 5 -> relatorioPanel.atualizar();
            }
        });

        add(abas, BorderLayout.CENTER);

        // ── Rodapé ───────────────────────────────────────────────────────────
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(new Color(0x0B0E17));
        footer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, Cores.BORDA),
                BorderFactory.createEmptyBorder(6, 20, 6, 20)));

        JLabel credits = new JLabel("Gabriel Teixeira  ·  POO 2026  ·  Prof. Rafael Bisogno");
        credits.setFont(Cores.FONTE_PEQUENA);
        credits.setForeground(Cores.TEXTO_APAGADO);

        JLabel version = new JLabel("FitManager Pro  v1.0");
        version.setFont(Cores.FONTE_PEQUENA);
        version.setForeground(Cores.TEXTO_APAGADO);

        footer.add(credits, BorderLayout.WEST);
        footer.add(version, BorderLayout.EAST);
        add(footer, BorderLayout.SOUTH);
    }
}
