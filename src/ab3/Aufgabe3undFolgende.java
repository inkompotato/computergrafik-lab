package ab3;

import static org.lwjgl.opengl.GL30.*;

import opengl.AbstractOpenGLBase;
import opengl.ShaderProgram;

public class Aufgabe3undFolgende extends AbstractOpenGLBase {

    private ShaderProgram shaderProgram;

    public static void main(String[] args) {
        new Aufgabe3undFolgende().start("CG Aufgabe 3", 700, 700);
    }

    private Mat4 matrix = new Mat4();
    int loc;

    @Override
    protected void init() {

        matrix.translate(0, 0, -2);

        shaderProgram = new ShaderProgram("aufgabe3");
        glUseProgram(shaderProgram.getId());

        loc = glGetUniformLocation(shaderProgram.getId(), "matrix");


        int vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        createVBO(vertices, 0, 3);


        glEnable(GL_DEPTH_TEST); // z-Buffer aktivieren
        glEnable(GL_CULL_FACE); // backface culling aktivieren

        int projloc = glGetUniformLocation(shaderProgram.getId(), "projectionMatrix");
        glUniformMatrix4fv(projloc, false, new Mat4(0.5f, 100f).getValuesAsArray());

        //normalen vektoren - stuff
        float[] normalen = new float[]{
                                        0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,//vorne
                                        0,0,-1,0,0,-1,0,0,-1,0,0,-1,0,0,-1,0,0,-1,//hinten
                                        0,-1,0,0,-1,0,0,-1,0,0,-1,0,0,-1,0,0,-1,0,//unten
                                        0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,//oben
                                        -1,0,0,-1,0,0,-1,0,0,-1,0,0,-1,0,0,-1,0,0,//links
                                        1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0 //rechts
                                    };

        createVBO(normalen,1,3);



    }

    private int counter;

    @Override
    public void update() {
        if (counter < 45) {
            matrix.translate(0, 0, 2).rotateX(0.01f).translate(0, 0, -2);
            matrix.translate(0, 0, 2).rotateZ(0.01f).translate(0, 0, -2);
        }
        else
            matrix.translate(0, 0, 2).rotateY(0.01f).translate(0, 0, -2);

        counter = (counter + 1) % 90;
    }

//    private void createVBO(float[] array, int id, int size) {
//
//        createVBo(array, id, size);
//    }

    public static void createVBO(float[] array, int id, int size) {
        int vboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);

        glBufferData(GL_ARRAY_BUFFER, array, GL_STATIC_DRAW);

        glVertexAttribPointer(id, size, GL_FLOAT, false, 0, 0);

        glEnableVertexAttribArray(id);
    }


    @Override
    protected void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        // Matrix an Shader übertragen

        // VAOs zeichnen
        //int uniformTransformationMatrixID = glGetUniformLocation(shaderProgram.getId(), "matrix");
        glUniformMatrix4fv(loc, false, matrix.getValuesAsArray());

        int anzEcken = 36;
        glDrawArrays(GL_TRIANGLES, 0, anzEcken);
    }

    @Override
    public void destroy() {
    }

    float[] vertices = new float[]{
            //vorne
            -0.5F, -0.5F, 0.5F,
            0.5F, -0.5F, 0.5F,
            -0.5F, 0.5F, 0.5F,

            -0.5F, 0.5F, 0.5F,
            0.5F, -0.5F, 0.5F,
            0.5F, 0.5F, 0.5F,

            //hinten
            -0.5F, -0.5F, -0.5F,
            -0.5F, 0.5F, -0.5F,
            0.5F, -0.5F, -0.5F,

            -0.5F, 0.5F, -0.5F,
            0.5F, 0.5F, -0.5F,
            0.5F, -0.5F, -0.5F,

            //unten
            -0.5F, -0.5F, 0.5F,
            0.5F, -0.5F, -0.5F,
            0.5F, -0.5F, 0.5F,

            -0.5F, -0.5F, 0.5F,
            -0.5F, -0.5F, -0.5F,
            0.5F, -0.5F, -0.5F,

            //oben
            -0.5F, 0.5F, 0.5F,
            0.5F, 0.5F, 0.5F,
            0.5F, 0.5F, -0.5F,

            -0.5F, 0.5F, 0.5F,
            0.5F, 0.5F, -0.5F,
            -0.5F, 0.5F, -0.5F,

            //links
            -0.5F, 0.5F, 0.5F,
            -0.5F, 0.5F, -0.5F,
            -0.5F, -0.5F, 0.5F,

            -0.5F, -0.5F, 0.5F,
            -0.5F, 0.5F, -0.5F,
            -0.5F, -0.5F, -0.5F,

            //rechts
            0.5F, 0.5F, 0.5F,
            0.5F, -0.5F, 0.5F,
            0.5F, 0.5F, -0.5F,

            0.5F, -0.5F, 0.5F,
            0.5F, -0.5F, -0.5F,
            0.5F, 0.5F, -0.5F
    };
}
