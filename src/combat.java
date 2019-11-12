import java.util.Arrays;

public class combat extends Ucigame{
static final long serialVersionUID = 1;
static final double gravity = 1;
Sprite p1;
Sprite p2;

int health1 = 100;
int health2 = 100;

int charge1;
int charge2;

Sprite bulletp1;
Sprite bulletp2;

boolean victoryP1 = false;
boolean vicotryP2 = false;

boolean p1Fighting = false;
boolean p2Fighting = false;

boolean jumpingp1 = false;
boolean jumpingp2 = false;

boolean firingp1 = false;
boolean firingp2 = false;

boolean rightp1 = true;
boolean rightp2 = false;

boolean rightbu1 = true;

boolean rightb1 = true;
boolean rightb2 = false;

boolean p1rpressed = false;
boolean p1lpressed =false;

boolean p1crouching = false;
boolean p1crouchpressed = false;
boolean stopAll1 = false;

Sprite h1;
Sprite h2;
Sprite mana1;
Sprite mana2;
Sprite bbox1;
Sprite bbox2;

Sprite gold1, gold2;
Sprite floor;
Sprite platform;

Sprite ult1;
Sprite ult2;

double p1vely = 0;
double p1velx;
double p2velx;
double p2vely = 0;

long startp1 = -1300;
long endp1  = 0;
long staf1 = -650;
long endf1 = 0;

long startp2 = -1300;
long endp2 = 0;

double p1sin = Math.PI/2;
double p2sin = Math.PI/2;

int p1hurtx = 4;
boolean hitrightp1;

Sound ugh = getSound("mario-ugh.wav");
Sound background = getSound("x05-final-destination.mp3");
Sound haha = getSound("sm64_mario_haha.wav");
Sound hoo = getSound("sm64_mario_hoo.wav");
Sound hurt = getSound("sm64_mario_hurt.wav");

int frames = 0;

Image[] marioStand = {getImage("mariov2/m.png", 255, 0,255),getImage("mariov2/m2.png", 255, 0,255),getImage("mariov2/m3.png", 255, 0,255),getImage("mariov2/m4.png", 255, 0,255),getImage("mariov2/m5.png", 255, 0,255),getImage("mariov2/marioShoot1.png", 255, 0,255),
		getImage("mariov2/marioJump1.png",255,0,255), getImage("mariov2/marioJumpShoot1.png",255,0,255),getImage("mariowalk/mwalk1.png",255,0,255),getImage("mariowalk/mwalk2.png",255,0,255)
		,getImage("mariowalk/mwalk3.png",255,0,255),getImage("mariowalk/mwalk4.png",255,0,255),getImage("mariowalk/mwalk5.png",255,0,255)
		,getImage("mariopunch/mariopunch1.png",255,0,255),getImage("mariopunch/mariopunch2.png",255,0,255),getImage("mariopunch/mariopunchv2.png",255,0,255)
		,getImage("mariov2/mariocrouch.png",255,0,255), getImage("mariov2/marioflinch.png",255,0,255)};

public void setup()
{

	window.size(1440, 900);
	window.title("Super Mortal Bros Brawl");
	framerate(80);
	window.showFPS();

	//canvas.background(255,255, 255);
	floor = new Sprite(getImage("transparent.png"), window.clientWidth, 100);
	floor.position(0, 800);
	platform =  new Sprite(getImage("platform.png",255,255,255));
	platform.position((window.clientWidth/2)-(platform.width/2), window.clientHeight/2);
	canvas.background(getImage("gameArena.jpg"));
	keyboard.typematicOff(keyboard.Z);
	
	doLocalSetup();
	

}
public void draw(){
	frames++;
	if(frames>10){
		frames = 10;
	}
	p1.stopIfCollidesWith(floor);
	p1.stopIfCollidesWith(LEFTEDGE, RIGHTEDGE, TOPEDGE, BOTTOMEDGE);
	p2.stopIfCollidesWith(floor, LEFTEDGE, RIGHTEDGE, TOPEDGE, BOTTOMEDGE);

	
	if(jumpingp1){
		
		double maxC = 10;
		p1.nextY(p1.y()-(Math.sin(p1sin)*maxC));
		p1sin+=0.039269908169872;
		if(p1sin > 3*Math.PI/2){
			p1sin = Math.PI/2;
			jumpingp1 = false;
		}
	}
	if(jumpingp2){
		double maxC = 10;
		p2.nextY(p2.y()-(Math.sin(p2sin)*maxC));
		p2sin+=0.039269908169872;
			if(p2sin>3*Math.PI/2){
			
				p2sin = Math.PI/2;
				jumpingp2 = false;
			}
	}
	/*

	if(p1.y()+p1.height < floor.y()&&!keyboard.isDown(keyboard.F)){
		p1vely = -1.2*(Math.pow(p1vely,2))-gravity;
		if(p1vely<-11.2){
			p1vely = -11.2;
		}
		p1.nextY(p1.y()-p1vely);
		jumpingp1 = true;
		
	} else {
		p1vely = 11;
		jumpingp1=false;
	}
*/
	if(p1crouching){
		p1.play("crouchDefault");
	}
	if(p1Fighting){
		endf1 = System.currentTimeMillis();
		if(endf1-staf1>=650){
			p1Fighting = false;
		}
	}
	if(firingp1){
		
		bulletp1.move();
		if(bulletp1.xPixel()<=0 || bulletp1.xPixel()>=1440){
			firingp1=false;
			bulletp1.hide();
		}
	}
	if(firingp2){
		bulletp2.move();
		if(bulletp2.xPixel()<=0|| bulletp2.xPixel()>=1440){
			firingp2 =false;
			bulletp2.hide();
		}
	}
	//make gold mana
	if(mana1.x() >=0){
		gold1.show();
	} else{
		gold1.hide();
	}
	if(mana2.x()<=window.clientWidth-mana2.width){
		gold2.show();
	} else{
		gold2.hide();
	}
	p1.checkIfCollidesWith(bulletp2 ,PIXELPERFECT);
	if(p1.collided()&&(bulletp2.x() <=p1.x()+0.5*p1.width&&bulletp2.x()>=p1.x()) && (bulletp2.y() <= p1.y()+p1.height &&bulletp2.y() >= p1.y())){
		System.out.println("hit! on player1");
		hurt.play();
		firingp2 =false;
		bulletp2.hide();
		bulletp2.position(0, 0);
		health1-=7;
		h1.position(h1.x()-(0.07*400), h1.y());
		p1.play("flinch",1, "default");
		hitrightp1 = p1.collided(RIGHT);
		p1hurtx = 0;
		mana1.position(mana1.x()+10, mana1.y());
		if(mana1.x()>0){
			mana1.position(0,mana1.y());
		}
		mana2.position(mana2.x()-4, mana2.y());
		if(mana2.x()<window.clientWidth-mana2.width){
			mana2.position(window.clientWidth-mana2.width,mana2.y());
		}
	}
	p2.checkIfCollidesWith(bulletp1, PIXELPERFECT);
	if(p2.collided()&&(bulletp1.x()<=p2.x()+0.75*p2.width &&bulletp1.x()>=p2.x())&& (bulletp1.y() <= p2.y()+p2.height &&bulletp1.y() >= p2.y()-10)){
		System.out.println("hit! on player 2");
		firingp1 = false;
		bulletp1.hide();
		bulletp1.position(0, 0);
		health2-=7;
		h2.position(h2.x()+(0.07*400), h2.y());
		mana2.position(mana2.x()-10, mana2.y());
		if(mana2.x()<window.clientWidth-mana2.width){
			mana2.position(window.clientWidth-mana2.width,mana2.y());
		}
		mana1.position(mana1.x()+4, mana1.y());
		if(mana1.x()>0){
			mana1.position(0,mana1.y());
		}
	}
	
	canvas.clear();
	h1.draw();
	h2.draw();
	bbox1.draw();
	bbox2.draw();
	mana1.draw();
	mana2.draw();
	gold1.draw();
	gold2.draw();
	if(!rightp1){
		p1.flipHorizontal();
	}
	
	if(!rightb1){
		bulletp1.flipHorizontal();
	}
	p1.draw();
	bulletp1.draw();
	floor.draw();
	if(!rightp2){
		p2.flipHorizontal();
	}
	p2.draw();
	bulletp2.draw();
	//platform.draw();

}
public void doLocalSetup(){
	h1 = new Sprite(getImage("redBar.jpg"), 400, 10);
	h2 = new Sprite(getImage("redBar.jpg"), 400, 10);
	h1.position(0, 50);
	h2.position(1440-h2.width(), 50);
	
	bbox1 = new Sprite(getImage("black.jpg"), 105, 10);
	bbox2 = new Sprite(getImage("black.jpg"), 105, 10);
	bbox1.position(-5, 70);
	bbox2.position(1440-bbox2.width()+5, 70);
	
	mana1 = new Sprite(getImage("blue.png"), 100, 5);
	mana2 = new Sprite(getImage("blue.png"), 100, 5);
	mana1.position(0-mana1.width(), 72);
	mana2.position(1440, 72);
	
	gold1 = new Sprite(getImage("gold.jpg"), 100, 5);
	gold2 = new Sprite(getImage("gold.jpg"), 100, 5);
	gold1.position(0,mana1.y());
	gold2.position(1440-gold2.width, mana2.y());
	gold1.hide();
	gold2.hide();
	
	p1 = new Sprite(getImage("mariov2/m.png", 255, 0, 255), 96, 101);
	p1.position(100,floor.y()-p1.height);
	for(Image i: marioStand){
		p1.addFrame(i, 0, 0);
	}

	p1.framerate(9);
	p1.defineSequence("default", 0,1,2,3,4,5,5,5,4,3,2,1);
	p1.play("default");
	p1.defineSequence("shoot", 6,6,6,6);
	p1.defineSequence("jumpDefault", 7,7,7,7,7,7,7,7,7);
	p1.defineSequence("jumpshoot1", 8,8,8,7,7,7,7);
	p1.defineSequence("jumpshoot2", 8,8,8,7,7,7);
	p1.defineSequence("jumpshoot3", 8,8,7);
	p1.defineSequence("jumpshoot4", 8,8);
	p1.defineSequence("walkright", 9,10,11,12,13,12,11,10);
	p1.defineSequence("punchDefault", 16,16,16);
	p1.defineSequence("crouchDefault", 17,17);
	p1.defineSequence("flinch", 18,18,18);
	
	
	p2 = new Sprite(getImage("mega1.png",0,0,0));
	p2.position(window.clientWidth-100-p2.width,floor.y()-p2.height);
	p2.framerate(9);
	
	bulletp1 = new Sprite(getImage("fireball.jpg", 255,255,255));
	bulletp1.hide();
	
	bulletp2 = new Sprite(getImage("bulletmega.png",255,0,255));
	bulletp2.hide();
	
	background.loop();
	
	//ultimate moves
	ult1 = new Sprite(getImage("firinlazor.png"));
	ult1.position(0, 0);
	ult1.hide();
	
}
public void onKeyPress(){
	if(keyboard.isDown( keyboard.B)){
		if(!p1crouching){
		p1rpressed = true;
		endp1 = System.currentTimeMillis();
		if(endp1-startp1<500 || p1Fighting){
			p1.nextX(p1.x() +1);
		}else{
		p1.nextX(p1.x() +5);
		}
		if(!rightp1){
			p1.flipHorizontal();
		}
		if(!rightbu1){
			bulletp1.flipHorizontal();
		}
		if(!jumpingp1 && !firingp1 &&!p1Fighting){
			p1.play("walkright",1,"default");
		}
		rightbu1 = true;
		rightp1 =true;
		}
	}
	if(keyboard.isDown(keyboard.C)){
		if(!p1crouching){
		p1lpressed=true;
		endp1 = System.currentTimeMillis();
		if(endp1-startp1<500 || p1Fighting){	
		p1.nextX(p1.x() -2);
		} else{
			p1.nextX(p1.x() -5);
		}
		if(rightp1){
			p1.flipHorizontal();
		}
		if(rightbu1){
			bulletp1.flipHorizontal();
		}
		if(!jumpingp1&&!firingp1&&!p1Fighting){
			p1.play("walkright",1,"default");
		}
		rightbu1 = false;
		rightp1 = false;
		}
	}
	if(keyboard.isDown(keyboard.F)){
	
		if(jumpingp1 ==false){
			p1.play("jumpDefault", 1, "default");
			hoo.play();
			jumpingp1 =true;
			}
	}
	if(keyboard.key() == keyboard.X){
		if(!p1Fighting){
		endp1 = System.currentTimeMillis();
		if(!firingp1){
			if(endp1-startp1>=1300){
			haha.play();
			startp1 = System.currentTimeMillis();
			if(!jumpingp1){
			p1.play("shoot",1, "default");
			} else{
				if(p1sin<Math.PI/2+Math.PI/8){
				p1.play("jumpshoot1",1, "default");
				} else if(p1sin<Math.PI/2+(2*Math.PI/4)){
					p1.play("jumpshoot2",1, "default");
				}else if(p1sin<Math.PI/2+(3*Math.PI/4)){
					p1.play("jumpshoot3",1, "default");
				}else if(p1sin<Math.PI/2+(4*Math.PI/4)){
					p1.play("jumpshoot4",1, "default");
				}
			}
			bulletp1.show();
			if(rightp1) {bulletp1.motion(10, 0, SET);} else{bulletp1.motion(-10, 0, SET); }
			firingp1 = true;
			bulletp1.position(p1.xPixel()-(p1.width/2-bulletp1.width/2), p1.yPixel()+(p1.height/2-bulletp1.height));
			rightb1 = rightp1;
			}
		}
		}
	}
	if(keyboard.key() == keyboard.Z){
		endf1 = System.currentTimeMillis();
		if(endf1-staf1 >=650){
			ugh.play();
			if(!jumpingp1){
			p1.play("punchDefault",1,"default");
			}
			staf1 = System.currentTimeMillis();
		p1Fighting = true;
		double hitx = p1.x()+p1.width;
		double hity = p1.height/2+ p1.y();
		if(hitx <=p2.x()+p2.width && hitx>=p2.x()+7 && hity <=p2.y()+p2.height && hity>=p2.y()){
			System.err.println("punch on p2!");
			health2-=8;
			h2.position(h2.x()+(0.08*400), h2.y());
			//change mana pos
			mana2.position(mana2.x()-12, mana2.y());
			if(mana2.x()<window.clientWidth-mana2.width){
				mana2.position(window.clientWidth-mana2.width,mana2.y());
			}
			mana1.position(mana1.x()+3, mana1.y());
			if(mana1.x()>0){
				mana1.position(0,mana1.y());
			}
			
		}
		
		}
	}
	if(keyboard.key() == keyboard.SHIFT){
		if(mana1.x() >=0){
			mana1.position(-mana1.width, mana1.y());
			//due ultimate move here
		}
	}
	
	if(keyboard.isDown(keyboard.V)){
		if(!jumpingp1){
		p1crouching = true;
		p1crouchpressed =true;
		}
	}
	
	if(keyboard.isDown(keyboard.RIGHT)){
		endp2 = System.currentTimeMillis();
		if(endp2-startp2<500 || p2Fighting){
		p2.nextX(p2.x() +1);
		} else{
			p2.nextX(p2.x() +5);
		}
		rightp2 = true;
	}
	if(keyboard.isDown(keyboard.LEFT)){
		endp2 = System.currentTimeMillis();
		if(endp2-startp2<500 || p2Fighting){
			p2.nextX(p2.x() -2);
			} else{
				p2.nextX(p2.x() -5);
			}
		rightp2 = false;
	}
	if(keyboard.isDown(keyboard.UP)){
		
		if(jumpingp2 ==false){
			jumpingp2 =true;
			}
	}
	if(keyboard.key() == keyboard.SLASH){
		if(!p2Fighting){
			endp2 = System.currentTimeMillis();
			if(!firingp2 && endp2-startp2 >=1300){
			startp2 = System.currentTimeMillis();
			bulletp2.show();
			if(!rightp2) {bulletp2.motion(-10, 0, SET);} else {bulletp2.motion(10, 0, SET);}
			firingp2 = true;
			bulletp2.position(p2.x()-(p2.width/2-bulletp2.width/2), p2.y()+(p2.height/2-bulletp2.height));
			}
		}
	}
}
public void onKeyRelease(){
	if(p1rpressed && !keyboard.isDown(keyboard.B)&&!jumpingp1&&!p1Fighting){
		p1.play("default");
		p1rpressed = false;
	}
	if(p1lpressed && !keyboard.isDown(keyboard.C)&&!jumpingp1&&!p1Fighting){
		p1.play("default");
		p1lpressed = false;
	}
	if(p1crouchpressed && !keyboard.isDown(keyboard.V)&&!jumpingp1&&!p1Fighting){
		p1.play("default");
		p1crouchpressed = false;
		p1crouching = false;
	}
}





}
