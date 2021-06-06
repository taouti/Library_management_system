package database;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.File;
import java.sql.*;


public class DatabaseHandler {

    public static Connection getCon() {
        Connection con = null;
        // check if the file data.db exists if not create it
        File data = new File("data.db");
        if (!data.exists()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("\\installation Folder\\data.db File is missing!");
            alert.showAndWait();
            System.exit(0);
        }
        try {
            final String SQLITE_URL = "jdbc:sqlite:data.db";
            final String DRIVER = "org.sqlite.JDBC";
            Class.forName(DRIVER).newInstance();
            con = DriverManager.getConnection(SQLITE_URL);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public static void addTestUsers() throws SQLException { // temporary Method
        Connection con = DatabaseHandler.getCon();
        Statement st = con.createStatement();
        st.execute("INSERT INTO ADMINS VALUES ('" + "taouti" + "','" + "admin" + "','" + "false" + "');");
        //st.execute("INSERT INTO users VALUES ('"+"LMS000ST"+"','"+"moh"+"','"+"aaaa"+"','"+"student"+"');");
        //st.execute("INSERT INTO users VALUES ('"+"LMS001AD"+"','"+"madani"+"','"+"aaaa"+"','"+"admin"+"');");
        //st.execute("INSERT INTO users VALUES ('"+"LMS001ST"+"','"+"med"+"','"+"aaaa"+"','"+"student"+"');");
        System.out.println("admin added added");
    }

    public static void createNewTable(String tableName, String[][] clumns) {
        try {
            // clumns is a String Array of two lines the first's for column names and the second's for types
            Connection con = DatabaseHandler.getCon();
            Statement st = null;
            DatabaseMetaData dmd = con.getMetaData();
            ResultSet table = dmd.getTables(null, null, tableName.trim(), null);
            if (!table.next()) {
                st = con.createStatement();
                //st.execute("CREATE TABLE "+ tableName.trim()+"("+clumns[0][0].trim()+ " varchar("+clumns[1][0].trim()+") primary key);");
                st.execute("CREATE TABLE [" + tableName.trim() + "](" + clumns[0][0].trim() + " " +/*extract typ*/ clumns[1][0] + ");");
                int nmbrOfClmns = clumns[0].length;
                for (int i = 1; i < nmbrOfClmns; i++) {
                    //st.execute("ALTER TABLE "+ tableName.trim()+" ADD "+clumns[0][i].trim()+ " varchar("+Integer.parseInt(clumns[1][i])+");");
                    st.execute("ALTER TABLE [" + tableName.trim() + "] ADD " + /*extract column name*/ clumns[0][i].trim() + " " + /*extract typ*/ clumns[1][i] + ";");
                }
                System.out.println(tableName + " table created");
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void createDefaultDatabase() throws SQLException {
        Connection con = DatabaseHandler.getCon();
        Statement st = con.createStatement();
        st.execute("" +
                "-- Table: admins\n" +
                "CREATE TABLE admins (\n" +
                "    userName VARCHAR(50) PRIMARY KEY,\n" +
                "    userPassword VARCHAR(50),\n" +
                "    loggedin VARCHAR(10)\n" +
                ");");
        System.out.println("table admins created");
        st.execute("" +
                "INSERT INTO admins VALUES ('taouti','admin','false');");
        st.execute("" +
                "-- Table: students\n" +
                "CREATE TABLE students (\n" +
                "    userName VARCHAR(50) PRIMARY KEY,\n" +
                "    userPassword VARCHAR(50),\n" +
                "    email VARCHAR(100),\n" +
                "    loggedin VARCHAR(10)\n" +
                ");");
        System.out.println("table students created");
        st.execute("" +
                "INSERT INTO students VALUES('student1','aaaa','students@univ.com','false');");
        st.execute("" +
                "INSERT INTO students VALUES('student2','aaaa','students@univ.com','false');");
        st.execute("" +
                "INSERT INTO students VALUES('student3','aaaa','students@univ.com','false');");
        st.execute("" +
                "INSERT INTO students VALUES('student4','aaaa','students@univ.com','false');");
        st.execute("" +
                "INSERT INTO students VALUES('student5','aaaa','students@univ.com','false');");
        st.execute("" +
                "INSERT INTO students VALUES('student6','aaaa','students@univ.com','false');");
        st.execute("" +
                "INSERT INTO students VALUES('student7','aaaa','students@univ.com','false');");
        st.execute("" +
                "INSERT INTO students VALUES('student8','aaaa','students@univ.com','false');");
        st.execute("" +
                "INSERT INTO students VALUES('student9','aaaa','students@univ.com','false');");
        st.execute("" +
                "INSERT INTO students VALUES('student10','aaaa','students@univ.com','false');");
        st.execute("" +
                "-- Table: account_demands\n" +
                "CREATE TABLE account_demands (\n" +
                "    demandNumber INTEGER PRIMARY KEY,\n" +
                "    userName VARCHAR(10),\n" +
                "    password VARCHAR(50),\n" +
                "    email VARCHAR(100)\n" +
                ");");
        System.out.println("table account demands created");
        st.execute("" +
                "INSERT INTO account_demands (userName, password,email) VALUES ('student11','aaaa','student@gmail.com');");
        st.execute("" +
                "INSERT INTO account_demands (userName, password,email) VALUES ('student12','aaaa','student@gmail.com');");
        st.execute("" +
                "INSERT INTO account_demands (userName, password,email) VALUES ('student13','aaaa','student@gmail.com');");
        st.execute("" +
                "INSERT INTO account_demands (userName, password,email) VALUES ('student14','aaaa','student@gmail.com');");
        st.execute("" +
                "-- Table: branch_list\n" +
                "CREATE TABLE branch_list (\n" +
                "    branchId VARCHAR(10) PRIMARY KEY,\n" +
                "    branchName VARCHAR(50)\n" +
                ");");
        System.out.println("table branch list created");
        st.execute("" +
                "INSERT INTO branch_list VALUES ('000','Data Science');");
        st.execute("" +
                "INSERT INTO branch_list VALUES ('001','ELECTRONICS');");
        st.execute("" +
                "-- Table: book_stock\n" +
                "CREATE TABLE book_stock (\n" +
                "    bookId VARCHAR(10) PRIMARY KEY NOT NULL,\n" +
                "    bookTitle VARCHAR(100),\n" +
                "    bookAuthor VARCHAR(100),\n" +
                "    bookPublisher VARCHAR(100),\n" +
                "    bookBranch VARCHAR(50),\n" +
                "    FOREIGN KEY (bookBranch) REFERENCES branch_list (branchId)\n" +
                ");");
        System.out.println("table book stock created");
        st.execute("" +
                "INSERT INTO book_stock VALUES('000','JAVA HEAD FIRST','HEAD FIRST','HEAD FIRST','000');");
        st.execute("" +
                "INSERT INTO book_stock VALUES('001','SQL HEAD FIRST','HEAD FIRST','HEAD FIRST','000');");
        st.execute("" +
                "INSERT INTO book_stock VALUES('002','DESIGN PATTERNS HEAD FIRST','HEAD FIRST','HEAD FIRST','000');");
        st.execute("" +
                "INSERT INTO book_stock VALUES('003','Electrnics','No','No','001');");
        st.execute("" +
                "INSERT INTO book_stock VALUES('004','Omperational Amplifiers','No','No','001');");
        st.execute("" +
                "INSERT INTO book_stock VALUES('005','Linear Circuit Analysis','No','No','001');");
        st.execute("" +
                "-- Table: book_demands\n" +
                "CREATE TABLE book_demands (\n" +
                "    userName VARCHAR(10) NOT NULL,\n" +
                "    bookId VARCHAR(10) NOT NULL,\n" +
                "    FOREIGN KEY (userName) REFERENCES students (userName),\n" +
                "    FOREIGN KEY (bookId) REFERENCES book_stock (bookId),\n" +
                "    PRIMARY KEY (userName) " +
                ");");
        System.out.println("table book demands created");
        st.execute("" +
                "INSERT INTO book_demands VALUES('student1','000');");
        st.execute("" +
                "INSERT INTO book_demands VALUES('student2','001');");
        st.execute("" +
                "-- Table: book_return_records\n" +
                "CREATE TABLE book_return_records (\n" +
                "    userName VARCHAR(10) NOT NULL,\n" +
                "    bookId VARCHAR(10) NOT NULL,\n" +
                "    FOREIGN KEY (userName) REFERENCES students (userName),\n" +
                "    FOREIGN KEY (bookId) REFERENCES book_stock (bookId),\n" +
                "    PRIMARY KEY (userName) " +
                ");");
        System.out.println("table book return records created");
        st.execute("" +
                "INSERT INTO book_return_records VALUES('student1','000');");
        st.execute("" +
                "INSERT INTO book_return_records VALUES('student2','001');");
        st.execute("" +
                "-- Table: borrower_records\n" +
                "CREATE TABLE borrower_records (\n" +
                "    userName VARCHAR(10) NOT NULL,\n" +
                "    bookId VARCHAR(10) NOT NULL,\n" +
                "    FOREIGN KEY (userName) REFERENCES students (userName),\n" +
                "    FOREIGN KEY (bookId) REFERENCES book_stock (bookId),\n" +
                "    PRIMARY KEY (userName) " +
                ");");
        System.out.println("table borrower records created");
        st.execute("" +
                "INSERT INTO borrower_records VALUES('student1','000');");
        st.execute("" +
                "INSERT INTO borrower_records VALUES('student2','001'); ");
        con.close();
    }
}