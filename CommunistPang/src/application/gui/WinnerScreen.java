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
import javafx.scene.layout.StackPane;

public class WinnerScreen implements Initializable {
	
	@FXML private BorderPane rootPane;
	@FXML private Label score;
	@FXML private JFXButton playAgainButton;
	
	private Controller controller;
	
	public WinnerScreen() {
		controller = Controller.getInstance();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rootPane.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
			if (e.getCode() == KeyCode.SPACE) e.consume();
		});
		//Show score
		score.setText("YOUR SCORE: " + controller.getCurrentScore());
		//Clear score and level from controller
		controller.setCurrentLevel(1);
		controller.setCurrentScore(0);
	}
	
	@FXML private void handlePlayAgain() {
		try {
			((StackPane) controller.getWindow().getScene().getRoot()).getChildren().clear();
			((StackPane) controller.getWindow().getScene().getRoot()).getChildren().add(FXMLLoader.load(getClass().getClassLoader().getResource("application/gui/fxml/GameWindow.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML private void handleGoBack() {
		try {
			((StackPane) controller.getWindow().getScene().getRoot()).getChildren().clear();
			((StackPane) controller.getWindow().getScene().getRoot()).getChildren().add(FXMLLoader.load(getClass().getClassLoader().getResource("application/gui/fxml/MainWindow.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML private void handleExit() {
		controller.getWindow().close();
	}

}
