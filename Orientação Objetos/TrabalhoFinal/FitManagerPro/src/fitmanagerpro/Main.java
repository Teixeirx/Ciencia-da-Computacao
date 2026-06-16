package fitmanagerpro;

import fitmanagerpro.database.DatabaseManager;
import fitmanagerpro.gui.MainFrame;
import fitmanagerpro.services.AcademiaService;

import javax.swing.*;

/**
 * Ponto de entrada da aplicação FitManager Pro.
 *
 * Requisito 1 — Instanciação de múltiplos objetos no fluxo principal (main).
 * Inicia o banco de dados SQLite e abre a janela Swing na Event Dispatch Thread.
 */
public class Main {

    public static void main(String[] args) {
        // Inicia banco e serviço central — Requisito 1
        DatabaseManager db       = new DatabaseManager();
        AcademiaService academia = new AcademiaService();

        // Abre a janela na Event Dispatch Thread (prática obrigatória em Swing)
        SwingUtilities.invokeLater(() -> new MainFrame(db, academia));
    }
}
