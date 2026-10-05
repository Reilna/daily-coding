package Email;

public class Validator {

    boolean isValid(String email) {
        int indexOfAt = email.indexOf("@");
        int indexOfDotAfterAt = -1;
        if(indexOfAt != -1) {
            indexOfDotAfterAt = email.indexOf(".", indexOfAt);
        }
        if(indexOfDotAfterAt > 0 && indexOfAt > 0 && email.equals(email.trim()) && !email.isBlank()) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Validator email = new Validator();
        System.out.println(email.isValid("user@example.com"));
        System.out.println(email.isValid("invalid.email"));
        System.out.println(email.isValid("  user@example.com  "));
        System.out.println(email.isValid("user@domain"));
    }
}
