package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {
    public JFXTextField txtEmail;
    public JFXPasswordField txtPassword;
    public AnchorPane LoginForm;


    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        if(txtEmail.getText().contains("Thrimal") && txtPassword.getText().contains("1234")) {
            Stage stage = (Stage) LoginForm.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/MainForm.fxml"))));
        }else{
            if((txtEmail.getText().isEmpty()) && (txtPassword.getText().isEmpty())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Empty Fields");
                alert.show();
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Wrong Input Try Again");
                alert.show();
            }
        }
    }
}
