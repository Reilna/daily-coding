import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class BsFight {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int randomNum = ThreadLocalRandom.current().nextInt(1, 101);
        System.out.println("Загадано число от 1 до 100");

        int ch = 0;
        int trP = 0;
        do {
            System.out.print("Введите число:");
            trP++;
            ch = scanner.nextInt();
            if(ch > randomNum) {
                System.out.println("Меньше.");
            } else if (ch < randomNum) {
                System.out.println("Больше.");
            } else {
                System.out.println("Угадал.");
            }
        } while (ch != randomNum);
        System.out.println("Попыток: "+ trP);
    }
}
