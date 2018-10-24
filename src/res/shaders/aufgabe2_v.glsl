#version 330
#define M_PI 3.1415926535897932384626433832795


layout(location=0) in vec2 eckenAusJava;
layout(location=1) in vec3 farbenAusJava;


out vec3 farbe;

void main(){

    //rotation
    float degree = 0;
    float angle = M_PI*2/360*degree;
    mat2 rotation = mat2(cos(angle), sin(angle), -sin(angle), cos(angle));


    vec2 eckenRotiert = rotation*eckenAusJava;
    farbe = farbenAusJava;
    gl_Position = vec4(eckenRotiert,0.0,1.0);
}