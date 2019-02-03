package model;

import javafx.scene.image.Image;

public class Reward {
	//Attributes
		private double x, y, width, height, vx, vy;
		private RewardType type;
		private Image img;
		//Constructor
		public Reward(double x, double y, double width, double height, double vx, double vy, RewardType type , Image img) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.vx = vx;
			this.vy = vy;
			this.type = type;
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
		public RewardType getType() {
			return type;
		}
		public void setType(RewardType type) {
			this.type = type;
		}
		public Image getImg() {
			return img;
		}
		public void setImg(Image img) {
			this.img = img;
		}
		//Methods
		public void move() {
			y += vy;
		}
		
}
