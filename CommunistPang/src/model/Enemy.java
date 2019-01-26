package model;

import controller.Controller;
import javafx.scene.image.Image;

public class Enemy {
	//Attributes
	private float x, y, width, height, vx, vy; //vx and vy are the components of speed of X,Y axis respectively
	private int lp; //Life points
	private Image img;
	//Constructor
	public Enemy(float x, float y, float width, float height, int lp, Image img) {
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
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public int getLp() {
		return lp;
	}
	public void setLp(int lp) {
		this.lp = lp;
	}
	public float getVx() {
		return vx;
	}
	public void setVx(float vx) {
		this.vx = vx;
	}
	public float getVy() {
		return vy;
	}
	public void setVy(float vy) {
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
