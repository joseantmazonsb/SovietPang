package application.gui;

import java.net.URL;
import java.util.ResourceBundle;

import controller.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class GameWindow implements Initializable{
	
	@FXML private GridPane topBar;
	@FXML private BorderPane rootPane;
	@FXML private Canvas canvas;
	@FXML private Label score, level;
	@FXML private HBox lifepointsContainer; //Contains Stalins that count how many LP the player has
	
	private Controller controller;
	private Image stalin;
	private Image trotsky;
	
	
	public GameWindow() {
		controller = Controller.getInstance();
		stalin = new Image(getClass().getClassLoader().getResource("application/resources/stalin.png").toExternalForm());
		trotsky = new Image(getClass().getClassLoader().getResource("application/resources/trotsky.png").toExternalForm());
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ImageView stalin1 = new ImageView(stalin);
		stalin1.setFitWidth(30);
		stalin1.setFitHeight(35);
		ImageView stalin2 = new ImageView(stalin);
		stalin2.setFitWidth(30);
		stalin2.setFitHeight(35);
		ImageView stalin3 = new ImageView(stalin);
		stalin3.setFitWidth(30);
		stalin3.setFitHeight(35);
		lifepointsContainer.getChildren().addAll(stalin1, stalin2, stalin3);
		score.setText("0");
		level.setText("01");
		//Set canvas' size
		canvas.setWidth(controller.getWindow().getWidth());
		//Add a listener to the topBar height property to set canvas's height when topBar height is set correctly
		ChangeListener<Number> heightListener = new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				canvas.setHeight(controller.getWindow().getHeight() - newValue.doubleValue());
				 //This listener will remove itself from height listeners of topBar once it has handled the fist change
				topBar.heightProperty().removeListener(this);
				//And then starts the game itself
				gameLogic();
			}
		};
		topBar.heightProperty().addListener(heightListener);
	}
	
	private void gameLogic() {
		GraphicsContext graphics = canvas.getGraphicsContext2D();
		graphics.drawImage(stalin, canvas.getWidth()/2 - 25, canvas.getHeight()-85, 70, 85);
	}

}
