public class Person {
    private String name;
    private int age;

    Person(String name, int age) {
        this.name = name;
        if(age >= 0) {
            this.age = age;
        } else {
            this.age = 0;
        }
    }

    void setAge(int age) {
        if(age >= 0) {
            this.age = age;
        }
    }

    int getAge() {
        return age;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        if(!name.isEmpty()) {
            this.name = name;
        }
    }

    public static void main(String[] args) {
        Person person1 = new Person("Kirill", -3);
        person1.setName("");
        System.out.println(person1.getName());
    }
}
