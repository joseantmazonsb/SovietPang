package controller;

import javafx.stage.Stage;

public class Controller {
	//Attributes
	private static Controller uniqueInstance;
	private Stage window;
	private String currentPlayer;
	private int currentScore;
	private int currentLevel;
	
	public static final int DEFAULT_WIDTH = 900;
	public static final int DEFAULT_HEIGHT = 640;
	
	public static final float ENEMY_X_AXIS_SPEED = 4;
	public static final float ENEMY_Y_AXIS_SPEED = 6;
	public static final float CHARACTER_X_AXIS_SPEED = 10;
	public static final float CHARACTER_Y_AXIS_SPEED = 0;
	public static final float PROJECTILE_X_AXIS_SPEED = 0;
	public static final float PROJECTILE_Y_AXIS_SPEED = 30;
	
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
	public Character createCharacter() {
		return null;
	}
	
}
