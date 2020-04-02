package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

/**
 * This class is to handle the creating a window for the GUI and start the GUI program.. * 
 * @author Patrick Li
 * @version 1.0
 */
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			GridPane root =  FXMLLoader.load(getClass().getResource("MainPage.fxml"));
			Scene scene = new Scene(root,600,400);		
			primaryStage.setScene(scene);
			primaryStage.setTitle("Pizza Order System");
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}
