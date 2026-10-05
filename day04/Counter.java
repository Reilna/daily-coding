public class Counter {

    private int value;

    Counter(int value) {
        this.value = value;
    }

    void increment() {
        this.value++;
    }

    int getValue() {
        return value;
    }

    static int sum(Counter c1, Counter c2) {
        return c1.value + c2.value;
    }

    public static void main(String[] args) {
        Counter c1 = new Counter(4);
        Counter c2 = new Counter(6);

        c1.increment();
        System.out.println(c1.getValue());

        System.out.println(Counter.sum(c1, c2));

    }
}
