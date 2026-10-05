import java.util.HashMap;

public class PhoneBook {
    public static void main(String[] args) {
        HashMap<String, String> book = new HashMap<>();
        book.put("Valera", "8933**");
        book.put("Noone", "845**");
        book.put("Andrew", "43333**");
        book.put("Mike", "86443**");

        System.out.println(book.get("Valera"));
        System.out.println(book.containsKey("Иван"));

        for (String key : book.keySet()){
            System.out.println(key + " " + book.get(key));
        }
    }
}
