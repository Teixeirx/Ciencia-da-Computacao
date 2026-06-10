# -*- coding: utf-8 -*-
# ==========================================================
# cenario_espacial.py
# ==========================================================
#
# TRABALHO FINAL - COMPUTAÇÃO GRÁFICA
# Curso de Ciência da Computação - UFN 2026-01
# Professor: André F. dos Santos
#
# Aluno(s): Gabriel Teixeira
#
# Tema: CENÁRIO ESPACIAL
#
# ==========================================================

import glfw                        
from OpenGL.GL import *            
import OpenGL.GL.shaders           
import numpy as np                 
import pyrr                        
from pyrr import matrix44, Vector3 
import ctypes                      
import math                        
import os                          
import sys                         

from Camera           import Camera            
from TextureLoader    import load_texture      
from GeometryGenerator import GeometryGenerator 
from ObjLoaderSimple   import ObjLoaderSimple   

TITULO  = "Cenário Espacial - OpenGL Moderno"
WIDTH   = 1280
HEIGHT  = 720

window         = None    
shader_program = None    
cam            = Camera() 

first_mouse = True
last_x      = WIDTH  / 2.0
last_y      = HEIGHT / 2.0
delta_time  = 0.0
last_frame  = 0.0

tempo_global = 0.0

class Objeto3D:
    def __init__(self, nome, vao, num_vertices, textura_id,
                 pos=(0,0,0), escala=(1,1,1), rotacao=(0,0,0),
                 usa_luz=True, emissao=0.0, ambiente=0.15):
        self.nome         = nome
        self.vao          = vao
        self.num_vertices = num_vertices
        self.textura_id   = textura_id
        self.pos          = list(pos)
        self.escala       = list(escala)
        self.rotacao      = list(rotacao)
        self.usa_luz      = usa_luz
        self.emissao      = emissao
        self.ambiente     = ambiente

objetos_cena = []    
asteroides   = []    

def ler_shader(caminho):
    with open(caminho, "r", encoding="utf-8") as f:
        return f.read()

def compilar_shaders():
    global shader_program
    vert_src = ler_shader("shaders/vertex_shader.glsl")
    frag_src = ler_shader("shaders/fragment_shader.glsl")
    vert = OpenGL.GL.shaders.compileShader(vert_src, GL_VERTEX_SHADER)
    frag = OpenGL.GL.shaders.compileShader(frag_src, GL_FRAGMENT_SHADER)
    shader_program = OpenGL.GL.shaders.compileProgram(vert, frag)
    print("[Shader] Compilado com sucesso!")

def criar_vao(buffer_np):
    vao = glGenVertexArrays(1)
    vbo = glGenBuffers(1)
    glBindVertexArray(vao)
    glBindBuffer(GL_ARRAY_BUFFER, vbo)
    glBufferData(GL_ARRAY_BUFFER, buffer_np.nbytes, buffer_np, GL_STATIC_DRAW)
    stride = 8 * ctypes.sizeof(ctypes.c_float)   
    glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, stride, ctypes.c_void_p(0))
    glEnableVertexAttribArray(0)
    glVertexAttribPointer(1, 2, GL_FLOAT, GL_FALSE, stride, ctypes.c_void_p(3 * 4))
    glEnableVertexAttribArray(1)
    glVertexAttribPointer(2, 3, GL_FLOAT, GL_FALSE, stride, ctypes.c_void_p(5 * 4))
    glEnableVertexAttribArray(2)
    glBindVertexArray(0)   
    return vao

def carregar_textura(caminho):
    tex_id = glGenTextures(1)
    load_texture(caminho, tex_id)
    return tex_id

def inicializar_cena():
    global objetos_cena, asteroides
    print("\n[Cena] Gerando geometrias e carregando texturas reais...\n")

    tex_stars      = carregar_textura("texturas/starfield.jpg")
    tex_terra      = carregar_textura("texturas/terra.jpg")
    tex_marte      = carregar_textura("texturas/marte.jpg")
    tex_aster      = carregar_textura("texturas/asteroide.jpg")
    tex_prometheus = carregar_textura("objetos/nave/Prometheus1.jpg")
    tex_metal      = carregar_textura("texturas/metal.jpg")

    buf_sky, n_sky = GeometryGenerator.create_sphere(radius=500.0, stacks=20, slices=20)
    vao_sky = criar_vao(buf_sky)
    objetos_cena.append(Objeto3D(
        nome="Skybox", vao=vao_sky, num_vertices=n_sky, textura_id=tex_stars,
        pos=(0, 0, 0), escala=(1, 1, 1), usa_luz=False, emissao=1.0, ambiente=1.0
    ))

    buf_marte, n_marte = GeometryGenerator.create_sphere(radius=1.0, stacks=32, slices=32)
    vao_marte = criar_vao(buf_marte)
    objetos_cena.append(Objeto3D(
        nome="Marte", vao=vao_marte, num_vertices=n_marte, textura_id=tex_marte,
        pos=(35, -5, -80), escala=(20, 20, 20), usa_luz=True, emissao=0.0, ambiente=0.2
    ))

    buf_terra, n_terra = GeometryGenerator.create_sphere(radius=1.0, stacks=48, slices=48)
    vao_terra = criar_vao(buf_terra)
    objetos_cena.append(Objeto3D(
        nome="Terra", vao=vao_terra, num_vertices=n_terra, textura_id=tex_terra,
        pos=(-20, 0, -60), escala=(15, 15, 15), usa_luz=True, emissao=0.0, ambiente=0.2
    ))

    print("  -> Carregando modelo OBJ da nave...")
    buf_nave, n_nave = ObjLoaderSimple.load_obj("objetos/nave/prometheus.obj")
    vao_nave = criar_vao(buf_nave)
    objetos_cena.append(Objeto3D(
        nome="Nave", vao=vao_nave, num_vertices=n_nave, textura_id=tex_prometheus,
        pos=(10, 0, -20), escala=(1.5, 1.5, 1.5), rotacao=(0, -45, 10),
        usa_luz=True, emissao=0.1, ambiente=0.4
    ))

    print("  -> Carregando modelo OBJ do OVNI...")
    buf_ovni, n_ovni = ObjLoaderSimple.load_obj("objetos/ovni/UFO_Empty.obj")
    vao_ovni = criar_vao(buf_ovni)
    objetos_cena.append(Objeto3D(
        nome="Ovni", vao=vao_ovni, num_vertices=n_ovni, textura_id=tex_metal,
        pos=(-30, 10, -50), escala=(5.0, 5.0, 5.0), rotacao=(0, 0, 0),
        usa_luz=True, emissao=0.2, ambiente=0.5
    ))

    import random
    random.seed(1)
    for i in range(5):
        seed_ast = i * 7 + 3
        buf_ast, n_ast = GeometryGenerator.create_asteroid(
            radius=1.0, stacks=12, slices=12, roughness=0.15, seed=seed_ast
        )
        vao_ast = criar_vao(buf_ast)
        angulo   = (i / 5) * 2 * math.pi
        raio_orb = random.uniform(25, 45)
        y_orb    = random.uniform(-5, 5)
        escala_ast = random.uniform(2.0, 5.0)

        ast_obj = Objeto3D(
            nome=f"Asteroide_{i}", vao=vao_ast, num_vertices=n_ast, textura_id=tex_aster,
            pos=[math.cos(angulo) * raio_orb, y_orb, -50 + math.sin(angulo) * raio_orb],
            escala=(escala_ast, escala_ast, escala_ast),
            rotacao=(random.uniform(0, 360), random.uniform(0, 360), 0),
            usa_luz=True, emissao=0.0, ambiente=0.4
        )
        asteroides.append({
            "obj": ast_obj, "angulo_ini": angulo, "raio": raio_orb,
            "y": y_orb, "vel": random.uniform(0.02, 0.05)
        })
        objetos_cena.append(ast_obj)

    print(f"\n[Cena] {len(objetos_cena)} objetos criados na cena!\n")

def callback_redimensiona(window, w, h):
    global WIDTH, HEIGHT
    WIDTH  = w
    HEIGHT = h
    glViewport(0, 0, w, h)

def callback_teclado(window, key, scancode, action, mods):
    if key == glfw.KEY_ESCAPE and action == glfw.PRESS:
        glfw.set_window_should_close(window, True)

def callback_mouse(window, xpos, ypos):
    global first_mouse, last_x, last_y
    if first_mouse:
        last_x      = xpos
        last_y      = ypos
        first_mouse = False
    xoffset =  (xpos - last_x)
    yoffset = -(ypos - last_y)   
    last_x = xpos
    last_y = ypos
    cam.process_mouse_movement(xoffset, yoffset)

def processar_teclado(window):
    global cam, delta_time
    if glfw.get_key(window, glfw.KEY_W) == glfw.PRESS or glfw.get_key(window, glfw.KEY_UP) == glfw.PRESS:
        cam.process_keyboard("FORWARD",  delta_time)
    if glfw.get_key(window, glfw.KEY_S) == glfw.PRESS or glfw.get_key(window, glfw.KEY_DOWN) == glfw.PRESS:
        cam.process_keyboard("BACKWARD", delta_time)
    if glfw.get_key(window, glfw.KEY_A) == glfw.PRESS or glfw.get_key(window, glfw.KEY_LEFT) == glfw.PRESS:
        cam.process_keyboard("LEFT",     delta_time)
    if glfw.get_key(window, glfw.KEY_D) == glfw.PRESS or glfw.get_key(window, glfw.KEY_RIGHT) == glfw.PRESS:
        cam.process_keyboard("RIGHT",    delta_time)
    if glfw.get_key(window, glfw.KEY_Q) == glfw.PRESS or glfw.get_key(window, glfw.KEY_PAGE_UP) == glfw.PRESS:
        cam.process_keyboard("UP",       delta_time)
    if glfw.get_key(window, glfw.KEY_E) == glfw.PRESS or glfw.get_key(window, glfw.KEY_PAGE_DOWN) == glfw.PRESS:
        cam.process_keyboard("DOWN",     delta_time)
    if glfw.get_key(window, glfw.KEY_EQUAL) == glfw.PRESS:
        cam.speed = min(cam.speed + 0.01, 50.0)
    if glfw.get_key(window, glfw.KEY_MINUS) == glfw.PRESS:
        cam.speed = max(cam.speed - 0.01, 0.05)

def atualizar_animacoes(tempo):
    for obj in objetos_cena:
        if obj.nome == "Terra":
            obj.rotacao[1] = tempo * 1.5
        elif obj.nome == "Marte":
            obj.rotacao[1] = tempo * 1.0
        elif obj.nome == "Nave":
            obj.pos[1] = 0.0 + math.sin(tempo * 0.4) * 0.2
            obj.rotacao[1] = -45 + math.sin(tempo * 0.2) * 5
        elif obj.nome == "Ovni":
            obj.pos[0] = -30 + math.sin(tempo * 0.5) * 60
            obj.pos[2] = -50 + math.cos(tempo * 0.3) * 30
            obj.rotacao[1] = tempo * 30.0

    terra_pos = [-15, 0, -50]
    for ast_data in asteroides:
        obj  = ast_data["obj"]
        ang  = ast_data["angulo_ini"] + tempo * ast_data["vel"]
        raio = ast_data["raio"]
        y    = ast_data["y"]
        obj.pos[0] = terra_pos[0] + math.cos(ang) * raio
        obj.pos[1] = terra_pos[1] + y + math.sin(tempo * 0.5 + raio) * 0.5
        obj.pos[2] = terra_pos[2] + math.sin(ang) * raio
        obj.rotacao[0] += 0.3
        obj.rotacao[1] += 0.2

def calcular_model(obj):
    m = matrix44.create_identity(dtype=np.float32)
    m = matrix44.multiply(m, matrix44.create_from_translation(Vector3(obj.pos), dtype=np.float32))
    m = matrix44.multiply(m, matrix44.create_from_y_rotation(math.radians(obj.rotacao[1]), dtype=np.float32))
    m = matrix44.multiply(m, matrix44.create_from_x_rotation(math.radians(obj.rotacao[0]), dtype=np.float32))
    m = matrix44.multiply(m, matrix44.create_from_z_rotation(math.radians(obj.rotacao[2]), dtype=np.float32))
    m = matrix44.multiply(m, matrix44.create_from_scale(Vector3(obj.escala), dtype=np.float32))
    return m

def renderizar_objeto(obj, view, projection):
    model = calcular_model(obj)
    glUniformMatrix4fv(glGetUniformLocation(shader_program, "model"), 1, GL_FALSE, model)
    glUniformMatrix4fv(glGetUniformLocation(shader_program, "view"), 1, GL_FALSE, view)
    glUniformMatrix4fv(glGetUniformLocation(shader_program, "projection"), 1, GL_FALSE, projection)
    glUniform3f(glGetUniformLocation(shader_program, "lightPos"), 0.0, 0.0, -200.0)
    glUniform3f(glGetUniformLocation(shader_program, "lightColor"), 1.0, 0.98, 0.9)
    glUniform1f(glGetUniformLocation(shader_program, "ambientStrength"), obj.ambiente)
    glUniform1f(glGetUniformLocation(shader_program, "emissionStrength"), obj.emissao)
    glUniform1i(glGetUniformLocation(shader_program, "useLighting"), 1 if obj.usa_luz else 0)
    glActiveTexture(GL_TEXTURE0)
    glBindTexture(GL_TEXTURE_2D, obj.textura_id)
    glUniform1i(glGetUniformLocation(shader_program, "texturaSampler"), 0)
    glBindVertexArray(obj.vao)
    glDrawArrays(GL_TRIANGLES, 0, obj.num_vertices)
    glBindVertexArray(0)

def loop_principal():
    global delta_time, last_frame, tempo_global
    while not glfw.window_should_close(window):
        current_frame = glfw.get_time()
        delta_time    = current_frame - last_frame
        last_frame    = current_frame
        tempo_global  = current_frame

        glfw.poll_events()
        processar_teclado(window)
        atualizar_animacoes(tempo_global)

        glDisable(GL_CULL_FACE)
        glEnable(GL_DEPTH_TEST)
        glClearColor(0.0, 0.0, 0.02, 1.0)
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)

        glUseProgram(shader_program)
        view = cam.get_view_matrix()
        projection = matrix44.create_perspective_projection_matrix(
            60.0, WIDTH / HEIGHT, 0.1, 10000.0, dtype=np.float32
        )

        for obj in objetos_cena:
            if obj.nome == "Skybox":
                glDepthMask(GL_FALSE)
                renderizar_objeto(obj, view, projection)
                glDepthMask(GL_TRUE)
            else:
                renderizar_objeto(obj, view, projection)

        glfw.swap_buffers(window)
    glfw.terminate()

def inicializar():
    global window
    if not glfw.init():
        print("[ERRO] Não foi possível inicializar GLFW!")
        sys.exit(1)
    glfw.window_hint(glfw.CONTEXT_VERSION_MAJOR, 3)
    glfw.window_hint(glfw.CONTEXT_VERSION_MINOR, 3)
    glfw.window_hint(glfw.OPENGL_PROFILE, glfw.OPENGL_CORE_PROFILE)

    window = glfw.create_window(WIDTH, HEIGHT, TITULO, None, None)
    if not window:
        print("[ERRO] Não foi possível criar janela GLFW!")
        glfw.terminate()
        sys.exit(1)

    glfw.make_context_current(window)
    glfw.set_framebuffer_size_callback(window, callback_redimensiona)
    glfw.set_key_callback(window, callback_teclado)
    glfw.set_cursor_pos_callback(window, callback_mouse)
    glfw.set_input_mode(window, glfw.CURSOR, glfw.CURSOR_DISABLED)

    glEnable(GL_DEPTH_TEST)
    glEnable(GL_BLEND)
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
    glEnable(GL_CULL_FACE)
    glCullFace(GL_BACK)

    print(f"[OpenGL] Versão: {glGetString(GL_VERSION).decode()}")
    compilar_shaders()

def main():
    print("=" * 60)
    print("  CENÁRIO ESPACIAL - OpenGL Moderno")
    print("=" * 60)
    inicializar()
    inicializar_cena()
    loop_principal()

if __name__ == "__main__":
    main()
