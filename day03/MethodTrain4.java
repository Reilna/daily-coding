public class MethodTrain4 {
    public static int countPositive(int[] numbers){
        int count = 0;
        for(int number : numbers){
            if(number>0) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        int[] numbers = {-2, 5, 7, -1, 0, 3};

        System.out.println(countPositive(numbers));
    }
}
