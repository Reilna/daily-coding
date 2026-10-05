import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class FunctionalInterfacesTask {
    public static void main(String[] args) {
        List<String> usernames = List.of(" Alice ", "bo", " Charlie ");
        Predicate<String> validUsername = name -> name != null && name.trim().length() >= 3;

        System.out.println(validUsername.test(" Alice "));
        System.out.println(validUsername.test("bo"));
        System.out.println(validUsername.test(" Charlie "));
        System.out.println(validUsername.test(null));

        Function<String, String> normalizeUsername =
                name -> name != null ? name.strip().toLowerCase() : null;
        System.out.println(normalizeUsername.apply(" Alice "));
        System.out.println(normalizeUsername.apply(null));

        Consumer<String> auditUsername =
                name -> System.out.println("AUDIT: принято имя " + name);
        auditUsername.accept("alice");

        Supplier<String> defaultUsername =
                () -> "guest";
        System.out.println("Имя по умолчанию: " + defaultUsername.get());

        usernames.stream()
                .filter(validUsername)
                .map(normalizeUsername)
                .forEach(auditUsername);


    }
}
