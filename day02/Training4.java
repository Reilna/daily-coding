public class Training4 {
    public static void main(String[] args) {
        int[] numbers = {1, 2, 3, 4, 5};
        int x = 0;
        int len = numbers.length;
        for (int i = 0; i < len/2; i++) {
            x = numbers[i];
            numbers[i] = numbers[len-1-i];
            numbers[len-1-i] = x;
        }
        for(int i = 0; i < numbers.length; i++) {
            System.out.print(numbers[i]);
        }
    }
}
