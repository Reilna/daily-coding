public class MiniBoss {
    public static int countEven(int[] numbers) {
        int count = 0;
        for (int number : numbers) {
            if(number % 2 == 0) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println(countEven(new int[]{1, 2, 4, 7, 10}));

    }
}
