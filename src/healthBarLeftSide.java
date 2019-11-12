
public class healthBarLeftSide extends Ucigame {
	Sprite redBar = new Sprite(getImage("redBar.jpg"), 300, 10);
	int health;
	boolean onRightSide;
public healthBarLeftSide(int health, boolean right){
	this.health = health;
	onRightSide = right;
}
public void changeHealth(int ch){
	health+=ch;
	//update();
}
public void draw(){
	if(onRightSide){
		redBar.flipHorizontal();
	}
	redBar.draw();
}
}
