public class Training6 {
    public static int findMin(int[] numbers){
        int min = numbers[0];
        for(int i = 1; i < numbers.length; i++) {
            if (min > numbers[i]) {
                min = numbers[i];
            }
        }
        return min;
    }

    public static int findMax(int[] numbers) {
        int max = numbers[0];
        for(int i = 1; i < numbers.length; i++) {
            if (max < numbers[i]) {
                max = numbers[i];
            }
        }
        return max;
    }

    public static double average(int[] numbers) {
        double count = 0;
        int sum = 0;
        for(int i = 0; i < numbers.length; i++) {
            sum += numbers[i];
            count++;
        }
        return sum / count;
    }

    public static void main(String[] args) {
        int[] numbers = {7, -2, 15, 4, -8, 3};
        System.out.println(findMax(numbers));
        System.out.println(findMin(numbers));
        System.out.println(average(numbers));
    }
}
