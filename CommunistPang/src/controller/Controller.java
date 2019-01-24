package controller;

import javafx.stage.Stage;

public class Controller {
	//Attributes
	private static Controller uniqueInstance;
	private Stage window;
	
	
	//public static final int DEFAULT_WIDTH = 1280;
	//public static final int DEFAULT_HEIGHT = 720;
	
	
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
	
}
