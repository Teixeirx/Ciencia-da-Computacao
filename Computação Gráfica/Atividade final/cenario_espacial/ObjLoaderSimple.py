# ==========================================================
# ObjLoaderSimple.py
# ==========================================================
#
# Loader simples de arquivos .OBJ
#
# O formato OBJ armazena:
#   v  = posições dos vértices (x, y, z)
#   vt = coordenadas de textura UV (u, v)
#   vn = normais dos vértices (nx, ny, nz)
#   f  = faces (triângulos ou polígonos)
#
# Este loader processa:
#   - v  : posição
#   - vt : coordenadas UV para textura
#   - vn : normais para iluminação
#   - f  : faces convertidas em triângulos
#
# Saída:
#   Array numpy com formato por vértice:
#   [x, y, z, u, v, nx, ny, nz]
#
# ==========================================================

import numpy as np


class ObjLoaderSimple:

    # ======================================================
    # MÉTODO ESTÁTICO load_obj
    # ======================================================
    # Permite chamar: ObjLoaderSimple.load_obj(caminho)
    # Sem precisar instanciar a classe.
    # ======================================================

    @staticmethod
    def load_obj(filename):

        # ==================================================
        # LISTAS DE DADOS DO OBJ
        # ==================================================

        vertices = []   # posições v x y z
        textures  = []  # UVs vt u v
        normals   = []  # normais vn nx ny nz
        faces     = []  # faces indexadas

        # ==================================================
        # LEITURA DO ARQUIVO OBJ
        # ==================================================

        with open(filename, "r") as file:

            for line in file:

                line = line.strip()

                # Ignora linhas vazias e comentários
                if not line or line.startswith("#"):
                    continue

                parts = line.split()
                prefix = parts[0]

                # ---------- Vértice de posição ----------
                if prefix == "v":
                    x, y, z = float(parts[1]), float(parts[2]), float(parts[3])
                    vertices.append((x, y, z))

                # ---------- Coordenada de textura (UV) ----------
                elif prefix == "vt":
                    u = float(parts[1])
                    v = float(parts[2]) if len(parts) > 2 else 0.0
                    textures.append((u, v))

                # ---------- Normal ----------
                elif prefix == "vn":
                    nx, ny, nz = float(parts[1]), float(parts[2]), float(parts[3])
                    normals.append((nx, ny, nz))

                # ---------- Face ----------
                elif prefix == "f":
                    # Converte polígonos em triângulos (fan triangulation)
                    face_indices = []
                    for token in parts[1:]:
                        indices = token.split("/")
                        v_idx  = int(indices[0]) - 1
                        vt_idx = int(indices[1]) - 1 if len(indices) > 1 and indices[1] else -1
                        vn_idx = int(indices[2]) - 1 if len(indices) > 2 and indices[2] else -1
                        face_indices.append((v_idx, vt_idx, vn_idx))

                    # Triangulação em leque: (0,1,2), (0,2,3), (0,3,4)...
                    for i in range(1, len(face_indices) - 1):
                        faces.append([face_indices[0], face_indices[i], face_indices[i + 1]])

        # ==================================================
        # MONTAGEM DO BUFFER FINAL
        # ==================================================
        # Para cada vértice de cada triângulo:
        # [x, y, z, u, v, nx, ny, nz]
        # ==================================================

        buffer = []

        for face in faces:
            for (v_idx, vt_idx, vn_idx) in face:

                # Posição
                pos = vertices[v_idx] if v_idx < len(vertices) else (0.0, 0.0, 0.0)

                # UV (usa (0,0) se não houver textura)
                uv = textures[vt_idx] if vt_idx >= 0 and vt_idx < len(textures) else (0.0, 0.0)

                # Normal (usa (0,1,0) como padrão)
                nrm = normals[vn_idx] if vn_idx >= 0 and vn_idx < len(normals) else (0.0, 1.0, 0.0)

                buffer.extend([pos[0], pos[1], pos[2], uv[0], uv[1], nrm[0], nrm[1], nrm[2]])

        return np.array(buffer, dtype=np.float32), len(faces) * 3
