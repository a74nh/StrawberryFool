package com.sleepware.GameObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class RotatedStaticImage extends StaticImage {

	
	private int rotation;

	public RotatedStaticImage(int x, int y, int width, int height, int rotation) {
		super(x, y, width, height);
		this.rotation=rotation;
		
	}
	
	
	public void draw(SpriteBatch batcher, TextureRegion texture) {
		
		batcher.draw(texture,
				boundingRectangle.x,
				boundingRectangle.y,
				boundingRectangle.width / 2.0f, 
				boundingRectangle.height / 2.0f,
				boundingRectangle.width, 
				boundingRectangle.height, 
			    1, 
			    1,
			    rotation);
	}

}
