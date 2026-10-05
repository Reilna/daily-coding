public class MassiveAnalysis {
    public static void main(String[] args) {
        int[][] numbers = {
                {4, 8, 2},
                {15, 3, 7},
                {6, 12, 1}
        };
        int sum = 0;
        int max = 0;

        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers[i].length; j++) {
                sum += numbers[i][j];
                if (numbers[i][j] > max) {
                    max = numbers[i][j];
                }
            }
        }
        System.out.println("Сумма: " + sum);
        System.out.println("Максимальное: " + max);
    }
}
