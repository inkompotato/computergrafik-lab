package ab3;

public class Geometry {

    //sphere
    static float val = (float) (Math.sqrt(3) / 4);

    private static float[] sphereVertices = new float[] {
        //front
        0.5f, val, 0,
                -0.5f, val, 0,
                0, -val, 0.5f,

                //left
                -0.5f, val, 0,
                0, -val, -0.5f,
                0, -val, 0.5f,

                //right
                0.5f, val, 0,
                0, -val, 0.5f,
                0, -val, -0.5f,

                //back
                -0.5f, val, 0,
                0.5f, val, 0,
                0, -val, -0.5f
    };

    //cube
    private static float[] cubeVertices = new float[]{
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

    static float[] getCubeVertices() {
        return cubeVertices;
    }

    static float[] getSphereVertices(){
        return sphereVertices;
    }
}
