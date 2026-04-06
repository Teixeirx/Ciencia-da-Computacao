import numpy as np
import matplotlib.pyplot as plt


calcular_reta = lambda x, a, b: a * x + b

a, b = 2, 4

valores_x = np.sort(np.random.randint(0, 20, size=5))

valores_y = calcular_reta(valores_x, a, b)

print(f"Valores de X: {valores_x}")
print(f"Valores de Y: {valores_y}")

fig, ax = plt.subplots()

ax.plot(valores_x, valores_y, color='#1f77b4', linestyle='-', label=f'Reta y = {a}x + {b}')
ax.scatter(valores_x, valores_y, color='red', s=60, zorder=5, label='Amostras Aleatórias')

ax.set(
    title='Gráfico Linear com Pontos Aleatórios',
    xlabel='Eixo X (Variável Independente)',
    ylabel='Eixo Y (Variável Dependente)'
)

ax.grid(True, linestyle=':', alpha=0.7)
ax.legend()

plt.show()
