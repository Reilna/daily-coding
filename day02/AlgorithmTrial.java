import java.util.Arrays;

public class AlgorithmTrial {
    public static void main(String[] args) {
        int[] numbers = {7, 2, 9, 1, 5, 3};
        int[] numbers2 = {7, 2, 9, 1, 5, 3};
        Arrays.sort(numbers2);
        int temp;
        for(int i = 0; i < numbers.length-1; i++) {
            for(int j = i+1; j < numbers.length; j++) {
                if (numbers[i] > numbers[j]) {
                    temp = numbers[j];
                    numbers[j] = numbers[i];
                    numbers[i] = temp;
                }
            }
        }
        for(int i = 0; i < numbers.length; i++) {
            System.out.print(numbers[i] + " ");
        }
        System.out.println(" ");
        for(int i = 0; i < numbers.length; i++) {
            System.out.print(numbers2[i] + " ");
        }
    }
}
