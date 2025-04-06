package publickeyattack;

public class InvalidCurveAttack {
    static final int p = 97;
    static class Curve {
        int a, b;

        Curve(int a, int b) {
            this.a = a;
            this.b = b;
        }
        boolean isOnCurve(Point P) {
            if (P.infinity) {
                return true;
            }
            int x = P.x;
            int y = P.y;
            int left = (y * y) % p;
            int right = (x * x * x + a * x + b) % p;
            if(right < 0) {
                right = right + p;
            }
            return left == right;
        }
    }
    static class Point {
        int x, y;
        boolean infinity;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
            this.infinity = false;
        }
        Point() {
            this.infinity = true;
        }
        public String toString() {
            String point;
            if(infinity) {
                point = "Point(infinity)";
            } else {
                point = "(" + x + ", " + y + ")";
            }
            return point;
        }
    }
    static int modInverse(int a) {
        a = a % p;
        if(a < 0) {
            a = a + p;
        }
        int modInv = -1;
        for(int i = 1; i < p; i++) {
            if ((a * i) % p == 1) {
                modInv = i;
                break;
            }
        }
        return modInv;
    }
    static Point add(Point P, Point Q, Curve curve) {
        if (P.infinity) {
            return Q;
        }
        if (Q.infinity) {
            return P;
        }
        if (P.x == Q.x && (P.y + Q.y) % p == 0) {
            return new Point();
        }
        int lambda;
        if (P.x == Q.x && P.y == Q.y) {
            // When P = Q
            int num = (3 * P.x * P.x + curve.a) % p;
            int den = (2 * P.y) % p;
            lambda = (num * modInverse(den)) % p;
        } else {
            // When P ≠ Q
            int num = (Q.y - P.y + p) % p;
            int den = (Q.x - P.x + p) % p;
            lambda = (num * modInverse(den)) % p;
        }
        int xr = (lambda * lambda - P.x - Q.x) % p;
        int yr = (lambda * (P.x - xr + p) - P.y ) % p;
        if(xr < 0) {
            xr = xr + p;
        }
        if(yr < 0) {
            yr = yr + p;
        }
        return new Point(xr, yr);
    }
    static Point multiply(Point P, int k, Curve curve) {
        Point R = new Point(); // Infinity
        Point Q = P;

        for (int i = 31; i >= 0; i--) {
            R = add(R, R, curve);
            if (((k >> i) & 1) == 1) {
                R = add(R, Q, curve);
            }
        }
        return R;
    }
    public static void main(String[] args) {
        Curve validCurve = new Curve(2, 3);
        Point G = new Point(3, 6); // Generator
        Point fakePoint = new Point(42, 56); // Invalid point
        int alicePrivate = 13;
        if (!validCurve.isOnCurve(fakePoint)) {
            System.out.println("Received point is NOT on valid curve!");
        }
        Point sharedSecret = multiply(fakePoint, alicePrivate, validCurve);
        System.out.println("Attacker sent fake point (P): " + fakePoint);
        System.out.println("Alice's private key (k): " + alicePrivate);
        System.out.println("Computed shared secret (k*P): " + sharedSecret);
    }
}