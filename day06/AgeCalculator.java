import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;


public class AgeCalculator {

    public static void main(String[] args) {
        AgeCalculator calculator = new AgeCalculator();

        System.out.println(calculator.isAdult("15.01.2000"));
        System.out.println(calculator.isAdult("01.10.2010"));
        System.out.println(calculator.isAdult("invalid-date"));

    }

    int calculateAge(String birthDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate today = LocalDate.now();

        LocalDate date;
        try {
            date = LocalDate.parse(birthDate, formatter);
        } catch (Exception e) {
            throw new IllegalArgumentException("Неверный формат даты: " + birthDate);
        }

        int years = Period.between(date, today).getYears();
        return years;
    }

    boolean isAdult(String birthDate) {
        return calculateAge(birthDate) >= 18;
    }
}
