# ==========================================================
# TextureLoader.py
# ==========================================================
#
# Responsável por:
#   - Abrir imagem com Pillow (PIL)
#   - Criar ID de textura no OpenGL
#   - Configurar parâmetros (wrap, filtro)
#   - Enviar pixels para a GPU
#
# Suporta: PNG, JPG, BMP, TGA
#
# ==========================================================

from OpenGL.GL import *
from PIL import Image


# ==========================================================
# FUNÇÃO: load_texture
# ==========================================================
# Parâmetros:
#   path    : caminho do arquivo de imagem
#   texture : ID da textura OpenGL (gerado com glGenTextures)
#
# Retorna:
#   Textura configurada e enviada para a GPU
# ==========================================================

def load_texture(path, texture):

    # ======================================================
    # ATIVA TEXTURA
    # ======================================================
    # Vincula o ID recebido como textura 2D ativa.
    # Todas configurações seguintes se aplicam a ela.
    # ======================================================

    glBindTexture(GL_TEXTURE_2D, texture)

    # ======================================================
    # CONFIGURAÇÃO DE WRAP (REPETIÇÃO)
    # ======================================================
    # GL_REPEAT: repete a textura ao ultrapassar UV [0,1]
    # Útil para superfícies grandes (chão, parede, etc.)
    # ======================================================

    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT)
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT)

    # ======================================================
    # CONFIGURAÇÃO DE FILTRAGEM
    # ======================================================
    # GL_LINEAR_MIPMAP_LINEAR : filtragem suave ao diminuir
    # GL_LINEAR               : filtragem suave ao ampliar
    # ======================================================

    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR)
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)

    # ======================================================
    # ABERTURA DA IMAGEM
    # ======================================================
    # Pillow abre o arquivo e converte para RGBA.
    # flip vertical: OpenGL começa do canto inferior esquerdo.
    # ======================================================

    img = Image.open(path).convert("RGBA")
    img = img.transpose(Image.FLIP_TOP_BOTTOM)

    img_data = img.tobytes()
    width, height = img.size

    # ======================================================
    # ENVIO PARA A GPU
    # ======================================================
    # glTexImage2D: transfere os pixels para a textura
    # Formato interno e de entrada: GL_RGBA (4 canais)
    # ======================================================

    glTexImage2D(
        GL_TEXTURE_2D,   # alvo
        0,               # mipmap nível 0 (original)
        GL_RGBA,         # formato interno
        width,           # largura
        height,          # altura
        0,               # borda (sempre 0)
        GL_RGBA,         # formato dos dados
        GL_UNSIGNED_BYTE,# tipo dos dados
        img_data         # pixels
    )

    # Gera mipmaps automaticamente (reduz artefatos de aliasing)
    glGenerateMipmap(GL_TEXTURE_2D)
