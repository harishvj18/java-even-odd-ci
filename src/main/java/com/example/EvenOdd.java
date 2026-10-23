package com.example;

/** Utility for classifying integers as even or odd. */
public final class EvenOdd {
    private EvenOdd() {
        // Utility class; do not instantiate.
    }

    public static String classify(int number) {
        return number % 2 == 0 ? "Even" : "Odd";
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java -jar app.jar <integer>");
            System.exit(2);
        }

        try {
            int number = Integer.parseInt(args[0]);
            System.out.println(number + " is " + classify(number));
        } catch (NumberFormatException ex) {
            System.err.println("Invalid integer: " + args[0]);
            System.exit(2);
        }
    }
}
