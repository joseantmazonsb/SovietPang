package application.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public class LoserScreen implements Initializable {
	
	@FXML private BorderPane rootPane;
	@FXML private Label score;
	@FXML private JFXButton playAgainButton;
	
	private Controller controller;
	
	public LoserScreen() {
		controller = Controller.getInstance();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rootPane.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
			if (e.getCode() == KeyCode.SPACE) e.consume();
		});
		//Show score
		score.setText("YOUR SCORE: " + controller.getCurrentScore());
		//Clear score from controller
		controller.setCurrentScore(0);
	}
	
	@FXML private void handlePlayAgain() {
		try {
			rootPane.setTop(null);
			rootPane.setCenter(FXMLLoader.load(getClass().getClassLoader().getResource("application/gui/fxml/GameWindow.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML private void handleGoBack() {
		try {
			controller.getWindow().setScene(new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("application/gui/fxml/MainWindow.fxml"))));
			controller.getWindow().setFullScreen(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML private void handleExit() {
		controller.getWindow().close();
	}

}
