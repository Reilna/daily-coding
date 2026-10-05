public class DiagonalPob {
    public static void main(String[] args) {
        int[][] numbers = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        int sum = 0;
        for(int i = 0; i < numbers.length; i++) {
            sum += numbers[i][numbers.length-1-i];
        }
        System.out.println("Сумма: "+sum);
    }
}
