#version 330

in vec3 vertexColor;
in vec3 pixelKoordinateInRaum;
in vec3 normalenImRaum;
in vec2 uv;

out vec3 pixelColor;

uniform sampler2D smplr;

vec3 licht = vec3(-1,3,3);


void main(){

    vec4 texel = texture(smplr,uv);

    vec3 lichtpixel = normalize(licht-pixelKoordinateInRaum);
    float il = 1;
    float ia = 0;
    float id = max(0,dot(lichtpixel,normalenImRaum))*il;
    vec3 r = normalize(2*(dot(lichtpixel,normalenImRaum))*normalenImRaum-lichtpixel);
    vec3 v = normalize(-pixelKoordinateInRaum);
    float is = pow(max(0,dot(r,v)),100)*il;
    float i = ia+id+is;
    //pixelColor = i*vertexColor;

    pixelColor = i*texel.rgb;
}