#version 330

in vec3 farbe;
out vec3 bgFarbe;

void main(){
    //bgFarbe = vec3(0.4, 0.9, 0.9);
    bgFarbe = farbe;
}