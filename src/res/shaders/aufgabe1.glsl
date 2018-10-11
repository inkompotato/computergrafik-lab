#version 330

out vec3 bgFarbe;
vec2 coordinate;

//a simple circle with adjustable position and radius
void circle(vec2 middle, int radius){

    coordinate = gl_FragCoord.xy;

    //int radius = 150;
    //vec2 middle = vec2(700/2, 700/2);

    //checks if point is inside circle radius
    float dist = distance(middle,coordinate);
    if (dist<radius){
        bgFarbe = vec3(0.2, 0.3, 0.4);
    }
}

//just a simple rectangle in red
void redRectangle(vec2 lowerBound, vec2 upperBound){
        coordinate = gl_FragCoord.xy;
        if (coordinate.x > lowerBound.x && coordinate.x < upperBound.x && coordinate.y > lowerBound.y && coordinate.y < upperBound.y){
            bgFarbe = vec3(1.0, 0.0, 0.0);
        }
}

//a rotated rectangle with the initial dimensions and the rotation angle
void rotatedRectangle(vec2 lowerBound, vec2 upperBound, float angle){

    coordinate = gl_FragCoord.xy;

    //rotation matrix
    mat2 rotation = mat2(cos(-angle), sin(-angle), -sin(-angle), cos(-angle));

    vec2 coordinate_r = rotation*coordinate;

    if (coordinate_r.x > lowerBound.x && coordinate_r.x < upperBound.x && coordinate_r.y > lowerBound.y && coordinate_r.y < upperBound.y){
        //coordinate = -rotation*coordinate_r;
        bgFarbe = vec3(1.0, 1.0, 1.0);
    }
}

void line(vec2 p1, vec2 p2){
    coordinate = gl_FragCoord.xy;

    //vector of the line
    vec2 li = p2-p1;

    //make sure the vector is not negative
    li.x = abs(li.x);
    li.y = abs(li.y);

    //3 possibilities: the leine either has a 90 or 180 degree angle or any other angle
    if (li.x != 0 && li.y != 0){
        vec2 x_base = vec2(1,0);
        float angle = acos((dot(li,x_base))/(length(li)*length(x_base)));

        //rotate the same way like the rectangle
        mat2 rotation = mat2(cos(-angle), sin(-angle), -sin(-angle), cos(-angle));

        vec2 coordinate_r = rotation*coordinate;
        float y_coord = p1.y;

        //draw the line and adjust for rotation error by substracting the y_coord
        if (coordinate_r.y < y_coord+1-y_coord && coordinate_r.y > y_coord-1-y_coord){
                bgFarbe = vec3(1.0, 1.0, 1.0);
            }
    } else if (li.x == 0 && li.y != 0){
        if (coordinate.x < p1.x+1 && coordinate.x > p1.x-1){
                bgFarbe = vec3(1.0, 1.0, 1.0);
            }
    } else if (li.y == 0 && li.x != 0){
        if (coordinate.y < p1.y+1 && coordinate.y > p1.y-1){
                bgFarbe = vec3(1.0, 1.0, 1.0);
            }
    }
}

void simpleline(int y){
    coordinate = gl_FragCoord.xy;
    if (coordinate.y < y+1 && coordinate.y > y-1){
        bgFarbe = vec3(1.0, 1.0, 1.0);
    }
}

void drawPicture(){

    //defaults for rectangle
    vec2 upperBound = vec2(700/4*3, 700/4*3);
    vec2 lowerBound = vec2(700/4, 700/4);

    //draw rectangles
    redRectangle(vec2(0,0), vec2(700,100));

    //defaults for circle
    vec2 middle = vec2(700/2, 700/2);
    int radius = 150;

    //draw circles
    circle(vec2(100, 100), 50);
    circle(vec2(400,400), 100);

    rotatedRectangle(vec2(100,100), vec2(400,400), 0.2);


    simpleline(100);
    simpleline(400);

    line(vec2(100,0),vec2(100,10));
    line(vec2(400,0),vec2(400,10));

    line(vec2(0,0),vec2(100,100));
}


void main(){
    //background color
    bgFarbe = vec3(0.4, 0.9, 0.9);
    drawPicture();
}