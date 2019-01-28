package model;

import controller.Controller;
import javafx.scene.image.Image;

public class Character {
	//Attributes
		private double x, y, width, height, vx, vy; //vx and vy are the components of speed of X,Y axis respectively
		private int lp; //Life points
		private boolean ultiAvailable; //Ultimate skill
		private Image img;
		//Constructor
		public Character(double x, double y, double width, double height, int lp, Image img) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.lp = lp;
			this.img = img;
			ultiAvailable = false;
			vx = Controller.CHARACTER_X_AXIS_SPEED;
			vy = Controller.CHARACTER_Y_AXIS_SPEED;
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
		public int getLp() {
			return lp;
		}
		public void setLp(int lp) {
			this.lp = lp;
		}
		public boolean isUltiAvailable() {
			return ultiAvailable;
		}
		public void setUltiAvailable(boolean ultiAvailable) {
			this.ultiAvailable = ultiAvailable;
		}
		public Image getImg() {
			return img;
		}
		public void setImg(Image img) {
			this.img = img;
		}
		//Mehods
		public void moveRight() {
			x += vx;
		}
		public void moveLeft() {
			x -= vx;
		}
		public void moveUp() {
			y += vy;
		}
		public void moveDown() {
			y -= vy;
		}
		public boolean reduceLP() {
			lp--;
			if (lp > 0 ) return true; //Returns true if player still has LP
			return false;
		}
}
