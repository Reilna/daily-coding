import java.util.Scanner;

public class TrenDoWhile {
    public static void main(String[] args) {
        int qw = 0;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.print("Введите пароль: ");
            int ans = scanner.nextInt();
            qw = ans;
            if (qw != 12345) {
                System.out.println("Пароль неверный!");
            }
        } while (qw != 12345);
        System.out.println("Пароль верный!");
    }
}
