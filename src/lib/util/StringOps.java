package lib.util;

public class StringOps {
    public static String formatDouble(double number, int n) {
        return String.format("%." + n + "f", number);
    }
}
