abstract class Animal{
    String name;

    Animal(String name) {
        this.name = name;
    }

    abstract void eat();
}

class Cat extends Animal{

    Cat(String name) {
        super(name);
    }

    @Override
    void eat() {
        System.out.println(name + " ест китячи корм Т___Т");
    }

    void meow() {
        System.out.println(name + " мяукает");
    }
}

interface Flyable {
    void fly();
}

class Bird extends Animal implements Flyable{
    Bird(String name) {
        super(name);
    }

    @Override
    void eat() {
        System.out.println(name + " клюёт");
    }

    @Override
    public void fly() {
        System.out.println(name + " летает");
    }
}

public class Dog extends Animal{

    Dog (String name) {
        super(name);
    }

    void eat(String food) {
        System.out.println(name + " ест " + food);
    }

    @Override
    void eat() {
        System.out.println(name + " ест собачий корм X___X");
    }

    void bark(){
        System.out.println("AW-AW");
    }

    public static void main(String[] args) {
        Animal[] animals = {
                new Dog("Barry"),
                new Cat("Рыжик"),
                new Bird("Кеша")
        };

        for (Animal animal : animals) {
            animal.eat();
            if (animal instanceof Dog) {
                Dog dog = (Dog) animal;
                dog.bark();
            } else if (animal instanceof Cat) {
                Cat cat = (Cat) animal;
                cat.meow();
            }

            if (animal instanceof Flyable) {
                Flyable flyable = (Flyable) animal;
                flyable.fly();
            }
        }


    }
}

