package src.dev.elliotjarnit.ElliotEngine.Utils;

public class MathUtils {
    public static class Vector4 {
        public double x;
        public double y;
        public double z;
        public double w;

        public Vector4() {
            this.x = 0;
            this.y = 0;
            this.z = 0;
            this.w = 0;
        }
        public Vector4(double x, double y, double z, double w) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.w = w;
        }
        public Vector4(Vector4 v) {
            this.x = v.x;
            this.y = v.y;
            this.z = v.z;
            this.w = v.w;
        }

        public Vector4 add(Vector4 v) {
            return new Vector4(this.x + v.x, this.y + v.y, this.z + v.z, this.w + v.w);
        }

        public Vector4 sub(Vector4 v) {
            return new Vector4(this.x - v.x, this.y - v.y, this.z - v.z, this.w - v.w);
        }

        public Vector4 mul(Vector4 v) {
            return new Vector4(this.x * v.x, this.y * v.y, this.z * v.z, this.w * v.w);
        }
        public Vector4 mul(double d) {
            return new Vector4(this.x * d, this.y * d, this.z * d, this.w * d);
        }

        public Vector4 div(Vector4 v) {
            return new Vector4(this.x / v.x, this.y / v.y, this.z / v.z, this.w / v.w);
        }
        public Vector4 div(double d) {
            return new Vector4(this.x / d, this.y / d, this.z / d, this.w / d);
        }
    }

    public static class Vector3 {
        public double x;
        public double y;
        public double z;

        public Vector3() {
            this.x = 0;
            this.y = 0;
            this.z = 0;
        }
        public Vector3(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
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
        public Vector3 mul(double d) {
            return new Vector3(this.x * d, this.y * d, this.z * d);
        }

        public Vector3 div(Vector3 v) {
            return new Vector3(this.x / v.x, this.y / v.y, this.z / v.z);
        }
        public Vector3 div(double d) {
            return new Vector3(this.x / d, this.y / d, this.z / d);
        }

        public String toString() {
            return "(" + this.x + ", " + this.y + ", " + this.z + ")";
        }
    }

    public static class Vector2 {
        public double x;
        public double y;

        public Vector2() {
            this.x = 0;
            this.y = 0;
        }
        public Vector2(double x, double y) {
            this.x = x;
            this.y = y;
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

        public Matrix3() {
            this.values = new double[9];
            for (int i = 0; i < 16; i++) {
                this.values[i] = 0.0;
            }
        }
        public Matrix3(double[] values) {
            this.values = new double[9];
            for (int i = 0; i < 16; i++) {
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
                    result[i] += this.values[j + (i / 3) * 3] * other.values[(i % 3) + j * 3];
                }
            }
            return new Matrix3(result);
        }

        public Vector3 transform(Vector3 in) {
            return new Vector3(
                    in.x * values[0] + in.y * values[1] + in.z * values[2],
                    in.x * values[3] + in.y * values[4] + in.z * values[5],
                    in.x * values[6] + in.y * values[7] + in.z * values[8]
            );
        }

        public String toString() {
            return "[" + values[0] + ", " + values[1] + ", " + values[2] + "]\n" +
                    "[" + values[3] + ", " + values[4] + ", " + values[5] + "]\n" +
                    "[" + values[6] + ", " + values[7] + ", " + values[8] + "]";
        }
    }

    public static class Matrix4 {
        double[] values;

        public Matrix4() {
            this.values = new double[16];
            for (int i = 0; i < 16; i++) {
                this.values[i] = 0.0;
            }
        }
        public Matrix4(double[] values) {
            this.values = new double[16];
            for (int i = 0; i < 16; i++) {
                if (i >= values.length) {
                    this.values[i] = 0.0;
                } else {
                    this.values[i] = values[i];
                }
            }
        }

        public Matrix4 mul(Matrix4 other) {
            double[] result = new double[16];
            for (int row = 0; row < 4; row++) {
                for (int col = 0; col < 4; col++) {
                    for (int i = 0; i < 4; i++) {
                        result[row * 4 + col] +=
                                this.values[row * 4 + i] * other.values[i * 4 + col];
                    }
                }
            }
            return new Matrix4(result);
        }

        public Vector4 transform(Vector4 in) {
            return new Vector4(
                    in.x * values[0] + in.y * values[1] + in.z * values[2] + in.w * values[3],
                    in.x * values[4] + in.y * values[5] + in.z * values[6] + in.w * values[7],
                    in.x * values[8] + in.y * values[9] + in.z * values[10] + in.w * values[11],
                    in.x * values[12] + in.y * values[13] + in.z * values[14] + in.w * values[15]
            );
        }

        public String toString() {
            return "[" + values[0] + ", " + values[1] + ", " + values[2] + ", " + values[3] + "]\n" +
                    "[" + values[4] + ", " + values[5] + ", " + values[6] + ", " + values[7] + "]\n" +
                    "[" + values[8] + ", " + values[9] + ", " + values[10] + ", " + values[11] + "]\n" +
                    "[" + values[12] + ", " + values[13] + ", " + values[14] + ", " + values[15] + "]";
        }
    }
    
    public static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }
}
