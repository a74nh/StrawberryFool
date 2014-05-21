package com.sleepware.GameObjects;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.sleepware.ZBHelpers.AssetLoader;

public class StaticBackgroundFruit {

	private static final int NUMBER_OF_FRUIT = 5;
	
	private RotatedStaticImage fruit[];
	private int fruitId[];
	
	
	public StaticBackgroundFruit(int x, int y, int fruitDiameter, int minX, int maxX) {
		
		Random r = new Random();

		fruit = new RotatedStaticImage[NUMBER_OF_FRUIT];
		fruitId = new int[NUMBER_OF_FRUIT];
		
		
		final float offset = (maxX-minX)/NUMBER_OF_FRUIT;

		for(int i=0; i<NUMBER_OF_FRUIT; i++) {
			
			final int thisMinX = (int)(minX+(i*offset));
			final int thisMaxX = (int)(minX+((i+1)*offset));
			
			final int fruitX = r.nextInt(thisMaxX-thisMinX-fruitDiameter)+thisMinX;

			final int fruitY = y - (fruitDiameter*2/10) - r.nextInt(fruitDiameter*4/10);

			final int fruitRot = r.nextInt(360);

			fruit[i] = new RotatedStaticImage(fruitX, fruitY, fruitDiameter,fruitDiameter, fruitRot);
			
			fruitId[i] = r.nextInt(AssetLoader.NUMBER_OF_FRUIT);
		}
	}
	
	
	public void draw(SpriteBatch batcher, TextureRegion yoghurtImage, Fruit[] fruitImages) {
		
		for(int i=0; i<NUMBER_OF_FRUIT; i++) {
			fruit[i].draw(batcher, fruitImages[fruitId[i]].getImage());
		}		
	}


	
}
