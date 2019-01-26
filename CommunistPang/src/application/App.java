package application;
	
import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application {
	
	private Controller controller;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			controller = Controller.getInstance();
			primaryStage.setMaximized(true);
			primaryStage.setResizable(false);
			primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../gui/fxml/MainWindow.fxml"))));
			primaryStage.show();
			controller.setWindow(primaryStage);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
