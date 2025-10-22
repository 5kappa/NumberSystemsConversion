import java.util.Scanner;

public class Converter {
    
    final static int BINARY = 2;
    final static int OCTAL = 8;
    final static int DECIMAL = 10;
    final static int HEX = 16;

    static class Number {
        String intPart;
        String fracPart;
        Number(String intPart, String fracPart) {
            this.intPart = intPart;
            this.fracPart = fracPart;
        }
    }

    static Number parser(String input) {
        String[] parts = input.split("\\.");

        String intPart = parts[0];
        String fracPart = parts.length == 2 ? parts[1] : "0";

        if (parts.length > 2) {
            return null;
        }

        return new Number(intPart, fracPart);
    }

    static String fracConvertToDecimal(String fracPart, int radix) {
        double fracValue = 0.0;
        for (int i = 0; i < fracPart.length(); i++) {
            fracValue += Character.getNumericValue(fracPart.charAt(i)) * Math.pow(radix, -(i + 1));
        }

        String fracValueString = String.valueOf(fracValue);
        
        return fracValueString;
    }

    static String fracConvertToBinary(String fracPart, int radix) {
        if (radix != DECIMAL) {
            fracPart = fracConvertToDecimal(fracPart, radix);
        }

        double fracPartDouble = Double.parseDouble(fracPart);

        int precisionLimit = 20;
        StringBuilder fracValueStringBuilder = new StringBuilder();
        while (fracPartDouble % 1.0 != 0 && precisionLimit-- > 0) {
            fracPartDouble *= 2;
            if (fracPartDouble > 1) {
                fracValueStringBuilder.append("1");
                fracPartDouble -= 1;
            } else {
                fracValueStringBuilder.append("0");
            }
        }

        return fracValueStringBuilder.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=".repeat(36));
        System.out.printf("%30s\n", "NUMBER SYSTEMS CONVERTER");
        System.out.println("=".repeat(36) + "\n");

        System.out.println(" [1] Binary to Octal");
        System.out.println(" [2] Binary to Decimal");
        System.out.println(" [3] Binary to Hexadecimal");
        System.out.println(" [4] Octal to Binary");
        System.out.println(" [5] Octal to Decimal");
        System.out.println(" [6] Octal to Hexadecimal");
        System.out.println(" [7] Decimal to Binary");
        System.out.println(" [8] Decimal to Octal");
        System.out.println(" [9] Decimal to Hexadecimal");
        System.out.println("[10] Hexadecimal to Binary");
        System.out.println("[11] Hexadecimal to Octal");
        System.out.println("[12] Hexadecimal to Decimal");
        System.out.print("\nYOUR CHOICE: ");

        int choice = sc.nextInt();
        sc.nextLine(); // clear input buffer

        switch (choice) {

        }

        sc.close();
    }
}
