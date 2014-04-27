package com.sleepware.GameObjects;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
	private Rectangle headLeft, headRight;

	private int middle_gap;
	private int headWidth;
	private int headHeight;
	private int pipeSize;

	private int minX;
	private int maxX;
	private int destination;

	private boolean isScored = false;

	private boolean isBar;

	private boolean caughtFruit;
	private int additionalCaughtGap;

	private int deathVelocity;

	
	public Spoon(float y, int pipeSize, int headWidth, int headHeight, int minX, int maxX, float velocityMultiplier) {
		super(0, y, 0, headHeight, velocityMultiplier);

		this.headWidth=headWidth;
		this.headHeight=headHeight;
		this.pipeSize=pipeSize;
		r = new Random();
		headLeft = new Rectangle();
		headRight = new Rectangle();
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
				pipeSize);
		
		float barRightXPosition = width + middle_gap + additionalCaughtGap;
		
		barRight.set(barRightXPosition + headWidth, 
				position.y, 
				maxX-barRightXPosition-headWidth,
				pipeSize);

		headLeft.set(width - headWidth - additionalCaughtGap,
				position.y - ((headHeight - pipeSize) / 2),
				headWidth, 
				headHeight);
		
		headRight.set(barRightXPosition,
				position.y - ((headHeight - pipeSize) / 2),
				headWidth,
				headHeight);

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
		headLeft.y=newY;
		headRight.y=newY;
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
					Intersector.overlaps(bird.getBoundingCircle(), headLeft)	) {
				caughtFruit=true;
				return Collides.LEFT;
			}
			
			if (Intersector.overlaps(bird.getBoundingCircle(), barRight) ||
					Intersector.overlaps(bird.getBoundingCircle(), headRight)) {
				caughtFruit=true;
				return Collides.RIGHT;
			}
		}
		return Collides.NONE;
	}

	public boolean pointYcollides(int screenY) {
		return (screenY>=headLeft.y && screenY<=headLeft.y+headLeft.height);
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
	
	public void draw(SpriteBatch batcher, TextureRegion normalTexture) {

		batcher.draw(normalTexture, barLeft.x,barLeft.y,barLeft.width,barLeft.height);
		batcher.draw(normalTexture, barRight.x,barRight.y,barRight.width,barRight.height);
	}
	
	
	public void drawHeads(SpriteBatch batcher, TextureRegion textureUp, TextureRegion textureDown, TextureRegion forkup, TextureRegion forkdown) {

		if (!isBar) {

			batcher.draw(textureUp, 
					headLeft.x, 
					headLeft.y,
					headLeft.width,
					headLeft.height);
			
			batcher.draw(textureDown, 
					headRight.x, 
					headRight.y,
					headRight.width,
					headRight.height);
		}
		else
		{
			batcher.draw(forkup, 
					headLeft.x, 
					headLeft.y,
					headLeft.width,
					headLeft.height);
			
			batcher.draw(forkdown, 
					headRight.x, 
					headRight.y,
					headRight.width,
					headRight.height);
			
		}
	}

	public void setIsBar(boolean state) {
		isBar=state;
	}

	public boolean getIsBar() {
		return isBar;
	}



	
}
