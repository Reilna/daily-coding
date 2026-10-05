public class Order implements Payable {

    private int total;
    private int paid;

    Order(int total) {
        if(total < 0) {
            this.total = 0;
        } else {
            this.total = total;
        }
    }

    public void pay(int amount) {
        if(amount > 0 && paid + amount <= total) {
            paid = amount;
        }
    }

    int getPaid() {
        return paid;
    }


    public static void main(String[] args) {
        Order order = new Order(200);
        order.pay(500);
        order.pay(100);
        System.out.println(order.getPaid());
    }
}

interface Payable {
    void pay(int amount);
}

