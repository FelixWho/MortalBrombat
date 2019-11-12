
public class fighter extends Sprite{
	boolean facingRight;
	Sprite projectile;
	public fighter(boolean facing, Image[] im, int width, int height, Sprite project){
		super(im[0], width, height);
		facingRight = facing;
		projectile = project;
	}
	public void setDirection(boolean isRight){
		if(isRight){
			facingRight = true;
		} else{
			facingRight = false;
		}
	}
	public void fire(){
		//fire projectile towards direction
	}
}
