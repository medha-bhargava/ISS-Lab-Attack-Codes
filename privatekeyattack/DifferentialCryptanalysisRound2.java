package privatekeyattack;

public class DifferentialCryptanalysisRound2 {
    static int[] SBOX = {
            14, 4, 13, 1,
            2, 15, 11, 8,
            3, 10, 6, 12,
            5, 9, 0, 7
    };
    static int encrypt2Rounds(int plaintext, int key1, int key2) {
        int round1 = SBOX[plaintext ^ key1];
        int round2 = SBOX[round1 ^ key2];
        return round2;
    }
    public static void main(String[] args) {
        int deltaP = 0b0110;
        int P1 = 0b1101;
        int P2 = P1 ^ deltaP;

        System.out.println("Plaintext P1: " + Integer.toBinaryString(P1));
        System.out.println("Plaintext P2: " + Integer.toBinaryString(P2));
        System.out.println("Input Difference (ΔP): " + Integer.toBinaryString(deltaP));
        System.out.println("----- Trying All Key Pairs -----");

        for (int key1 = 0; key1 < 16; key1++) {
            for (int key2 = 0; key2 < 16; key2++) {
                int C1 = encrypt2Rounds(P1, key1, key2);
                int C2 = encrypt2Rounds(P2, key1, key2);
                int deltaC = C1 ^ C2;

                System.out.println("Key1: " + key1 + ", Key2: " + key2 +
                        "\t | C1: " + Integer.toBinaryString(C1) +
                        "\t | C2: " + Integer.toBinaryString(C2) +
                        "\t | Output Diff (ΔC): " + Integer.toBinaryString(deltaC));
            }
        }
    }
}
