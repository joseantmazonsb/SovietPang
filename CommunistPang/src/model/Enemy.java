package model;

import controller.Controller;
import javafx.scene.image.Image;

public class Enemy {
	//Attributes
	private double x, y, width, height, vx, vy; //vx and vy are the components of speed of X,Y axis respectively
	private int lp; //Life points
	private Image img;
	//Constructor
	public Enemy(double x, double y, double width, double height, int lp, Image img) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.lp = lp;
		this.img = img;
		vx = Controller.ENEMY_X_AXIS_SPEED;
		vy = Controller.ENEMY_Y_AXIS_SPEED;
	}
	//Getters & setters
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public int getLp() {
		return lp;
	}
	public void setLp(int lp) {
		this.lp = lp;
	}
	public double getVx() {
		return vx;
	}
	public void setVx(double vx) {
		this.vx = vx;
	}
	public double getVy() {
		return vy;
	}
	public void setVy(double vy) {
		this.vy = vy;
	}
	public Image getImg() {
		return img;
	}
	public void setImg(Image img) {
		this.img = img;
	}
	//Methods
	public void move() {
		x += vx;
		y += vy;
	}
	public boolean reduce() {
		width /= 1.2;
		height /= 1.2;
		lp--;
		vy /= 1.2;
		vx /= 1.2;
		if (lp > 0) return true;
		else return false; //If life points get to 0 the enemy is destroyed
	}
}
