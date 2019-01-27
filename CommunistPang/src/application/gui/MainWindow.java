package application.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import controller.Controller;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainWindow implements Initializable{
	@FXML private BorderPane rootPane;
	@FXML private JFXButton playButton, startButton, highscoresButton;
	@FXML private VBox playMenu;
	@FXML private JFXTextField nick;
	
	private Controller controller;
	private JFXListView<String> recordsList;
	
	public MainWindow() {
		controller = Controller.getInstance();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		rootPane.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ESCAPE)) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setHeaderText(null);
				alert.setTitle("¿Salir?");
				alert.setContentText("¿Estás seguro de que quieres salir?¿Quién eliminará a los contrarrevolucionarios entonces?");
				alert.initOwner(controller.getWindow());
				alert.getDialogPane().getStylesheets().add(getClass().getResource("style/app.css").toExternalForm());
				alert.showAndWait();
				if (alert.getResult().getButtonData().isDefaultButton()) handleExit();
			}
		});
		
		recordsList = new JFXListView<>();
		recordsList.getStyleClass().add("listView");
		nick.getStyleClass().add("textField");
		nick.setPromptText("Introduce tu nombre");
		//Bind TextField to start button to only enable button if TextField not empty
		startButton.disableProperty().bind(Bindings.createBooleanBinding(() -> nick.getText().trim().isEmpty(), nick.textProperty())); //TODO learn about this
		//Graphic glitch
		playButton.setDisableVisualFocus(true);
	}
	
	@FXML private void handlePlay() {
		nick.clear();
		((BorderPane) controller.getWindow().getScene().getRoot()).setCenter(playMenu);
	}
	
	@FXML private void handleStartGame() {
		System.out.println("Player '" + nick.getText() + "' is now playing.");
		controller.setCurrentPlayer(nick.getText());
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setTitle("¡A matar revisionistas!");
		alert.setContentText("Utiliza las flechas horizontales para moverte por el mapa y dispara presionando la barra espaciadora. La tecla 'E' "
				+ "te permitirá matar más trotskos a su debido tiempo ;)");
		alert.initOwner(controller.getWindow());
		alert.getDialogPane().getStylesheets().add(getClass().getResource("style/app.css").toExternalForm());
		alert.showAndWait();
		try {
			rootPane.setLeft(null);
			rootPane.setCenter(FXMLLoader.load(getClass().getClassLoader().getResource("application/gui/fxml/GameWindow.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML private void handleViewHighscores() {
		if (recordsList.getItems().isEmpty()) {
			Label title = new Label("Todavía no se ha registrado ninguna puntuación.");
			title.getStyleClass().add("italicBodyText");
			((BorderPane) controller.getWindow().getScene().getRoot()).setCenter(title);
		}
		else {
			HBox center = new HBox();
			center.setPadding(new Insets(20,20,20,20));
			center.setAlignment(Pos.CENTER);
			VBox content = new VBox(8);
			Label title = new Label("Récords");
			title.getStyleClass().add("simpleBodyText");
			content.getChildren().addAll(title, recordsList);
			center.getChildren().add(content);
			((BorderPane) controller.getWindow().getScene().getRoot()).setCenter(center);
			//TODO iterate file and show records
		}	
	}
	
	@FXML private void handleExit() {
		System.out.println("Exiting SovietPang...");
		controller.getWindow().close();
	}
}
