package privatekeyattack;

public class DifferentialCryptanalysisRound1 {
    static int[] SBOX = {
            14, 4, 13, 1,
            2, 15, 11, 8,
            3, 10, 6, 12,
            5, 9, 0, 7
    };
    static int encryptRound(int plaintext, int key) {
        int xor = plaintext ^ key;
        return SBOX[xor];
    }
    public static void main(String[] args) {
        int deltaP = 0b0110;
        int P1 = 0b1101;
        int P2 = (P1 ^ deltaP);

        System.out.println("Plaintext P1: " + Integer.toBinaryString(P1));
        System.out.println("Plaintext P2: " + Integer.toBinaryString(P2));
        System.out.println("Input Difference (ΔP): " + Integer.toBinaryString(deltaP));

        for (int keyGuess=0; keyGuess<16; keyGuess++) {
            int C1 = encryptRound(P1, keyGuess);
            int C2 = encryptRound(P2, keyGuess);
            int deltaC = (C1 ^ C2);

            System.out.println("Key Guess: " + keyGuess +
                    "\t | C1: " + Integer.toBinaryString(C1) +
                    "\t | C2: " + Integer.toBinaryString(C2) +
                    "\t | Output Diff (ΔC): " + Integer.toBinaryString(deltaC));
        }
    }
}