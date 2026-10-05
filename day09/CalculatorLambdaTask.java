public class CalculatorLambdaTask {
    public static void main(String[] args) {
        MathOperation addition = (first, second) -> first + second;

        int result = addition.apply(10, 5);
        System.out.println("Сложение: " + result);

        MathOperation subtraction = (first, second) -> first - second;
        result = subtraction.apply(10, 5);
        System.out.println("Вычитание: " + result);

        MathOperation multiplication = (first, second) -> first * second;
        result = multiplication.apply(10, 5);
        System.out.println("Умножение: " + result);

        MathOperation division = (first, second) -> first / second;
        result = division.apply(10, 5);
        System.out.println("Деление: " + result);

        MathOperation safeDivision = (first, second) -> {
            if (second == 0) {
                throw new ArithmeticException("Деление на ноль.");
            }
            return first / second;

        };

        try {
            result = safeDivision.apply(10, 0);
            System.out.println("Безопасное деление: " + result);
        } catch (ArithmeticException exception) {
            System.out.println("Ошибка: " + exception.getMessage());
        }

        result = safeDivision.apply(10, 5);
        System.out.println("Безопасное деление: " + result);
    }

    @FunctionalInterface
    interface MathOperation {
        int apply(int first, int second);
    }
}
