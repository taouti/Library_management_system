package uiControllers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.ResourceBundle;

import application.Main;
import classes.users.admin.Admin;
import classes.users.student.Student;
import com.jfoenix.controls.*;
import database.DatabaseHandler;
//import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.xml.crypto.Data;

public class LoginController implements Initializable{
	
	@FXML
	JFXButton btnLogin;
	@FXML
	JFXButton btnClose;
	@FXML
	JFXTextField textUserName;
	@FXML
	JFXPasswordField textPassword;
	@FXML
	JFXTextField textUserId;
	@FXML
	Label signin;
	@FXML
	JFXTogglePane toggleGroup;
	@FXML
	JFXRadioButton radioBtnAdmin;
	@FXML
	JFXRadioButton radioBtnStudent;
	@FXML
	JFXButton signUp;
	@FXML
	Label lblDate;

	ToggleGroup group = new ToggleGroup();
	Admin admin = new Admin();
	Student student = new Student();

	@FXML
	public void login(ActionEvent event) throws IOException, SQLException {
		// temporary direct access to ADMIN panel
		//Stage current = (Stage) btnLogin.getScene().getWindow();
		//Main.setRoot(current,"AdminHomeUi","AdminHomeStyle");

		Connection con = DatabaseHandler.getCon();
		Statement st = con.createStatement();
		String userName = textUserName.getText();
		String password = textPassword.getText();
		if (radioBtnStudent.isSelected() || radioBtnAdmin.isSelected()){
			if (radioBtnAdmin.isSelected()) {
				admin.setId(userName);
				admin.setPassword(password);
				admin.login();
				if (admin.isLoggedIn()) {
					// save the object in a file
					//admin.setLoggedinFalse();
					FileOutputStream file = new FileOutputStream("user.ta");
					ObjectOutputStream os = new ObjectOutputStream(file);
					os.writeObject(admin);
					os.close();
					System.out.println("user.ta created Admin object saved");
					st.execute("update ADMINS set loggedin = '"+"true"+"' where userName = '"+userName+"'");
					System.out.println(admin.getId()+" logged IN and his state is true");
					Stage current = (Stage) btnLogin.getScene().getWindow();
					//current.initStyle(StageStyle.UNDECORATED);
					Main.setRoot(current,"AdminHomeUi","AdminHomeStyle");
				} else {
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setContentText("username or password incorrect!");
					alert.showAndWait();
				}
			} else {
				student.setId(userName);
				student.setPassword(password);
				student.login();
				if (student.isLoggedIn()) {
					st.execute("update STUDENTS set loggedin = '"+"true"+"' where userName = '"+userName+"'");
					Stage current = (Stage) btnLogin.getScene().getWindow();
					Main.setRoot(current,"StudentHomeUi","StudentHomeStyle");
				} else {
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setContentText("username or password incorrect!");
					alert.showAndWait();
				}
			}
			con.close();
		} else {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setContentText("Please Select Admin or Student ?");
			alert.showAndWait();
		}


	}
	
	@FXML
	public void close(ActionEvent event) throws IOException {
		System.exit(0);
	}
	
	@FXML
	public void demandAccount() throws IOException {
		//System.out.println("Demanding");
		Stage current = (Stage) signUp.getScene().getWindow();
		Main.setRoot(current,"DemandAccountUi","DemandAccountStyle");
		//Main.setRoot("DemandAccountUi");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		radioBtnAdmin.setToggleGroup(group);
		radioBtnStudent.setToggleGroup(group);
		Date today = new Date();
		//lblDate.setText(String.format("%tc",new Date()));
		//lblDate.setText(String.format("%tA, %tB %td",today,today,today));
		lblDate.setText(String.format("%tA, %<tB %<td",today));
		try {
			DatabaseHandler.createDefaultDatabase();
			/*
			//must check if admins table exists if not create them
    		String[][] adminTable = { {         "userName"       ,"userPassword", "loggedin" },
					                  {"varchar(50) primary key" ,"varchar(50)" ,"varchar(4)"} };
    		DatabaseHandler.createNewTable("admins", adminTable);
			//must check if students table exists if not create them

			String[][] studentTable = { {         "userName"       ,"userPassword", "loggedin" },
									    {"varchar(50) primary key" , "varchar(50)","varchar(4)"} };
			DatabaseHandler.createNewTable("students", studentTable);

			// check if brach_list table exists if not create it
    		String[][] branchTable = { {        "branchId"       ,"branchName" },
    								   {"varchar(10) primary key","varchar(50)"} };
    		DatabaseHandler.createNewTable("branch_list", branchTable);

    		// check book_Stock table exists or not if not create it
    		String[][] bookStockTable = { {          "bookId"       ,  "bookTitle" , "bookAuthor" ,"bookPublisher",                    "bookBranch"                  },
	  				  					  {"varchar(10) primary key","varchar(100)","varchar(100)", "varchar(100)","varchar(50) REFERENCES branch_list (branchName) "} };
    		DatabaseHandler.createNewTable("book_stock", bookStockTable);

			// check if account_Demands table exist if not create it
			String[][] accounts = {{      "demandNumber"     , "userName"  ,  "password" ,    "email"    , "newUserId" },
				      	           {"varchar(10) primary key","varchar(10)","varchar(50)", "varchar(100)","varchar(10)"} };
			DatabaseHandler.createNewTable("account_demands",accounts);

			// check if borrowers_records table exist if not create it
			String[][] borrowersTable = {{                   "userName"               ,                  "bookId"                  },
					                     {"varchar(10) REFERENCES students (userName)","varchar(10) REFERENCES book_stock (bookId)"} };
			DatabaseHandler.createNewTable("borrower_records",borrowersTable);

			// check if book_return_records table exist if not create it
			String[][] bookReturnTable = {{                 "userName"                 ,                   "bookId"                 },
					                      {"varchar(10) REFERENCES students (userName)","varchar(10) REFERENCES book_stock (bookId)"} };
			DatabaseHandler.createNewTable("book_return_records",bookReturnTable);

			// check if Book_Demands table exist if not create it
			String[][] bookDemandsTable = {{"                  userName                ",                   "bookId"                 },
					                       {"varchar(10) REFERENCES students (userName)","varchar(10) REFERENCES book_stock (bookId)"} };
			DatabaseHandler.createNewTable("book_demands",bookDemandsTable);

			// you must define the foreign keys and refactor names and finish with the database
    		//DatabaseHandler.addTestUsers();

			 */

    	} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}