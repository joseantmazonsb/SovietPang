package application.gui;

import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import controller.Controller;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.Character;
import model.Projectile;

public class GameWindow implements Initializable{
	
	@FXML private GridPane topBar;
	@FXML private BorderPane rootPane;
	@FXML private Canvas canvas;
	@FXML private Label score, level;
	@FXML private HBox lifepointsContainer; //Contains Stalins that count how many LP the player has
	
	private Controller controller;
	private Image stalin;
	private Image trotsky;
	private List<Image> projectileImgs;
	
	
	public GameWindow() {
		controller = Controller.getInstance();
		stalin = new Image(getClass().getClassLoader().getResource("application/resources/stalin.png").toExternalForm());
		trotsky = new Image(getClass().getClassLoader().getResource("application/resources/trotsky.png").toExternalForm());
		projectileImgs = new LinkedList<>();
		projectileImgs.add(new Image(getClass().getClassLoader().getResource("application/resources/bullet1.png").toExternalForm()));
		projectileImgs.add(new Image(getClass().getClassLoader().getResource("application/resources/bullet2.png").toExternalForm()));
		projectileImgs.add(new Image(getClass().getClassLoader().getResource("application/resources/bullet3.png").toExternalForm()));
		projectileImgs.add(new Image(getClass().getClassLoader().getResource("application/resources/bullet4.png").toExternalForm()));
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
		//Create player
		Character player = controller.createCharacter(canvas.getWidth()/2 -25, canvas.getHeight()-85, stalin);
		//Create enemies
	
		//Create list of projectiles
		List<Projectile> projectiles = new LinkedList<>();
		//Gameplay logic
		
		Set<KeyCode> keysPressed = new HashSet<>();
				
		controller.getWindow().getScene().setOnKeyPressed(e -> {
			
    		if (e.getCode() == KeyCode.RIGHT) keysPressed.add(e.getCode());
    		if (e.getCode() == KeyCode.LEFT) keysPressed.add(e.getCode());
    		if (e.getCode() == KeyCode.SPACE) {
    			keysPressed.add(e.getCode());
    		}
    		if (e.getCode() == KeyCode.ESCAPE) keysPressed.add(e.getCode());
    	
    	});
		
		controller.getWindow().getScene().setOnKeyReleased(e -> {
			
    		if (e.getCode() == KeyCode.RIGHT) keysPressed.remove(e.getCode());
    		if (e.getCode() == KeyCode.LEFT) keysPressed.remove(e.getCode());	
    		if (e.getCode() == KeyCode.SPACE) keysPressed.remove(e.getCode());
    		if (e.getCode() == KeyCode.ESCAPE) keysPressed.remove(e.getCode());
    		
    	});
		
		GraphicsContext graphics = canvas.getGraphicsContext2D();
        
        new AnimationTimer()
        {
            public void handle(long currentTime) {
            	//Clear screen
            	graphics.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            	List<Projectile> aux = projectiles.stream().filter(p -> p.getY() + p.getHeight() < 0).collect(Collectors.toList());
            	aux.forEach(p -> projectiles.remove(p));
            	//Update pressed keys
            	if (keysPressed.contains(KeyCode.RIGHT) && player.getX() + player.getVx() < canvas.getWidth() - player.getWidth()/2) player.moveRight();
            	if (keysPressed.contains(KeyCode.LEFT) && player.getX() - player.getVx() > 0 - player.getWidth()/2) player.moveLeft();
            	if (keysPressed.contains(KeyCode.SPACE)) {
            		projectiles.add(controller.createProjectile(player.getX()+player.getWidth()/3, player.getY(), projectileImgs));
            		keysPressed.remove(KeyCode.SPACE); //no shooting when holding down space bar
            	}
            	if (keysPressed.contains(KeyCode.ESCAPE)) {
            		//TODO pause
            		controller.getWindow().close();
            	}
            	
            	//Update projectiles
            	projectiles.stream().forEach(p -> {
            		p.move();
            		graphics.drawImage(p.getCurrentImg(), p.getX(), p.getY(), p.getWidth(), p.getHeight());
            	});
                //Update player
            	graphics.drawImage(player.getImg(), player.getX(), player.getY(), player.getWidth(), player.getHeight());
            }
        }.start();
	}
}
