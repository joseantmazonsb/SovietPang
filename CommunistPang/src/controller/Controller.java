package controller;

import java.util.List;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Character;
import model.Enemy;
import model.Projectile;

public class Controller {
	//Attributes
	private static Controller uniqueInstance;
	private Stage window;
	private String currentPlayer;
	private int currentScore;
	private int currentLevel;
	
	public static final int DEFAULT_WIDTH = 900;
	public static final int DEFAULT_HEIGHT = 640;
	
	private static final double CHARACTER_WIDTH = 70;
	private static final double CHARACTER_HEIGHT = 85;
	private static final int CHARACTER_LP = 3;
	public static final double CHARACTER_X_AXIS_SPEED = 30;
	public static final double CHARACTER_Y_AXIS_SPEED = 0;
	
	private static final double ENEMY_WIDTH = 250;
	private static final double ENEMY_HEIGHT = 265;
	public static final double ENEMY_X_AXIS_SPEED = 4;
	public static final double ENEMY_Y_AXIS_SPEED = 6;
	private static final int ENEMY_LP = 4;
	
	private static final double PROJECTILE_WIDTH = 30;
	private static final double PROJECTILE_HEIGHT = 30;
	public static final double PROJECTILE_X_AXIS_SPEED = 0;
	public static final double PROJECTILE_Y_AXIS_SPEED = 25;
	
	public static final int REWARDS_BONUS = 150;
	
	//Constructor
	public Controller() {
		currentScore = 0;
		currentLevel = 1;
	}
	
	//Singleton
	public static Controller getInstance() {
		if (uniqueInstance == null) uniqueInstance = new Controller();
		return uniqueInstance;
	}
	//Getters & setters
	public Stage getWindow() {
		return window;
	}
	public void setWindow(Stage window) {
		this.window = window;
	}
	public String getCurrentPlayer() {
		return currentPlayer;
	}
	public void setCurrentPlayer(String currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	public int getCurrentScore() {
		return currentScore;
	}
	public void setCurrentScore(int currentScore) {
		this.currentScore = currentScore;
	}
	public int getCurrentLevel() {
		return currentLevel;
	}
	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}
	//Methods
	public void nextLevel() {
		currentLevel++;
	}
	public void addScore(int score) {
		currentScore+=score;
	}
	public Character createCharacter(double x, double y, Image img) {
		return new Character(x, y, CHARACTER_WIDTH, CHARACTER_HEIGHT, CHARACTER_LP, img);
	}
	public Enemy createEnemy(double x, double y, Image img) {
		return new Enemy(x, y, ENEMY_WIDTH, ENEMY_HEIGHT, ENEMY_LP, img);
	}
	public Projectile createProjectile(double x, double y, List<Image> imgs) {
		return new Projectile(x, y, PROJECTILE_WIDTH, PROJECTILE_HEIGHT, imgs);
		
	}
}
