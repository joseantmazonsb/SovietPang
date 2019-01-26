package model;

public class Record {
	//Attributes
	private int score;
	private String player;
	//Constructor
	public Record(int score, String player) {
		this.score = score;
		this.player = player;
	}
	//Methods
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getPlayer() {
		return player;
	}
	public void setPlayer(String player) {
		this.player = player;
	}
	@Override
	public String toString() {
		return score + " - '"+ player + "'";
	}
}
