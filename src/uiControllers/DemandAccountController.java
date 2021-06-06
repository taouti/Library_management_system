package uiControllers;

import application.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;

public class DemandAccountController {

    @FXML
    private JFXButton btnSend;
    @FXML
    private JFXButton btnCancel;
    @FXML
    private JFXTextField textUserName;
    @FXML
    private JFXPasswordField newPassword;
    @FXML
    private JFXPasswordField confirmPassword;


    @FXML
    public void send(ActionEvent event) {
        System.out.println("sent");
    }

    @FXML
    public void cancel(ActionEvent event) throws IOException {
        Stage current = (Stage) btnCancel.getScene().getWindow();
        Main.setRoot(current, "LoginUi", "LoginStyle");
    }

}
