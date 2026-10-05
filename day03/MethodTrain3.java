public class MethodTrain3 {
    public static int max(int x, int y) {
        if(x > y) {
            return x;
        }
        return y;
    }

    public static void main(String[] args) {
        System.out.println(max(10, 7));
        System.out.println(max(3, 15));
        System.out.println(max(3, 3));
    }
}
