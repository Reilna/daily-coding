public class Subscription implements Payable {
    private final String owner;
    private final int monthlyPrice;
    private int numPaidMonth;

    Subscription(String owner, int monthlyPrice, int numPaidMonth) {
        this.owner = owner;
        if(monthlyPrice <= 0) {
            this.monthlyPrice = 0;
        } else {
            this.monthlyPrice = monthlyPrice;
        }
        if(numPaidMonth < 0) {
            this.numPaidMonth = 0;
        } else {
            this.numPaidMonth = numPaidMonth;
        }

    }

    int getNumPaidMonth() {
        return numPaidMonth;
    }

    @Override
    public void pay(int amount) {
        if(amount > 0 && monthlyPrice == amount) {
            numPaidMonth++;
        }
    }

    public static void main(String[] args) {
        Subscription sub = new Subscription("Georg", 100, 3);
        sub.pay(20);
        sub.pay(100);
        System.out.println(sub.getNumPaidMonth());
        Subscription sub2 = new Subscription("Georg", -100, -3);
        System.out.println(sub2.getNumPaidMonth());
    }
}

