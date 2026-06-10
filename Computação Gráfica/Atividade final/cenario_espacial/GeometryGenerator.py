# ==========================================================
# GeometryGenerator.py
# ==========================================================
#
# Gerador de geometrias 3D procedurais.
#
# Cria malhas simples diretamente em código Python,
# sem necessidade de arquivos .OBJ externos.
#
# Geometrias disponíveis:
#   - Esfera UV (planetas, lua, sol)
#   - Cubo     (caixas, asteroides cúbicos)
#   - Avião/nave (triângulo estilizado)
#   - Superfície lunar (plano com elevação aleatória)
#
# Formato de saída (por vértice):
#   [x, y, z, u, v, nx, ny, nz]  → 8 floats
#
# ==========================================================

import numpy as np
import math
import random


class GeometryGenerator:

    # ======================================================
    # ESFERA UV
    # ======================================================
    # Gera uma esfera suave com coordenadas UV mapeadas
    # de forma a receber texturas de planetas corretamente.
    #
    # Parâmetros:
    #   radius  : raio da esfera
    #   stacks  : subdivisões verticais (latitude)
    #   slices  : subdivisões horizontais (longitude)
    # ======================================================

    @staticmethod
    def create_sphere(radius=1.0, stacks=32, slices=32):

        vertices = []

        for i in range(stacks):

            # Ângulo de latitude: vai de -π/2 (sul) a π/2 (norte)
            lat0 = math.pi * (-0.5 + float(i)     / stacks)
            lat1 = math.pi * (-0.5 + float(i + 1) / stacks)

            sin_lat0, cos_lat0 = math.sin(lat0), math.cos(lat0)
            sin_lat1, cos_lat1 = math.sin(lat1), math.cos(lat1)

            for j in range(slices):

                # Ângulo de longitude: vai de 0 a 2π
                lon0 = 2.0 * math.pi * float(j)     / slices
                lon1 = 2.0 * math.pi * float(j + 1) / slices

                # ---- Quatro cantos do quadrilátero ----
                x00 = cos_lat0 * math.cos(lon0)
                y00 = sin_lat0
                z00 = cos_lat0 * math.sin(lon0)

                x10 = cos_lat1 * math.cos(lon0)
                y10 = sin_lat1
                z10 = cos_lat1 * math.sin(lon0)

                x01 = cos_lat0 * math.cos(lon1)
                y01 = sin_lat0
                z01 = cos_lat0 * math.sin(lon1)

                x11 = cos_lat1 * math.cos(lon1)
                y11 = sin_lat1
                z11 = cos_lat1 * math.sin(lon1)

                # ---- UVs ----
                u0 = float(j)     / slices
                u1 = float(j + 1) / slices
                v0 = float(i)     / stacks
                v1 = float(i + 1) / stacks

                # ---- Primeiro triângulo do quad ----
                def add(x, y, z, u, v):
                    nx, ny, nz = x, y, z  # normal = posição normalizada na esfera unitária
                    vertices.extend([x * radius, y * radius, z * radius, u, v, nx, ny, nz])

                add(x00, y00, z00, u0, v0)
                add(x10, y10, z10, u0, v1)
                add(x01, y01, z01, u1, v0)

                # ---- Segundo triângulo do quad ----
                add(x01, y01, z01, u1, v0)
                add(x10, y10, z10, u0, v1)
                add(x11, y11, z11, u1, v1)

        arr = np.array(vertices, dtype=np.float32)
        return arr, len(arr) // 8

    # ======================================================
    # CUBO
    # ======================================================
    # Cubo centrado na origem, com UVs em cada face.
    # Usado para asteroides cúbicos ou caixas metálicas.
    #
    # Parâmetros:
    #   size : comprimento de cada aresta
    # ======================================================

    @staticmethod
    def create_cube(size=1.0):

        s = size / 2.0

        # Formato: [x, y, z, u, v, nx, ny, nz]
        faces = [
            # Frente (+Z)
            [-s,-s, s,  0,0,  0, 0, 1],
            [ s,-s, s,  1,0,  0, 0, 1],
            [ s, s, s,  1,1,  0, 0, 1],
            [-s,-s, s,  0,0,  0, 0, 1],
            [ s, s, s,  1,1,  0, 0, 1],
            [-s, s, s,  0,1,  0, 0, 1],

            # Trás (-Z)
            [ s,-s,-s,  0,0,  0, 0,-1],
            [-s,-s,-s,  1,0,  0, 0,-1],
            [-s, s,-s,  1,1,  0, 0,-1],
            [ s,-s,-s,  0,0,  0, 0,-1],
            [-s, s,-s,  1,1,  0, 0,-1],
            [ s, s,-s,  0,1,  0, 0,-1],

            # Esquerda (-X)
            [-s,-s,-s,  0,0, -1, 0, 0],
            [-s,-s, s,  1,0, -1, 0, 0],
            [-s, s, s,  1,1, -1, 0, 0],
            [-s,-s,-s,  0,0, -1, 0, 0],
            [-s, s, s,  1,1, -1, 0, 0],
            [-s, s,-s,  0,1, -1, 0, 0],

            # Direita (+X)
            [ s,-s, s,  0,0,  1, 0, 0],
            [ s,-s,-s,  1,0,  1, 0, 0],
            [ s, s,-s,  1,1,  1, 0, 0],
            [ s,-s, s,  0,0,  1, 0, 0],
            [ s, s,-s,  1,1,  1, 0, 0],
            [ s, s, s,  0,1,  1, 0, 0],

            # Topo (+Y)
            [-s, s, s,  0,0,  0, 1, 0],
            [ s, s, s,  1,0,  0, 1, 0],
            [ s, s,-s,  1,1,  0, 1, 0],
            [-s, s, s,  0,0,  0, 1, 0],
            [ s, s,-s,  1,1,  0, 1, 0],
            [-s, s,-s,  0,1,  0, 1, 0],

            # Base (-Y)
            [-s,-s,-s,  0,0,  0,-1, 0],
            [ s,-s,-s,  1,0,  0,-1, 0],
            [ s,-s, s,  1,1,  0,-1, 0],
            [-s,-s,-s,  0,0,  0,-1, 0],
            [ s,-s, s,  1,1,  0,-1, 0],
            [-s,-s, s,  0,1,  0,-1, 0],
        ]

        arr = np.array(faces, dtype=np.float32).flatten()
        return arr, len(faces)

    # ======================================================
    # ASTEROIDE IRREGULAR
    # ======================================================
    # Esfera com deformação aleatória nos vértices.
    # Cria uma aparência rochosa e irregular.
    #
    # Parâmetros:
    #   radius    : raio base
    #   stacks    : subdivisões verticais
    #   slices    : subdivisões horizontais
    #   roughness : intensidade da deformação (0.0 a 1.0)
    #   seed      : semente aleatória (para reprodutibilidade)
    # ======================================================

    @staticmethod
    def create_asteroid(radius=1.0, stacks=10, slices=10, roughness=0.3, seed=42):

        random.seed(seed)
        vertices = []

        for i in range(stacks):
            lat0 = math.pi * (-0.5 + float(i)     / stacks)
            lat1 = math.pi * (-0.5 + float(i + 1) / stacks)

            sl0, cl0 = math.sin(lat0), math.cos(lat0)
            sl1, cl1 = math.sin(lat1), math.cos(lat1)

            for j in range(slices):
                lon0 = 2.0 * math.pi * float(j)     / slices
                lon1 = 2.0 * math.pi * float(j + 1) / slices

                # Deformação aleatória por vértice
                def deform():
                    return radius + random.uniform(-roughness, roughness) * radius

                r00 = deform(); r10 = deform()
                r01 = deform(); r11 = deform()

                x00 = r00 * cl0 * math.cos(lon0); y00 = r00 * sl0; z00 = r00 * cl0 * math.sin(lon0)
                x10 = r10 * cl1 * math.cos(lon0); y10 = r10 * sl1; z10 = r10 * cl1 * math.sin(lon0)
                x01 = r01 * cl0 * math.cos(lon1); y01 = r01 * sl0; z01 = r01 * cl0 * math.sin(lon1)
                x11 = r11 * cl1 * math.cos(lon1); y11 = r11 * sl1; z11 = r11 * cl1 * math.sin(lon1)

                u0 = float(j) / slices; u1 = float(j+1) / slices
                v0 = float(i) / stacks; v1 = float(i+1) / stacks

                def norm(x,y,z):
                    l = math.sqrt(x*x+y*y+z*z) or 1
                    return x/l, y/l, z/l

                def add(x,y,z,u,v):
                    nx,ny,nz = norm(x,y,z)
                    vertices.extend([x,y,z,u,v,nx,ny,nz])

                add(x00,y00,z00,u0,v0); add(x10,y10,z10,u0,v1); add(x01,y01,z01,u1,v0)
                add(x01,y01,z01,u1,v0); add(x10,y10,z10,u0,v1); add(x11,y11,z11,u1,v1)

        arr = np.array(vertices, dtype=np.float32)
        return arr, len(arr) // 8
