public class Car {
    String brand;
    int year;

    Car(String brand, int year) {
        this.brand = brand;
        this.year = year;
    }

    void printInfo() {
        System.out.println(brand + " " + year);
    }

    public static void main(String[] args) {
        Car car1 = new Car("Toyota", 2000);
        Car car2 = new Car("Porsche", 2024);
        car1.printInfo();
        car2.printInfo();
    }
}
