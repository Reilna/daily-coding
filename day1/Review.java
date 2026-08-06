public class Review {
    public static void main(String[] args) {
        // примитивы
        int count = 5;
        double price = 99.90;

        // строка
        String[] fruits = {"яблоко", "банан", "апельсин"};

        // условие и цикл for-each
        if (fruits.length > 0) {
            for (String fruit : fruits) {
                System.out.println(fruit.toUpperCase());
            }
        }

        // цикл for + условие
        for (int i = 0; i < fruits.length; i++) {
            if (fruits[i].startsWith("я")) {
                System.out.println("Нашлось на 'я': " + fruits[i]);
            }
        }
    }
}