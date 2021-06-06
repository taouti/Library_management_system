package classes.users.admin;

import classes.Book;
import classes.users.User;
import database.DatabaseHandler;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Admin extends User {


    public Admin() {
        super();
        this.setUserType("ADMINS");
    }

    public Admin(String name, String password, String id) {
        super(name, password, id);
        this.setUserType("ADMINS");
    }

    public void addBookToBranch(Book book) {
        if (this.isLoggedIn()) {
            try {
                Connection con = DatabaseHandler.getCon();
                Statement st = con.createStatement();
                st.execute("INSERT INTO BOOK_STOCK VALUES ('" + book.getId() + "','" + book.getTitle() + "','" + book.getAuthor() + "','" + book.getPublisher() + "','" + book.getBranch() + "');");
                st.execute("INSERT INTO " + book.getBranch() + " VALUES ('" + book.getId() + "','" + book.getTitle() + "','" + book.getAuthor() + "','" + book.getPublisher() + "','" + true + "');");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Admin is not loggedIn !!");
            alert.showAndWait();
            System.exit(0);
        }
    }

    public void removeBookFromBranch(String bookId) throws SQLException {
        Connection con = DatabaseHandler.getCon();
        Statement st = con.createStatement();
        boolean bookDeleted = false;
        ResultSet rs = st.executeQuery("SELECT * FROM BOOK_STOCK");
        while (rs.next()) {
            if (rs.getString("id").equals(bookId)) {
                String branch = rs.getString("bookBranch");
                st.execute("DELETE FROM BOOK_STOCK where id = '" + bookId + "';");
                st.execute("DELETE FROM " + branch + " where bookId = '" + bookId + "';");
                bookDeleted = true;
            }
        }
        if (!bookDeleted) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Book Not Found");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Book Deleted !");
            alert.showAndWait();
        }
        con.close();
    }

    public void createNewBranch() {

    }

    public void removeBranch() {

    }

    public void addStudent() {
        // create a file that holds the id pass and user name for that student to send it to him
        // generates auto userId
    }

    public void removeStudent() {

    }

    public void checkBookDemands() {

    }

    public void giveBookToStudent() {

    }

    public void takeReturnedBook() {

    }

    public void addNewAdmin() {

    }

    public void removeAdmin() {

    }

    public void checkBookList() {

    }

    public void checkAccountCreationDemands() {

    }

    @Override
    public void logout() {
        Connection con = DatabaseHandler.getCon();
        Statement st = null;
        try {
            st = con.createStatement();
            st.execute("UPDATE ADMINS SET loggedin = '" + "false" + "' where userName = '" + this.getId() + "'");
            System.out.println(this.getId() + " logged out and his state is false");
            super.logout();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
