package fitmanagerpro.interfaces;

/**
 * Interface que define o contrato para geração de relatórios no sistema.
 * Requisito 6 — Interface: qualquer classe que gere relatórios deve implementar
 * este contrato, garantindo que os métodos essenciais estejam presentes.
 */
public interface Relatorio {

    /**
     * Exibe um relatório geral do sistema (totais, receita, inadimplentes).
     */
    void gerarRelatorioGeral();

    /**
     * Exibe um relatório resumido de um item específico.
     *
     * @param identificador identificador do item (ex.: ID do aluno, nome do plano)
     */
    void gerarRelatorioItem(String identificador);
}
