# Resolução gráfica de um problema de Programação Linear
# Máx Z = 200x1 + 300x2
#
# Restrições:
# 2x1 + x2 <= 20
# 4x1 <= 32
# x2 <= 10
# x1, x2 >= 0

import numpy as np
import matplotlib.pyplot as plt

# Intervalo de valores para x1
x1 = np.linspace(0, 10, 400)

# Equações das restrições
r1 = 20 - 2*x1      # 2x1 + x2 = 20
r2 = np.full_like(x1, 10)  # x2 = 10

# Criando o gráfico
plt.figure(figsize=(8,6))

# Plot das restrições
plt.plot(x1, r1, label='2x1 + x2 = 20')
plt.axvline(x=8, color='green', label='4x1 = 32')
plt.plot(x1, r2, label='x2 = 10')

# Região viável
x_vertices = [0, 0, 5, 8, 8]
y_vertices = [0,10,10,4,0]

plt.fill(x_vertices, y_vertices, color='lightblue', alpha=0.5,
         label='Região Viável')

# Vértices da região viável
vertices = [(0,0), (0,10), (5,10), (8,4), (8,0)]

# Cálculo da função objetivo
z_values = []

for x, y in vertices:
    z = 200*x + 300*y
    z_values.append((x, y, z))
    plt.plot(x, y, 'ro')
    plt.text(x+0.2, y+0.2, f'({x},{y})')

# Encontrar solução ótima
melhor = max(z_values, key=lambda item: item[2])

# Mostrar resultado
print("Valores da Função Objetivo nos vértices:\n")

for x, y, z in z_values:
    print(f"x1={x}, x2={y} --> Z={z}")

print("\nSolução Ótima:")
print(f"x1 = {melhor[0]}")
print(f"x2 = {melhor[1]}")
print(f"Z máximo = {melhor[2]}")

# Configurações do gráfico
plt.xlim(0,10)
plt.ylim(0,15)
plt.xlabel('x1')
plt.ylabel('x2')
plt.title('Resolução Gráfica - Programação Linear')
plt.grid(True)
plt.legend()

plt.show()