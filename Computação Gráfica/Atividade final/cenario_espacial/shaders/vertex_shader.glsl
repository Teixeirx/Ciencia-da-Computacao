#version 330 core

// ==========================================================
// vertex_shader.glsl
// ==========================================================
//
// Shader de vértice (Vertex Shader)
//
// Executado uma vez para cada vértice do objeto.
//
// Responsabilidades:
//   - Transformar posição do espaço local → clip space
//   - Repassar UV e Normal para o fragment shader
//   - Calcular posição no espaço do mundo (para luz)
//
// Matrizes de transformação:
//   model      : objeto → mundo
//   view       : mundo  → câmera
//   projection : câmera → clip space (perspectiva)
//
// ==========================================================

// ---------- Atributos de entrada (por vértice) ----------
layout (location = 0) in vec3 position;   // posição local
layout (location = 1) in vec2 texCoord;   // UV da textura
layout (location = 2) in vec3 normal;     // normal local

// ---------- Saídas para o Fragment Shader ----------
out vec2 fragTexCoord;     // UV interpolada
out vec3 fragNormal;       // normal no espaço do mundo
out vec3 fragPos;          // posição no espaço do mundo

// ---------- Uniformes (enviados pelo Python) ----------
uniform mat4 model;        // matriz de modelo
uniform mat4 view;         // matriz de câmera
uniform mat4 projection;   // matriz de projeção

void main()
{
    // Posição no espaço do mundo (para cálculo de luz)
    fragPos = vec3(model * vec4(position, 1.0));

    // Normal transformada para o espaço do mundo
    // Usa a inversa transposta para preservar direção correta
    fragNormal = mat3(transpose(inverse(model))) * normal;

    // Repassa UV sem modificação
    fragTexCoord = texCoord;

    // Posição final no clip space (pipeline de projeção)
    gl_Position = projection * view * model * vec4(position, 1.0);
}
