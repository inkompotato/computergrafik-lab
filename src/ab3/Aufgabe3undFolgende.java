package ab3;

import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.opengl.GL30.*;

import opengl.AbstractOpenGLBase;
import opengl.ShaderProgram;
import opengl.Texture;
import org.lwjgl.glfw.GLFWKeyCallback;


public class Aufgabe3undFolgende extends AbstractOpenGLBase {

    private ShaderProgram shaderProgram_obj1;
    private ShaderProgram shaderProgram_obj2;

    public static void main(String[] args) {
        new Aufgabe3undFolgende().start("CG Aufgabe 3", 700, 700);
    }

    private Mat4 matrix_obj1 = new Mat4();
    private Mat4 matrix_obj2 = new Mat4();

    private int vaoId_1;
    private int vaoId_2;

    private int texID;
    private int texID2;

    private GLFWKeyCallback keyCallback;

    private float[] cubeVertices = Geometry.getCubeVertices();
    private float[] tVertices = Geometry.getSphereVertices();

    @Override
    protected void init() {
        glfwSetKeyCallback(super.getWindow(), keyCallback = new KeyboardHandler());

        shaderProgram_obj1 = new ShaderProgram("aufgabe3");
        shaderProgram_obj2 = new ShaderProgram("aufgabe3b");

        makeCube();
        makeTetra();


        glEnable(GL_DEPTH_TEST); // z-Buffer aktivieren
        glEnable(GL_CULL_FACE); // backface culling aktivieren

        int projloc = glGetUniformLocation(shaderProgram_obj1.getId(), "projectionMatrix");
        glUniformMatrix4fv(projloc, false, new Mat4(2f, 100f).getValuesAsArray());
    }


    protected void makeTetra() {

        matrix_obj2.translate(1,1,-3);

        float[] normalen = generateNorms(tVertices, 12);

        float[] uvCoords = new float[]{
                0, 0, 0, 0.5f, 0.5f, 0, 0.5f, 0, 0, 0.5f, 0.5f, 0.5f, //gut 1.
                0, 0, 0, 0.25f, 0.25f, 0, 0, 0.25f, 0.25f, 0.25f, 0.25f, 0, //gut 2.
                0, 0.75f, 0.75f, 0, 0, 0, 0, 0.75f, 0.75f, 0.75f, 0.75f, 0,//gut 3.
                0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1,// gut 4.
                0.75f, 0.75f, 0.75f, 1, 1, 0.75f, 1, 0.75f, 0.75f, 1, 1, 1, //gut 5.
                0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0,
        };

        glUseProgram(shaderProgram_obj2.getId());
        vaoId_2 = glGenVertexArrays();
        glBindVertexArray(vaoId_2);

        createVBO(tVertices, 0, 3);
        createVBO(normalen, 1, 3);

        Texture texture = new Texture("texture2.jpg", 8, true);
        texID2 = texture.getId();
        glGenerateMipmap(GL_TEXTURE_2D);
        //edit this for a different texture filtering mode
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST_MIPMAP_NEAREST);
        glBindTexture(GL_TEXTURE_2D, texID);
    }

    protected void makeCube() {

        matrix_obj1.translate(0, 0, -4);

        float[] normalen = generateNorms(cubeVertices, 36);

        float[] uvCoords = new float[]{
                0, 0, 0, 0.5f, 0.5f, 0, 0.5f, 0, 0, 0.5f, 0.5f, 0.5f, //gut 1.
                0, 0, 0, 0.25f, 0.25f, 0, 0, 0.25f, 0.25f, 0.25f, 0.25f, 0, //gut 2.
                0, 0.75f, 0.75f, 0, 0, 0, 0, 0.75f, 0.75f, 0.75f, 0.75f, 0,//gut 3.
                0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1,// gut 4.
                0.75f, 0.75f, 0.75f, 1, 1, 0.75f, 1, 0.75f, 0.75f, 1, 1, 1, //gut 5.
                0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0,
        };


        glUseProgram(shaderProgram_obj1.getId());
        vaoId_1 = glGenVertexArrays();
        glBindVertexArray(vaoId_1);

        createVBO(cubeVertices, 0, 3);
        createVBO(normalen, 1, 3);
        createVBO(uvCoords, 2, 2);


        Texture texture = new Texture("texture2.jpg", 8, true);
        texID = texture.getId();
        glGenerateMipmap(GL_TEXTURE_2D);
        //edit this for a different texture filtering mode
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST_MIPMAP_NEAREST);
        glBindTexture(GL_TEXTURE_2D, texID);
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

        //matrix1
        int loc1 = glGetUniformLocation(shaderProgram_obj1.getId(), "matrix_obj1");

        glUniformMatrix4fv(loc1, false, matrix_obj1.getValuesAsArray());

        glBindVertexArray(vaoId_1);
        glBindTexture(GL_TEXTURE_2D, texID);

        glDrawArrays(GL_TRIANGLES, 0, 36);


        //matrix2
        int loc2 = glGetUniformLocation(shaderProgram_obj2.getId(), "matrix_obj2");

        glUniformMatrix4fv(loc2, false, matrix_obj2.getValuesAsArray());

        glBindVertexArray(vaoId_2);
        glBindTexture(GL_TEXTURE_2D, texID2);
        glDrawArrays(GL_TRIANGLES, 0, 12);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void update() {

        if (KeyboardHandler.isKeyDown(65)) { //A
            matrix_obj1.translate(-0.01f, 0, 0);
        }
        if (KeyboardHandler.isKeyDown(68)) { //D
            matrix_obj1.translate(0.01f, 0, 0);
        }
        if (KeyboardHandler.isKeyDown(87)) { //W
            matrix_obj1.translate(0, 0.01f, 0);
        }
        if (KeyboardHandler.isKeyDown(83)) { //S
            matrix_obj1.translate(0, -0.01f, 0);
        }
        if (KeyboardHandler.isKeyDown(81)) { //Q
            matrix_obj1.translate(0, 0, 0.01f);
        }
        if (KeyboardHandler.isKeyDown(69)) { //E
            matrix_obj1.translate(0, 0, -0.01f);
        }
        if (KeyboardHandler.isKeyDown(263)) {//Left Arrow Key
            float[] loc = matrix_obj1.getMatLoc();
            matrix_obj1.translate(-loc[0], -loc[1], -loc[2]).rotateY(0.01f).translate(loc[0], loc[1], loc[2]);
        }
        if (KeyboardHandler.isKeyDown(262)) { //Right Arrow Key
            float[] loc = matrix_obj1.getMatLoc();
            matrix_obj1.translate(-loc[0], -loc[1], -loc[2]).rotateY(-0.01f).translate(loc[0], loc[1], loc[2]);
        }
        if (KeyboardHandler.isKeyDown(265)) {//Up Arrow Key
            float[] loc = matrix_obj1.getMatLoc();
            matrix_obj1.translate(-loc[0], -loc[1], -loc[2]).rotateX(-0.01f).translate(loc[0], loc[1], loc[2]);
        }
        if (KeyboardHandler.isKeyDown(264)) {//Down Arrow Key
            float[] loc = matrix_obj1.getMatLoc();
            matrix_obj1.translate(-loc[0], -loc[1], -loc[2]).rotateX(0.01f).translate(loc[0], loc[1], loc[2]);
        }
        if (KeyboardHandler.isKeyDown(82)) { //R
            matrix_obj1.scale(1 / 1.03f);
        }
        if (KeyboardHandler.isKeyDown(70)) { //F
            matrix_obj1.scale(1.03f);
        }
        if (KeyboardHandler.isKeyDown(98)) { //Y

        }
        float[] loc = matrix_obj2.getMatLoc();
        matrix_obj2.translate(-loc[0], -loc[1], -loc[2]).rotateY(0.01f).translate(loc[0], loc[1], loc[2]);

    }

    private static float[] combine(float[] a, float[] b) {
        float[] result = new float[a.length + b.length];
        for (int i = 0; i < result.length; i++) {
            if (i < a.length) {
                result[i] = a[i];
            } else {
                result[i] = b[i - a.length];
            }
        }
        return result;
    }

    private float[] generateNorms(float[] objectKoords, int amountPoints) {
        Vector[] vectors = new Vector[amountPoints];
        int counter = 0;
        for (int i = 0; i < objectKoords.length; i += 3) {
            Vector p = new Vector(objectKoords[i], objectKoords[i + 1], objectKoords[i + 2]);
            vectors[counter] = p;
            counter++;
        }

        float[] norms = new float[amountPoints * 3];
        int normscounter = -1;
        for (int i = 0; i < vectors.length; i += 3) {
            Vector a = vectors[i];
            Vector b = vectors[i + 1];
            Vector c = vectors[i + 2];

            Vector normA = a.getVectorTo(b).crossWith(a.getVectorTo(c));
            for (int j = 0; j < 3; j++) {
                norms[++normscounter] = normA.x;
                norms[++normscounter] = normA.y;
                norms[++normscounter] = normA.z;
            }
        }

        return norms;
    }


}
