package com.sleepware.GameObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.sleepware.GameWorld.GameWorld;
import com.sleepware.ZBHelpers.AssetLoader;

public class ScoreBoard {

	public static final int NUMBER_OF_FALLING_FRUIT = 5;
	
	private GameWorld gameWorld;
	private final int gameWidth;
	private final int gameHeight;
	private int grassLeftStart;

	private FallingScore[] fallingFruit;
	private StaticImage[] staticImage;

	private int fruitScore;

	private boolean highscore;


	
	public ScoreBoard(GameWorld gameWorld, 
			int grassLeftStart, 
			int grassRightStart, 
			int gameWidth, 
			int gameHeight, //Actually this is where the ground starts
			int fruitDiameter) {
		
		this.gameWorld = gameWorld;
		this.gameWidth = gameWidth;
		this.gameHeight = gameHeight;
		this.grassLeftStart = grassLeftStart;		
		
		fallingFruit = new FallingScore[NUMBER_OF_FALLING_FRUIT];
		
		final int gap= fruitDiameter+10;
		
		for(int i=0; i<NUMBER_OF_FALLING_FRUIT; i++)
		{
			fallingFruit[i] = new FallingScore(fruitDiameter,fruitDiameter, (gameHeight/2), (grassLeftStart+(i*gap)+40), 1);
		}
		
		staticImage = new StaticImage[NUMBER_OF_FALLING_FRUIT];
		
		for(int i=0; i<NUMBER_OF_FALLING_FRUIT; i++) {
			staticImage[i] = new StaticImage((grassLeftStart+(i*gap)+40)+(fruitDiameter/4), (gameHeight/2)+(fruitDiameter/4), fruitDiameter/2, fruitDiameter/2);
		}

		
		onRestart(0, false);
	}
	
	public void update(float delta) {
		
		for(int i=0; i<fruitScore; i++) {
			fallingFruit[i].update(delta);
		}
		for(int i=0; i<fruitScore; i++) {
			if(fallingFruit[i].checkForOverflow()) {
				if(i<NUMBER_OF_FALLING_FRUIT-1)
				fallingFruit[i+1].onRestart(gameWorld.getBird().getFruitValue());
			}
		}
	
	}
	
	private void drawText(SpriteBatch batcher, String s, int x, int y) {
		AssetLoader.shadow.draw(batcher, s, x, y);
		AssetLoader.font.draw(batcher, s, x, y);
	}
	
	
	public void draw(SpriteBatch batcher, TextureRegion[] blurredFruit, TextureRegion star) {

		final int x = grassLeftStart + 40;
		
		drawText(batcher,"GAMEOVER", x, (gameHeight/2) - 150 );

		drawText(batcher,"SCORE: "+gameWorld.getScore(), x, (gameHeight/2) - 100 );

		if(highscore) {
			drawText(batcher,"NEW HIGHSCORE!", x, (gameHeight/2) - 80 );
		} else {
			drawText(batcher,"HIGHSCORE: "+AssetLoader.getHighScore(), x, (gameHeight/2) -80 );
		}

		drawText(batcher,"RATING:", x, (gameHeight/2) - 20 );

		drawText(batcher,"RETRY?", x, (gameHeight/2) + 100 );

		
		for(int i=0; i<NUMBER_OF_FALLING_FRUIT; i++)
		{
			staticImage[i].draw(batcher,star);
		}
		for(int i=0; i<NUMBER_OF_FALLING_FRUIT; i++)
		{
			fallingFruit[i].draw(batcher,blurredFruit);
		}	
		
	}
	
	
	public void onRestart(int fruitScore, boolean highscore) {
		
		this.highscore=highscore;
		
		for(int i=0; i<NUMBER_OF_FALLING_FRUIT; i++)
		{
			fallingFruit[i].reset();
		}
		
		this.fruitScore=fruitScore;
		
		if(fruitScore>0) {
			fallingFruit[0].onRestart(gameWorld.getBird().getFruitValue());
		}
		
	}
}
