package com.sleepware.GameObjects;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Spoon extends Scrollable {

	private final boolean COLLISION_DETECTION = true;
	//private final boolean COLLISION_DETECTION = false;
	
	public enum Collides {
		NONE, LEFT, RIGHT;
	}
	
	
	private Random r;

	private Rectangle barLeft, barRight;
//	private Rectangle headLeft, headRight;
	private SpoonHead headL, headR;
	
	private int middle_gap;
	private int headWidth;
	private int headHeight;
	private int barHeight;

	private int minX;
	private int maxX;
	private int destination;

	private boolean isScored = false;

	private boolean isBar;

	private boolean caughtFruit;
	private int additionalCaughtGap;

	private int deathVelocity;

	
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

	public void setLevelAttributes(int scrollSpeed, int pipeGap, int deathVelocity) {
		super.setLevelAttributes(scrollSpeed);
		middle_gap = pipeGap;
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
				position.y - ((headHeight - barHeight) / 2));
		
		headR.updateBoundingBoxes(barRightXPosition,
				position.y - ((headHeight - barHeight) / 2));

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
	}

	@Override
	public void onRestart(float y) {
		reset(y);
	}


	public Collides collides(Bird bird) {
		if (COLLISION_DETECTION && position.x < bird.getX() + bird.getDiameter()) {
			
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
		destination=screenX-(middle_gap/2);
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
