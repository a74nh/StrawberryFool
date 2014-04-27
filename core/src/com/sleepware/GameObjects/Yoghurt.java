package com.sleepware.GameObjects;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.sleepware.ZBHelpers.AssetLoader;

public class Yoghurt {

	private static final int NUMBER_OF_FRUIT = 5;
	
	private StaticImage solidWhite, white;
	private RotatedStaticImage fruit[];
	private int fruitId[];
	
	
	public Yoghurt(int x, int y, int width, int height, int fruitDiameter, int minX, int maxX) {
		
		solidWhite = new StaticImage(x,y, width, height);

		white = new StaticImage(0, y-5, width, 64);

		Random r = new Random();

		fruit = new RotatedStaticImage[NUMBER_OF_FRUIT];
		fruitId = new int[NUMBER_OF_FRUIT];
		
		
		final float offset = (maxX-minX)/NUMBER_OF_FRUIT;

		for(int i=0; i<NUMBER_OF_FRUIT; i++) {
			
			final int thisMinX = (int)(minX+(i*offset));
			final int thisMaxX = (int)(minX+((i+1)*offset));
			
			final int fruitX = r.nextInt(thisMaxX-thisMinX-fruitDiameter)+thisMinX;

			final int fruitY = y - 5 - r.nextInt(fruitDiameter*7/10);

			final int fruitRot = r.nextInt(360);

			fruit[i] = new RotatedStaticImage(fruitX, fruitY, fruitDiameter,fruitDiameter, fruitRot);
			
			fruitId[i] = r.nextInt(AssetLoader.NUMBER_OF_FRUIT);
		}
	}
	
	
	public void draw(SpriteBatch batcher, ShapeRenderer shapeRenderer,
			TextureRegion yoghurtImage, TextureRegion[] fruitImages) {
		
		shapeRenderer.begin(ShapeType.Filled);

		shapeRenderer.setColor(255 / 255.0f, 255 / 255.0f, 255 / 255.0f, 1);
		
		solidWhite.drawShape(shapeRenderer);

		shapeRenderer.end();
		
		batcher.begin();
	
		for(int i=0; i<NUMBER_OF_FRUIT; i++) {
			fruit[i].draw(batcher, fruitImages[fruitId[i]]);
		}
		
		white.draw(batcher,yoghurtImage);

		
		batcher.end();

		
		
		
	}


	
}
