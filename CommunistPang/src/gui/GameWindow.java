package gui;

import java.net.URL;
import java.util.ResourceBundle;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class GameWindow implements Initializable{
	
	@FXML private Canvas canvas;
	@FXML private Label score, level;
	@FXML private HBox lifepointsContainer; //Contains Stalins that count how many LP the player has
	
	private Controller controller;
	
	public GameWindow() {
		controller = Controller.getInstance();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Image stalin = new Image(getClass().getResource("../resources/stalin.png").toExternalForm());
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
		canvas.setWidth(controller.getWindow().getWidth());
		canvas.setHeight(controller.getWindow().getHeight());
		GraphicsContext graphics = canvas.getGraphicsContext2D();
		graphics.drawImage(stalin, controller.getWindow().getWidth()/2 - 25, controller.getWindow().getHeight()-200, 70, 85);
	}

}
