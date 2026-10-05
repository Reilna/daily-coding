import java.util.ArrayList;
import java.util.HashMap;

public class StudentRegistry {
    public static void main(String[] args) {
        Registry registry = new Registry();

        Student s1 = new Student("Алиса", 1);
        Student s2 = new Student("Боб", 2);

        registry.addStudent(s1);
        registry.addStudent(s2);

        registry.addGrade(1, 5);
        registry.addGrade(1, 4);
        registry.addGrade(1, 5);

        registry.addGrade(2, 3);
        registry.addGrade(2, 4);

        registry.printGrades(1);
        System.out.println("Средний балл: " + registry.getAverage(1));

        registry.printGrades(2);
        System.out.println("Средний балл: " + registry.getAverage(2));
    }
}

class Student {
    private String name;
    private int id;

    Student(String name, int id){
        this.name = name;
        this.id = id;
    }

    String getName() {
        return name;
    }

    int getId(){
        return id;
    }

}

class Registry{

    private HashMap<Integer, Student>students = new HashMap<>();

    private HashMap<Integer, ArrayList<Integer>>grades = new HashMap<>();

    void addStudent(Student student){
        students.put(student.getId(), student);
    }


    void addGrade(int studentId, int grade) {
        if(!grades.containsKey(studentId)) {
            grades.put(studentId, new ArrayList<>());
        }
        grades.get(studentId).add(grade);
    }

    void printGrades(int studentId) {
        ArrayList<Integer> studentGrades = grades.get(studentId);
        if(studentGrades == null) {
            System.out.println("Оценок нет");
            return;
        }
        Student student = students.get(studentId);
        System.out.println(student.getName() + ": " + studentGrades);

    }

    double getAverage(int studentId) {
        ArrayList<Integer> studentGrades = grades.get(studentId);
        if (studentGrades == null) {
            return 0.0;
        }
        int sum = 0;
        for(int g : studentGrades) {
            sum += g;
        }
        return (double)sum / studentGrades.size();
    }


}
