package classes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DatabaseHandler;

public class Book {
	private String title = null;
	private String id = null;
	private String publisher = null;
	private String author = null;
	private String branch = null;
	
	
	
	
	//setter Methods
	public void setId(String ID) {
		id = ID;
	}
	
	public void setTitle(String Title) {
		this.title = Title;
	}
	
	public void setPublisher(String Publisher) {
		this.publisher = Publisher;
	}	
	
	public void setAhothor(String Author) {
		this.author = Author;
	}
	
	
	public void setBranch(String BranchName) {
		branch = BranchName;
	}
	
	//getter Methods
	public String getId() {
		return this.id;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getAuthor() {
		return this.author;
	}
	
	public String getPublisher() {
		return this.publisher;
	}
	
	
	public String getBranch() {
		return this.branch;
	}
	
	
	public void loadFromStock(String id) {
		//String branch = book.getBranch();
				try {
					Connection con = DatabaseHandler.getCon();
					Statement st = null;
					st = con.createStatement();
					ResultSet rs;
					rs = st.executeQuery("SELECT FROM BOOKSTOCK where bookId = '"+id+"';");
					if (rs.next()) {
						this.id = rs.getString("bookId");
						this.title = rs.getString("bookTitle");
						this.author = rs.getString("bookAuthor");
						this.publisher = rs.getString("bookPublisher");
						this.branch = rs.getString("bookBranch");
						//this.addBook(book.getId(), book);
					}
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	}
	
	public void updateStock() {
		
		String id = this.getId();
		String bookTitle = this.getTitle();
		String bookAuthor = this.getAuthor();
		String bookPublisher = this.getPublisher();
		//String branch = book.getBranch();
		boolean isAvail = true;
		try {
			Connection con = DatabaseHandler.getCon();
			Statement st = null;
			st = con.createStatement();
			ResultSet rs;
			rs = st.executeQuery("SELECT FROM BOOKSTOCK where bookId = '"+id+"';");
			if (!rs.next()) {
				
				//this.addBook(book.getId(), book);
			} else {
				if (!( this.getBranch() == null )) {
					st.execute("update "+this.getBranch()+" set bookTitle = '"+bookTitle+"',"
							+ "bookAuthor = '"+bookAuthor+"',"
							+ "bookPublisher = '"+bookPublisher+"',"
							+ "isAvail ='"+isAvail+"' where bookId = '"+id+"';");
				}
				st.execute("update BOOKSTOCK set bookTitle = '"+bookTitle+"',"
						+ "bookAuthor = '"+bookAuthor+"',"
						+ "bookPublisher = '"+bookPublisher+"' where id = '"+id+"';");
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// end of update book method
} // end of the SuperClass
