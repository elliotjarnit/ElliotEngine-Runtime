package dev.elliotjarnit.elliotengine.Utils;

// Row major order
public class Matrix3 {
    double[] values;

    public Matrix3() {
        this.values = new double[9];
        for (int i = 0; i < 16; i++) {
            this.values[i] = 0.0;
        }
    }
    public Matrix3(double[] values) {
        this.values = new double[9];
        for (int i = 0; i < 9; i++) {
            if (i >= values.length) {
                this.values[i] = 0.0;
            } else {
                this.values[i] = values[i];
            }
        }
    }

    public Matrix3 mul(Matrix3 other) {
        double[] result = new double[9];
        for (int i = 0; i < 9; i++) {
            result[i] = 0.0;
            for (int j = 0; j < 3; j++) {
                result[i] += this.values[j + (i / 3) * 3] * other.values[i % 3 + j * 3];
            }
        }
        return new Matrix3(result);
    }

    public Vector3 transform(Vector3 in) {
        return new Vector3(
                in.x * values[0] + in.y * values[3] + in.z * values[6],
                in.x * values[1] + in.y * values[4] + in.z * values[7],
                in.x * values[2] + in.y * values[5] + in.z * values[8]
        );
    }

    public String toString() {
        return "[" + values[0] + ", " + values[1] + ", " + values[2] + "]\n" +
                "[" + values[3] + ", " + values[4] + ", " + values[5] + "]\n" +
                "[" + values[6] + ", " + values[7] + ", " + values[8] + "]";
    }
}
