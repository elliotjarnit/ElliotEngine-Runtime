package dev.elliotjarnit.ElliotEngine.Utils;

// Row major order
public class Matrix4 {
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
                in.x * values[0] + in.y * values[4] + in.z * values[8] + in.w * values[12],
                in.x * values[1] + in.y * values[5] + in.z * values[9] + in.w * values[13],
                in.x * values[2] + in.y * values[6] + in.z * values[10] + in.w * values[14],
                in.x * values[3] + in.y * values[7] + in.z * values[11] + in.w * values[15]
        );
    }

    public Matrix4 inverse() {
        double[] augmented = new double[32];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                if (j < 4) {
                    augmented[i * 8 + j] = values[i * 4 + j];
                } else {
                    augmented[i * 8 + j] = (i == (j - 4)) ? 1.0f : 0.0f;
                }
            }
        }

        for (int pivot = 0; pivot < 4; pivot++) {
            // Find the pivot row
            int maxRow = pivot;
            for (int i = pivot + 1; i < 4; i++) {
                if (Math.abs(augmented[i * 8 + pivot]) > Math.abs(augmented[maxRow * 8 +pivot])) {
                    maxRow = i;
                }
            }

            // Swap the pivot row with the maxRow
            double[] temp = new double[8];

            System.arraycopy(augmented, pivot * 8, temp, 0, 8);

            for (int i = 0; i < 8; i++) {
                augmented[pivot * 8 + i] = augmented[maxRow * 8 + i];
            }

            System.arraycopy(temp, 0, augmented, maxRow * 8, 8);

            // Scale the pivot row
            double pivotValue = augmented[pivot * 8 + pivot];
            for (int j = 0; j < 8; j++) {
                augmented[pivot * 8 + j] /= pivotValue;
            }

            // Eliminate other rows
            for (int i = 0; i < 4; i++) {
                if (i != pivot) {
                    double factor = augmented[i * 8 + pivot];
                    for (int j = 0; j < 8; j++) {
                        augmented[i * 8 + j] -= factor * augmented[pivot * 8 + j];
                    }
                }
            }
        }

        double[] invertedMatrix = new double[16];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                invertedMatrix[i * 4 + j] = augmented[i * 8 + (j + 4)];
            }
        }

        return new Matrix4(invertedMatrix);
    }

    public String toString() {
        return "[" + values[0] + ", " + values[1] + ", " + values[2] + ", " + values[3] + "]\n" +
                "[" + values[4] + ", " + values[5] + ", " + values[6] + ", " + values[7] + "]\n" +
                "[" + values[8] + ", " + values[9] + ", " + values[10] + ", " + values[11] + "]\n" +
                "[" + values[12] + ", " + values[13] + ", " + values[14] + ", " + values[15] + "]";
    }
}
