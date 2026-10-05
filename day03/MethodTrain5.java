public class MethodTrain5 {
    public static int findMin(int[] numbers){
        int min = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            if(numbers[i] < min) {
                min = numbers[i];
            }
        }
        return min;
    }

    public static void main(String[] args) {
        int[] numbers = {7, -2, 15, 4, -8, 3};

        System.out.println(findMin(numbers));
    }
}
