package application;

import database.DatabaseHandler;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;


public class Main extends Application {

    public static Scene scene;
    public static double xOffset;
    public static double yOffset;

    @Override
    public void start(Stage stage) throws IOException {
        stage.initStyle(StageStyle.UNDECORATED);
        stage.centerOnScreen();
        Main.setRoot(stage,"LoginUi","LoginStyle");
    	//scene.getStylesheets().add(Main.class.getResource("/uiDesigns/LoginStyle.css").toExternalForm());

    }

    public static void setRoot(Stage current, String fxml, String css) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/uiDesigns/"+ fxml + ".fxml"));
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                xOffset = mouseEvent.getSceneX();
                yOffset = mouseEvent.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                current.setX(mouseEvent.getScreenX() - xOffset);
                current.setY(mouseEvent.getScreenY() - yOffset);
            }
        });
        Scene scene = new Scene(root);
        //scene.getStylesheets().add(Main.class.getResource("/uiStyles/"+ css + ".css").toExternalForm());
        current.setScene(scene);
        current.centerOnScreen();
        current.show();
    }
    
    public static void main(String[] args) throws IOException {
        File data = new File("data.db");
        if (!data.exists()){
            data.createNewFile();
            System.out.println("file does not exist!,\n new file data.db has been created");
        } else {
            data.delete();
            data.createNewFile();
            System.out.println("File already exists!,\n deleting file...\nnew file data.db created");
        }
        launch();
    }
}


























/*
package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/uiDesigns/LoginUi.fxml"));
			Scene scene = new Scene(root);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
*/
