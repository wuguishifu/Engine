#version 460 core

// input values
layout(location = 0) in vec3 vPosition;
layout(location = 5) in vec4 vColor;

// output values
out vec4 passColor;

// model, view, projection matrices
uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

// main runnable method
void main() {
    // set the position of this vertex
    gl_Position = projection * view * model * vec4(vPosition, 1.0);

    // pass the color
    passColor = vColor;
}
