public class BankException {
    public static void main(String[] args) {
        BankAccount bankAccount = new BankAccount(123);

        try {
            bankAccount.deposit(100);
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

        System.out.println(bankAccount.getBalance());


        try {
            bankAccount.withdraw(50);
        } catch (InsufficientFundsException e){
            System.out.println(e.getMessage());
        }

        System.out.println(bankAccount.getBalance());


        try {
            bankAccount.withdraw(5000);
        } catch (InsufficientFundsException e){
            System.out.println(e.getMessage());
        }

        System.out.println(bankAccount.getBalance());


        try {
            bankAccount.deposit(-1900);
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

        System.out.println(bankAccount.getBalance());



    }
}


class InsufficientFundsException extends Exception {
    InsufficientFundsException(String message) {
        super(message);
    }

}

class BankAccount {
    private int balance;

    BankAccount(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return this.balance;
    }

    void deposit(int amount) {
        if(amount <= 0) {
            throw new IllegalArgumentException("Сумма должна быть положительной!");
        }
        balance += amount;
    }

    void withdraw(int amount) throws InsufficientFundsException {
        if(balance < amount) {
            throw new InsufficientFundsException("Недостаточно денег.");
        }
        balance -= amount;
    }

}

