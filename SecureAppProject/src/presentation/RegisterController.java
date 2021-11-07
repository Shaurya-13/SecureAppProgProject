package presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import register.PasswordChecker;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static persistence.StaffDAO.addUser;

public class RegisterController implements Initializable {

    private Stage stage;

    @FXML
    private AnchorPane registerAnchorPane;

    @FXML
    private Label registrationMessageLabel;

    @FXML
    private Button btnRegistration;

    @FXML
    private TextField userNameTextfield;

    @FXML
    private TextField pinTextfield;

    @FXML
    private TextField emailTextfield;

    @FXML
    private PasswordField passwordTextfield;

    @FXML
    private PasswordField confirmPasswordTextfield;

    @FXML
    private Label messageLabel;


        public void btnRegistrationAction(ActionEvent event) {
        System.out.println("Registration Pressed");
        String userName = userNameTextfield.getText();
        String pass1 = passwordTextfield.getText();
        String pass2 = confirmPasswordTextfield.getText();
        String pin = pinTextfield.getText();
        String UserEmail = emailTextfield.getText();
        PasswordChecker pc = new PasswordChecker(pass1,pass2 ,pin );
        String message = pc.validatePasswordPin();
        showMessage(message);

        if(pc.isValidPassword()){
            System.out.println("Valid password");
            int status = addUser(userName, pass1, pin, UserEmail);
            System.out.println("add user "+status);
            if(status == 1){
                returnToLogin();
                closeWindow();
            }else{
                showMessage("Sorry could not process that request");
            }
        }

    }


    public void btntnCancelRegistrationAction(ActionEvent event) {
        goToLoginPage();
        closeWindow();
    }


    public void returnToLogin() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();
            LoginController loginController = loader.getController();

            //Show Login in new window
            Stage loginStage = new Stage();
            loginStage.setScene(new Scene(root));
            loginStage.initStyle(StageStyle.UNDECORATED);
            loginStage.show();


        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void goToLoginPage() {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root));
            registerStage.show();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void showMessage(String msg) {
        messageLabel.setText(msg);
    }

    public void resetPassword() {
        passwordTextfield.setText("");
        confirmPasswordTextfield.setText("");

    }

    public void closeWindow() {
        Stage stage = (Stage) registerAnchorPane.getScene().getWindow();
        stage.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Init Resister ");


    }
}
