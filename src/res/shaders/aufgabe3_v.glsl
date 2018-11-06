#version 330
#define M_PI 3.1415926535897932384626433832795


layout(location=0) in vec4 eckenAusJava;
layout(location=1) in vec3 farbenAusJava;

out vec3 farbe;

uniform mat4 matrix;

void main(){

    vec4 eckenRotiert = matrix*eckenAusJava;
    farbe = farbenAusJava;
    gl_Position = eckenRotiert;
}