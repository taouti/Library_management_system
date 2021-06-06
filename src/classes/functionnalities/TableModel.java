package classes.functionnalities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TableModel {
    String bookId;
    String bookTitle;
    String bookAuthor;
    String bookPublisher;
    String bookBranch;
    private ObservableList<TableModel> list = FXCollections.observableArrayList();

    public TableModel(String bookId, String bookTitle, String bookAuthor, String bookPublisher, String bookBranch) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookPublisher = bookPublisher;
        this.bookBranch = bookBranch;
    }

    public ObservableList<TableModel> getList() {
        return list;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    public String getBookBranch() {
        return bookBranch;
    }

    public void setBookBranch(String bookBranch) {
        this.bookBranch = bookBranch;
    }
}
