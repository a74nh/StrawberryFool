package com.sleepware.GameObjects;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.sleepware.ZBHelpers.AssetLoader;

public class Title {

	private int gameWidth;
	private int gameHeight;
	private Spoon spoon;
	private StaticImage finger;
	private boolean fingerDisplayed;

	final private int titleY;
	
	public Title(int grassLeftStart, int grassRightStart, int gameWidth, int gameHeight, int spoonSize, int spoonHeadWidth, int spoonHeadHeight) {
		this.gameWidth=gameWidth;
		this.gameHeight=gameHeight;
		
		final int spoonY = (gameHeight/2) - 50;
		
		titleY = (gameHeight/2) - 130;
		
		spoon = new Spoon(spoonY, spoonSize, spoonHeadWidth, spoonHeadHeight, grassLeftStart, grassRightStart, 1);
		spoon.setLevelAttributes(0, 100, 0);
		spoon.reset(spoonY);
		

		int fingerX = grassRightStart- spoon.getWidth();

		finger = new StaticImage(fingerX, spoonY, spoonHeadHeight, spoonHeadHeight);
		fingerDisplayed=true;
	}
	
	
	
	private void drawText(SpriteBatch batcher, String s, int x, int y) {
		AssetLoader.shadow.draw(batcher, s, x, y);
		AssetLoader.font.draw(batcher, s, x, y);
	}
	
	
	
	public void draw(SpriteBatch batcher,
			TextureRegion bar, 
			TextureRegion headUp, 
			TextureRegion headDown,
			TextureRegion finger) {
		
		drawText(batcher,
				"Strawberry",
				(gameWidth / 2) - 30,
				titleY);

		drawText(batcher,
				"Fool",
				(gameWidth / 2) - 30,
				titleY - (int)AssetLoader.font.getLineHeight());
		
		spoon.draw(batcher, bar);
		spoon.drawHeads(batcher,headUp,headDown,headUp,headDown);
		
		if(fingerDisplayed) {
			this.finger.draw(batcher,finger);
		}
	}


	public void update(float delta) {
		spoon.update(delta);
	}

	public boolean onClick(int screenX, int screenY) {
		
		if (spoon.pointYcollides(screenY)) {
			spoon.move(screenX);
			fingerDisplayed=false;
			return true;
		}
		return false;
	}
	
}
