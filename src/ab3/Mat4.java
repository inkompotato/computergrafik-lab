package ab3;

//Alle Operationen ändern das Matrixobjekt selbst und geben das eigene Matrixobjekt zurück
//Dadurch kann man Aufrufe verketten, z.B.
//Matrix4 m = new Matrix4().scale(5).translate(0,1,0).rotateX(0.5f);
public class Mat4 {

    private float[] v;

    public Mat4() {
        v = new float[]{1f, 0, 0, 0,
                        0, 1f, 0, 0,
                        0, 0, 1f, 0,
                        0, 0, 0, 1f};
    }
    public Mat4(float[] array){
        if (array.length==16) v = array;
    }

    public Mat4(Mat4 copy) {
        v = copy.getValuesAsArray();
    }

    public Mat4(float near, float far) {
        v = new float[] {
                near, 0, 0, 0,
                0, near, 0, 0,
                0, 0, (-far - near) / (far - near), -1,
                0, 0, (-2 * near * far) / (far - near), 0
        };
    }

    public Mat4 multiply(Mat4 other) {
        float[] u = other.getValuesAsArray();
        float[] r = new float[16];

        r[0] = u[0]*v[0]+u[4]*v[1]+u[8]*v[2]+u[12]*v[3];
        r[4] = u[0]*v[4]+u[4]*v[5]+u[8]*v[6]+u[12]*v[7];
        r[8] = u[0]*v[8]+u[4]*v[9]+u[8]*v[10]+u[12]*v[11];
        r[12] = u[0]*v[12]+u[4]*v[13]+u[8]*v[14]+u[12]*v[15];

        r[1] = u[1]*v[0]+u[5]*v[1]+u[9]*v[2]+u[13]*v[3];
        r[5] = u[1]*v[4]+u[5]*v[5]+u[9]*v[6]+u[13]*v[7];
        r[9] = u[1]*v[8]+u[5]*v[9]+u[9]*v[10]+u[13]*v[11];
        r[13] = u[1]*v[12]+u[5]*v[13]+u[9]*v[14]+u[13]*v[15];

        r[2] = u[2]*v[0]+u[6]*v[1]+u[10]*v[2]+u[14]*v[3];
        r[6] = u[2]*v[4]+u[6]*v[5]+u[10]*v[6]+u[14]*v[7];
        r[10] = u[2]*v[8]+u[6]*v[9]+u[10]*v[10]+u[14]*v[11];
        r[14] = u[2]*v[12]+u[6]*v[13]+u[10]*v[14]+u[14]*v[15];

        r[3] = u[3]*v[0]+u[7]*v[1]+u[11]*v[2]+u[15]*v[3];
        r[7] = u[3]*v[4]+u[7]*v[5]+u[11]*v[6]+u[15]*v[7];
        r[11] = u[3]*v[8]+u[7]*v[9]+u[11]*v[10]+u[15]*v[11];
        r[15] = u[3]*v[12]+u[7]*v[13]+u[11]*v[14]+u[15]*v[15];

        v=r;

        return this;
    }


    public Mat4 translate(float x, float y, float z) {
        multiply(new Mat4(new float[]{1, 0, 0, 0,
                                      0, 1, 0, 0,
                                      0, 0, 1, 0,
                                      x, y, z, 1}));
        return this;
    }

    public Mat4 scale(float uniformFactor) {
        return scale(uniformFactor,uniformFactor,uniformFactor);
    }

    public Mat4 scale(float sx, float sy, float sz) {
        multiply(new Mat4(new float[]{sx, 0, 0, 0,
                                      0, sy, 0, 0,
                                      0, 0, sz, 0,
                                      0, 0, 0,  1}));
        return this;
    }


    public Mat4 rotateX(float angle) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);

        multiply(new Mat4(new float[]{1,   0,   0,   0,
                                      0, cos, -sin,  0,
                                      0, sin, cos,   0,
                                      0,   0,   0,   1}));
        return this;
    }

    public Mat4 rotateY(float angle) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);

        multiply(new Mat4(new float[]{cos,  0, sin,  0,
                                      0,   1,   0,   0,
                                      -sin, 0, cos,  0,
                                      0,   0,   0,   1}));
        return this;
    }

    public Mat4 rotateZ(float angle) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);

        multiply(new Mat4(new float[]{cos, sin, 0,  0,
                                      -sin, cos, 0, 0,
                                      0,  0,   1,   0,
                                      0,  0,   0,   1}));
        return this;
    }


    public float[] getValuesAsArray() {
        return v;
    }

    public float[] getMatLoc(){
        float[] matLoc = new float[]{v[12],v[13],v[14]};
        return matLoc;
    }
}