package model;

import controller.Controller;
import javafx.scene.image.Image;

public class Reward {
	//Attributes
		private float x, y, width, height;
		private int bonus; //Bonus points
		private Image img;
		//Constructor
		public Reward(float x, float y, float width, float height, Image img) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.bonus = Controller.REWARDS_BONUS;
			this.img = img;
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
		public int getBonus() {
			return bonus;
		}
		public void setBonus(int bonus) {
			this.bonus = bonus;
		}
		public Image getImg() {
			return img;
		}
		public void setImg(Image img) {
			this.img = img;
		}
		
}
