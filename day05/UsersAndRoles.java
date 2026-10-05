public class UsersAndRoles {
    public static void main(String[] args) {
        UserAccount[] user = new UserAccount[3];
        user[0] = new AdminUser("Mike", 4);
        user[1] = new RegularUser("Bob");
        user[2] = new RegularUser("Cat");

        for (int i = 0; i < 3; i++) {
            user[i].printInfo();
        }

        user[2].block();

        for (int i = 0; i < 3; i++) {
            user[i].printInfo();
        }
    }

}

abstract class UserAccount {

    private String name;
    private StatusAccount status;

    UserAccount(String name) {
        this.name = name;
        status = StatusAccount.ACTIVE;
    }

    String getName() {
        return name;
    }

    void block() {
        if (status == StatusAccount.ACTIVE) {
            status = StatusAccount.BLOCKED;
        }
    }

    void unblock() {
        if (status == StatusAccount.BLOCKED) {
            status = StatusAccount.ACTIVE;
        }
    }

    boolean isActive() {
        return status == StatusAccount.ACTIVE;
    }

    abstract void printInfo();


}

enum StatusAccount {
    ACTIVE, BLOCKED
}

class RegularUser extends UserAccount {

    RegularUser(String name) {
        super(name);
    }

    @Override
    void printInfo() {
        System.out.println(getName() + " " + isActive());
    }
}

class AdminUser extends UserAccount {

    private int accesLevel;
    AdminUser(String name, int accesLevel) {
        super(name);
        this.accesLevel = accesLevel;
    }

    @Override
    void printInfo() {
        System.out.println(getName() + " " + isActive() + " " + accesLevel);
    }
}
