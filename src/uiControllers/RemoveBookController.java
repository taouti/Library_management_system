package uiControllers;

import classes.users.admin.Admin;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.sql.SQLException;

public class RemoveBookController {

    @FXML
    private JFXTextField textBookId;

    @FXML
    private JFXButton btnRemove;

    private Admin admin = new Admin();

    @FXML
    void removeBook(ActionEvent event) throws SQLException {
        String id = textBookId.getText().trim();
        if (id.equals(null)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("ID Field is Empty");
            alert.showAndWait();
        } else {
            admin.removeBookFromBranch(id);
            textBookId.setText("");
        }
    }

}
