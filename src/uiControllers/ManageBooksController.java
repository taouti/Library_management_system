package uiControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManageBooksController implements Initializable {

    @FXML
    Button btnAddNewBook;

    @FXML
    Button btnRemoveBook;

    @FXML
    BorderPane mainPane;

    Pane current;
    boolean flag = true;
    boolean flag1 = true;

    @FXML
    public void loadAddBookUi(ActionEvent event) throws IOException {
        if (flag) {
            flag = false;
            flag1 = true;
            Pane view = FXMLLoader.load(getClass().getResource("/uiDesigns/AddNewBookUi.fxml"));
            mainPane.setCenter(view);
        } else {
            flag = true;
            mainPane.setCenter(current);
        }
    }

    @FXML
    public void loadRemoveBookUi(ActionEvent event) throws IOException {
        if (flag1) {
            flag1 = false;
            flag = true;
            Pane view = FXMLLoader.load(getClass().getResource("/uiDesigns/RemoveBookUi.fxml"));
            mainPane.setCenter(view);
        } else {
            flag1 = true;
            mainPane.setCenter(current);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        current = (Pane) mainPane.getCenter();
    }

}
