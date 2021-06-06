package classes.users.student;

import classes.users.User;

public class Student extends User {

    private String userType = "STUDENTS";

    public Student() {
        super();
        this.setUserType("STUDENT");
    }

    public Student(String name, String password, String id) {
        super(name, password, id);
        this.setUserType("STUDENT");
    }

    public void demandBook() {

    }

    public void returnBook() {

    }

    public void checkBookList() {

    }

    public void deleteAccount() {

    }
}
