package model;

import controller.Controller;
import javafx.scene.image.Image;

public class Reward {
	//Attributes
		private double x, y, width, height;
		private int bonus; //Bonus points
		private Image img;
		//Constructor
		public Reward(double x, double y, double width, double height, Image img) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.bonus = Controller.REWARDS_BONUS;
			this.img = img;
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
