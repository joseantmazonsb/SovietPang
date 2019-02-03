package application.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.Character;
import model.Collisions;
import model.Enemy;
import model.Movement;
import model.Projectile;

public class GameWindow implements Initializable {

	@FXML
	private GridPane topBar;
	@FXML
	private BorderPane rootPane;
	@FXML
	private Canvas canvas;
	@FXML
	private Label score, level;
	@FXML
	private HBox lifepointsContainer; // Contains Stalins that count how many LP the player has
	@FXML private ProgressBar ultiProgressBar;
	
	private Controller controller;
	private Image stalin, trotsky, stalinHit, e_key;
	private List<Image> projectileImgs;
	boolean playerHit; // Stores if player was hit by an enemy
	double nFramesHit; // Number of frames we show hit character since it has been hit
	Character player; // player
	List<Projectile> projectiles; // projectiles
	List<Projectile> ultiProjectiles; // projectiles when ulti
	Set<Enemy> enemies; // enemies
	Set<KeyCode> keysPressed; // keys currently pressed
	Random random; // random generator
	AnimationTimer animator;
	GraphicsContext graphics;
	double ultiScore; //stores remaining score to be able to use ulti
	boolean usingUlti; //whether user is using ulti
	double framesUlti; //remaining frames of ulti

	public GameWindow() {
		controller = Controller.getInstance();
		stalin = new Image(
				getClass().getClassLoader().getResource("application/resources/stalin.png").toExternalForm());
		stalinHit = new Image(
				getClass().getClassLoader().getResource("application/resources/stalin_hit.png").toExternalForm());
		trotsky = new Image(
				getClass().getClassLoader().getResource("application/resources/trotsky.png").toExternalForm());
		projectileImgs = new LinkedList<>();
		projectileImgs.add(new Image(
				getClass().getClassLoader().getResource("application/resources/bullet1.png").toExternalForm()));
		projectileImgs.add(new Image(
				getClass().getClassLoader().getResource("application/resources/bullet2.png").toExternalForm()));
		projectileImgs.add(new Image(
				getClass().getClassLoader().getResource("application/resources/bullet3.png").toExternalForm()));
		projectileImgs.add(new Image(
				getClass().getClassLoader().getResource("application/resources/bullet4.png").toExternalForm()));
		e_key = new Image(
				getClass().getClassLoader().getResource("application/resources/key_e.png").toExternalForm());
		playerHit = false;
		nFramesHit = 0;
		player = controller.createCharacter(0, 0, stalin);
		projectiles = new LinkedList<>();
		ultiProjectiles = new LinkedList<>();
		keysPressed = new HashSet<>();
		enemies = new HashSet<>();
		random = new Random(Calendar.getInstance().getTimeInMillis());
		ultiScore = 0;
		framesUlti = Controller.N_FRAMES_ULTI_AVAILABLE;
		usingUlti = false;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// listener for pause
		controller.addBooleanListener(l -> {
			Pane pauseMenu;
			try {
				pauseMenu = FXMLLoader
						.load(getClass().getClassLoader().getResource("application/gui/fxml/PauseMenu.fxml"));
				if (l.getNewValue()) {
					((StackPane) controller.getWindow().getScene().getRoot()).getChildren().add(pauseMenu);
					animator.stop();
				} else {
					for (Node n : ((StackPane) controller.getWindow().getScene().getRoot()).getChildren()) {
						if (n instanceof VBox) {
							((StackPane) controller.getWindow().getScene().getRoot()).getChildren().remove(n);
							break;
						}
					}
					animator.start();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		for (int i = 0; i < Controller.CHARACTER_LP; i++) {
			ImageView lp = new ImageView(stalin);
			lp.setFitWidth(30);
			lp.setFitHeight(35);
			lifepointsContainer.getChildren().add(lp);
		}

		score.setText("0");
		level.setText("1");
		// Set canvas' size
		canvas.setWidth(controller.getWindow().getWidth());
		graphics = canvas.getGraphicsContext2D();
		// Add a listener to the topBar height property to set canvas's height when
		// topBar height is set correctly
		ChangeListener<Number> heightListener = new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				canvas.setHeight(controller.getWindow().getHeight() - newValue.doubleValue());
				// This listener will remove itself from height listeners of topBar once it has
				// handled the fist change
				topBar.heightProperty().removeListener(this);
				// Set gameLogic
				gameLogic();
			}
		};
		topBar.heightProperty().addListener(heightListener);

	}

	private void setKeysLogic() {
		controller.getWindow().getScene().setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.RIGHT)
				keysPressed.add(e.getCode());
			if (e.getCode() == KeyCode.LEFT)
				keysPressed.add(e.getCode());
			if (e.getCode() == KeyCode.SPACE)
				keysPressed.add(e.getCode());
			if (e.getCode() == KeyCode.E)
				keysPressed.add(e.getCode());
			if (e.getCode() == KeyCode.ESCAPE)
				keysPressed.add(e.getCode());
			}
		);

		controller.getWindow().getScene().setOnKeyReleased(e -> {
			if (e.getCode() == KeyCode.RIGHT)
				keysPressed.remove(e.getCode());
			if (e.getCode() == KeyCode.LEFT)
				keysPressed.remove(e.getCode());
			if (e.getCode() == KeyCode.SPACE)
				keysPressed.remove(e.getCode());
			if (e.getCode() == KeyCode.E)
				keysPressed.remove(e.getCode());
			if (e.getCode() == KeyCode.ESCAPE)
				keysPressed.remove(e.getCode());
			}
		);
	}
	//for pause
	private void disableKeysLogic() {
		controller.getWindow().getScene().setOnKeyPressed(e -> e.consume());
		controller.getWindow().getScene().setOnKeyReleased(e -> e.consume());
	}
	
	private void handleProjectilesCollisions(List<Projectile> projectiles) {
		List<Projectile> projectilesToRemove = projectiles.stream().filter(p -> p.getY() + p.getHeight() < 0)
				.collect(Collectors.toList());
		projectiles.stream().forEach(p -> {
			p.move();
			graphics.drawImage(p.getCurrentImg(), p.getX(), p.getY(), p.getWidth(), p.getHeight());
			// Check collisions between projectiles and enemies //TODO improve
			Set<Enemy> enemiesToRemove = new HashSet<>();
			Set<Enemy> enemiesToAdd = new HashSet<>();
			enemies.forEach(e -> {
				if (Collisions.rectangularCollision(p.getX(), p.getY(), p.getWidth(), p.getHeight(), e.getX(),
						e.getY(), e.getWidth(), e.getHeight())) {
					// Update score
					controller.increaseScore();
					score.setText(Integer.toString(controller.getCurrentScore()));
					
					if (!ultiProjectiles.contains(p)) {
						ultiScore += Controller.SCORE_FOR_HITTING_ENEMIES;
						ultiProgressBar.setProgress(ultiProgressBar.getProgress() + 0.1);
					}
					// Add projectile to remove list
					projectilesToRemove.add(p);
					// Update enemy
					if (e.reduce()) {
						// Set new position of enemy
						double newX = (e.getX() - e.getWidth() * 2) % canvas.getWidth();
						if (newX < 0) newX = 0;
						e.setX(newX);
						e.setY(e.getY() - e.getHeight() / 2 % (canvas.getHeight() - e.getHeight()));
						if (e.getY() < 0) e.setY(0);
						// Duplicate enemy and set new X position for it
						Enemy newEnemy = controller.createEnemy(e);
						newEnemy.setX((e.getX() + e.getWidth() * 2) % (canvas.getWidth() - e.getWidth()));
						// Add new enemy to add list
						enemiesToAdd.add(newEnemy);

					} else
						enemiesToRemove.add(e);
				}
				;
			});
			// Remove enemies with 0 LP
			enemiesToRemove.forEach(e -> enemies.remove(e));
			// Add new enemies created by reduction
			enemiesToAdd.forEach(e -> enemies.add(e));
		});
		// Remove projectiles that hit any enemy and went out of screen
		projectilesToRemove.forEach(p -> projectiles.remove(p));
	}
	
	private void updateProjectiles() {
		if (!projectiles.isEmpty()) handleProjectilesCollisions(projectiles);
		if (!ultiProjectiles.isEmpty()) handleProjectilesCollisions(ultiProjectiles);
	}
	
	private void updatePlayer() {
		
		if (player.isUltiAvailable()) {
			graphics.drawImage(e_key, 100, 100, 100, 100);
		}
		
		if (ultiScore == Controller.SCORE_TO_ULTI) {
			ultiScore = 0;
			player.setUltiAvailable(true);
		}
		
		if (usingUlti) {
			if (framesUlti > 0) {
				//shoot
				ultiProjectiles.add(controller.createProjectile(player.getX() + player.getWidth() / 3, player.getY(),
						projectileImgs));
				Projectile left = controller.createProjectile(player.getX() + player.getWidth() / 3, player.getY(), projectileImgs);
				left.setVx(left.getVx() - Controller.PROJECTILE_Y_AXIS_SPEED/10);
				ultiProjectiles.add(left);
				Projectile right = controller.createProjectile(player.getX() + player.getWidth() / 3, player.getY(), projectileImgs);
				right.setVx(right.getVx() + Controller.PROJECTILE_Y_AXIS_SPEED/10);
				ultiProjectiles.add(right);
				//decrement ttl of ulti
				framesUlti--; 
			}
			if (framesUlti == 0) {
				usingUlti = false;
				framesUlti = Controller.N_FRAMES_ULTI_AVAILABLE;
			}
		}
		
		if (keysPressed.contains(KeyCode.RIGHT)
				&& player.getX() + player.getVx() < canvas.getWidth() - player.getWidth() / 1.5)
			player.moveRight();
		if (keysPressed.contains(KeyCode.LEFT) && player.getX() - player.getVx() > 0 - player.getWidth() / 2.6)
			player.moveLeft();
		if (keysPressed.contains(KeyCode.SPACE)) {
			projectiles.add(controller.createProjectile(player.getX() + player.getWidth() / 3, player.getY(),
					projectileImgs));
			keysPressed.remove(KeyCode.SPACE); // no shooting when holding down space bar
		}
		if (keysPressed.contains(KeyCode.E)) {
			if (player.isUltiAvailable()) {
				player.setUltiAvailable(false);
				usingUlti = true;
				ultiProgressBar.setProgress(0);
			}
		}
		if (keysPressed.contains(KeyCode.ESCAPE)) {
			keysPressed.remove(KeyCode.ESCAPE);
			disableKeysLogic();
			controller.setPaused(true);
		}
	}
	
	private void updateEnemies() {
		enemies.stream().forEach(e -> {
			// Check current X move
			if (e.getXCurrentMove() == null) {
				// Decide if first x move goes to left or right
				if (random.nextBoolean()) {
					e.setXCurrentMove(Movement.RIGHT);
				} else {
					e.setXCurrentMove(Movement.LEFT);
				}
			}
			if (e.getXCurrentMove().equals(Movement.RIGHT)) {
				// Move right till end of screen
				if (e.getX() + e.getWidth() / 1.1 < canvas.getWidth())
					e.moveRight();
				else
					e.setXCurrentMove(Movement.LEFT);
			} else {
				// Move left till end of screen
				if (e.getX() > 0)
					e.moveLeft();
				else
					e.setXCurrentMove(Movement.RIGHT);
			}
			// Check current Y move
			if (e.getYCurrentMove().equals(Movement.DOWN)) {
				// Move down till end of screen
				if (e.getY() + e.getHeight() < canvas.getHeight())
					e.moveDown();
				else
					e.setYCurrentMove(Movement.UP);
			} else {
				// Move up till end of screen
				if (e.getY() > 0)
					e.moveUp();
				else
					e.setYCurrentMove(Movement.DOWN);
			}
			// Draw enemies
			graphics.drawImage(e.getImg(), e.getX(), e.getY(), e.getWidth(), e.getHeight());
		});
	}
	
	private void handleCollisions() {
		// Check collisions between enemies TODO improve
		/*
		enemies.forEach(e1 -> {
			enemies.forEach(e2 -> {
				if (!e1.equals(e2) && Collisions.rectangularCollision(e1.getX(), e1.getY(), e1.getWidth(), e1.getHeight(), e2.getX(), e2.getY(), e2.getWidth(), e2.getHeight())) {
					if (random.nextBoolean()) e1.reverseXMovement();
					else e2.reverseXMovement();
				}
			});
		});
		*/
		// Check collisions between player and enemies //TODO improve
		if (playerHit && nFramesHit == 0) { // If player has been hit by enemy, but has been nFramesHit without
											// any other hit, we show normal character
			playerHit = false;
		}
		enemies.forEach(e -> {
			if (Collisions.rectangularCollision(player.getX(), player.getY(), player.getWidth(),
					player.getHeight(), e.getX(), e.getY(), e.getWidth(), e.getHeight())) {
				// Update enemy
				e.setYCurrentMove(Movement.UP);
				// Randomly decide if enemy goes left or right when collision
				if (random.nextBoolean()) e.setXCurrentMove(Movement.LEFT);
				else e.setXCurrentMove(Movement.RIGHT);
				// Get enemy out of player radius so it only hits the player once
				e.setY(player.getY() - e.getHeight());
				//If player doesn't have hit indicator, reduce LP (makes player immune while indicator of hit is set) 
				if (nFramesHit == 0) {
					if (!player.reduceLP()) {
						endGame();
					}
					else {
						// Reduce player's LP in gui
						lifepointsContainer.getChildren().clear();
						for (int i = 0; i < player.getLp(); i++) {
							ImageView lp = new ImageView(stalin);
							lp.setFitWidth(30);
							lp.setFitHeight(35);
							lifepointsContainer.getChildren().add(lp);
						}
						playerHit = true;
						nFramesHit = Controller.N_FRAMES_SHOW_HIT_CHARACTER;
					}
				}
			} 
			else {
				if (nFramesHit > 0) nFramesHit--;
			}
		});
		if (playerHit) {
			// Change img of player to inform of collision
			player.setImg(stalinHit);
		} else
			player.setImg(stalin);
		// Update player
		graphics.drawImage(player.getImg(), player.getX(), player.getY(), player.getWidth(),
				player.getHeight());
	}
	
	private void updateLevel() {
		// Check if there are enemies. If not, go to next level
		if (enemies.isEmpty()) {
			if (!controller.nextLevel()) {
				// Player won the game
				endGame();
			} else {
				//clear projectiles on screen
				projectiles.clear();
				ultiProjectiles.clear();
				//set next level text
				level.setText(Integer.toString(controller.getCurrentLevel()));
				// Next level enemies
				// Variables to try that enemies do not show too much near of each other
				double x = random.nextInt((int) (canvas.getWidth() - Controller.ENEMY_WIDTH));
				double y = random
						.nextInt((int) (canvas.getHeight() - Controller.ENEMY_HEIGHT - player.getHeight() * 5));
				random.setSeed(Calendar.getInstance().getTimeInMillis());
				double previousX = 1;
				double previousY = 1;
				int n_enemies = 0;
				// Things to do
				if (controller.getCurrentLevel() >= 2 && controller.getCurrentLevel() <= 3) {
					n_enemies = random
							.nextInt(Controller.MAX_ENEMIES_LEVEL_2_3 - Controller.MIN_ENEMIES_LEVEL_2_3)
							+ Controller.MIN_ENEMIES_LEVEL_2_3;
				}
				if (controller.getCurrentLevel() >= 4 && controller.getCurrentLevel() <= 5) {
					n_enemies = random
							.nextInt(Controller.MAX_ENEMIES_LEVEL_4_5 - Controller.MIN_ENEMIES_LEVEL_4_5)
							+ Controller.MIN_ENEMIES_LEVEL_4_5;
				}
				if (controller.getCurrentLevel() >= 6 && controller.getCurrentLevel() <= 7) {
					n_enemies = random
							.nextInt(Controller.MAX_ENEMIES_LEVEL_6_7 - Controller.MIN_ENEMIES_LEVEL_6_7)
							+ Controller.MIN_ENEMIES_LEVEL_6_7;
				}
				if (controller.getCurrentLevel() >= 8 && controller.getCurrentLevel() <= 9) {
					n_enemies = random
							.nextInt(Controller.MAX_ENEMIES_LEVEL_8_9 - Controller.MIN_ENEMIES_LEVEL_8_9)
							+ Controller.MIN_ENEMIES_LEVEL_8_9;
				}

				if (controller.getCurrentLevel() == Controller.MAX_LEVELS) {
					n_enemies = random
							.nextInt(Controller.MAX_ENEMIES_LEVEL_10 - Controller.MIN_ENEMIES_LEVEL_10)
							+ Controller.MIN_ENEMIES_LEVEL_10;
					// add boss
					enemies.add(controller.createBoss(
							(previousX * x) % (canvas.getWidth() - Controller.ENEMY_WIDTH),
							(previousY * y) % (canvas.getHeight() - Controller.ENEMY_HEIGHT
									- controller.getCurrentPlayer().getHeight() * 5),
							trotsky));
					previousX = x;
					previousY = y;
					x = random.nextInt((int) (canvas.getWidth() - Controller.ENEMY_WIDTH));
					y = random.nextInt((int) (canvas.getHeight() - Controller.ENEMY_HEIGHT
							- controller.getCurrentPlayer().getHeight() * 5));
				}
				for (int i = 0; i < n_enemies; i++) {
					enemies.add(controller.createEnemy(
							(previousX * x) % (canvas.getWidth() - Controller.ENEMY_WIDTH),
							(previousY * y) % (canvas.getHeight() - Controller.ENEMY_HEIGHT
									- controller.getCurrentPlayer().getHeight() * 5),
							trotsky));
					previousX = x;
					previousY = y;
					x = random.nextInt((int) (canvas.getWidth() - Controller.ENEMY_WIDTH));
					y = random.nextInt((int) (canvas.getHeight() - Controller.ENEMY_HEIGHT
							- controller.getCurrentPlayer().getHeight() * 5));
				}
			}
		}
	}
	
	private void gameLogic() {
		// Set start coordinates of player
		player.setX(canvas.getWidth() / 2 - 25);
		player.setY(canvas.getHeight() - Controller.CHARACTER_HEIGHT);
		//Set keys logic
		setKeysLogic();
		// We start level 1 with 2 enemies that spawn randomly
		double x = random.nextInt((int) (canvas.getWidth() - Controller.ENEMY_WIDTH));
		double y = random.nextInt((int) (canvas.getHeight() - Controller.ENEMY_HEIGHT - player.getHeight() * 5));
		enemies.add(controller.createEnemy(x, y, trotsky));
		random.setSeed(Calendar.getInstance().getTimeInMillis());
		double previousX = x;
		double previousY = y;
		x = random.nextInt((int) (canvas.getWidth() - Controller.ENEMY_WIDTH));
		y = random.nextInt((int) (canvas.getHeight() - Controller.ENEMY_HEIGHT - player.getHeight() * 5));
		enemies.add(controller.createEnemy((previousX * x) % (canvas.getWidth() - Controller.ENEMY_WIDTH),
				(previousY * y) % (canvas.getHeight() - Controller.ENEMY_HEIGHT - player.getHeight() * 5), trotsky));
		// set animator
		animator = new AnimationTimer() {

			@Override
			public void handle(long now) {
				setKeysLogic(); //TODO no idea why but works for pause
				// Clear screen
				graphics.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				// Update player
				updatePlayer();
				// Update projectiles
				updateProjectiles();
				// Update enemies
				updateEnemies();
				//Check and handle collisions between player and enemies and between enemies and other enemies
				handleCollisions();
				//Update level
				updateLevel();
			}
		};
		// start
		animator.start();
	}

	private void endGame() {
		//stop animation
		animator.stop();
		//remove listeners of controller
		controller.clearBooleanListeners();
		//disable key logic
		disableKeysLogic();
		// register score
		File file = new File("Highscores.txt");
		File fileNew = new File("tmp.txt");
		try {
			if (!file.exists()) {
				FileWriter writer = new FileWriter(file, false); // Always overwrite
				writer.write(controller.getPlayerName() + " - " + controller.getCurrentScore() + "\n");
				writer.close();
			} else {
				FileWriter writer = new FileWriter(fileNew, false); // Always overwrite
				// Read current scores to store this one in order
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String line = reader.readLine();
				int nLines = 1;
				boolean newScoreWrited = false;
				while (line != null && nLines <= Controller.MAX_NUMBER_OF_REGISTERED_SCORES) {
					String[] score = line.split(" - ");
					if (controller.getCurrentScore() > Integer.parseInt(score[1])) {
						if (!newScoreWrited) {
							writer.write(controller.getPlayerName() + " - " + controller.getCurrentScore() + "\n");
							nLines++;
							newScoreWrited = true;
						}
						writer.write(score[0] + " - " + score[1] + "\n");
						nLines++;
					} else {
						writer.write(score[0] + " - " + score[1] + "\n");
						nLines++;
					}

					line = reader.readLine();
				}
				// if current score is worse that all scores registered and there is still room
				// for it to register
				if (!newScoreWrited && nLines <= Controller.MAX_NUMBER_OF_REGISTERED_SCORES) {
					writer.write(controller.getPlayerName() + " - " + controller.getCurrentScore() + "\n");
				}

				reader.close();
				writer.close();
				file.delete();
				fileNew.renameTo(new File("Highscores.txt"));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		if (controller.getCurrentPlayer().getLp() > 0) {
			try {
				((StackPane) controller.getWindow().getScene().getRoot()).getChildren().clear();
				((StackPane) controller.getWindow().getScene().getRoot()).getChildren().add(FXMLLoader
						.load(getClass().getClassLoader().getResource("application/gui/fxml/WinnerScreen.fxml")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				((StackPane) controller.getWindow().getScene().getRoot()).getChildren().clear();
				((StackPane) controller.getWindow().getScene().getRoot()).getChildren().add(FXMLLoader
						.load(getClass().getClassLoader().getResource("application/gui/fxml/LoserScreen.fxml")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
