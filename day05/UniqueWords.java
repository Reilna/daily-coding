import java.util.HashSet;

public class UniqueWords {
    public static void main(String[] args) {
        HashSet<String> words = new HashSet<>();
        words.add("Java");
        words.add("67");
        words.add("Water");
        words.add("Water");

        System.out.println(words.size());

        System.out.println(words.contains("Java"));

        for(String word : words) {
            System.out.println(word);
        }
     }
}
