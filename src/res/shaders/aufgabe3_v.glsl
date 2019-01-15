#version 330
#define M_PI 3.1415926535897932384626433832795

layout(location=0) in vec3 vertex;
layout(location=1) in vec3 normalen;
layout(location=2) in vec2 uvCoords;

uniform mat4 matrix_obj1;
uniform mat4 projectionMatrix;

out vec3 vertexColor;
out vec3 pixelKoordinateInRaum;
out vec3 normalenImRaum;
out vec2 uv;

void main(){

//generate colors
vertexColor = vec3(vertex.x, vertex.y, vertex.z);

    uv = uvCoords;
    pixelKoordinateInRaum = vec3(matrix_obj1*vec4(vertex,1.0));
    normalenImRaum = normalize(mat3(matrix_obj1)*normalen);

    gl_Position = projectionMatrix*matrix_obj1*vec4(vertex, 1.0);

}