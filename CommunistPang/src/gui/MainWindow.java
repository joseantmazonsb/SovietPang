package gui;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import controller.Controller;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setTitle("¡A matar revisionistas!");
		alert.setContentText("Utiliza las flechas horizontales para moverte por el mapa y dispara presionando la barra espaciadora. La tecla 'E' "
				+ "te permitirá matar más trotskos a su debido tiempo ;)");
		alert.initOwner(controller.getWindow());
		alert.getDialogPane().getStylesheets().add(getClass().getResource("style/app.css").toExternalForm());
		alert.showAndWait();
		controller.setCurrentPlayer(nick.getText());
		initGame();
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
			ImageView close = new ImageView(new Image(getClass().getResource("../resources/close_button.png").toExternalForm()));
			close.setFitWidth(13);
			close.setFitHeight(13);
			close.setCursor(Cursor.HAND);
			close.setOnMouseClicked(e -> ((BorderPane) controller.getWindow().getScene().getRoot()).setCenter(null));
			center.getChildren().add(close);
			((BorderPane) controller.getWindow().getScene().getRoot()).setCenter(center);
			//TODO iterate file and show records
		}	
	}
	
	private void initGame() {
		rootPane.setLeft(null); //Clear left
		//Create and set canvas on center
		Canvas canvas = new Canvas(controller.getWindow().getWidth(), controller.getWindow().getHeight());
		GraphicsContext drawer = canvas.getGraphicsContext2D();
		rootPane.getStyleClass().clear();
		rootPane.getStyleClass().add("gameLayout");
		rootPane.setCenter(canvas);
		//Add top bar 
		drawer.drawImage(new Image(getClass().getResource("../resources/trotsky.png").toExternalForm()), 20, 5, 40, 45);
		//Game logic
		
	}
	
}
