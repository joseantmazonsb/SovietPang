package controller;

import javafx.stage.Stage;

public class Controller {
	//Attributes
	private static Controller uniqueInstance;
	private Stage window;
	private String currentPlayer;
	
	public static final int DEFAULT_WIDTH = 900;
	public static final int DEFAULT_HEIGHT = 640;
	
	public static final float ENEMY_X_AXIS_SPEED = 4;
	public static final float ENEMY_Y_AXIS_SPEED = 6;
	public static final float CHARACTER_X_AXIS_SPEED = 10;
	public static final float CHARACTER_Y_AXIS_SPEED = 0;
	public static final float PROJECTILE_X_AXIS_SPEED = 0;
	public static final float PROJECTILE_Y_AXIS_SPEED = 30;
	
	public static final int REWARDS_BONUS = 150;
	
	//Singleton
	public static Controller getInstance() {
		if (uniqueInstance == null) uniqueInstance = new Controller();
		return uniqueInstance;
	}
	//Methods
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
	
}
