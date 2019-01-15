#version 330
#define M_PI 3.1415926535897932384626433832795

layout(location=0) in vec3 vertex;
layout(location=1) in vec3 normalen;
layout(location=2) in vec2 uvCoords;

uniform mat4 matrix_obj2;
uniform mat4 projectionMatrix;

out vec3 vertexColor2;
out vec3 pixelKoordinateInRaum2;
out vec3 normalenImRaum2;
out vec2 uv2;

void main(){
bool mode = true;

//generate colors
vertexColor2 = vec3(vertex.x, vertex.y, vertex.z);

    uv2 = uvCoords;
    pixelKoordinateInRaum2 = vec3(matrix_obj2*vec4(vertex,1.0));
    normalenImRaum2 = normalize(mat3(matrix_obj2)*normalen);

    gl_Position = projectionMatrix*matrix_obj2*vec4(vertex, 1.0);
}