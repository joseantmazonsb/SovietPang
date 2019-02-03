package controller;

import java.util.LinkedList;
import java.util.List;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import listeners.BooleanEvent;
import listeners.BooleanListener;
import model.Character;
import model.Enemy;
import model.Projectile;
import model.Reward;
import model.RewardType;

public class Controller {
	//Attributes
	private static Controller uniqueInstance;
	private Stage window;
	private Character currentPlayer;
	private String playerName;
	private int currentScore;
	private int currentLevel;
	private boolean paused; //Stores if user has pressed ESC key to pause the game
	private List<BooleanListener> booleanListeners;
	
	public static final int DEFAULT_WIDTH = 900;
	public static final int DEFAULT_HEIGHT = 640;
	
	public static final double CHARACTER_WIDTH = 70;
	public static final double CHARACTER_HEIGHT = 85;
	public static final int CHARACTER_LP = 5;
	public static final double CHARACTER_X_AXIS_SPEED = 24;
	public static final double CHARACTER_Y_AXIS_SPEED = 0;
	
	public static final double ENEMY_WIDTH = 200;
	public static final double ENEMY_HEIGHT = 215;
	public static final double ENEMY_X_AXIS_SPEED = 6;
	public static final double ENEMY_Y_AXIS_SPEED = 8;
	public static final int ENEMY_LP = 4;
	
	public static final double PROJECTILE_WIDTH = 30;
	public static final double PROJECTILE_HEIGHT = 30;
	public static final double PROJECTILE_X_AXIS_SPEED = 0;
	public static final double PROJECTILE_Y_AXIS_SPEED = 25;
	
	public static final double REWARD_WIDTH = 30;
	public static final double REWARD_HEIGHT = 30;
	public static final double REWARD_X_AXIS_SPEED = 0;
	public static final double REWARD_Y_AXIS_SPEED = 10;
	public static final int REWARD_LP_BONUS = 1;
	public static final int REWARD_SCORE_BONUS = 150;
	public static final int REWARD_ATTACK_BONUS = 1;
	
	public static final int SCORE_FOR_HITTING_ENEMIES = 100;
	
	public static final int MAX_LEVELS = 10;
	
	public static final int ENEMIES_LEVEL_1 = 2;
	
	public static final int MIN_ENEMIES_LEVEL_2_3 = 2;
	public static final int MAX_ENEMIES_LEVEL_2_3 = 4;
	
	public static final int MIN_ENEMIES_LEVEL_4_5 = 4;
	public static final int MAX_ENEMIES_LEVEL_4_5 = 6;
	
	public static final int MIN_ENEMIES_LEVEL_6_7 = 6;
	public static final int MAX_ENEMIES_LEVEL_6_7 = 8;
	
	public static final int MIN_ENEMIES_LEVEL_8_9 = 8;
	public static final int MAX_ENEMIES_LEVEL_8_9 = 10;
	
	public static final int MIN_ENEMIES_LEVEL_10 = 4;
	public static final int MAX_ENEMIES_LEVEL_10 = 6;
	
	public static final double N_FRAMES_SHOW_HIT_CHARACTER = 60*3;
	
	public static final double N_FRAMES_ULTI_AVAILABLE = 60;
	public static final double SCORE_TO_ULTI = SCORE_FOR_HITTING_ENEMIES*10;
	
	public static final double N_FRAMES_NEXT_REWARD = 60*15;
	
	public static final int MAX_NUMBER_OF_REGISTERED_SCORES = 8;
	
	//Constructor
	public Controller() {
		currentScore = 0;
		currentLevel = 0;
		paused = false;
		booleanListeners = new LinkedList<>();
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
	public Character getCurrentPlayer() {
		return currentPlayer;
	}
	public void setCurrentPlayer(Character currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
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
	public boolean nextLevel() {
		currentLevel++;
		if (currentLevel <= MAX_LEVELS) return true;
		return false;
	}
	public void increaseScore() {
		currentScore+=SCORE_FOR_HITTING_ENEMIES;
	}
	public Character createCharacter(double x, double y, Image img) {
		currentPlayer = new Character(x, y, CHARACTER_WIDTH, CHARACTER_HEIGHT, CHARACTER_LP, img);
		return currentPlayer;
	}
	public Enemy createEnemy(double x, double y, Image img) {
		return new Enemy(x, y, ENEMY_WIDTH, ENEMY_HEIGHT, ENEMY_LP, img);
	}
	public Enemy createBoss(double x, double y, Image img) {
		return new Enemy(x, y, ENEMY_WIDTH*2, ENEMY_HEIGHT*2, ENEMY_LP*2, img);
	}
	public Enemy createEnemy(Enemy e) {
		return new Enemy(e.getX(), e.getY(), e.getWidth(), e.getHeight(), e.getLp(), e.getImg());
	}
	public Projectile createProjectile(double x, double y, List<Image> imgs) {
		return new Projectile(x, y, PROJECTILE_WIDTH, PROJECTILE_HEIGHT, imgs);	
	}
	public Reward createLPReward(double x, double y, Image img) {
		return new Reward(x, y, REWARD_WIDTH, REWARD_HEIGHT, REWARD_X_AXIS_SPEED, REWARD_Y_AXIS_SPEED, RewardType.LP, img);
	}
	public Reward createAttackReward(double x, double y, Image img) {
		return new Reward(x, y, REWARD_WIDTH, REWARD_HEIGHT, REWARD_X_AXIS_SPEED, REWARD_Y_AXIS_SPEED, RewardType.ATTACK, img);
	}
	public boolean isPaused() {
		return paused;
	}
	public void setPaused(boolean paused) {
		BooleanEvent e = new BooleanEvent(this, this.paused, paused);
		this.paused = paused;
		notifyBooleanListeners(e);
	}
	private void notifyBooleanListeners(BooleanEvent e) {
		List<BooleanListener> copy;
		synchronized (booleanListeners) {
			copy = new LinkedList<>(booleanListeners);
		}
		for (BooleanListener l : copy) {
			l.booleanPropertyChanged(e);
		}
	}
	synchronized public boolean addBooleanListener(BooleanListener l) {
		return booleanListeners.add(l);
	}
	synchronized public boolean removeBooleanListener(BooleanListener l) {
		return booleanListeners.remove(l);
	}
	synchronized public void clearBooleanListeners() {
		booleanListeners.clear();
	}
}
