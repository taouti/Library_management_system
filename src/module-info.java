module LibraryManagementSystem {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.sql;
    requires com.jfoenix;
    requires java.xml.crypto;

    opens application;
    opens uiControllers;
    opens uiDesigns;
    opens uiStyles;
}