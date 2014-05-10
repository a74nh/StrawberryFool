package com.sleepware.GameObjects;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.sleepware.ZBHelpers.AssetLoader;

public class Title {

	private final int MAX_FINGER_DISPLAYS = 2;
	
	private Spoon spoon;
	private StaticImage finger;
	private int fingerDisplayed;
	private final int grassLeftStart;
	private final int grassRightStart;

	final private int titleY;
	final private int titleX;
	final private int titleFruitX;
	final private int titleFruitY;
	final private String foolStr;
	
	public Title(int grassLeftStart, 
			int grassRightStart, 
			int gameHeight, 
			int spoonSize, 
			int spoonHeadWidth, 
			int spoonHeadHeight,
			int birdDiameter) {
		
		final int spoonY = (gameHeight/2) - 50;
		
		titleY = (gameHeight/2) - 200;
		titleX = grassLeftStart + 40;
		
		spoon = new Spoon(spoonY, spoonSize, spoonHeadWidth, spoonHeadHeight, grassLeftStart, grassRightStart, 1);
		spoon.setLevelAttributes(0, 100, 0);
		spoon.reset(spoonY);
		
		this.grassLeftStart = grassLeftStart;
		this.grassRightStart = grassRightStart;
		
		int fingerX = grassRightStart - spoon.getWidth();

		finger = new StaticImage(fingerX, spoonY, spoonHeadHeight, spoonHeadHeight);
		fingerDisplayed=0;
		
		foolStr = "Fool";
		
		TextBounds textBounds = AssetLoader.titleFont.getBounds(foolStr);
		
		titleFruitX = (int) (titleX + textBounds.width + 10);
		titleFruitY = titleY - (int)AssetLoader.titleFont.getLineHeight() - 10;
	}
	
	
	
	public void draw(SpriteBatch batcher,
			TextureRegion bar, 
			TextureRegion headUp, 
			TextureRegion headDown,
			TextureRegion finger,
			Fruit fruit) {
		
		AssetLoader.drawTitle(batcher,
				fruit.getName(),
				titleX,
				titleY,
				fruit.getColour());

		AssetLoader.drawTitle(batcher,
				foolStr,
				titleX,
				titleFruitY,
				fruit.getColour());
	
		spoon.drawBar(batcher, bar);
		spoon.drawHeads(batcher,headUp,headDown,headUp,headDown);
		
		if(fingerDisplayed<MAX_FINGER_DISPLAYS) {
			this.finger.draw(batcher,finger);
		}
	}


	public void update(float delta) {
		spoon.update(delta);
	}

	public boolean onClick(int screenX, int screenY) {
		
		if (spoon.pointYcollides(screenY)) {
			spoon.move(screenX);
			finger.setX(grassRightStart - screenX);
			fingerDisplayed++;
			return true;
		}
		return false;
	}
	
	public void drawCollisions(ShapeRenderer shapeRenderer) {
		spoon.drawCollisions(shapeRenderer);		
	}
	
	
	public int getTitleFruitY() {
		return titleFruitY;
	}
	
	public int getTitleFruitX() {
		return titleFruitX;
	}
}
