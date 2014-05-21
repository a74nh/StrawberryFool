package com.sleepware.GameObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.sleepware.GameWorld.GameLevel;
import com.sleepware.GameWorld.GameWorld;
import com.sleepware.ZBHelpers.AssetLoader;

public class Hud {

	private GameWorld gameWorld;
	private GameLevel level;
	
	final private int y1, y2;
	final private int minX;
	final private int maxX;
	final private int middle;
	
	private final String levelString;
	private final int levelStringX;


	public Hud (GameWorld gameWorld, int y, int minX, int maxX) {
		this.gameWorld=gameWorld;
		this.level=gameWorld.getLevel();
		this.minX=minX;
		this.maxX=maxX;
		int fontHeight=(int)AssetLoader.buttonFont.getLineHeight();
		this.y1 = y + (6*(fontHeight));
		this.y2 = y + (5*(fontHeight)) + 5;
		this.middle=((maxX-minX)/2)+minX;
		
		levelString = "Level";
		TextBounds textBounds = AssetLoader.buttonFont.getBounds(levelString);
		levelStringX = maxX - 10 - (int)textBounds.width;

	}
	

	private void drawScore(SpriteBatch batcher) {
		
		AssetLoader.drawButtonText(batcher,
				"Score",
				minX+10,
				y1);

		AssetLoader.drawButtonText(batcher,
				"" + gameWorld.getScore(),
				minX+10,
				y2 );
	}

	private void drawLevel(SpriteBatch batcher) {	
		
		AssetLoader.drawButtonText(batcher,
				levelString,
				levelStringX,
				y1);

		AssetLoader.drawButtonText(batcher,
				"" + level.getLevel(),
				levelStringX,
				y2);
	}

	private void drawFPS(SpriteBatch batcher, float delta) {
		
		AssetLoader.drawButtonText(batcher,
				"FPS",
				middle,
				y1);

		AssetLoader.drawButtonText(batcher,
				"" + (int)(1/delta),
				middle,
				y2);
	}
	
	public void draw(SpriteBatch batcher, float delta) {
		drawScore(batcher);
		drawLevel(batcher);
		drawFPS(batcher, delta);
	}
	
}
