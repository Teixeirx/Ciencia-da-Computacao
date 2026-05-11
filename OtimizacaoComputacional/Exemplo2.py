# ==========================================================
# RESOLUÇÃO GRÁFICA - PROGRAMAÇÃO LINEAR
# Exercício 2
#
# Função Objetivo:
# Max Z = 80xa + 60xb
#
# Restrições:
# 4xa + 6xb <= 24
# 4xa + 2xb <= 16
# xb <= 3
# xa >= 0 ; xb >= 0
# ==========================================================

import numpy as np
import matplotlib.pyplot as plt

# ----------------------------------------------------------
# Definindo valores de xa
# ----------------------------------------------------------

xa = np.linspace(0, 6, 400)

# ----------------------------------------------------------
# Equações das restrições
# ----------------------------------------------------------

r1 = (24 - 4*xa) / 6      # 4xa + 6xb = 24
r2 = (16 - 4*xa) / 2      # 4xa + 2xb = 16
r3 = np.full_like(xa, 3)  # xb = 3

# ----------------------------------------------------------
# Criando gráfico
# ----------------------------------------------------------

plt.figure(figsize=(9,7))

# Retas das restrições
plt.plot(xa, r1, label='4xa + 6xb = 24')
plt.plot(xa, r2, label='4xa + 2xb = 16')
plt.plot(xa, r3, label='xb = 3')

# ----------------------------------------------------------
# Região viável
# ----------------------------------------------------------

x_vertices = [0, 0, 1.5, 3, 4]
y_vertices = [0, 3, 3, 2, 0]

plt.fill(x_vertices, y_vertices,
         color='lightblue',
         alpha=0.5,
         label='Região Viável')

# ----------------------------------------------------------
# Vértices da solução
# ----------------------------------------------------------

vertices = [
    (0,0),
    (0,3),
    (1.5,3),
    (3,2),
    (4,0)
]

print("\n======================================")
print("VÉRTICES DA REGIÃO VIÁVEL")
print("======================================")

for x, y in vertices:
    print(f"({x}, {y})")

# ----------------------------------------------------------
# Tabela da Função Objetivo
# ----------------------------------------------------------

print("\n======================================")
print("TABELA DA FUNÇÃO OBJETIVO")
print("======================================")
print("   xa      xb        Z = 80xa + 60xb")
print("--------------------------------------")

resultados = []

for x, y in vertices:

    z = 80*x + 60*y

    resultados.append((x, y, z))

    print(f"{x:5}   {y:5}        {z:8}")

    # Pontos no gráfico
    plt.plot(x, y, 'ro')
    plt.text(x+0.1, y+0.1, f'({x},{y})')

# ----------------------------------------------------------
# Melhor solução
# ----------------------------------------------------------

melhor = max(resultados, key=lambda item: item[2])

print("\n======================================")
print("SOLUÇÃO ÓTIMA")
print("======================================")

print(f"xa = {melhor[0]}")
print(f"xb = {melhor[1]}")
print(f"Z máximo = {melhor[2]}")

# ----------------------------------------------------------
# Configurações do gráfico
# ----------------------------------------------------------

plt.xlim(0,6)
plt.ylim(0,5)

plt.xlabel('xa')
plt.ylabel('xb')

plt.title('Resolução Gráfica - Programação Linear')

plt.grid(True)
plt.legend()

plt.show()