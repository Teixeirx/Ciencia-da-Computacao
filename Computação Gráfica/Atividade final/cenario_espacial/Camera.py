# ==========================================================
# Camera.py
# ==========================================================
#
# Gerencia a câmera 3D usando yaw (rotação horizontal)
# e pitch (rotação vertical).
#
# Convenção utilizada (padrão OpenGL moderno):
#   - frente = eixo Z negativo (-Z)
#   - yaw inicial = -90°
#
# Atributos:
#   camera_pos        : Vector3 – posição da câmera no mundo
#   camera_front      : Vector3 – direção que a câmera aponta
#   camera_up         : Vector3 – vetor "para cima"
#   camera_right      : Vector3 – vetor "para direita"
#   yaw               : float   – rotação horizontal (graus)
#   pitch             : float   – rotação vertical (graus)
#   mouse_sensitivity : float   – sensibilidade do mouse
#   speed             : float   – velocidade de movimentação
#
# Métodos:
#   get_view_matrix()         → Retorna matriz look-at
#   process_mouse_movement()  → Atualiza yaw/pitch pelo mouse
#   process_keyboard()        → Move câmera pelo teclado
#   update_camera_vectors()   → Converte yaw/pitch em vetores 3D
#
# ==========================================================

from pyrr import Vector3, vector, vector3, matrix44
from math import sin, cos, radians


class Camera:

    def __init__(self):

        # ==================================================
        # POSIÇÃO INICIAL DA CÂMERA
        # ==================================================
        # Câmera posicionada para visualizar o cenário espacial.
        # X = esquerda/direita
        # Y = cima/baixo
        # Z = profundidade (afastado para ver o cenário)
        # ==================================================

        self.camera_pos   = Vector3([0.0, 5.0, 30.0])

        # ==================================================
        # VETORES DE ORIENTAÇÃO
        # ==================================================
        # front: câmera olha para o eixo -Z inicialmente
        # up:    vetor "para cima" global = Y positivo
        # right: calculado automaticamente pelo cross product
        # ==================================================

        self.camera_front = Vector3([0.0, 0.0, -1.0])
        self.camera_up    = Vector3([0.0, 1.0,  0.0])
        self.camera_right = Vector3([1.0, 0.0,  0.0])

        # ==================================================
        # ÂNGULOS DE EULER
        # ==================================================
        # yaw   = -90° → câmera olha para -Z no início
        # pitch =   0° → câmera horizontal (sem inclinação)
        # ==================================================

        self.yaw   = -90.0
        self.pitch =   0.0

        # ==================================================
        self.mouse_sensitivity = 0.1
        self.speed             = 30.0  # Velocidade controlada

    # ==========================================================
    # MATRIZ VIEW (LOOK-AT)
    # ==========================================================
    # Retorna a matriz que posiciona e orienta a câmera no mundo.
    # Usada no shader como uniform "view".
    # ==========================================================

    def get_view_matrix(self):
        return matrix44.create_look_at(
            self.camera_pos,
            self.camera_pos + self.camera_front,
            self.camera_up
        )

    # ==========================================================
    # PROCESSAR MOVIMENTO DO MOUSE
    # ==========================================================
    # xoffset: deslocamento horizontal do mouse
    # yoffset: deslocamento vertical do mouse
    # constrain_pitch: limita pitch para evitar gimbal lock
    # ==========================================================

    def process_mouse_movement(self, xoffset, yoffset, constrain_pitch=True):

        xoffset *= self.mouse_sensitivity
        yoffset *= self.mouse_sensitivity

        self.yaw   += xoffset
        self.pitch += yoffset

        # Limita o pitch entre -89° e 89°
        # para a câmera não "virar de cabeça para baixo"
        if constrain_pitch:
            if self.pitch > 89.0:
                self.pitch = 89.0
            if self.pitch < -89.0:
                self.pitch = -89.0

        # Atualiza os vetores de direção
        self.update_camera_vectors()

    # ==========================================================
    # PROCESSAR TECLADO
    # ==========================================================
    # Teclas:
    #   W / Seta Cima    → avançar
    #   S / Seta Baixo   → recuar
    #   A / Seta Esq.    → mover para esquerda
    #   D / Seta Dir.    → mover para direita
    #   Q / Page Up      → subir
    #   E / Page Down    → descer
    # ==========================================================

    def process_keyboard(self, direction, delta_time):

        velocity = self.speed * delta_time

        if direction == "FORWARD":
            self.camera_pos += self.camera_front * velocity

        if direction == "BACKWARD":
            self.camera_pos -= self.camera_front * velocity

        if direction == "LEFT":
            self.camera_pos -= self.camera_right * velocity

        if direction == "RIGHT":
            self.camera_pos += self.camera_right * velocity

        if direction == "UP":
            self.camera_pos += self.camera_up * velocity

        if direction == "DOWN":
            self.camera_pos -= self.camera_up * velocity

    # ==========================================================
    # ATUALIZAR VETORES DA CÂMERA
    # ==========================================================
    # Recalcula camera_front, camera_right e camera_up
    # com base nos ângulos yaw e pitch atuais.
    # ==========================================================

    def update_camera_vectors(self):

        # Converte ângulos em vetor direção (trigonometria esférica)
        front = Vector3([0.0, 0.0, 0.0])
        front.x = cos(radians(self.yaw)) * cos(radians(self.pitch))
        front.y = sin(radians(self.pitch))
        front.z = sin(radians(self.yaw)) * cos(radians(self.pitch))

        # Normaliza o vetor frente
        self.camera_front = vector.normalise(front)

        # Calcula vetor direita (produto vetorial frente x cima)
        self.camera_right = vector.normalise(
            vector3.cross(self.camera_front, Vector3([0.0, 1.0, 0.0]))
        )

        # Recalcula vetor cima real da câmera
        self.camera_up = vector.normalise(
            vector3.cross(self.camera_right, self.camera_front)
        )
