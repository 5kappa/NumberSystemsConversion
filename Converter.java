import java.util.Scanner;
import java.util.InputMismatchException;
import java.math.BigDecimal;
import java.math.RoundingMode;

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

    static Number parse(String input) {
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

        BigDecimal fracPartBD = new BigDecimal(fracPart);

        int precisionLimit = 20;
        StringBuilder fracValueStringBuilder = new StringBuilder();
        while (fracPartBD.remainder(BigDecimal.valueOf(1)).compareTo(BigDecimal.ZERO) != 0 && precisionLimit-- > 0) {
            fracPartBD = fracPartBD.multiply(BigDecimal.valueOf(2));
            if (fracPartBD.compareTo(BigDecimal.valueOf(1)) == 1) {
                fracValueStringBuilder.append("1");
                fracPartBD = fracPartBD.subtract(BigDecimal.valueOf(1));
            } else {
                fracValueStringBuilder.append("0");
            }
        }

        return fracValueStringBuilder.toString();
    }

    static String fracConvertToOctal(String fracPart, int radix) {
        if (radix != DECIMAL) {
            fracPart = fracConvertToDecimal(fracPart, radix);
        }

        BigDecimal fracPartBD = new BigDecimal(fracPart);

        BigDecimal intPart;
        int precisionLimit = 20;
        String intPartString;
        StringBuilder fracValueStringBuilder = new StringBuilder();

        while (fracPartBD.remainder(BigDecimal.valueOf(1)).compareTo(BigDecimal.ZERO) != 0 && precisionLimit-- > 0) {
            fracPartBD = fracPartBD.multiply(BigDecimal.valueOf(8));
            intPart = fracPartBD.setScale(0, RoundingMode.DOWN);
            intPartString = intPart.toPlainString();
            fracValueStringBuilder.append(intPartString);
            fracPartBD = fracPartBD.subtract(intPart);
        }

        return fracValueStringBuilder.toString();
    }

    static String fracConvertToHex(String fracPart, int radix) {
        if (radix != DECIMAL) {
            fracPart = fracConvertToDecimal(fracPart, radix);
        }

        BigDecimal fracPartBD = new BigDecimal(fracPart);
        int precisionLimit = 20;
        int intPartInt;
        BigDecimal intPart;
        StringBuilder fracValueStringBuilder = new StringBuilder();

        while (fracPartBD.remainder(BigDecimal.valueOf(1)).compareTo(BigDecimal.ZERO) != 0 && precisionLimit-- > 0) {
            fracPartBD = fracPartBD.multiply(BigDecimal.valueOf(16));
            intPart = fracPartBD.setScale(0, RoundingMode.DOWN);
            fracPartBD = fracPartBD.subtract(intPart);
            intPartInt = intPart.intValue();
            fracValueStringBuilder.append(Integer.toHexString(intPartInt));
        }

        return fracValueStringBuilder.toString();
    }

    static void binaryToOctal(Scanner sc) {
        while (true) {
            System.out.print("Enter a number in BINARY: ");
            String input = sc.nextLine();

            Number parsed = parse(input);
            if (parsed == null) {
                System.err.println("Unable to parse number. Please try again.\n");
                continue;
            }

            try {
                int intPart = Integer.parseInt(parsed.intPart, BINARY);
                String fracPart = fracConvertToOctal(parsed.fracPart, BINARY);

                System.out.printf("%s in OCTAL: %s.%s\n",
                                   input,
                                   Integer.toOctalString(intPart),
                                   fracPart);
                break;
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format. Please try again.\n");
            }
        }
    }

    static void binaryToDecimal(Scanner sc) {
        while (true) {
            System.out.print("Enter a number in BINARY: ");
            String input = sc.nextLine();

            Number parsed = parse(input);
            if (parsed == null) {
                System.err.println("Unable to parse number. Please try again.\n");
                continue;
            }

            try {
                int intPart = Integer.parseInt(parsed.intPart, BINARY);
                String fracPart = fracConvertToDecimal(parsed.fracPart, BINARY).replace("0.", "");

                System.out.printf("%s in DECIMAL: %d.%s\n",
                                  input,
                                  intPart,
                                  fracPart);
                break;
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format. Please try again.\n");
            }
        }
    }

    static void binaryToHex(Scanner sc) {
        while (true) {
            System.out.print("Enter a number in BINARY: ");
            String input = sc.nextLine();

            Number parsed = parse(input);
            if (parsed == null) {
                System.err.println("Unable to parse number. Please try again.\n");
                continue;
            }

            try {
                int intPart = Integer.parseInt(parsed.intPart, BINARY);
                String fracPart = fracConvertToHex(parsed.fracPart, BINARY);

                System.out.printf("%s in HEX: %s.%s\n",
                                  input,
                                  Integer.toHexString(intPart),
                                  fracPart);
                break;
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format. Please try again.\n");
            }
        }
    }

    static void octalToBinary(Scanner sc) {
        while (true) {
            System.out.print("Enter a number in OCTAL: ");
            String input = sc.nextLine();

            Number parsed = parse(input);
            if (parsed == null) {
                System.err.println("Unable to parse number. Please try again.\n");
                continue;
            }

            try {
                int intPart = Integer.parseInt(parsed.intPart, OCTAL);
                String fracPart = fracConvertToBinary(parsed.fracPart, OCTAL);

                System.out.printf("%s in BINARY: %s.%s\n",
                                  input,
                                  Integer.toBinaryString(intPart),
                                  fracPart);
                break;
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format. Please try again.\n");
            }
        }
    }

    static void octalToDecimal(Scanner sc) {
        while (true) {
            System.out.print("Enter a number in OCTAL: ");
            String input = sc.nextLine();

            Number parsed = parse(input);
            if (parsed == null) {
                System.err.println("Unable to parse number. Please try again.\n");
                continue;
            }

            try {
                int intPart = Integer.parseInt(parsed.intPart, OCTAL);
                String fracPart = fracConvertToDecimal(parsed.fracPart, OCTAL).replace("0.", "");

                System.out.printf("%s in DECIMAL: %s.%s\n",
                                  input,
                                  intPart,
                                  fracPart);
                break;
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format. Please try again.\n");
            }
        }
    }

    static void octalToHex(Scanner sc) {
        while (true) {
            System.out.print("Enter a number in OCTAL: ");
            String input = sc.nextLine();

            Number parsed = parse(input);
            if (parsed == null) {
                System.err.println("Unable to parse number. Please try again.\n");
                continue;
            }

            try {
                int intPart = Integer.parseInt(parsed.intPart, OCTAL);
                String fracPart = fracConvertToHex(parsed.fracPart, OCTAL);

                System.out.printf("%s in HEX: %s.%s\n",
                                  input,
                                  Integer.toHexString(intPart),
                                  fracPart);
                break;
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format. Please try again.\n");
            }
        }
    }

    static void decimalToBinary(Scanner sc) {
        while (true) {
            System.out.print("Enter a number in DECIMAL: ");
            String input = sc.nextLine();

            Number parsed = parse(input);
            if (parsed == null) {
                System.err.println("Unable to parse number. Please try again.\n");
                continue;
            }

            try {
                int intPart = Integer.parseInt(parsed.intPart, DECIMAL);
                String fracPart = fracConvertToBinary(parsed.fracPart, DECIMAL);

                System.out.printf("%s in BINARY: %s.%s\n",
                                  input,
                                  Integer.toBinaryString(intPart),
                                  fracPart);
                break;
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format. Please try again.\n");
            }
        }
    }

    static void decimalToOctal(Scanner sc) {
        while (true) {
            System.out.print("Enter a number in DECIMAL: ");
            String input = sc.nextLine();

            Number parsed = parse(input);
            if (parsed == null) {
                System.err.println("Unable to parse number. Please try again.\n");
                continue;
            }

            try {
                int intPart = Integer.parseInt(parsed.intPart, DECIMAL);
                String fracPart = fracConvertToOctal(parsed.fracPart, DECIMAL);

                System.out.printf("%s in OCTAL: %s.%s\n",
                                  input,
                                  Integer.toOctalString(intPart),
                                  fracPart);
                break;
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format. Please try again.\n");
            }
        }
    }

    static void decimalToHex(Scanner sc) {
        while (true) {
            System.out.print("Enter a number in DECIMAL: ");
            String input = sc.nextLine();

            Number parsed = parse(input);
            if (parsed == null) {
                System.err.println("Unable to parse number. Please try again.\n");
                continue;
            }

            try {
                int intPart = Integer.parseInt(parsed.intPart, DECIMAL);
                String fracPart = fracConvertToHex(parsed.fracPart, DECIMAL);

                System.out.printf("%s in HEX: %s.%s\n",
                                  input,
                                  Integer.toHexString(intPart),
                                  fracPart);
                break;
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format. Please try again.\n");
            }
        }
    }

    static void hexToBinary(Scanner sc) {
        while (true) {
            System.out.print("Enter a number in HEX: ");
            String input = sc.nextLine();

            Number parsed = parse(input);
            if (parsed == null) {
                System.err.println("Unable to parse number. Please try again.\n");
                continue;
            }

            try {
                int intPart = Integer.parseInt(parsed.intPart, HEX);
                String fracPart = fracConvertToBinary(parsed.fracPart, HEX);

                System.out.printf("%s in BINARY: %s.%s\n",
                                  input,
                                  Integer.toBinaryString(intPart),
                                  fracPart);
                break;
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format. Please try again.\n");
            }
        }
    }    
    
    static void hexToOctal(Scanner sc) {
        while (true) {
            System.out.print("Enter a number in HEX: ");
            String input = sc.nextLine();

            Number parsed = parse(input);
            if (parsed == null) {
                System.err.println("Unable to parse number. Please try again.\n");
                continue;
            }

            try {
                int intPart = Integer.parseInt(parsed.intPart, HEX);
                String fracPart = fracConvertToOctal(parsed.fracPart, HEX);

                System.out.printf("%s in OCTAL: %s.%s\n",
                                  input,
                                  Integer.toOctalString(intPart),
                                  fracPart);
                break;
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format. Please try again.\n");
            }
        }
    }

    static void hexToDecimal(Scanner sc) {
        while (true) {
            System.out.print("Enter a number in HEX: ");
            String input = sc.nextLine();

            Number parsed = parse(input);
            if (parsed == null) {
                System.err.println("Unable to parse number. Please try again.\n");
                continue;
            }

            try {
                int intPart = Integer.parseInt(parsed.intPart, HEX);
                String fracPart = fracConvertToDecimal(parsed.fracPart, HEX).replace("0.", "");

                System.out.printf("%s in DECIMAL: %s.%s\n",
                                  input,
                                  intPart,
                                  fracPart);
                break;
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format. Please try again.\n");
            }
        }
    }  

    static boolean proceed(Scanner sc) {
        while (true) {
            System.out.print("\nProceed with another operation? [Y/N]: ");
            String input = sc.next().trim();

            if (input.equalsIgnoreCase("Y")) {
                return true;
            } else if (input.equalsIgnoreCase("N")) {
                return false;
            } else {
                System.err.println("Please enter either Y or N.\n");
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=".repeat(36));
        System.out.printf("%30s\n", "NUMBER SYSTEMS CONVERTER");
        System.out.println("=".repeat(36));

        boolean redo = true;
        int choice = -1;

        do {
            System.out.println("""
                    
                               [0] EXIT
                               [1] Binary to Octal
                               [2] Binary to Decimal
                               [3] Binary to Hexadecimal
                               [4] Octal to Binary
                               [5] Octal to Decimal
                               [6] Octal to Hexadecimal
                               [7] Decimal to Binary
                               [8] Decimal to Octal
                               [9] Decimal to Hexadecimal
                               [10] Hexadecimal to Binary
                               [11] Hexadecimal to Octal
                               [12] Hexadecimal to Decimal
                               """);

            try {
                System.out.print("YOUR CHOICE: ");
                choice = sc.nextInt();
                sc.nextLine(); // clear buffer
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                sc.nextLine(); // clear buffer
                continue;
            }

            if (choice == 0) {
                System.out.println("Goodbye!");
                break;
            }
            
            switch (choice) {
                case 1  -> binaryToOctal(sc);
                case 2  -> binaryToDecimal(sc);
                case 3  -> binaryToHex(sc);
                case 4  -> octalToBinary(sc);
                case 5  -> octalToDecimal(sc);
                case 6  -> octalToHex(sc);
                case 7  -> decimalToBinary(sc);
                case 8  -> decimalToOctal(sc);
                case 9  -> decimalToHex(sc);
                case 10 -> hexToBinary(sc);
                case 11 -> hexToOctal(sc);
                case 12 -> hexToDecimal(sc);
                default -> System.out.println("Invalid choice!\n");
            }

            redo = proceed(sc);

        } while (redo);

        sc.close();
    }
}