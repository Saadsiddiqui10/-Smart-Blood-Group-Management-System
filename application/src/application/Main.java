package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class Main extends Application {

    Admin admin = new Admin("admin", "1234");
    NormalUser user = new NormalUser("user", "1111");
    BloodInventory inventory = new BloodInventory();

    @Override
    public void start(Stage stage) {
        stage.setScene(createLoginScene(stage));
        stage.setTitle("Login");
        stage.show();
    }

    // ================= LOGIN =================
    private Scene createLoginScene(Stage stage) {

        TextField username = new TextField();
        PasswordField password = new PasswordField();

        ComboBox<String> roleBox = new ComboBox<>();
        roleBox.getItems().addAll("Admin", "User", "Donor");
        roleBox.setValue("User");

        Button loginBtn = new Button("Login");
        Label msg = new Label();

        loginBtn.setOnAction(e -> {
        	if (roleBox.getValue().equals("Admin") &&
        	        admin.login(username.getText(), password.getText())) {
        	    stage.setScene(createAdminDashboard(stage));
        	}
        	else if (roleBox.getValue().equals("User") &&
        	        user.login(username.getText(), password.getText())) {
        	    stage.setScene(createUserDashboard(stage));
        	}
        	else if (roleBox.getValue().equals("Donor")) {
        	    stage.setScene(createDonorScene(stage)); // 👈 NEW
        	}
        	else {
        	    msg.setText("Invalid Credentials");
        	}

        });

        GridPane grid = new GridPane();
        grid.setVgap(10); grid.setHgap(10);

        grid.add(new Label("Username"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password"), 0, 1);
        grid.add(password, 1, 1);
        grid.add(new Label("Role"), 0, 2);
        grid.add(roleBox, 1, 2);
        grid.add(loginBtn, 1, 3);
        grid.add(msg, 1, 4);

        return new Scene(grid, 300, 250);
    }

    // ================= ADMIN DASHBOARD =================
    private Scene createAdminDashboard(Stage stage) {

        Button addBtn = new Button("Add Donor");
        Button viewBtn = new Button("View Donors");
        Button logout = new Button("Logout");

        addBtn.setOnAction(e -> stage.setScene(createAddDonorScene(stage)));
        viewBtn.setOnAction(e -> stage.setScene(createViewDonorScene(stage)));
        logout.setOnAction(e -> stage.setScene(createLoginScene(stage)));

        GridPane grid = new GridPane();
        grid.setVgap(15);

        grid.add(addBtn, 0, 0);
        grid.add(viewBtn, 0, 1);
        grid.add(logout, 0, 2);

        return new Scene(grid, 300, 200);
    }

    // ================= ADD DONOR =================
    private Scene createAddDonorScene(Stage stage) {

        TextField name = new TextField();
        TextField age = new TextField();
        TextField contact = new TextField();

        ComboBox<String> blood = new ComboBox<>();
        blood.getItems().addAll("A+","A-","B+","B-","O+","O-","AB+","AB-");

        Label msg = new Label();
        Button save = new Button("Save");
        Button back = new Button("Back");

        save.setOnAction(e -> {
            try {
                Doner d = new Doner(
                        name.getText(),
                        Integer.parseInt(age.getText()),
                        blood.getValue(),
                        contact.getText()
                );
                FileHandler.saveDonor(d);
                inventory.addBlood(blood.getValue());
                msg.setText("Donor Added");
            } catch (Exception ex) {
                msg.setText("Invalid Input");
            }
        });

        back.setOnAction(e -> stage.setScene(createAdminDashboard(stage)));

        GridPane grid = new GridPane();
        grid.setVgap(10); grid.setHgap(10);

        grid.add(new Label("Name"),0,0); grid.add(name,1,0);
        grid.add(new Label("Age"),0,1); grid.add(age,1,1);
        grid.add(new Label("Blood"),0,2); grid.add(blood,1,2);
        grid.add(new Label("Contact"),0,3); grid.add(contact,1,3);
        grid.add(save,1,4); grid.add(back,1,5);
        grid.add(msg,1,6);

        return new Scene(grid,400,350);
    }

    // ================= VIEW DONORS =================
    private Scene createViewDonorScene(Stage stage) {

        TextArea area = new TextArea();
        area.setEditable(false);

        try (BufferedReader br = new BufferedReader(new FileReader("donors.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                area.appendText(line + "\n");
            }
        } catch (IOException e) {
            area.setText("No donors found");
        }

        Button back = new Button("Back");
        back.setOnAction(e -> stage.setScene(createAdminDashboard(stage)));

        GridPane grid = new GridPane();
        grid.add(area, 0, 0);
        grid.add(back, 0, 1);

        return new Scene(grid, 400, 300);
    }

    // ================= USER DASHBOARD =================
    private Scene createUserDashboard(Stage stage) {

        ComboBox<String> blood = new ComboBox<>();
        blood.getItems().addAll("A+","A-","B+","B-","O+","O-","AB+","AB-");

        Label result = new Label();
        Button check = new Button("Check");
        Button logout = new Button("Logout");

        check.setOnAction(e -> {
            int units = inventory.getUnits(blood.getValue());
            result.setText("Available Units: " + units);
        });

        logout.setOnAction(e -> stage.setScene(createLoginScene(stage)));

        GridPane grid = new GridPane();
        grid.setVgap(10);

        grid.add(new Label("Blood Group"),0,0);
        grid.add(blood,1,0);
        grid.add(check,1,1);
        grid.add(result,1,2);
        grid.add(logout,1,3);

        return new Scene(grid,300,250);
    }
    private Scene createDonorScene(Stage stage) {

        TextField name = new TextField();
        TextField age = new TextField();
        TextField contact = new TextField();

        ComboBox<String> blood = new ComboBox<>();
        blood.getItems().addAll(
                "A+","A-","B+","B-","O+","O-","AB+","AB-"
        );

        Label msg = new Label();
        Button donate = new Button("Donate Blood");
        Button back = new Button("Back");

        donate.setOnAction(e -> {
            try {
                Doner donor = new Doner(
                        name.getText(),
                        Integer.parseInt(age.getText()),
                        blood.getValue(),
                        contact.getText()
                );

                FileHandler.saveDonor(donor);
                inventory.addBlood(blood.getValue());

                msg.setText("Thank you for donating blood ❤️");
            } catch (Exception ex) {
                msg.setText("Invalid Information");
            }
        });

        back.setOnAction(e -> stage.setScene(createLoginScene(stage)));

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);

        grid.add(new Label("Donor Name"), 0, 0);
        grid.add(name, 1, 0);

        grid.add(new Label("Age"), 0, 1);
        grid.add(age, 1, 1);

        grid.add(new Label("Blood Group"), 0, 2);
        grid.add(blood, 1, 2);

        grid.add(new Label("Contact"), 0, 3);
        grid.add(contact, 1, 3);

        grid.add(donate, 1, 4);
        grid.add(back, 1, 5);
        grid.add(msg, 1, 6);

        return new Scene(grid, 400, 350);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
