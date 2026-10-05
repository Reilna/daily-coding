import java.util.ArrayList;

public class TaskList {
    String title;
    boolean done;

    TaskList(String title) {
        this.title = title;
        done = false;
    }

    void markDone() {
        done = true;
    }
    public static void main(String[] args) {
        ArrayList<TaskList> task  = new ArrayList<>();
        task.add(new TaskList("Проснуться"));
        task.add(new TaskList("Помыть полы"));
        task.add(new TaskList("Покормить кота"));
        task.add(new TaskList("Лечь спать"));

        for(TaskList element : task) {
            System.out.println(element.title + " - выполнена: " + element.done);
        }

        task.get(0).markDone();

        for(TaskList element : task) {
            System.out.println(element.title + " - выполнена: " + element.done);
        }

    }
}
