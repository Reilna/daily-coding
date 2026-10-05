public class ReverseString {

    public static String solution(String str) {
            String result = "";
            for (int i = str.length(); i >= 0; i--) {
                result += str.charAt(i);
            }
            return result;
    }
}
