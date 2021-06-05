package uiControllers;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;


import application.Main;
import classes.users.admin.Admin;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import classes.Branch;
import classes.Book;
import database.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import database.DatabaseHandler;
import javafx.stage.Stage;

public class AddNewBookController implements Initializable {

	@FXML
	private JFXButton btnAdd;
	//@FXML
	//private JFXButton btnExit;
	@FXML
	private JFXTextField textBookTitle;
	@FXML
	private JFXTextField textBookId;
	@FXML
	private JFXTextField textBookAuthor;
	@FXML
	private JFXTextField textBookPublisher;

	@FXML
	private JFXComboBox<String> selectBranchComboBox;


	private Statement st;
	//private Branch branch;
	private Book book;
	private Admin admin;
	private Object loadAdmin;

	@FXML
	public void addBook(ActionEvent event) {
		Admin admin = (Admin) loadAdmin;
		String branchName = selectBranchComboBox.getValue();
		if (branchName == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Please Select Branch");
			alert.setContentText("Thank you!");
			alert.showAndWait();
		} else {
			//int index = branchName.indexOf(";");
			//String branchId = branchName.substring(1, index-2).trim();
			//branch = new Branch(branchId);
			book = new Book();
			book.setId(textBookId.getText());
			book.setTitle(textBookTitle.getText());
			book.setAhothor(textBookAuthor.getText());
			book.setPublisher(textBookPublisher.getText());
			book.setBranch(branchName);
			boolean bookExist = false;
			if (book.getId().equals("") || book.getTitle().equals("")) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Info");
				alert.setHeaderText("You must Enter All Book Informations");
				alert.setContentText("THANK YOU !");
				alert.showAndWait();
			} else {

				try {
					Connection con = DatabaseHandler.getCon();
					st = con.createStatement();
					ResultSet rs = st.executeQuery("SELECT *FROM BOOK_STOCK");
					while(rs.next()) {
						if (book.getId().contentEquals(rs.getString("id").trim())) {
							bookExist = true;
							break;
						} else {
							bookExist = false;
						}
					}
					if(!bookExist) {
						//con.close();
						//branch.addBook(book.getId(), book);
						//AdminHomeController adminHome = new AdminHomeController();
					//	adminHome.setTableItems(book.getId(),book.getTitle(),book.getAuthor(),book.getPublisher(),book.getBranch());
						admin.addBookToBranch(book);
					//	Stage stage = (Stage) btnAdd.getScene().getWindow();
						//Main.setRoot(stage,"AdminHomeUi","AdminHomeStyle");

						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setTitle("Info");
						alert.setHeaderText("Done");
						alert.setContentText("THANK YOU !");
						alert.showAndWait();
						textBookId.setText("");
						textBookTitle.setText("");
						textBookAuthor.setText("");
						textBookPublisher.setText("");
					} else { // if book found in the stock
						rs = st.executeQuery("SELECT *FROM "+book.getBranch()+" where bookId = '"+book.getId()+"';");
						if (rs.next()) { // if book found in branch
							Alert alert1 = new Alert(AlertType.CONFIRMATION);
							alert1.setTitle("Confirmation");
							alert1.setHeaderText("You are About to lose old informations of this book ID");
							alert1.setContentText("You can click Cancel to see old Data if not data will be updated");
							Optional<ButtonType> result = alert1.showAndWait();
							if (result.get() == ButtonType.OK) {
								con.close();
								//branch.updateBook(book);
								System.out.println("Admin updated book");
								Alert alert = new Alert(AlertType.INFORMATION);
								alert.setTitle("Info");
								alert.setHeaderText("Book info Updated Successfully");
								alert.setContentText("THANK YOU !");
								alert.showAndWait();
								textBookId.setText("");
								textBookTitle.setText("");
								textBookAuthor.setText("");
								textBookPublisher.setText("");
							} else {
								System.out.println(book.getId());
								System.out.println(book.getTitle());
								System.out.println(book.getAuthor());
								System.out.println(book.getPublisher());
								rs = st.executeQuery("select *from BOOK_STOCK where id = '"+book.getId()+"'");
								textBookTitle.setText(rs.getString("bookTitle"));
								textBookAuthor.setText(rs.getString("bookAuthor"));
								textBookPublisher.setText(rs.getString("bookPublisher"));
							}

						} else {
							con.close();
							book.loadFromStock(book.getId());
							//branch.addBook(book.getId(), book);
							admin.addBookToBranch(book);
							System.out.println(book.getId());
							System.out.println(book.getTitle());
							System.out.println(book.getAuthor());
							System.out.println(book.getPublisher());
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Info");
							alert.setHeaderText("added to branch");
							alert.setContentText("THANK YOU !");
							alert.showAndWait();
						}
					}

					con.close();
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	//@FXML
	//public void cancel(ActionEvent event) {
		//System.exit(0);
//	}

	@Override
	public void initialize(URL url,ResourceBundle rb) {
		Connection con = DatabaseHandler.getCon();
		//Branch b = new Branch();
		//b.createNewBranch("000", "Data_Science");
		//b.createNewBranch("001", "Electronics");
		try {
			FileInputStream in = new FileInputStream("user.ta");
			ObjectInputStream os = new ObjectInputStream(in);
			loadAdmin = os.readObject();
			os.close();
			st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT *FROM BRANCH_LIST;");
			while(rs.next()) {
				selectBranchComboBox.getItems().add(rs.getString("branchName"));
				//selectBranchComboBox.getItems().add("\""+rs.getString("branchId")+"\" ; "+rs.getString("branchName"));

			}
			con.close();
		} catch (SQLException | FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
}
