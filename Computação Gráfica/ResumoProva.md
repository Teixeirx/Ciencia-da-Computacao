# 📚 Revisão – Computação Gráfica

## 🧠 1. Transformações Geométricas
Transformações são usadas para modificar objetos no espaço 2D ou 3D.

### Tipos principais:
- **Translação** → move o objeto (posição)
- **Rotação** → gira o objeto
- **Escala** → altera o tamanho

---

## 🔢 2. Matrizes em Computação Gráfica
As transformações são aplicadas usando **matrizes**.

### Por que usar matrizes?
- Permitem representar transformações matematicamente
- Facilitam aplicar várias transformações juntas
- Funcionam em 2D e 3D

### Importante:
- Multiplicação de matriz por ponto (linha × coluna)
- Uso de matrizes **4x4** em 3D

---

## 📍 3. Translação
Move um objeto no espaço.

### Matriz de translação (3D):

| 1 0 0 Tx |
| 0 1 0 Ty |
| 0 0 1 Tz |
| 0 0 0 1 |

---

## 📏 4. Escala
Altera o tamanho do objeto.

### Fórmulas:
- x' = x * Sx  
- y' = y * Sy  
- z' = z * Sz  

### Matriz:
| Sx 0 0 0 |
| 0 Sy 0 0 |
| 0 0 Sz 0 |
| 0 0 0 1 |


---

## 🔄 5. Rotação
Gira o objeto em torno de um eixo (X, Y ou Z).

### Exemplo (rotação em X):
| 1 0 0 0 |
| 0 cosθ -senθ 0 |
| 0 senθ cosθ 0 |
| 0 0 0 1 |


👉 Dica:  
- X fixo → rotação no eixo X  
- Y fixo → rotação no eixo Y  
- Z fixo → rotação no eixo Z  

---

## ✂️ 6. Clipping
Processo de remover partes que não aparecem na tela.

### Para que serve:
- Melhorar desempenho
- Mostrar apenas o que está visível

---

## 🧱 7. Rasterização
Converte formas matemáticas (vetores) em **pixels**.

### Exemplo:
- Linha → vários pontos na tela

### Caso importante:
- Se **m ≤ 1**:
  - X cresce mais rápido que Y
  - Calcula-se Y para cada X

---

## ⚪ 8. Círculos em Computação Gráfica
Importantes para:
- Criar curvas suaves
- Modelar superfícies
- Representar objetos naturais

---

## 🎥 9. Pipeline Gráfico
Etapas para renderizar um objeto:

1. Modelagem
2. Transformações
3. Projeção
4. Clipping
5. Rasterização

---

## 🧭 10. Projeções

### 🔹 Projeção Paralela
- Linhas paralelas continuam paralelas
- Usada em engenharia e CAD

### 🔹 Projeção Perspectiva
- Objetos distantes parecem menores
- Usada em jogos e gráficos realistas

---
