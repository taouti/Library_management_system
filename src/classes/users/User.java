package classes.users;

import database.DatabaseHandler;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public abstract class User implements Serializable {

    private transient String userName = null;
    private transient String userPassword = null;
    private String userId = null;
    private boolean loggedin = false;
    private transient String userType = null;

    public User() {

    }

    public User(String name, String password, String id) {
        userName = name;
        userPassword = password;
        userId = id;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    //getter Methods
    public String getName() {
        return userName;
    }

    //Setter Methods
    public void setName(String name) {
        userName = name;
    }

    public String getPassword() {
        return userPassword;
    }
	/*
	public void setLoggedinFalse(){
		loggedin = false;
	}
*/

    public void setPassword(String password) {
        userPassword = password;
    }

    public String getId() {
        return userId;
    }

    public void setId(String id) {
        userId = id;
    }

    public boolean isLoggedIn() {
        return loggedin;
    }

    public String login() {
        try {
            Connection con = DatabaseHandler.getCon();
            Statement st = con.createStatement();
            boolean checkUserName = false;
            boolean checkPassword = false;
            ResultSet rs = st.executeQuery("select * from " + this.userType + ";");
            while (rs.next()) {
                //System.out.println("Entered userId : "+userId+" password : "+userPassword);
                //System.out.println("in db userId : "+rs.getString("userName")+" password : "+rs.getString("userPassword"));
                checkUserName = userId.equals(rs.getString("userName"));
                checkPassword = userPassword.equals(rs.getString("userPassword"));
                if (checkPassword && checkUserName) {
                    loggedin = true;
                    break;
                } else {
                    loggedin = false;
                }
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void logout() {
        userName = null;
        userPassword = null;
        userId = null;
        loggedin = false;
    }

    public void chanchePassword(String oldPassword, String newPassword) throws IOException {
        //check if logged in
        if (loggedin) {
            // check the old password
            if (oldPassword.equals(userPassword)) {
                userPassword = newPassword;
            }
        }
    }

    public void changeUserName(String oldUserName, String newUserName) {
        //check if logged in
        if (loggedin) {
            // check the old password
            if (oldUserName.equals(userName)) {
                userName = newUserName;
            }
        }
    }

    public void searchBook() {

    }


    public void chackBookList() {

    }
}
