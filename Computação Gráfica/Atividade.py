import pygame
from pygame.locals import *
from OpenGL.GL import *
from OpenGL.GLU import *

objeto_atual = None

# posições iniciais dos objetos
cube_pos = [ -6.0, 0.0 ]
tri_pos  = [  0.0, 0.0 ]
pyr_pos  = [  6.0, 0.0 ]

# zoom e rotação da cena
zoom = -15.0
rot_x = 0.0
rot_y = 0.0

def init():
    glClearColor(0.0, 0.0, 0.0, 1.0)
    glClearDepth(1.0)
    glEnable(GL_DEPTH_TEST)
    glDepthFunc(GL_LEQUAL)
    glMatrixMode(GL_PROJECTION)
    glLoadIdentity()
    gluPerspective(45.0, 1280.0 / 480.0, 0.1, 100.0)
    glMatrixMode(GL_MODELVIEW)

def reset_transformations():
    global cube_pos, tri_pos, pyr_pos, zoom, rot_x, rot_y
    cube_pos = [ -6.0, 0.0 ]
    tri_pos  = [  0.0, 0.0 ]
    pyr_pos  = [  6.0, 0.0 ]
    zoom = -15.0
    rot_x = 0.0
    rot_y = 0.0

def draw_cube():
    glPushMatrix()
    glTranslatef(cube_pos[0], cube_pos[1], 0)
    glBegin(GL_QUADS)
    # face traseira
    glColor3f(1, 0, 0)
    glVertex3f(1, 1, -1); glVertex3f(-1, 1, -1); glVertex3f(-1, -1, -1); glVertex3f(1, -1, -1)
    # face frontal
    glColor3f(0, 1, 0)
    glVertex3f(1, -1, 1); glVertex3f(-1, -1, 1); glVertex3f(-1, 1, 1); glVertex3f(1, 1, 1)
    # face direita
    glColor3f(0, 0, 1)
    glVertex3f(1, 1, 1); glVertex3f(1, 1, -1); glVertex3f(1, -1, -1); glVertex3f(1, -1, 1)
    # face esquerda
    glColor3f(1, 1, 0)
    glVertex3f(-1, 1, -1); glVertex3f(-1, 1, 1); glVertex3f(-1, -1, 1); glVertex3f(-1, -1, -1)
    # face superior
    glColor3f(0, 1, 1)
    glVertex3f(1, 1, 1); glVertex3f(-1, 1, 1); glVertex3f(-1, 1, -1); glVertex3f(1, 1, -1)
    # face inferior
    glColor3f(1, 0, 1)
    glVertex3f(1, -1, -1); glVertex3f(-1, -1, -1); glVertex3f(-1, -1, 1); glVertex3f(1, -1, 1)
    glEnd()
    glPopMatrix()

def draw_triangle():
    glPushMatrix()
    glTranslatef(tri_pos[0], tri_pos[1], 0)
    glBegin(GL_TRIANGLES)
    glColor3f(1, 1, 0)
    glVertex3f(0, 2, 0)
    glVertex3f(-2, -2, 0)
    glVertex3f(2, -2, 0)
    glEnd()
    glPopMatrix()

def draw_pyramid():
    glPushMatrix()
    glTranslatef(pyr_pos[0], pyr_pos[1], 0)
    glBegin(GL_TRIANGLES)
    glColor3f(1, 0, 0)
    glVertex3f(0, 1, 0); glVertex3f(-1, -1, 1); glVertex3f(1, -1, 1)
    glColor3f(0, 1, 0)
    glVertex3f(0, 1, 0); glVertex3f(1, -1, 1); glVertex3f(1, -1, -1)
    glColor3f(0, 0, 1)
    glVertex3f(0, 1, 0); glVertex3f(1, -1, -1); glVertex3f(-1, -1, -1)
    glColor3f(1, 1, 0)
    glVertex3f(0, 1, 0); glVertex3f(-1, -1, -1); glVertex3f(-1, -1, 1)
    glEnd()
    glBegin(GL_QUADS)
    glColor3f(1, 0, 1)
    glVertex3f(-1, -1, -1); glVertex3f(1, -1, -1); glVertex3f(1, -1, 1); glVertex3f(-1, -1, 1)
    glEnd()
    glPopMatrix()

def handle_keys():
    global zoom, rot_x, rot_y, cube_pos, tri_pos, pyr_pos
    keys = pygame.key.get_pressed()

    
    if keys[K_z]: zoom += 0.2
    if keys[K_x]: zoom -= 0.2
    if keys[K_q]: rot_x += 2 # Rotação Eixo X
    if keys[K_e]: rot_x -= 2
    if keys[K_r]: rot_y += 2 # Rotação Eixo Y
    if keys[K_f]: rot_y -= 2

    if objeto_atual == 1: # Cubo
        if keys[K_w]: cube_pos[1] += 0.1
        if keys[K_s]: cube_pos[1] -= 0.1
        if keys[K_a]: cube_pos[0] -= 0.1
        if keys[K_d]: cube_pos[0] += 0.1

    elif objeto_atual == 2: # Triângulo
        if keys[K_w]: tri_pos[1] += 0.1
        if keys[K_s]: tri_pos[1] -= 0.1
        if keys[K_a]: tri_pos[0] -= 0.1
        if keys[K_d]: tri_pos[0] += 0.1

    elif objeto_atual == 3: # Cubo + Triângulo (Movem juntos)
        if keys[K_w]: cube_pos[1] += 0.1; tri_pos[1] += 0.1
        if keys[K_s]: cube_pos[1] -= 0.1; tri_pos[1] -= 0.1
        if keys[K_a]: cube_pos[0] -= 0.1; tri_pos[0] -= 0.1
        if keys[K_d]: cube_pos[0] += 0.1; tri_pos[0] += 0.1

    elif objeto_atual == 4: # Pirâmide
        if keys[K_w]: pyr_pos[1] += 0.1
        if keys[K_s]: pyr_pos[1] -= 0.1
        if keys[K_a]: pyr_pos[0] -= 0.1
        if keys[K_d]: pyr_pos[0] += 0.1

    elif objeto_atual == 5: # Todos juntos (WASD)
        for pos in [cube_pos, tri_pos, pyr_pos]:
            if keys[K_w]: pos[1] += 0.1
            if keys[K_s]: pos[1] -= 0.1
            if keys[K_a]: pos[0] -= 0.1
            if keys[K_d]: pos[0] += 0.1

    elif objeto_atual == 6: # Controle Individual
        # Cubo (I, K, J, L)
        if keys[K_i]: cube_pos[1] += 0.1
        if keys[K_k]: cube_pos[1] -= 0.1
        if keys[K_j]: cube_pos[0] -= 0.1
        if keys[K_l]: cube_pos[0] += 0.1
        # Triângulo (G, B, V, N)
        if keys[K_g]: tri_pos[1] += 0.1
        if keys[K_b]: tri_pos[1] -= 0.1
        if keys[K_v]: tri_pos[0] -= 0.1
        if keys[K_n]: tri_pos[0] += 0.1
        # Pirâmide (Setas)
        if keys[K_UP]:    pyr_pos[1] += 0.1
        if keys[K_DOWN]:  pyr_pos[1] -= 0.1
        if keys[K_LEFT]:  pyr_pos[0] -= 0.1
        if keys[K_RIGHT]: pyr_pos[0] += 0.1

def draw_scene():
    glLoadIdentity()
    glTranslatef(0, 0, zoom)
    glRotatef(rot_y, 0, 1, 0)
    glRotatef(rot_x, 1, 0, 0)
    if objeto_atual == 1:
        draw_cube()
    elif objeto_atual == 2:
        draw_triangle()
    elif objeto_atual == 3:
        draw_cube()
        draw_triangle()
    elif objeto_atual == 4:
        draw_pyramid()
    elif objeto_atual in [5, 6]:
        draw_cube()
        draw_triangle()
        draw_pyramid()

def main():
    global objeto_atual
    print("=== MENU DE FORMAS ===")
    print("1 - Cubo") 
    print("2 - Triângulo") 
    print("3 - Cubo + Triângulo") 
    print("4 - Apenas Pirâmide") 
    print("5 - Todos juntos (WASD para todos)") 
    print("6 - Todos com controle individual") 

    try:
        objeto_atual = int(input("Escolha uma opção: ")) 
    except:
        print("Opção inválida!") 
        return

    pygame.init()
    pygame.display.set_mode((1280, 480), DOUBLEBUF | OPENGL)
    pygame.display.set_caption("Trabalho 01 - Computação Gráfica")
    init()
    reset_transformations()

    running = True
    while running:
        for event in pygame.event.get():
            if event.type == QUIT or (event.type == KEYDOWN and event.key == K_ESCAPE): 
                running = False

        handle_keys()
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)
        draw_scene()
        pygame.display.flip()
        pygame.time.wait(10)

    pygame.quit()

if __name__ == "__main__":
    main()