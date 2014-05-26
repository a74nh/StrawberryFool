package com.sleepware.GameObjects;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.sleepware.ZBHelpers.AssetLoader;

public class Spoon extends Scrollable {

	
	public enum Collides {
		NONE, LEFT, RIGHT;
	}
	
	
	private Random r;

	private Rectangle barLeft, barRight;
//	private Rectangle headLeft, headRight;
	private SpoonHead headL, headR;
	
	private float middle_gap;
	private final int headWidth;
	private final int headHeight;
	private int barHeight;

	private final int minX;
	private final int maxX;
	private int destination;

	private boolean isScored = false;

	private boolean isBar;

	private boolean caughtFruit;
	private float additionalCaughtGap;

	private int deathVelocity;

	private boolean collisions;

	
	public Spoon(float y, int barHeight, int headWidth, int headHeight, int minX, int maxX, float velocityMultiplier) {
		super(0, y, 0, headHeight, velocityMultiplier);

		this.headWidth=headWidth;
		this.headHeight=headHeight;
		this.barHeight=barHeight;
		r = new Random();
		headL = new SpoonHead(headWidth, headHeight, 12, barHeight, false);
		headR = new SpoonHead(headWidth, headHeight, 12, barHeight, true);
		barLeft = new Rectangle();
		barRight = new Rectangle();
		
		this.minX = minX;
		this.maxX = maxX;
		
		reset(y);
	}

	public void setLevelAttributes(float f, float g, int deathVelocity) {
		super.setLevelAttributes(f);
		middle_gap = g;
		this.deathVelocity = deathVelocity;
	}
	
	public void updateBoundingBoxes() {
		
		barLeft.set(minX, 
				position.y, 
				width - headWidth - additionalCaughtGap, 
				barHeight);
		
		float barRightXPosition = width + middle_gap + additionalCaughtGap;
		
		barRight.set(barRightXPosition + headWidth, 
				position.y, 
				maxX-barRightXPosition-headWidth,
				barHeight);
		
		headL.updateBoundingBoxes(width - headWidth - additionalCaughtGap,
				position.y - ((headHeight - barHeight) / 2), isBar);
		
		headR.updateBoundingBoxes(barRightXPosition,
				position.y - ((headHeight - barHeight) / 2), isBar);

	}

	
	@Override
	public void update(float delta) {

		super.update(delta);

		if(width>destination) {
			width -= (500 * delta);
			
			if(width<destination) width=destination;
		
		} else if(width<destination) {
			width += (500 * delta);
			
			if(width>destination) width=destination;
		}

		if(caughtFruit) {
			additionalCaughtGap += (deathVelocity * delta);
		}
		
		updateBoundingBoxes();
	}

	@Override
	public void reset(float newY) {
		super.reset(newY);
		// Change to a random number
		width = r.nextInt(maxX-minX) +minX;
		destination=width;
		updateBoundingBoxes();
		isScored = false;
		isBar=false;
		caughtFruit=false;
		additionalCaughtGap=0;
		collisions=AssetLoader.getCollisions();
	}

	@Override
	public void onRestart(float y) {
		reset(y);
	}


	public Collides collides(Bird bird) {
		if (collisions && position.x < bird.getX() + bird.getDiameter()) {
			
			if (Intersector.overlaps(bird.getBoundingCircle(), barLeft) ||
				headL.collides(bird)) {
				caughtFruit=true;
				return Collides.LEFT;
			}
			
			if (Intersector.overlaps(bird.getBoundingCircle(), barRight) ||
				headR.collides(bird)) {
				caughtFruit=true;
				return Collides.RIGHT;
			}
		}
		return Collides.NONE;
	}

	public boolean pointYcollides(int screenY) {
		return (headL.pointYcollides(screenY));
	}
	
	public boolean isScored() {
		return isScored;
	}

	public void setScored(boolean b) {
		isScored = b;
	}

	public void move(int screenX) {
		destination=(int) (screenX-(middle_gap/2));
	}
	
	public void drawBar(SpriteBatch batcher, TextureRegion normalTexture) {

		batcher.draw(normalTexture, barLeft.x,barLeft.y,barLeft.width,barLeft.height);
		batcher.draw(normalTexture, barRight.x,barRight.y,barRight.width,barRight.height);
	}
	
	
	public void drawHeads(SpriteBatch batcher, TextureRegion spoonLeft, TextureRegion spoonRight, TextureRegion forkLeft, TextureRegion forkRight) {

		if (!isBar) {
			headL.draw(batcher, spoonLeft);
			headR.draw(batcher, spoonRight);
		}
		else
		{
			headL.draw(batcher, forkLeft);
			headR.draw(batcher, forkRight);
		}
	}

	public void setIsBar(boolean state) {
		isBar=state;
	}

	public boolean getIsBar() {
		return isBar;
	}

	public void drawCollisions(ShapeRenderer shapeRenderer) {
		
		shapeRenderer.rect(barLeft.x, barLeft.y, barLeft.width, barLeft.height);
		shapeRenderer.rect(barRight.x, barRight.y, barRight.width, barRight.height);
		headL.drawCollisions(shapeRenderer);
		headR.drawCollisions(shapeRenderer);
	}
	
}
