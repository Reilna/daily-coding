import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OrderFormatter {
    public static void main(String[] args) {
        OrderFormatter order = new OrderFormatter();
        System.out.println(order.formatOrder("Иван Иванов", "#ORD-001", "2026-09-23"));
    }

    String formatOrder(String customerName, String orderId, String orderDate) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        if(customerName.isBlank() || orderId.isBlank() || orderDate.isBlank()) {
            throw new IllegalArgumentException("Неверный формат параметра: " + customerName + " " + orderId + " " + orderDate);
        }
        String formattedDate = null;
        LocalDate date = null;
        try {
            date = LocalDate.parse(orderDate, inputFormatter);
            formattedDate = date.format(outputFormatter);
        } catch (Exception e) {
            throw new IllegalArgumentException("Неверный формат даты: " + orderDate);
        }



        StringBuilder sb = new StringBuilder("Заказ ");
        sb.append(orderId);
        sb.append(" | Клиент: ");
        sb.append(customerName);
        sb.append(" | Дата: ");
        sb.append(formattedDate);
        sb.append(" | Статус: В обработке");
        return sb.toString();
        }
    }

