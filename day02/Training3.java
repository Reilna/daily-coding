import java.util.Scanner;

public class Training3 {
    public static void main(String[] args) {
        int[] numbers = {4, 7, 2, 9, 15, 3, 8};
        Scanner scanner = new Scanner(System.in);
        boolean ans = false;

        System.out.println("Введите число: ");
        int x = scanner.nextInt();
        for(int i = 0; i < numbers.length; i++) {
            if(x == numbers[i]) {
                ans = true;
                break;
            }
        }
        if(ans) {
            System.out.println("Число найдено.");
        } else {
            System.out.println("Число не найдено.");
        }
    }
}
