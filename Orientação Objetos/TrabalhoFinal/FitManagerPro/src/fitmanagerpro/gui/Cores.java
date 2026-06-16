package fitmanagerpro.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Constantes de cores, fontes e helpers de estilo — tema premium para FitManager Pro.
 * Paleta escura com alto contraste, acentos vibrantes e separação visual clara.
 */
public class Cores {

    // ── Paleta principal ─────────────────────────────────────────────────────
    /** Fundo principal — quase preto com toque azul-escuro */
    public static final Color FUNDO            = new Color(0x0E1117);
    /** Painéis e cards — cinza-azulado escuro */
    public static final Color FUNDO_PAINEL     = new Color(0x181D2A);
    /** Campos de formulário — levemente mais claro que o painel */
    public static final Color FUNDO_CAMPO      = new Color(0x1E2535);
    /** Header das colunas da tabela */
    public static final Color FUNDO_HEADER_TAB = new Color(0x12172A);
    /** Bordas sutis */
    public static final Color BORDA            = new Color(0x2A3550);
    /** Borda de foco / hover */
    public static final Color BORDA_FOCO       = new Color(0x0096FF);

    // ── Acentos ──────────────────────────────────────────────────────────────
    /** Azul vibrante — cor primária de ação (igual aos slides) */
    public static final Color ACENTO           = new Color(0x0096FF);
    public static final Color ACENTO_HOVER     = new Color(0x33B5FF);
    /** Verde-menta — confirmação / sucesso */
    public static final Color VERDE            = new Color(0x00E5A0);
    /** Vermelho-coral — atenção / exclusão */
    public static final Color VERMELHO         = new Color(0xFF4D6A);
    /** Amarelo âmbar — avisos */
    public static final Color AMARELO          = new Color(0xFFB800);

    // ── Texto ────────────────────────────────────────────────────────────────
    public static final Color TEXTO_CLARO      = new Color(0xEEF2FF);
    public static final Color TEXTO_SECUNDARIO = new Color(0x6B7AAE);
    public static final Color TEXTO_APAGADO    = new Color(0x3D4875);

    // ── Tabela ───────────────────────────────────────────────────────────────
    public static final Color LINHA_PAR        = new Color(0x181D2A);
    public static final Color LINHA_IMPAR      = new Color(0x1C2236);
    public static final Color LINHA_SELECIONADA = new Color(0x122B4D);

    // ── Fontes ───────────────────────────────────────────────────────────────
    public static final Font FONTE_TITULO  = new Font("Segoe UI", Font.BOLD, 17);
    public static final Font FONTE_NORMAL  = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONTE_BOLD    = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONTE_PEQUENA = new Font("Segoe UI", Font.PLAIN, 11);

    // ── Estilização de tabela ────────────────────────────────────────────────

    public static void estilizarTabela(JTable tabela) {
        tabela.setFont(FONTE_NORMAL);
        tabela.setForeground(TEXTO_CLARO);
        tabela.setBackground(LINHA_PAR);
        tabela.setSelectionBackground(LINHA_SELECIONADA);
        tabela.setSelectionForeground(Color.WHITE);
        tabela.setGridColor(BORDA);
        tabela.setRowHeight(30);
        tabela.setIntercellSpacing(new Dimension(0, 0));
        tabela.setShowVerticalLines(false);
        tabela.setShowHorizontalLines(true);
        tabela.setFocusable(false);

        // Header da tabela
        JTableHeader header = tabela.getTableHeader();
        header.setBackground(FUNDO_HEADER_TAB);
        header.setForeground(TEXTO_SECUNDARIO);
        header.setFont(FONTE_PEQUENA.deriveFont(Font.BOLD));
        header.setPreferredSize(new Dimension(0, 34));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDA));
        header.setReorderingAllowed(false);

        // Renderer customizado
        tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                setOpaque(true);
                setFont(FONTE_NORMAL);
                if (sel) {
                    setBackground(LINHA_SELECIONADA);
                    setForeground(TEXTO_CLARO);
                } else {
                    setBackground(row % 2 == 0 ? LINHA_PAR : LINHA_IMPAR);
                    String txt = val != null ? val.toString() : "";
                    if (txt.contains("ATRASO"))      setForeground(VERMELHO);
                    else if (txt.contains("Em dia")) setForeground(VERDE);
                    else if (txt.contains("Ativo"))  setForeground(VERDE);
                    else if (txt.contains("Inativo"))setForeground(TEXTO_APAGADO);
                    else                             setForeground(TEXTO_CLARO);
                }
                setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
                return this;
            }
        });
    }

    // ── Botões ───────────────────────────────────────────────────────────────

    /** Botão primário — violeta sólido com hover mais claro. */
    public static JButton botaoPrimario(String texto) {
        JButton btn = new JButton(texto) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(FONTE_BOLD);
        btn.setBackground(ACENTO);
        btn.setForeground(Color.WHITE);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(232, 40));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(ACENTO_HOVER); btn.repaint(); }
            public void mouseExited(MouseEvent e)  { btn.setBackground(ACENTO);       btn.repaint(); }
        });
        return btn;
    }

    /** Botão de perigo — vermelho para ações destrutivas. */
    public static JButton botaoPerigo(String texto) {
        JButton btn = new JButton(texto) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(FONTE_BOLD);
        btn.setBackground(new Color(0x3D1A22));
        btn.setForeground(VERMELHO);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(232, 38));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(new Color(0x5A2530)); btn.repaint(); }
            public void mouseExited(MouseEvent e)  { btn.setBackground(new Color(0x3D1A22)); btn.repaint(); }
        });
        return btn;
    }

    /** Botão secundário — ghost/outline para ações secundárias. */
    public static JButton botaoSecundario(String texto) {
        JButton btn = new JButton(texto) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
            @Override protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BORDA);
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
                g2.dispose();
            }
        };
        btn.setFont(FONTE_NORMAL);
        btn.setBackground(FUNDO_CAMPO);
        btn.setForeground(TEXTO_SECUNDARIO);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(true);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(232, 36));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(new Color(0x252A40)); btn.setForeground(TEXTO_CLARO); btn.repaint(); }
            public void mouseExited(MouseEvent e)  { btn.setBackground(FUNDO_CAMPO); btn.setForeground(TEXTO_SECUNDARIO); btn.repaint(); }
        });
        return btn;
    }

    // ── Helpers de layout ────────────────────────────────────────────────────

    public static GridBagConstraints gbc() {
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0; g.gridy = 0;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1; g.weighty = 0;
        g.insets = new Insets(4, 0, 4, 0);
        return g;
    }

    public static void addLabel(JPanel painel, String texto,
                                 GridBagConstraints gbc, int x, int y, boolean titulo) {
        JLabel lbl = new JLabel(texto);
        if (titulo) {
            lbl.setForeground(TEXTO_CLARO);
            lbl.setFont(FONTE_BOLD.deriveFont(14f));
            lbl.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDA));
        } else {
            lbl.setForeground(TEXTO_SECUNDARIO);
            lbl.setFont(FONTE_PEQUENA);
        }
        gbc.gridx = x; gbc.gridy = y;
        gbc.insets = titulo ? new Insets(0, 0, 14, 0) : new Insets(10, 0, 3, 0);
        painel.add(lbl, gbc);
    }

    public static JTextField addCampo(JPanel painel, String label,
                                       GridBagConstraints gbc, int x, int y) {
        addLabel(painel, label, gbc, x, y, false);

        JTextField campo = new JTextField() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        campo.setBackground(FUNDO_CAMPO);
        campo.setForeground(TEXTO_CLARO);
        campo.setCaretColor(ACENTO);
        campo.setFont(FONTE_NORMAL);
        campo.setOpaque(false);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDA, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        campo.setPreferredSize(new Dimension(0, 34));

        // Glow ao focar
        campo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                campo.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(ACENTO, 1, true),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)));
            }
            public void focusLost(java.awt.event.FocusEvent e) {
                campo.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDA, 1, true),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)));
            }
        });

        gbc.gridx = x; gbc.gridy = y + 1;
        gbc.insets = new Insets(0, 0, 2, 0);
        painel.add(campo, gbc);
        return campo;
    }
}
