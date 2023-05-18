package src.main.java.Utils;

public class MathUtils {
    public static class Vector3 {
        public float x;
        public float y;
        public float z;

        public Vector3(float x, float y, float z) {
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
    }

    public static class Vector2 {
        public float x;
        public float y;

        public Vector2(float x, float y) {
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
    }
}
