package uiControllers;

import application.Main;
import classes.functionnalities.TableModel;
import classes.users.admin.Admin;
import database.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AdminHomeController implements Initializable {

    Pane standard;
    boolean flag = true;
    Object objAdmin;
    @FXML
    private BorderPane mainPane;
    @FXML
    private Button btnManageBooks;
    @FXML
    private Button btnManageStudents;
    @FXML
    private Button btnManageBranchs;
    @FXML
    private Button btnAccountSettings;
    @FXML
    private Label statusLabel;
    @FXML
    private Button btnLogout;
    @FXML
    private TextField textSearchBook;
    @FXML
    private Button btnSearch;
    @FXML
    private TableView<TableModel> tableBookStock;
    @FXML
    private TableColumn<TableModel, String> bookId;
    @FXML
    private TableColumn<TableModel, String> bookTitle;
    @FXML
    private TableColumn<TableModel, String> bookAuthor;
    @FXML
    private TableColumn<TableModel, String> bookPublisher;
    @FXML
    private TableColumn<TableModel, String> bookBranch;
    private ObservableList<TableModel> list = FXCollections.observableArrayList();


    @FXML
    public void manageBooks(ActionEvent event) throws IOException {
        statusLabel.setText("Admin Home Page/Manage Books");
        Pane view = FXMLLoader.load(getClass().getResource("/uiDesigns/ManageBooksUi.fxml"));
        view.getStylesheets().add(getClass().getResource("/uiStyles/ManageBooksStyle.css").toExternalForm());
        if (flag) {
            flag = false;
            mainPane.setCenter(view);
        } else {
            flag = true;
            mainPane.setCenter(standard);
        }
    }

    @FXML
    public void manageStudents(ActionEvent event) {

    }

    @FXML
    public void manageBranchs(ActionEvent event) {

    }

    @FXML
    public void accountSettings(ActionEvent event) {

    }

    @FXML
    public void searchBook() {

    }


    @FXML
    public void logout(ActionEvent event) throws IOException {
        Admin admin = (Admin) objAdmin;
        System.out.println("loggingOut" + "userId :" + admin.getId());
        admin.logout();
        System.out.println("loggedOut" + "userId :" + admin.getId());
        File f = new File("user.ta");
        f.delete();
        System.out.println("user.ta deleted");
        Stage current = (Stage) btnLogout.getScene().getWindow();
        Main.setRoot(current, "LoginUi", "LoginStyle");

    }
//	public static void setTableItems(String id,String title, String author,String publisher,String branch) {
    //	list.removeAll(list);
    //	list.add(new TableModel(id,title,author,publisher,branch));
    //	tableBookStock.refresh();
    //}

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // save the old mainPaie Centre view
        standard = (Pane) mainPane.getCenter();
        // load Admin object from the stream
        FileInputStream load;
        try {
            load = new FileInputStream("user.ta");
            ObjectInputStream os = new ObjectInputStream(load);
            objAdmin = os.readObject();
            os.close();
            Connection con = DatabaseHandler.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM BOOK_STOCK;");
            while (rs.next()) {
                list.add(new TableModel(rs.getString("bookId"), rs.getString("bookTitle"), rs.getString("bookAuthor"), rs.getString("bookPublisher"), rs.getString("bookBranch")));
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        // upload books from stock to the table view
        bookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        bookTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        bookAuthor.setCellValueFactory(new PropertyValueFactory<>("bookAuthor"));
        bookPublisher.setCellValueFactory(new PropertyValueFactory<>("bookPublisher"));
        bookBranch.setCellValueFactory(new PropertyValueFactory<>("bookBranch"));
        tableBookStock.setItems(list);
    }
}
