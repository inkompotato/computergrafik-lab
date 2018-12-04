#version 330
#define M_PI 3.1415926535897932384626433832795


layout(location=0) in vec3 vertex;
layout(location=1) in vec3 normalen;


uniform mat4 matrix;
uniform mat4 projectionMatrix;

out vec3 vertexColor;
out vec3 pixelKoordinateInRaum;
out vec3 normalenImRaum;

void main(){
bool mode = true;

if (mode){
        //black
        if (vertex.x == -0.5 && vertex.y == -0.5 && vertex.z == -0.5) {
            vertexColor = vec3(0.0, 0.0, 0.0);
        //red
        } else if (vertex.x == -0.5 && vertex.y == 0.5 && vertex.z == -0.5) {
            vertexColor = vec3(1.0, 0.0, 0.0);
        //green
        } else if (vertex.x == -0.5 && vertex.y == -0.5 && vertex.z == 0.5) {
            vertexColor = vec3(0.0, 1.0, 0.0);
        //blue
        } else if (vertex.x == 0.5 && vertex.y == -0.5 && vertex.z == -0.5) {
            vertexColor = vec3(0.0, 0.0, 1.0);
        //magenta
        } else if (vertex.x == 0.5 && vertex.y == 0.5 && vertex.z == -0.5) {
            vertexColor = vec3(1.0, 0.0, 1.0);
        //yellow
        } else if (vertex.x == -0.5 && vertex.y == 0.5 && vertex.z == 0.5) {
            vertexColor = vec3(1.0, 1.0, 0.0);
        //cyan
        } else if (vertex.x == 0.5 && vertex.y == -0.5 && vertex.z == 0.5) {
            vertexColor = vec3(0.0, 1.0, 1.0);
        //white
        } else if (vertex.x == 0.5 && vertex.y == 0.5 && vertex.z == 0.5) {
            vertexColor = vec3(1.0, 1.0, 1.0);
        } else {
            vertexColor = vec3(0.5, 0.5, 0.5);
        }
        }else {

        if (gl_VertexID < 6) {
                vertexColor = vec3(1.0, 0.0, 0.0);
            } else if (gl_VertexID < 12) {
                vertexColor = vec3(0.0, 1.0, 0.0);
            } else if (gl_VertexID < 18) {
                vertexColor = vec3(0.0, 0.0, 1.0);
            } else if (gl_VertexID < 24) {
                vertexColor = vec3(1.0, 1.0, 0.0);
            } else if (gl_VertexID < 30) {
                vertexColor = vec3(1.0, 0.0, 1.0);
            } else {
                vertexColor = vec3(0.0, 1.0, 1.0);
            }
            }

    //vec4 eckenRotiert = matrix*eckenAusJava;
    //farbe = farbenAusJava;
    pixelKoordinateInRaum = vec3(matrix*vec4(vertex,1.0));
    normalenImRaum = normalize(mat3(matrix)*normalen);
    gl_Position = projectionMatrix*matrix*vec4(vertex, 1.0);
}