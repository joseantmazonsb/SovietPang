package application.gui;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class PauseMenu {
	
	@FXML private VBox rootPane;
	@FXML private JFXButton playAgainButton;
	
	private Controller controller;
	
	public PauseMenu() {
		controller = Controller.getInstance();
	}
	
	@FXML private void handleContinue() {
		controller.setPaused(false);
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

	@FXML private void handleKeyPressed(KeyEvent event) throws IOException {
		if (event.getCode().equals(KeyCode.ESCAPE)) {
			controller.setPaused(false);
		}
	}
}
