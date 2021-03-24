#version 330 core

// input values
in vec4 passColor;

// the lighting values for shading
uniform vec3 lightPos;
uniform vec3 lightColor;
uniform float lightLevel;

// the position of the camera
uniform vec3 viewPos;

// the output color
out vec4 outColor;

void main() {
    // output color
    outColor = passColor;
}
