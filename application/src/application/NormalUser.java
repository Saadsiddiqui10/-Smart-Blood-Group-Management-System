package application;

public class NormalUser extends User {

    public NormalUser(String username, String password) {
        super(username, password);
    }

    @Override
    public void displayRole() {
        System.out.println("Role: User");
    }
}
