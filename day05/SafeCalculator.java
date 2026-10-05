public class SafeCalculator {
    public static void main(String[] args) {
        SafeCalculator calc = new SafeCalculator();
        int a = 8;
        int b = 4;
        String a1 = "Ffds";
        String a2 = "2";
        String b1 = "4";
        System.out.println(calc.divide(a,b));
        System.out.println(calc.parseAndAdd(a2, b1));
        System.out.println(calc.parseAndAdd(a1, b1));
    }
    double divide(int a, int b) {
        try {
            return (double) a / b;
        } catch (ArithmeticException e) {
            System.out.println("Нельзя делить на ноль");
            return 0;
        }
    }
    int parseAndAdd(String a, String b) {
        try {
            return Integer.parseInt(a) + Integer.parseInt(b);
        } catch (NumberFormatException e) {
            System.out.println("Некорректная запись");
            return 0;
        }
    }
}
