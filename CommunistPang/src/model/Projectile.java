package model;

import java.util.List;

import controller.Controller;
import javafx.scene.image.Image;

public class Projectile {
	//Attributes
	private double x, y, width, height, vx, vy; //vx and vy are the components of speed of X,Y axis respectively
	private int currentImgIndex;
	private List<Image> imgs;
	//Constructor
	public Projectile(double x, double y, double width, double height, List<Image> imgs) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.imgs = imgs;
		currentImgIndex = 0;
		vx = Controller.PROJECTILE_X_AXIS_SPEED;
		vy = Controller.PROJECTILE_Y_AXIS_SPEED;
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
	public Image getCurrentImg() {
		int aux = currentImgIndex;
		currentImgIndex++;
		currentImgIndex %= imgs.size();
		return imgs.get(aux);
	}
	public void setImgs(List<Image> imgs) {
		this.imgs = imgs;
	}
	public void move() {
		y -= vy;
		x += vx;
	}
}