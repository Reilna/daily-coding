import java.util.Scanner;

public class PrimeNumber {
    public static void main(String[] args) {
        System.out.println("Введите положительное число: ");
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        boolean ans = false;

        if (num > 1) {
            for(int i = 2; i < num; i++) {
                if (num % i == 0) {
                    ans = true;
                    break;
                }
            }
            if (ans) {
                System.out.println("Число составное.");
            }else {
                System.out.println("Число простое.");
            }
        } else {
            System.out.println("Непростое число.");
        }
    }
}
