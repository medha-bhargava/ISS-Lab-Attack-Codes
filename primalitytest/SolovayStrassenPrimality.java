package primalitytest;

import java.util.Random;
import java.util.Scanner;

public class SolovayStrassenPrimality {
    private static int modularExp(int base, int exp, int modulo) {
        /*
         * Assume that val = modularExp(base, exp/2, modulo) -> exp/2 is integer division
         * If exponent is odd: result = val * val * base
         * If exponent is even: result = val * val
         */
        if(base == 1 || exp == 0) {
            return 1;
        }
        int val = modularExp(base, exp/2, modulo);
        int result = (int) (((((long) val) % modulo) * (((long) val) % modulo)) % modulo);
        if(exp % 2 != 0) {
            result = (int) (((((long) result) % modulo) * (base % modulo)) % modulo);
        }
        return result;
    }
    private static int gcd(int a, int b) {
        int dividend = a;
        int divisor = b;
        while(divisor != 0) {
            int remainder = dividend % divisor;
            dividend = divisor;
            divisor = remainder;
        }
        return dividend;
    }
    private static int jacobi(int a, int n) {
        a = a % n;
        if(a < 0) {
            a = a + n;
        }
        if(gcd(a, n) != 1) {
            return 0;
        }
        int result = 1;
        while(a != 0) {
            while(a % 2 == 0) {
                if(n % 8 == 3 || n % 8 == 5) {
                    result = -result;
                }
                a /= 2;
            }
            int temp = a;
            a = n;
            n = temp;
            if(a % 4 == 3 && n % 4 == 3) {
                result = -result;
            }
            a = a % n;
        }
        if(n == 1) {
            return result;
        } else {
            return 0;
        }
    }
    private static boolean isPrime(int n, int numIterations) {
        if(n < 2) {
            return false;
        }
        if(n == 2) {
            return true;
        }
        if(n % 2 == 0) {
            return false;
        }
        Random random = new Random();
        for(int i = 0; i < numIterations; i++) {
            int a = random.nextInt(2, n); // {2, 3, 4, ... , n - 1}
            int jacobiValue = jacobi(a, n);
            if(jacobiValue < 0) {
                jacobiValue = jacobiValue + n;
            }
            if(jacobiValue != modularExp(a, (n - 1) / 2, n)) {
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a number: ");
        int number = sc.nextInt();
        int numIterations = 10; // Hard-coded to 10
        if(isPrime(number, numIterations)) {
            System.out.println(number + " is prime");
        } else {
            System.out.println(number + " is not prime");
        }
        sc.close();
    }
}