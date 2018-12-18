package ab3;

import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.opengl.GL30.*;

import opengl.AbstractOpenGLBase;
import opengl.ShaderProgram;
import opengl.Texture;
import org.lwjgl.glfw.GLFWKeyCallback;

public class Aufgabe3undFolgende extends AbstractOpenGLBase {

    private ShaderProgram shaderProgram;

    public static void main(String[] args) {
        new Aufgabe3undFolgende().start("CG Aufgabe 3", 700, 700);
    }

    private Mat4 matrix = new Mat4();
    int loc;

    private GLFWKeyCallback keyCallback;

    private float[] cubeVertices = Geometry.getCubeVertices();

    @Override
    protected void init() {
        glfwSetKeyCallback(super.getWindow(), keyCallback = new KeyboardHandler());

        matrix.translate(0, 0, -4);

        shaderProgram = new ShaderProgram("aufgabe3");
        glUseProgram(shaderProgram.getId());

        loc = glGetUniformLocation(shaderProgram.getId(), "matrix");


        int vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        createVBO(cubeVertices, 0, 3);

        glEnable(GL_DEPTH_TEST); // z-Buffer aktivieren
        glEnable(GL_CULL_FACE); // backface culling aktivieren

        int projloc = glGetUniformLocation(shaderProgram.getId(), "projectionMatrix");
        glUniformMatrix4fv(projloc, false, new Mat4(2f, 100f).getValuesAsArray());

        float[] normalen = generateNorms(cubeVertices,36);

        createVBO(normalen,1,3);

        float[] uvCoords = new float[]{
                0,0,0,0.5f,0.5f,0, 0.5f,0,0,0.5f,0.5f,0.5f, //gut 1.
                0,0,0,0.25f,0.25f,0, 0,0.25f,0.25f,0.25f,0.25f,0, //gut 2.
                0,0.75f,0.75f,0,0,0, 0,0.75f,0.75f,0.75f,0.75f,0,//gut 3.
                0,0,1,0,1,1, 0,0,1,1,0,1,// gut 4.
                0.75f,0.75f,0.75f,1,1,0.75f, 1,0.75f,0.75f,1,1,1, //gut 5.
                0,0,0,1,1,0, 0,1,1,1,1,0,
        };

        createVBO(uvCoords,2,2);

        Texture texture = new Texture("texture3.png");

        glBindTexture(GL_TEXTURE_2D,texture.getId());
    }

    private int counter;

    @Override
    public void update() {

        if(KeyboardHandler.isKeyDown(65)) { //A
            matrix.translate(-0.01f, 0, 0);
        }
        if(KeyboardHandler.isKeyDown(68)) { //D
            matrix.translate(0.01f, 0, 0);
        }
        if(KeyboardHandler.isKeyDown(87)) { //W
            matrix.translate(0, 0.01f,0);
        }
        if(KeyboardHandler.isKeyDown(83)) { //S
            matrix.translate(0, -0.01f, 0);
        }
        if(KeyboardHandler.isKeyDown(263)) {//Left Arrow Key
            float[] loc = matrix.getMatLoc();
            matrix.translate(-loc[0], -loc[1], -loc[2]).rotateY(0.01f).translate(loc[0], loc[1], loc[2]);
        }
        if(KeyboardHandler.isKeyDown(262)) { //Right Arrow Key
            float[] loc = matrix.getMatLoc();
            matrix.translate(-loc[0], -loc[1], -loc[2]).rotateY(-0.01f).translate(loc[0], loc[1], loc[2]);
        }
        if(KeyboardHandler.isKeyDown(265)) {//Up Arrow Key
            float[] loc = matrix.getMatLoc();
            matrix.translate(-loc[0], -loc[1], -loc[2]).rotateX(-0.01f).translate(loc[0], loc[1], loc[2]);
        }
        if(KeyboardHandler.isKeyDown(264)) {//Down Arrow Key
            float[] loc = matrix.getMatLoc();
            matrix.translate(-loc[0], -loc[1], -loc[2]).rotateX(0.01f).translate(loc[0], loc[1], loc[2]);
        }

    }


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


    private float[] generateNorms(float[] objectKoords, int amountPoints){
        Vector[] vectors = new Vector[amountPoints];
        int counter = 0;
        for (int i = 0; i < objectKoords.length; i+=3) {
            Vector p = new Vector(objectKoords[i],objectKoords[i+1],objectKoords[i+2]);
            vectors[counter] = p;
            counter++;
        }

        float[] norms = new float[36*3];
        int normscounter = -1;
        for (int i = 0; i < vectors.length; i+=3) {
            Vector a = vectors[i];
            Vector b = vectors[i+1];
            Vector c = vectors[i+2];

            Vector normA = a.getVectorTo(b).crossWith(a.getVectorTo(c));
            for (int j = 0; j < 3; j++) {
                norms[++normscounter]=normA.x;
                norms[++normscounter]=normA.y;
                norms[++normscounter]=normA.z;
            }
        }

        return norms;
    }


}
