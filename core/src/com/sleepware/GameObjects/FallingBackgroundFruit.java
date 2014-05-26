package com.sleepware.GameObjects;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sleepware.ZBHelpers.AssetLoader;


public class FallingBackgroundFruit extends RotatingScrollable {


	final private int gameHeight;
	private Random r;
	final private int minX;
	final private int maxX;
	final private int maxDiameter;
	private int baseYVelocity;
	private int fruitId;

	
	public FallingBackgroundFruit(int maxDiameter, int gameHeight, int minX, int maxX, float velocityMultiplier) {
		super(0, 0, maxDiameter, maxDiameter, velocityMultiplier);
		this.gameHeight=gameHeight;
		r = new Random();
		this.minX=minX;
		this.maxX=maxX;
		this.maxDiameter=maxDiameter;
		baseYVelocity=1;

		reset(0);
	}
	
	@Override
	public void setLevelAttributes(float scrollSpeed) {
		baseYVelocity=(int)-scrollSpeed;
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

		width = r.nextInt(maxDiameter/3)+(2*maxDiameter/3);
		height = width;
		
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
		super.drawBar(batcher,fruit[fruitId].getImage());
	}

}
