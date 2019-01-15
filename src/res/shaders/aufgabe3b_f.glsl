#version 330

in vec3 vertexColor2;
in vec3 pixelKoordinateInRaum2;
in vec3 normalenImRaum2;
in vec2 uv2;

out vec3 pixelColor2;

uniform sampler2D smplr2;

vec3 licht = vec3(-1,3,3);

void main(){

    vec4 texel = texture(smplr2,uv2);

    vec3 lichtpixel = normalize(licht-pixelKoordinateInRaum2);
    float il = 1;
    float ia = 0.1;
    float id = max(0,dot(lichtpixel,normalenImRaum2))*il;
    vec3 r = normalize(2*(dot(lichtpixel,normalenImRaum2))*normalenImRaum2-lichtpixel);
    vec3 v = normalize(-pixelKoordinateInRaum2);
    float is = pow(max(0,dot(r,v)),100)*il;
    float i = ia+id+is;
    //pixelColor2 = i*vertexColor2;
    pixelColor2 = i*texel.rgb;


}