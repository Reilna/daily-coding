import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class OrderStreamTask {

    public static void main(String[] args) {
        StreamOrder order1 = new StreamOrder(1, "Новый", 1500.50);
        StreamOrder order2 = new StreamOrder(2, "Оплачен", 4200.00);
        StreamOrder order3 = new StreamOrder(3, "В обработке", 850.25);
        StreamOrder order4 = new StreamOrder(4, "Доставлен", 12500.00);
        StreamOrder order5 = new StreamOrder(5, "Оплачен", 20500.00);
        StreamOrder order6 = new StreamOrder(6, "Оплачен", 25500.00);

        System.out.println("id=" + order1.id + ", status=" + order1.status + ", amount=" + order1.totalAmount);

        List<StreamOrder> orderList = new ArrayList<>(List.of(order1, order2,order3,order4, order5, order6));
        System.out.println(orderList.size());

        List<StreamOrder> paidOrders = orderList.stream()
                .filter(order -> order.status.equals("Оплачен"))
                .filter(order -> order.totalAmount > 5000.0)
                .sorted(Comparator.comparing(order -> order.totalAmount))
                .toList();
        System.out.println(paidOrders.size());
        for (int i = 0; i<paidOrders.size(); i++) {
            StreamOrder order = paidOrders.get(i);
            System.out.printf("ID: %d | Статус: %s | Сумма: %.2f руб.%n",
                    order.id, order.status, order.totalAmount);
        }

        List<Integer> paidOrdersIds = paidOrders.stream()
                .map(order ->  order.id)
                .toList();
        System.out.println("Список ID отфильтрованных заказов: " + paidOrdersIds);

        List<Integer> expensivePaidOrderIds = orderList.stream()
                .filter(order -> order.status.equals("Оплачен"))
                .filter(order -> order.totalAmount > 5000.0)
                .sorted(Comparator.comparing(order -> order.totalAmount))
                .map(order -> order.id)
                .toList();
        System.out.println(expensivePaidOrderIds);

        expensivePaidOrderIds.stream()
                .forEach(id -> System.out.println("Order ID: " + id));

        orderList.stream()
                .filter(order -> order.id == 500)
                .findFirst()
                .ifPresentOrElse(
                        order -> System.out.println("Заказ найден. Сумма: " + order.totalAmount),
                        () -> System.out.println("Заказа с таким ID не существует")
                );

        boolean expensiveOrder = orderList.stream()
                .anyMatch(order -> order.totalAmount > 20000);

        System.out.println("Есть ли хоть один товар дороже 20_000? " + expensiveOrder);

        boolean correctCostOrders = orderList.stream()
                .allMatch(order -> order.totalAmount > 0);

        System.out.println("Все ли заказы имеют положительную сумму? " + correctCostOrders);

        boolean wrongCostOrders = orderList.stream()
                .noneMatch(order -> order.totalAmount < 0);

        System.out.println("Нет ли заказов с отрицательной суммой? " + wrongCostOrders);


        orderList.stream()
                .max(Comparator.comparing(order -> order.totalAmount))
                .ifPresentOrElse(
                        order -> System.out.println("Самый дорогой заказ. ID: " + order.id + ". Сумма: " + order.totalAmount),
                        () -> System.out.println("Заказов нет.")
                );

        orderList.stream()
                .min(Comparator.comparing(order -> order.totalAmount))
                .ifPresentOrElse(
                        order -> System.out.println("Самый дешёвый заказ. ID: " + order.id + ". Сумма: " + order.totalAmount),
                        () -> System.out.println("Заказов нет.")
                );



        //4 Задания которые дали

        long paidOrderCount = orderList.stream()
                .filter(order -> order.status.equals("Оплачен"))
                .count();
        System.out.println("Количество оплаченных заказов: " + paidOrderCount);

        double paidOrderTotal = orderList.stream()
                .filter(order -> order.status.equals("Оплачен"))
                .mapToDouble(order -> order.totalAmount)
                .sum();
        System.out.println("Сумма оплаченных заказов: " + paidOrderTotal);

        Optional<StreamOrder> mostExpensivePaidOrder = orderList.stream()
                .filter(order -> order.status.equals("Оплачен"))
                .max(Comparator.comparing(order -> order.totalAmount));

        mostExpensivePaidOrder.ifPresentOrElse(
                order -> System.out.println("Самый дорогой оплаченный заказ ID: " + order.id),
                () -> System.out.println("Не найден.")
        );



        List<Integer> paidOrderIds = orderList.stream()
                .filter(order -> order.status.equals("Оплачен"))
                .sorted(Comparator.comparing(order -> order.totalAmount))
                .map(order -> order.id)
                .toList();
        System.out.println("ID по возрастанию суммы: " + paidOrderIds);
    }

}

class StreamOrder {
    int  id;
    String status;
    double totalAmount;

    StreamOrder(int id, String status, double totalAmount) {
        this.id = id;
        this.status = status;
        this.totalAmount = totalAmount;
    }
}
