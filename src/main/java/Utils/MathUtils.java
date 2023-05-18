package src.main.java.Utils;

public class MathUtils {
    public static class Vector3 {
        public double x;
        public double y;
        public double z;

        public Vector3(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public Vector3() {
            this.x = 0;
            this.y = 0;
            this.z = 0;
        }

        public Vector3(Vector3 v) {
            this.x = v.x;
            this.y = v.y;
            this.z = v.z;
        }

        public Vector3 add(Vector3 v) {
            return new Vector3(this.x + v.x, this.y + v.y, this.z + v.z);
        }

        public Vector3 sub(Vector3 v) {
            return new Vector3(this.x - v.x, this.y - v.y, this.z - v.z);
        }

        public Vector3 mul(Vector3 v) {
            return new Vector3(this.x * v.x, this.y * v.y, this.z * v.z);
        }

        public Vector3 div(Vector3 v) {
            return new Vector3(this.x / v.x, this.y / v.y, this.z / v.z);
        }

        public String toString() {
            return "(" + this.x + ", " + this.y + ", " + this.z + ")";
        }
    }

    public static class Vector2 {
        public double x;
        public double y;

        public Vector2(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public Vector2() {
            this.x = 0;
            this.y = 0;
        }

        public Vector2(Vector2 v) {
            this.x = v.x;
            this.y = v.y;
        }

        public Vector2 add(Vector2 v) {
            return new Vector2(this.x + v.x, this.y + v.y);
        }

        public Vector2 sub(Vector2 v) {
            return new Vector2(this.x - v.x, this.y - v.y);
        }

        public Vector2 mul(Vector2 v) {
            return new Vector2(this.x * v.x, this.y * v.y);
        }

        public Vector2 div(Vector2 v) {
            return new Vector2(this.x / v.x, this.y / v.y);
        }

        public String toString() {
            return "(" + this.x + ", " + this.y + ")";
        }
    }

    public static class Matrix3 {
        double[] values;
        public Matrix3(double[] values) {
            this.values = values;
        }
        public Matrix3 multiply(Matrix3 other) {
            double[] result = new double[9];
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    for (int i = 0; i < 3; i++) {
                        result[row * 3 + col] +=
                                this.values[row * 3 + i] * other.values[i * 3 + col];
                    }
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
    }
}
