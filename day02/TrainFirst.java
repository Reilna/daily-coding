public class TrainFirst {
    public static void main(String[] args) {
        int[] numbers = {5, 4, 3, 5, 2, 4, 5};
        int sum = 0;
        double sr = 0.0;
        for(int i = 0; i < numbers.length; i++) {
            sum += numbers[i];
        }
        sr = (double)sum / numbers.length;
        System.out.println("Сумма: " + sum);
        System.out.printf("Среднее арифметическое: %.1f%n", sr);
    }
}
