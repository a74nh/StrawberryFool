package com.sleepware.GameObjects;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.sleepware.ZBHelpers.AssetLoader;


public class Falling extends RotatingScrollable {


	final private int gameHeight;
	private Random r;
	final private int minX;
	final private int maxX;
	private int baseYVelocity;
	private int fruitId;

	
	public Falling(int width, int height, int gameHeight, int minX, int maxX, float velocityMultiplier) {
		super(0, 0, width, height, velocityMultiplier);
		this.gameHeight=gameHeight;
		r = new Random();
		this.minX=minX;
		this.maxX=maxX;
		baseYVelocity=1;

		reset(0);
	}
	
	@Override
	public void setLevelAttributes(int scrollSpeed) {
		baseYVelocity=-scrollSpeed;
	}
	
	@Override
	public boolean checkForOverflow() {
		
		if (position.y > gameHeight) {
			//Up to just half a screen in the past
			reset((-r.nextInt(gameHeight/2)) - height);
			return true;
		} else if (position.y + height > gameHeight + 5) {
			velocity.y = 10;
			rotationVelocity=0;
		}
		return false;
	}
	
	@Override
	public void reset(float newY) {
		super.reset(newY);

		position.x = r.nextInt(maxX-minX-width)+minX;

		velocity.y = r.nextInt(baseYVelocity*3/2) + (baseYVelocity/4);
		
		rotationVelocity = r.nextInt(baseYVelocity*3/2) + (baseYVelocity/2);
		if(r.nextInt(2)==0) {
			rotationVelocity=-rotationVelocity;
		}
		
		fruitId = r.nextInt(AssetLoader.NUMBER_OF_FRUIT);
	}

	public void onRestart() {
		//Up to a full screen in the past
		super.onRestart((-r.nextInt(gameHeight)) - height);
	}
	
	public void draw(SpriteBatch batcher, Fruit[] fruit) {	
		super.draw(batcher,fruit[fruitId].getImage());
	}

}
