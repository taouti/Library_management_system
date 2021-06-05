package classes;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import database.DatabaseHandler;

public class Branch {
	private String branchName = null;
	private String branchId = null;
	
	public String getBranchName() {
		return branchName;
	}
	
	public String getBranchId() {
		return branchId;
	}
	
	public Branch() {
		
	}

	public Branch(String BranchId) {
		Connection con = DatabaseHandler.getCon();
		Statement st = null;
		ResultSet rs = null;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT *FROM BRANCHESLIST where branchId = '"+BranchId+"'");
			if(rs.next()) {
				branchName = rs.getString("branchName");
				branchId = BranchId;
			}
		} catch (SQLException e) {
			e.printStackTrace();		
		}
		try {
			if (!con.isClosed())
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void createNewBranch(String BranchId, String BranchName) {
		Connection con = DatabaseHandler.getCon();
		Statement st = null;
		ResultSet table = null;
		DatabaseMetaData dmd;
		try {
			st = con.createStatement();
			dmd = con.getMetaData();
			table = dmd.getTables(null, null, BranchName, null);
			if(!table.next()) {
				st.execute("insert into BRANCHESLIST values('"+BranchId+"','"+BranchName+"');");
				st.execute("CREATE TABLE "+BranchName+" (" + 
							"bookId varchar(200)primary key," + 
							"bookTitle varchar(500)," + 
							"bookAuthor varchar(500)," + 
							"bookPublisher varchar(500)," + 
							"isAvail boolean);");
				branchName = BranchName;
				branchId = BranchId;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	/*
	public void addBook(String bookId,Book book) {
		
		//String branch = book.getBranch();
		try {
			Connection con = DatabaseHandler.getCon();
			Statement st = null;
			st = con.createStatement();
			ResultSet rs = st.executeQuery("select *from BOOK_STOCK where id = '"+bookId+"'");
			if(rs.next()) {
				String id = bookId;
				String bookTitle = rs.getString("bookTitle");
				String bookAuthor = rs.getString("bookAuthor");
				String bookPublisher = rs.getString("bookPublisher");
				st.execute("insert into "+branchName+" "
						+ "values('"+id+"','"+bookTitle+"','"+bookAuthor+"','"+bookPublisher+"','"+true+"');");
				book.setBranch(branchName);
			} else {
				book.addToStock();
				st.execute("insert into "+branchName+" "
						+ "values('"+book.getId()+"',"
								+ "'"+book.getTitle()+"',"
								+ "'"+book.getAuthor()+"',"
								+ "'"+book.getPublisher()+"',"
								+ "'"+true+"');");
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}//end of add book Method

	 */
	
	public void updateBook(Book book) {
		
		String id = book.getId();
		String bookTitle = book.getTitle();
		String bookAuthor = book.getAuthor();
		String bookPublisher = book.getPublisher();
		//String branch = book.getBranch();
		boolean isAvail = true;
		try {
			Connection con = DatabaseHandler.getCon();
			Statement st = null;
			st = con.createStatement();
			ResultSet rs;
			rs = st.executeQuery("SELECT *FROM "+branchName+" where bookId = '"+id+"';");
			if (!rs.next()) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Warning");
				alert.setHeaderText("This Book Does not exist in this Branch it is in the stock");
				alert.setContentText("Do you want to update it in the stock?");
				Optional<ButtonType> answer = alert.showAndWait();
				if (answer.get() == ButtonType.OK) {
					st.execute("update BOOK_STOCK set "
							+ "bookTitle = '"+bookTitle+"',"
							+ "bookAuthor = '"+bookAuthor+"',"
							+ "bookPublisher = '"+bookPublisher+"' where id = '"+id+"';");
					
				}
			} else {
				st.execute("update "+branchName+" set bookTitle = '"+bookTitle+"',"
						+ "bookAuthor = '"+bookAuthor+"',"
						+ "bookPublisher = '"+bookPublisher+"',"
						+ "isAvail ='"+isAvail+"' where bookId = '"+id+"';");
				st.execute("update BOOK_STOCK set bookTitle = '"+bookTitle+"',"
						+ "bookAuthor = '"+bookAuthor+"',"
						+ "bookPublisher = '"+bookPublisher+"' where id = '"+id+"';");
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// end of update book method

	public void deleteBook(Book book) {
		
		String id = book.getId();
		//String branch = book.getBranch();
		try {
			Connection con = DatabaseHandler.getCon();
			Statement st = null;
			st = con.createStatement();
			st.execute("delete from "+branchName+" where bookId = '"+id+"'");
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}//end of delete Book Method
	
	public void makeUnavailableBook(Book book) {
		String bookId = book.getId();
		
		//String branch = book.getBranch();
		try {
			Connection con = DatabaseHandler.getCon();
			Statement st = null;
			st = con.createStatement();
			st.execute("update "+branchName+" set isAvail = '"+false+"' where bookId = '"+bookId+"'");
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}//end of delete Book Method

}
