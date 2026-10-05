import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileTask {
    public static void main(String[] args) {
        Path path = Path.of("message.txt");
        try {
            Files.writeString(path, "Я изучаю Java Backend");
            String text = Files.readString(path);
            System.out.println(text);
        } catch (IOException e) {
            System.err.println("Не удалось обработать файл! Причина: " + e.getMessage());
        }

        // Create user.txt if missing; otherwise read its contents
        Path path1 = Path.of("user.txt");
        try {
            if (!Files.exists(path1)) {
                System.out.println("Файла нет");
                Files.writeString(path1, "user1");
            } else {
                String text = Files.readString(path1);
                System.out.println(text);
            }

        } catch (IOException e) {
            System.err.println("Не удалось обработать файл! Причина: " + e.getMessage());
        }

        Path path2 = Path.of("log.txt");
        try {
            Files.writeString(path2, "Приложение запущено\n");
            Files.writeString(
                    path2,
                    "Пользователь вошёл в систему",
                    StandardOpenOption.APPEND
            );
            String text = Files.readString(path2);
            System.out.println(text);
        } catch (IOException e) {
            System.err.println("Не удалось обработать файл! Причина: " + e.getMessage());
        }

        Path path3 = Path.of("events.log");
        try {
            Files.writeString(
                    path3,
                    "Запуск приложения\n",
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
            Files.writeString(
                    path3,
                    "Открыта главная страница\n",
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
            String text = Files.readString(path3);
            System.out.println(text);
        } catch (IOException e) {
            System.err.println("Не удалось обработать файл! Причина: " + e.getMessage());
        }

        // Create a directory and write a user file
        Path directory = Path.of("data");
        Path userFile = directory.resolve("user.txt");

        try {
            Files.createDirectories(directory);
            Files.writeString(userFile, "Danya");
            String text = Files.readString(userFile);
            System.out.println(text);
        } catch (IOException e) {
            System.err.println("Не удалось обработать файл! Причина: " + e.getMessage());
        }

    }
}
