package application.gui;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import controller.Controller;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import model.Collisions;
import model.Enemy;
import model.Movement;
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

		for (int i = 0; i < Controller.CHARACTER_LP; i++) {
			ImageView lp = new ImageView(stalin);
			lp.setFitWidth(30);
			lp.setFitHeight(35);
			lifepointsContainer.getChildren().add(lp);
		}
		
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
		Character player = controller.createCharacter(canvas.getWidth()/2 - 25, canvas.getHeight() - Controller.CHARACTER_HEIGHT, stalin);
		controller.setCurrentPlayer(player);
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
		
		Set<Enemy> enemies = new HashSet<>();
		//We start level 1 with 2 enemies that spawn randomly
		Random random = new Random(Calendar.getInstance().getTimeInMillis());
		double x = random.nextInt((int)(canvas.getWidth() - Controller.ENEMY_WIDTH));
		double y = random.nextInt((int) (canvas.getHeight() - Controller.ENEMY_HEIGHT - player.getHeight()*5));
		enemies.add(controller.createEnemy(x, y, trotsky));
		random.setSeed(Calendar.getInstance().getTimeInMillis());
		double previousX = x;
		double previousY = y;
		x = random.nextInt((int)(canvas.getWidth() - Controller.ENEMY_WIDTH));
		y = random.nextInt((int) (canvas.getHeight() - Controller.ENEMY_HEIGHT - player.getHeight()*5));
		enemies.add(controller.createEnemy((previousX * x) % (canvas.getWidth() - Controller.ENEMY_WIDTH), (previousY * y) % (canvas.getHeight() - Controller.ENEMY_HEIGHT - player.getHeight()*5), trotsky));
		GraphicsContext graphics = canvas.getGraphicsContext2D();
        
        new AnimationTimer()
        {
            public void handle(long currentTime) {
            	//Clear screen
            	graphics.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            	List<Projectile> projectilesToRemove = projectiles.stream().filter(p -> p.getY() + p.getHeight() < 0).collect(Collectors.toList());
            	projectilesToRemove.forEach(p -> projectiles.remove(p));
            	
            	//Update level
            	
            	//Variables to try that enemies do not show too much near of each other
            	double x = random.nextInt((int)(canvas.getWidth() - Controller.ENEMY_WIDTH));
        		double y = random.nextInt((int) (canvas.getHeight() - Controller.ENEMY_HEIGHT - player.getHeight()*5));
        		random.setSeed(Calendar.getInstance().getTimeInMillis());
        		double previousX = 1;
        		double previousY = 1;
        		int n_enemies = 0;
            	//Things to do
        		if (controller.getCurrentLevel() >= 2 && controller.getCurrentLevel() <= 3) {
        			n_enemies = random.nextInt(Controller.MAX_ENEMIES_LEVEL_2_3 - Controller.MIN_ENEMIES_LEVEL_2_3) + Controller.MIN_ENEMIES_LEVEL_2_3;
        			for (int i = 0; i < n_enemies; i++) {
        				
        			}
        			enemies.add(controller.createEnemy(x, y, trotsky));
        			x = random.nextInt((int)(canvas.getWidth() - Controller.ENEMY_WIDTH));
        			y = random.nextInt((int) (canvas.getHeight() - Controller.ENEMY_HEIGHT - player.getHeight()*5));
        			enemies.add(controller.createEnemy(x, y, trotsky));
            		enemies.add(controller.createEnemy((previousX * x) % (canvas.getWidth() - Controller.ENEMY_WIDTH), (previousY * y) % (canvas.getHeight() - Controller.ENEMY_HEIGHT - player.getHeight()*5), trotsky));
        			
            	}
            	if (controller.getCurrentLevel() >= 4 && controller.getCurrentLevel() <= 5) {
            		
				}
				if (controller.getCurrentLevel() >= 6 && controller.getCurrentLevel() <= 7) {
				            		
				            	}
				if (controller.getCurrentLevel() >= 8 && controller.getCurrentLevel() <= 9) {
					
				}

            	if (controller.getCurrentLevel() == Controller.MAX_LEVELS) {
            		
            	}
            	//Update pressed keys
            	if (keysPressed.contains(KeyCode.RIGHT) && player.getX() + player.getVx() < canvas.getWidth() - player.getWidth()/1.5) player.moveRight();
            	if (keysPressed.contains(KeyCode.LEFT) && player.getX() - player.getVx() > 0 - player.getWidth()/2.6) player.moveLeft();
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
            		//Check collisions between projectiles and enemies
            		Set<Enemy> enemiesToRemove = new HashSet<>();
            		Set<Enemy> enemiesToAdd = new HashSet<>();
            		enemies.forEach(e -> {
            			if (Collisions.rectangularCollision(p.getX(), p.getY(), p.getWidth(), p.getHeight(), e.getX(), e.getY(), e.getWidth(), e.getHeight())) {
            				//Update score
            				controller.increaseScore();
            				score.setText(Integer.toString(controller.getCurrentScore()));
            				//Add projectile to remove list
            				projectilesToRemove.add(p);
            				//Update enemy
            				if (e.reduce()) {
            					//Set new position of enemy
            					e.setX((e.getX() - e.getWidth()*2) % (canvas.getWidth() - e.getWidth()));
            					e.setY(e.getY() - e.getHeight()/2 % (canvas.getHeight() - e.getHeight()));
            					//Duplicate enemy and set new X position for it
            					Enemy newEnemy = controller.createEnemy(e);
            					newEnemy.setX((e.getX() + e.getWidth()*2) % (canvas.getWidth() - e.getWidth()));
            					//Add new enemy to add list
            					enemiesToAdd.add(newEnemy);
            					
            				}
            				else enemiesToRemove.add(e);
            			};
            		});
            		//Remove enemies with 0 LP
                	enemiesToRemove.forEach(e -> enemies.remove(e));
                	//Add new enemies created by reduction
                	enemiesToAdd.forEach(e -> enemies.add(e));
            	});
            	//Remove projectiles that hit any enemy
            	projectilesToRemove.forEach(p -> projectiles.remove(p));
                //Update player
            	graphics.drawImage(player.getImg(), player.getX(), player.getY(), player.getWidth(), player.getHeight());
            	//Update enemies
            	enemies.stream().forEach(e -> {
            		//Check current X move
            		if (e.getXCurrentMove() == null) {
                		//Decide if first x move goes to left or right
            			random.setSeed(Calendar.getInstance().getTimeInMillis());
                		if (random.nextBoolean()) {
                			e.setXCurrentMove(Movement.RIGHT);
                		}
                		else {
                			e.setXCurrentMove(Movement.LEFT);
                		}
            		}
	            	if (e.getXCurrentMove().equals(Movement.RIGHT)) {
	            		//Move right till end of screen
	            		if (e.getX() + e.getWidth()/1.1 < canvas.getWidth()) e.moveRight();
	            		else e.setXCurrentMove(Movement.LEFT);
	            	}
	            	else {
	            		//Move left till end of screen
	            		if (e.getX() > 0) e.moveLeft();
	            		else e.setXCurrentMove(Movement.RIGHT);
	            	}
            		//Check current Y move
            		if (e.getYCurrentMove().equals(Movement.DOWN)) {
            			//Move down till end of screen
            			if (e.getY() + e.getHeight() < canvas.getHeight()) e.moveDown();
            			else e.setYCurrentMove(Movement.UP);
            		}
            		else {
            			//Move up till end of screen
            			if (e.getY() > 0) e.moveUp();
            			else e.setYCurrentMove(Movement.DOWN);
            		}
            		//Draw enemies
            		graphics.drawImage(e.getImg(), e.getX(), e.getY(), e.getWidth(), e.getHeight());
            	});
            	//Check collisions between player and enemies
        		enemies.forEach(e -> {
        			if (Collisions.rectangularCollision(player.getX(), player.getY(), player.getWidth(), player.getHeight(), e.getX(), e.getY(), e.getWidth(), e.getHeight())) {
        				if (!player.reduceLP()) {
        					this.stop();
        					endGame();
        				}
        				else {
        					//Update enemy
        					e.setYCurrentMove(Movement.UP);
        					//Randomly decide if enemy goes left or right when collision
            				if (random.nextBoolean()) e.setXCurrentMove(Movement.LEFT);
            				else e.setXCurrentMove(Movement.RIGHT);
            				//Get enemy out of player radius so it only hits the player once
            				e.setY(player.getY() - e.getHeight());
        					//Reduce player's LP in gui
        					lifepointsContainer.getChildren().clear();
        					for (int i = 0; i < player.getLp(); i++) {
        						ImageView lp = new ImageView(stalin);
            					lp.setFitWidth(30);
            					lp.setFitHeight(35);
        						lifepointsContainer.getChildren().add(lp);
        					}
        				}
        			};
        		});
        		//Check if there are enemies. If not, go to next level
        		if (enemies.isEmpty()) {
        			if (!controller.nextLevel()) {
        				//Player won the game
        				endGame();
        			}
        		}
            }
        }.start();
	}
	private void endGame() {
		//TODO register score
		if (controller.getCurrentPlayer().getLp() > 0) {
			//TODO load winner screen
		}
		else {
			//TODO load loser screen
			try {
				rootPane.setTop(null);
				rootPane.setCenter(FXMLLoader.load(getClass().getClassLoader().getResource("application/gui/fxml/LoserScreen.fxml")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
