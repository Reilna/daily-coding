enum OrderStatus {
    NEW, PAID, CANCELLED
}


public class Order1 {

    private int total;
    private OrderStatus status;

    Order1(int total) {
        this.status = OrderStatus.NEW;
        if(total < 0) {
            this.total = 0;
        } else {
            this.total = total;
        }
    }

    public void pay() {
        if (total > 0 && status == OrderStatus.NEW) {
            status = OrderStatus.PAID;
        }
    }

    void cancel() {
        if (status != OrderStatus.PAID) {
            status = OrderStatus.CANCELLED;
        }
    }

    OrderStatus getStatus() {
        return status;
    }

    public static void main(String[] args) {
        Order1 order1 = new Order1(200);

        System.out.println(order1.getStatus());

        order1.cancel();
        System.out.println(order1.getStatus());

        order1.pay();
        System.out.println(order1.getStatus());

        order1.cancel();
        System.out.println(order1.getStatus());
    }
}
