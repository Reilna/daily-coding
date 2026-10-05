public class ObhMass {
    public static void main(String[] args) {
        int[] numbers = {7, -2, 15, 4, 0, 9, -8, 3};
        int max = numbers[0];
        int min = numbers[0];
        for (int i = 0; i < numbers.length; i++) {
            if(min>numbers[i]) {
                min = numbers[i];
            } else if (max < numbers[i]) {
                max = numbers[i];
            }
        }
        System.out.println("Максимальное число: " + max);
        System.out.println("Минимальное число: " + min);
    }
}
