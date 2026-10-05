public class GenericsTask {
    public static void main(String[] args) {
        Storage<String> messageStorage = new Storage<>("Hello");
        Storage<Integer> retryStorage = new Storage<>(123);

        System.out.println(messageStorage.getValue());
        System.out.println(retryStorage.getValue());

        retryStorage.setValue(43);
        System.out.println(retryStorage.getValue());

        System.out.println(messageStorage.isEmpty());

        //retryStorage.setValue("sdf");
        //System.out.println(retryStorage.getValue());
    }
}

class Storage<T> {
    private T value;

    public Storage(T value){
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public boolean isEmpty() {
        return getValue() == null;
    }
}
