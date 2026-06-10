# 🚀 Cenário Espacial — OpenGL Moderno

**Trabalho Final — Computação Gráfica**  
Curso de Ciência da Computação — UFN 2026-01  
Professor: André F. dos Santos

---

## 🎯 Descrição

Cena 3D interativa do espaço sideral desenvolvida com **OpenGL Moderno** em Python.

### Elementos do cenário:
| Objeto | Descrição |
|---|---|
| ⭐ Fundo estrelado | Skybox esférico com texturas da Via Láctea em alta resolução |
| 🌍 Terra | Planeta central, texturizado com rotação no próprio eixo |
| 🔴 Marte | Planeta secundário |
| 🪨 Cinturão de Asteroides | Asteroides rochosos em órbita com tamanhos variados |
| 🛸 Nave Espacial | Nave Alienígena Prometheus observando o sistema (modelo .OBJ externo) |
| 🛸 OVNI | Objeto voador não identificado patrulhando o espaço (modelo .OBJ externo) |

---

## ▶️ Como executar

### 1. Instalar dependências
```bash
pip install -r requirements.txt
```

### 2. Executar o cenário
```bash
python cenario_espacial.py
```

---

## 🎮 Controles

| Tecla | Ação |
|---|---|
| `W` / `S` ou `↑` `↓` | Mover frente / trás |
| `A` / `D` ou `←` `→` | Mover esquerda / direita |
| `Q` / `E` | Subir / descer |
| `Mouse` | Olhar ao redor (câmera FPS) |
| `+` / `-` | Aumentar / diminuir velocidade |
| `ESC` | Sair |

---

## 🏗️ Estrutura do projeto

```
cenario_espacial/
├── cenario_espacial.py     # Arquivo principal (cena + loop)
├── Camera.py               # Câmera FPS (yaw/pitch/WASD)
├── ObjLoaderSimple.py      # Carregador de arquivos .OBJ
├── TextureLoader.py        # Carregador de texturas PNG/JPG
├── GeometryGenerator.py    # Gerador de geometrias procedurais
├── requirements.txt        # Dependências Python
├── shaders/
│   ├── vertex_shader.glsl  # Shader de vértice (MVP + UV + Normal)
│   └── fragment_shader.glsl# Shader de fragmento (Phong + Emissão)
├── texturas/               # Texturas JPG do Solar System Scope
│   ├── starfield.jpg
│   ├── terra.jpg
│   ├── marte.jpg
│   ├── asteroide.jpg
│   ├── metal.jpg
│   └── jupiter.jpg
└── objetos/                # Modelos .OBJ externos
    ├── nave/
    │   ├── prometheus.obj
    │   └── prometheus.mtl
    └── ovni/
        ├── UFO_Empty.obj
        └── UFO_Empty.mtl
```

---

## ⚙️ Tecnologias utilizadas

- **OpenGL 3.3 Core** — Renderização 3D moderna
- **GLFW** — Criação de janela e captura de input
- **VAO / VBO** — Gerenciamento de geometria na GPU
- **Shaders GLSL** — Programação direta na GPU
- **Modelo de iluminação Phong** — Ambiente + Difusa + Emissão
- **pyrr** — Matrizes e vetores 3D (numpy-based)
- **Pillow** — Carregamento e geração de texturas

---

## 📋 Requisitos atendidos

- ✅ Utilização de OpenGL Moderno
- ✅ Utilização de shaders (vertex + fragment)
- ✅ Câmera com movimentação (FPS: WASD + mouse)
- ✅ Objetos 3D (planetas, asteroides, nave via OBJ, skybox)
- ✅ Texturas (carregadas via Pillow do armazenamento local)
- ✅ Organização do código (módulos separados)
- ✅ Comentários explicativos (extensivos em todos arquivos)
- ✅ Renderização de cenário completo
