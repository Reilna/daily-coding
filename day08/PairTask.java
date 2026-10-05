public class PairTask {
    public static void main(String[] args) {
        Pair<Integer, String> user = new Pair<>(101, "Иван");
        Pair<String, Double> product = new Pair<>("Ноутбук", 89999.99);

        user.printPair();
        product.printPair();


        user.setValue("Пётр");
        user.printPair();

        System.out.println(user.getKey());
        System.out.println(user.getValue());

    }
}

class Pair<K, V> {
    private K key;
    private V value;

    Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    K getKey() {return key; }
    V getValue() {return value; }

    void setKey(K key) {
        this.key = key;
    }

    void setValue(V value) {
        this.value = value;
    }

    void printPair() {
        System.out.println("Ключ: " + this.key + ", Значение: " + this.value);
    }
}
