package com.sleepware.GameObjects;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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


	public Hud (GameWorld gameWorld, int y, int minX, int maxX) {
		this.gameWorld=gameWorld;
		this.level=gameWorld.getLevel();
		this.minX=minX;
		this.maxX=maxX;
		int fontHeight=(int)AssetLoader.getLineHeight();
		this.y1 = y + (3*(fontHeight));
		this.y2 = y + (2*(fontHeight));
		this.middle=((maxX-minX)/2)+minX;
	}
	

	private void drawScore(SpriteBatch batcher) {
		
		AssetLoader.drawText(batcher,
				"Score",
				minX+10,
				y1);

		AssetLoader.drawText(batcher,
				"" + gameWorld.getScore(),
				minX+10,
				y2 );
	}

	private void drawLevel(SpriteBatch batcher) {
		
		String s = "Level";
		int length = s.length();

		AssetLoader.drawText(batcher,
				s,
				maxX -10  - (8 * length),
				y1);

		AssetLoader.drawText(batcher,
				"" + level.getLevel(),
				maxX -10  - (8 * length),
				y2);
	}

	private void drawFPS(SpriteBatch batcher, float delta) {
		
		AssetLoader.drawText(batcher,
				"FPS",
				middle,
				y1);

		AssetLoader.drawText(batcher,
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
