public class MinMax2D {
    public static void main(String[] args) {
        int[][] numbers = {
                {-7, 4, 12},
                {3, -15, 8},
                {6, 2, -1}
        };
        int min = numbers[0][0];
        int max = numbers[0][0];

        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers[i].length; j++) {
                if(min > numbers[i][j]) {
                    min = numbers[i][j];
                }
                if(max < numbers[i][j]) {
                    max = numbers[i][j];
                }
            }
        }
        System.out.println("Минимальное " + min);
        System.out.println("Максимальное " + max);

    }
}
