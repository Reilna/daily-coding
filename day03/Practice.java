public class Practice {
    public static boolean isEven(int x) {
        if(x % 2 == 0) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(isEven(4));
        System.out.println(isEven(7));
    }
}
