import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class UserStreamTask {
    public static void main(String[] args) {
        List<User> users = List.of(
                new User("Иван", 25),
                new User("Анна", 17),
                new User("Пётр", 31)
        );

        Predicate<User> adultUser = user -> user.getAge() >= 18;
        Function<User, String> userName = User::getName;
        Consumer<String> printName = System.out::println;

        users.stream()
                .filter(adultUser)
                .map(userName)
                .forEach(printName);

        Function<User, String> userDescription =
                user -> user.getName() + ", возраст: " + user.getAge();

        Consumer<String> printDescription =
                description -> System.out.println("Данные: " + description);

        users.stream()
                .filter(adultUser)
                .map(userDescription)
                .forEach(printDescription);
    }
}

class User {
    private String name;
    private int age;

    User(String name, int age) {
        this.name = name;
        this.age = age;
    }
    String getName() {
        return name;
    }
    int getAge() {
        return age;
    }
}
