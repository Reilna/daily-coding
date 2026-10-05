import java.util.Scanner;

public class TrenWhile {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите число: ");
        int x = scanner.nextInt();
        int sum = 0;
        int ch = 0;
        while(x != 0) {
            System.out.print("Введите число: ");
            ch++;
            sum += x;
            x = scanner.nextInt();
        }

        System.out.println("Сумма:" + sum);
        System.out.println("Количество:" + ch);
    }
}
