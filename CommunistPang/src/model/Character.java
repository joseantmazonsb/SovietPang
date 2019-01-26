package model;

import controller.Controller;
import javafx.scene.image.Image;

public class Character {
	//Attributes
		private float x, y, width, height, vx, vy; //vx and vy are the components of speed of X,Y axis respectively
		private int lp; //Life points
		private boolean ultiAvailable; //Ultimate skill
		private Image img;
		//Constructor
		public Character(float x, float y, float width, float height, int lp, Image img) {
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
		public void move() {
			x += vx;
			y += vy;
		}
}
