package application;
	
import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;


public class App extends Application {
	
	private Controller controller;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			System.out.println("Starting SovietPang...");
			controller = Controller.getInstance();
			primaryStage.setFullScreen(true);
			primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH); //Makes unable to exit full screen
			primaryStage.setResizable(false);
			primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("application/gui/fxml/MainWindow.fxml"))));
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
