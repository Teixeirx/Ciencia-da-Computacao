#version 330 core

// ==========================================================
// fragment_shader.glsl
// ==========================================================
//
// Shader de fragmento (Fragment Shader)
//
// Executado uma vez para cada pixel visível do objeto.
//
// Responsabilidades:
//   - Amostrar a textura na posição UV do fragmento
//   - Calcular iluminação ambiente + difusa (Phong simplificado)
//   - Aplicar emissão para objetos que "brilham" (estrelas)
//   - Produzir a cor final do pixel
//
// Modelo de iluminação:
//   cor_final = (ambiente + difusa) * cor_textura + emissao
//
// ==========================================================

// ---------- Entradas do Vertex Shader (interpoladas) ----------
in vec2 fragTexCoord;   // coordenadas UV
in vec3 fragNormal;     // normal no espaço do mundo
in vec3 fragPos;        // posição no espaço do mundo

// ---------- Saída ----------
out vec4 outColor;      // cor final do pixel (RGBA)

// ---------- Uniformes enviados pelo Python ----------
uniform sampler2D texturaSampler;  // textura do objeto
uniform vec3 lightPos;             // posição da "estrela" (luz)
uniform vec3 lightColor;           // cor da luz (branco/amarelo)
uniform float ambientStrength;     // intensidade da luz ambiente
uniform float emissionStrength;    // 0.0 = sem emissão, 1.0 = brilho total
uniform bool useLighting;          // false = sem iluminação (skybox/estrelas)

void main()
{
    // Amostra a textura na coordenada UV atual
    vec4 texColor = texture(texturaSampler, fragTexCoord);

    // ======================================================
    // MODO SEM ILUMINAÇÃO
    // ======================================================
    // Usado para o fundo estrelado (skybox) e objetos
    // que devem aparecer com cor plana, sem sombreamento.
    // ======================================================
    if (!useLighting) {
        outColor = texColor * (ambientStrength + emissionStrength);
        return;
    }

    // ======================================================
    // COMPONENTE AMBIENTE
    // ======================================================
    // Luz mínima que atinge todos os pontos igualmente.
    // Simula a luz refletida indiretamente no ambiente.
    // ======================================================
    vec3 ambient = ambientStrength * lightColor;

    // ======================================================
    // COMPONENTE DIFUSA (Lambertiana)
    // ======================================================
    // A intensidade depende do ângulo entre:
    //   - a normal da superfície
    //   - a direção da luz
    //
    // Quanto mais a superfície aponta para a luz,
    // mais iluminada ela fica (cos θ).
    // ======================================================
    vec3 norm     = normalize(fragNormal);
    vec3 lightDir = normalize(lightPos - fragPos);
    float diff    = max(dot(norm, lightDir), 0.0);
    vec3 diffuse  = diff * lightColor;

    // ======================================================
    // COR FINAL
    // ======================================================
    // Combina:
    //   (ambiente + difusa) * cor da textura + emissão
    //
    // emissionStrength > 0 → objeto parece emitir luz própria
    // ======================================================
    vec3 lighting = (ambient + diffuse) * texColor.rgb;
    vec3 emission = emissionStrength * texColor.rgb;

    outColor = vec4(lighting + emission, texColor.a);
}
