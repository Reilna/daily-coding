import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GenericMethodTask {
    public static void main(String[] args) {
        List<String> names = new ArrayList<>(List.of("Число", "Строка", "Дом"));
        List<Integer> numbers = new ArrayList<>(List.of(1, 4, 6, 8, 2));
        List<Integer> blank = new ArrayList<>();

        System.out.println(firstElement(names));
        System.out.println(firstElement(numbers));

        System.out.println(firstElement(blank));

        firstElement(names).ifPresentOrElse(
                value -> System.out.println("Первое значение: " + value),
                () -> System.out.println("Список пуст")
        );

        firstElement(numbers).ifPresentOrElse(
                value -> System.out.println("Первое значение: " + value),
                () -> System.out.println("Список пуст")
        );

        firstElement(blank).ifPresentOrElse(
                value -> System.out.println("Первое значение: " + value),
                () -> System.out.println("Список пуст")
        );

    }

    static <T> Optional<T> firstElement(List<T> items) {
        if(items.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(items.get(0));

    }
}



