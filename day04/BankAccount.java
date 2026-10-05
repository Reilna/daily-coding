//public class BankAccount {
//
//    private String owner;
//    private int balance;
//
//    BankAccount(String owner, int balance) {
//        this.owner = owner;
//        if(balance < 0) {
//            this.balance = 0;
//        } else {
//            this.balance = balance;
//        }
//    }
//
//    int getBalance() {
//        return balance;
//    }
//
//    String getOwner() {
//        return owner;
//    }
//
//    void deposit(int amount) {
//        if(amount > 0) {
//            this.balance += amount;
//        }
//    }
//
//    void withdraw(int amount) {
//        if(amount > 0 && balance >= amount) {
//            this.balance -= amount;
//        }
//    }
//
//    public static void main(String[] args) {
//        BankAccount account = new BankAccount("Bill", 1000);
//        account.deposit(100);
//        account.withdraw(9999);
//        account.withdraw(20);
//        System.out.println(account.getBalance());
//    }
//}
//
