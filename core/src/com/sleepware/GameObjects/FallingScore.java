package com.sleepware.GameObjects;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class FallingScore extends RotatingScrollable {


	final private int destinationY;
	private Random r;
	private int baseYVelocity;
	private int fruitId;

	
	public FallingScore(int width, int height, int destinationY, int x, float velocityMultiplier) {
		super(x, -height, width, height, velocityMultiplier);
		this.destinationY=destinationY;
		r = new Random();
		baseYVelocity=250;

		//reset(0);
	}
	
	@Override
	public void setLevelAttributes(float scrollSpeed) {
		baseYVelocity=(int)-scrollSpeed;
	}
	
	@Override
	public boolean checkForOverflow() {
		
		if (position.y > destinationY) {
			position.y = destinationY;
			velocity.y = 0;
			rotationVelocity=0;
			return true;
		}
		return false;
	}
	
	public void reset() {
		super.reset(-height);
		velocity.y=0;
	}

	public void onRestart(int fruitId) {
		super.onRestart(-height);
		
		velocity.y = baseYVelocity;
		
		rotationVelocity = r.nextInt(baseYVelocity*3/2) + (baseYVelocity/2);
		if(r.nextInt(2)==0) {
			rotationVelocity=-rotationVelocity;
		}
		
		this.fruitId = fruitId;
	}
	
	public void draw(SpriteBatch batcher, Fruit[] fruit) {	
		super.drawBar(batcher,fruit[fruitId].getImage());
	}

}
