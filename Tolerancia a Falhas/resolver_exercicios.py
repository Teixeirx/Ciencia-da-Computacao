# -*- coding: utf-8 -*-
"""
Script de resolução e destaque em amarelo das respostas dos exercícios:
1. Arquivos EXERCÍCIOS AULA 08_09 - TOLERÂNCIA DA FALHAS.pdf (20 questões)
2. Arquivos EXERCÍCIOS AULA 01_09 -TOLERÂNCIA DA FALHAS.pdf (45 questões)
3. Arquivos EXERCÍCIOS AULA 22_09 - TOLERÂNCIA DA FALHAS.docx (60 questões)
"""

import os
import pymupdf
import docx
from docx.enum.text import WD_COLOR_INDEX

def highlight_pdf_page_scoped(pdf_path, answers_with_page):
    """
    answers_with_page: dict de {q_num: (page_index_0_based, answer_text)}
    """
    print(f"\n--- Processando PDF: {pdf_path} ---")
    doc = pymupdf.open(pdf_path)
    
    # Limpa anotações prévias
    for page in doc:
        while page.first_annot:
            page.delete_annot(page.first_annot)
            
    total_found = 0
    for q_num, (page_idx, ans_text) in answers_with_page.items():
        page = doc[page_idx]
        rects = page.search_for(ans_text)
        if not rects:
            # Tenta prefixo e sufixo se houver quebra de linha
            prefix = ans_text[:min(35, len(ans_text))].strip()
            rects = page.search_for(prefix)
            if rects and len(ans_text) > 40:
                suffix = ans_text[-30:].strip()
                rects_suf = page.search_for(suffix)
                rects.extend(rects_suf)
                
        if rects:
            annot = page.add_highlight_annot(rects)
            annot.set_colors(stroke=(1, 1, 0)) # amarelo puro
            annot.update()
            total_found += 1
        else:
            print(f"[ALERTA] Q{q_num} na pág {page_idx+1} NÃO encontrada: {ans_text}")
            
    print(f"Total de questões marcadas: {total_found} de {len(answers_with_page)}")
    
    temp_path = pdf_path + ".tmp.pdf"
    doc.save(temp_path, garbage=4, deflate=True)
    doc.close()
    
    os.replace(temp_path, pdf_path)
    print(f"[OK] Arquivo atualizado: {pdf_path}")


def highlight_docx(docx_path, answers):
    print(f"\n--- Processando DOCX: {docx_path} ---")
    doc = docx.Document(docx_path)
    
    q_current = None
    marked_count = 0
    
    for i, p in enumerate(doc.paragraphs):
        text = p.text.strip()
        parts = text.split('.', 1)
        if len(parts) == 2 and parts[0].isdigit():
            q_current = int(parts[0])
            continue
            
        if q_current and q_current in answers:
            target_letter = answers[q_current]
            lines = text.split('\n')
            
            has_alts = any(l.strip().startswith(('A)', 'B)', 'C)', 'D)', 'E)')) for l in lines)
            if has_alts:
                p.text = ""
                for line_idx, line in enumerate(lines):
                    clean_line = line.strip()
                    is_correct = clean_line.startswith(f"{target_letter})")
                    
                    if line_idx > 0:
                        run_break = p.add_run('\n ')
                        run_break.font.name = 'Arial'
                    
                    run = p.add_run(clean_line)
                    run.font.name = 'Arial'
                    if is_correct:
                        run.font.highlight_color = WD_COLOR_INDEX.YELLOW
                        marked_count += 1
                        
                q_current = None
                
    print(f"Total de questões marcadas no DOCX: {marked_count} de {len(answers)}")
    temp_docx = docx_path + ".tmp.docx"
    doc.save(temp_docx)
    os.replace(temp_docx, docx_path)
    print(f"[OK] Arquivo atualizado: {docx_path}")


if __name__ == "__main__":
    # -------------------------------------------------------------
    # 1. RESPOSTAS AULA 08_09 (20 questões)
    # -------------------------------------------------------------
    # Mapeamento para cada página (0-indexed):
    # Pág 0: 1-6
    # Pág 1: 7-12
    # Pág 2: 13-19 (Opção C da Q13 está no topo da pág 3 / index 2)
    # Pág 3: 20
    ans_08_09 = {
        1: (0, "B) Preservar informações importantes mesmo diante de determinadas falhas do sistema."),
        2: (0, "C) Porque podem apresentar diferentes tipos de falhas e corrupção de dados."),
        3: (0, "B) Bons ou ruins, dependendo da integridade da informação armazenada."),
        4: (0, "C) Escrever determinado dado em um endereço do armazenamento."),
        5: (0, "B) Uma operação apresenta erro temporariamente, mas pode funcionar em uma nova tentativa."),
        6: (0, "A) Uma página ou setor torna-se corrompido e seus dados podem não ser lidos corretamente."),
        7: (1, "C) O conteúdo pode continuar intacto, mas tornar-se temporariamente inacessível."),
        8: (1, "B) Soft Read Error."),
        9: (1, "B) Sucessivas operações de leitura continuam retornando erro."),
        10: (1, "B) Porque a leitura pode retornar uma informação incorreta como se estivesse correta."),
        11: (1, "C) A página permanece inalterada apesar da tentativa de escrita."),
        12: (1, "B) Conjunto de páginas que podem ser afetadas por determinado evento de deterioração."),
        13: (2, "C) Não é possível tolerar absolutamente todos os tipos de falha, pois vários componentes podem falhar simultaneamente."),
        14: (2, "C) Mantidos como imagens idênticas em dispositivos separados."),
        15: (2, "B) Permitir que os dados continuem acessíveis mesmo se um dos discos apresentar falha."),
        16: (2, "C) Striping."),
        17: (2, "C) Os dados do conjunto podem ser comprometidos, pois RAID 0 não possui redundância."),
        18: (2, "B) Utilizar espelhamento, mantendo cópias dos dados em discos diferentes."),
        19: (2, "B) Utilizar distribuição em nível de bits e mecanismos de correção de erros, como códigos de Hamming."),
        20: (3, "C) Auxiliar na reconstrução de dados quando ocorre a falha de um disco.")
    }
    
    # -------------------------------------------------------------
    # 2. RESPOSTAS AULA 01_09 (45 questões)
    # -------------------------------------------------------------
    # Mapeamento para cada página (0-indexed):
    # Pág 0 (p1): 1-4
    # Pág 1 (p2): 5-9
    # Pág 2 (p3): 10-14
    # Pág 3 (p4): 15-19
    # Pág 4 (p5): 20-24
    # Pág 5 (p6): 25-29
    # Pág 6 (p7): 30-34
    # Pág 7 (p8): 35-39
    # Pág 8 (p9): 40-44
    # Pág 9 (p10): 45
    ans_01_09 = {
        1: (0, "B) Coordenar processos, threads, máquinas ou serviços que trabalham em conjunto."),
        2: (0, "C) O resultado de uma operação depende da ordem ou do momento em que processos concorrentes executam suas ações."),
        3: (0, "C) Condição de corrida."),
        4: (0, "B) Ao trecho do programa que acessa ou altera um recurso compartilhado."),
        5: (1, "B) Apenas um processo por vez acesse determinada região crítica."),
        6: (1, "C) Mutex, semáforos, locks e monitores."),
        7: (1, "A) Outros processos podem permanecer esperando pelo recurso."),
        8: (1, "B) Um lock associado a um período de validade ou timeout."),
        9: (1, "B) Porque pequenas diferenças podem fazer eventos parecerem ter ocorrido em uma ordem diferente da real."),
        10: (2, "C) Auxiliar na sincronização dos relógios físicos."),
        11: (2, "C) relogio = max(relogio local, timestamp recebido) + 1"),
        12: (2, "C) Estabelecer uma ordem lógica entre eventos relacionados."),
        13: (2, "A) Todos os processos precisam concluir uma fase antes de prosseguir para a próxima."),
        14: (2, "B) Os demais processos permanecerem esperando indefinidamente."),
        15: (3, "B) Timeout, detecção de falhas e reconfiguração."),
        16: (3, "B) Distribuir tarefas e organizar decisões."),
        17: (3, "C) O processo com maior identificador ativo."),
        18: (3, "B) Fazer diferentes componentes concordarem sobre um valor ou decisão."),
        19: (3, "C) Perda."),
        20: (4, "B) Duplicação."),
        21: (4, "A) Reordenação."),
        22: (4, "B) Uma confirmação de recebimento."),
        23: (4, "B) Envia a mensagem e aguarda uma confirmação do receptor."),
        24: (4, "A) O emissor pode retransmitir a mensagem."),
        25: (5, "B) A transferência pode ser executada novamente."),
        26: (5, "B) Permitir reconhecer mensagens que já foram processadas."),
        27: (5, "B) Reconhecer uma mensagem repetida e evitar repetir sua operação."),
        28: (5, "B) Executá-la diversas vezes produz o mesmo resultado lógico de executá-la uma única vez."),
        29: (5, "C) status = \"pago\""),
        30: (6, "B) Detectar mensagens faltantes, duplicadas ou fora de ordem."),
        31: (6, "A) Uma mensagem periódica indicando que um componente continua ativo."),
        32: (6, "C) Não, pois pode ter ocorrido atraso, congestionamento ou perda da mensagem."),
        33: (6, "C) No máximo uma vez."),
        34: (6, "B) Retransmissões podem fazer a operação ocorrer mais de uma vez."),
        35: (7, "B) Uma única execução lógica."),
        36: (7, "C) ID, deduplicação e estado persistente."),
        37: (7, "A) Porque o sistema precisa conseguir lembrar o que já aconteceu mesmo após determinadas falhas."),
        38: (7, "B) Registrar operações importantes que podem auxiliar na recuperação."),
        39: (7, "B) Um estado intermediário consistente que pode ser utilizado na recuperação."),
        40: (8, "A) Manter cópias em componentes distintos."),
        41: (8, "B) Deduplicação."),
        42: (8, "B) Registrar → debitar → enviar → deduplicar → creditar → confirmar."),
        43: (8, "A) Número de sequência."),
        44: (8, "A) A necessidade de resolver estados incertos na comunicação distribuída."),
        45: (9, "C) Exclusão mútua.")
    }

    # -------------------------------------------------------------
    # 3. RESPOSTAS AULA 22_09 (60 questões)
    # -------------------------------------------------------------
    ans_22_09 = {
        1: 'B', 2: 'D', 3: 'A', 4: 'E', 5: 'C',
        6: 'B', 7: 'D', 8: 'A', 9: 'E', 10: 'C',
        11: 'B', 12: 'D', 13: 'A', 14: 'E', 15: 'C',
        16: 'B', 17: 'D', 18: 'A', 19: 'E', 20: 'C',
        21: 'B', 22: 'D', 23: 'A', 24: 'E', 25: 'C',
        26: 'B', 27: 'D', 28: 'A', 29: 'E', 30: 'C',
        31: 'B', 32: 'D', 33: 'A', 34: 'E', 35: 'C',
        36: 'B', 37: 'D', 38: 'A', 39: 'E', 40: 'C',
        41: 'B', 42: 'D', 43: 'A', 44: 'E', 45: 'C',
        46: 'B', 47: 'D', 48: 'A', 49: 'E', 50: 'C',
        51: 'B', 52: 'D', 53: 'A', 54: 'E', 55: 'C',
        56: 'B', 57: 'D', 58: 'A', 59: 'E', 60: 'C'
    }

    base_dir = os.path.dirname(__file__)
    pdf_08_09 = os.path.join(base_dir, "Arquivos EXERCÍCIOS AULA 08_09 - TOLERÂNCIA DA FALHAS.pdf")
    pdf_01_09 = os.path.join(base_dir, "Arquivos EXERCÍCIOS AULA 01_09 -TOLERÂNCIA DA FALHAS.pdf")
    docx_22_09 = os.path.join(base_dir, "Arquivos EXERCÍCIOS AULA 22_09 - TOLERÂNCIA DA FALHAS.docx")

    highlight_pdf_page_scoped(pdf_08_09, ans_08_09)
    highlight_pdf_page_scoped(pdf_01_09, ans_01_09)
    highlight_docx(docx_22_09, ans_22_09)
