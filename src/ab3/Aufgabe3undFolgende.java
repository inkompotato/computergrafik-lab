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
	private int loc;

	@Override
	protected void init() {
		shaderProgram = new ShaderProgram("aufgabe3");
		glUseProgram(shaderProgram.getId());



		// Koordinaten, VAO, VBO, ... hier anlegen und im Grafikspeicher ablegen
		float[]dreiecksKoordinaten = new float[]{1, 0, 0, 0, 0.8f, 0, -1, -1, 0};

		float[]eckenFarben = new float[]{1,0,1,0,1,0,0,0,1};

		int vaoId = glGenVertexArrays();
		glBindVertexArray(vaoId);
		createVBO(dreiecksKoordinaten, 0, 3);
		createVBO(eckenFarben,1,3);

		loc = glGetUniformLocation(shaderProgram.getId(),"matrix");

		glEnable(GL_DEPTH_TEST); // z-Buffer aktivieren
		//glEnable(GL_CULL_FACE); // backface culling aktivieren
	}

	@Override
	public void update() {
		// Transformation durchführen (Matrix anpassen)
		matrix.rotateZ(0.01f);
		matrix.rotateX(0.005f);
		matrix.rotateY(0.005f);
	}

	private void createVBO(float[] array, int id, int size) {

		createVBo(array, id, size);
	}

	public static void createVBo(float[] array, int id, int size) {
		int vboId = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboId);

		glBufferData(GL_ARRAY_BUFFER, array, GL_STATIC_DRAW);

		glVertexAttribPointer(id,size,GL_FLOAT, false, 0, 0);

		glEnableVertexAttribArray(id);
	}


	@Override
	protected void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// Matrix an Shader übertragen

		glUniformMatrix4fv(loc,false, matrix.getValuesAsArray());

		// VAOs zeichnen


		int anzEcken = 3;
		glDrawArrays(GL_TRIANGLES, 0, anzEcken);
	}

	@Override
	public void destroy() {
	}
}
