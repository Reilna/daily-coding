import java.util.Scanner;

public class CalculatorDay1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Введите первле число: ");
        int num1 = sc.nextInt();

        System.out.println("Введите знак операции: +, -, *, /");
        char s = sc.next().charAt(0);

        System.out.println("Введите второе число: ");
        int num2 = sc.nextInt();

        int ans = switch(s) {
            case '+'  -> num1 + num2;
            case '-'  -> num1 - num2;
            case '*'  -> num1 * num2;
            case '/'  -> {
                try {
                    yield num1 / num2;
                } catch (ArithmeticException e) {
                    System.out.println("Ошибка делить на ноль нельзя");
                    yield 0;
                }
            }
            default -> 0;
        };
        System.out.println("Результат: " + ans);
        sc.close();
    }
}
