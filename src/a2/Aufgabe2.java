package a2;

import static org.lwjgl.opengl.GL30.*;

import opengl.AbstractOpenGLBase;
import opengl.ShaderProgram;

public class Aufgabe2 extends AbstractOpenGLBase {

	public static void main(String[] args) {
		new Aufgabe2().start("CG Aufgabe 2", 700, 700);
	}

	@Override
	protected void init() {
		// folgende Zeile läd automatisch "aufgabe2_v.glsl" (vertex) und "aufgabe2_f.glsl" (fragment)
		ShaderProgram shaderProgram = new ShaderProgram("aufgabe2");
		glUseProgram(shaderProgram.getId());

		// Koordinaten, VAO, VBO, ... hier anlegen und im Grafikspeicher ablegen
		float[]dreiecksKoordinaten = new float[]{-1, -1, 0, 0.8f, 1, 0};

		float[]eckenFarben = new float[]{1,0,1,0,1,0,0,0,1};

		int vaoId = glGenVertexArrays();
		glBindVertexArray(vaoId);
		createVBO(dreiecksKoordinaten, 0, 2);
		createVBO(eckenFarben,1,3);

	}

	private void createVBO(float[] array, int id, int size) {

		int vboId = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboId);

		glBufferData(GL_ARRAY_BUFFER, array, GL_STATIC_DRAW);

		glVertexAttribPointer(id,size,GL_FLOAT, false, 0, 0);

		glEnableVertexAttribArray(id);
	}

	@Override
	public void update() {
	}

	@Override
	protected void render() {
		glClear(GL_COLOR_BUFFER_BIT); // Zeichenfläche leeren

		// hier vorher erzeugte VAOs zeichnen
		int anzEcken = 3;
		glDrawArrays(GL_TRIANGLES, 0, anzEcken);

	}

	@Override
	public void destroy() {
	}
}
