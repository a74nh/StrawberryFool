package com.sleepware.GameObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class SpoonHead {
	
	private Rectangle imageRect, collisonRect;
	
	private Circle collisonEllipse1, collisonEllipse2;

	private final int headWidth;
	private final int headHeight;
	private final int barWidth;
	private final int barHeight;
	private final int barYOffset;
	private final boolean invertX;


	public SpoonHead(int headWidth, int headHeight, int barWidth, int barHeight, boolean invertX) {

		this.headWidth=headWidth;
		this.headHeight=headHeight;
		this.barWidth=barHeight;
		this.barHeight=barHeight;
		this.barYOffset=(headHeight-barHeight)/2;
		this.invertX=invertX;

		imageRect = new Rectangle();
		collisonRect = new Rectangle();
		collisonEllipse1 = new Circle();
		collisonEllipse2 = new Circle();
		
	}
	
	public void updateBoundingBoxes(float x, float y, boolean isFork) {
		
		imageRect.set(x, 
				y, 
				headWidth, 
				headHeight);
		
		if(isFork) {
			collisonRect.set(x, 
					y, 
					headWidth, 
					headHeight);
			
			collisonEllipse1.set(0,0,0); 
			collisonEllipse2.set(0,0,0); 
			
		}
		else if(invertX) {

			final float baseX = x+headWidth-barWidth;
			
			collisonRect.set(baseX, 
					y+barYOffset, 
					barWidth, 
					barHeight);
			
			collisonEllipse1.set(baseX-(headHeight/2), 
					y+(headHeight/2), 
					(headHeight)/2);
			
			collisonEllipse2.set(baseX-headHeight, 
					y+(headHeight/2), 
					(headHeight)/2);
			
		} else {
			
			collisonRect.set(x, 
					y+barYOffset, 
					barWidth, 
					barHeight);
			
			collisonEllipse1.set(x+barWidth+(headHeight/2), 
					y+(headHeight/2), 
					(headHeight)/2);
			
			collisonEllipse2.set(x+barWidth+headHeight, 
					y+(headHeight/2), 
					(headHeight)/2);
		}
	}

	public boolean collides(Bird bird) {
		
		return (Intersector.overlaps(bird.getBoundingCircle(), collisonRect)
				|| Intersector.overlaps(bird.getBoundingCircle(), collisonEllipse1)
				|| Intersector.overlaps(bird.getBoundingCircle(), collisonEllipse2));
	}

	public boolean pointYcollides(int screenY) {
		return (screenY>=imageRect.y && screenY<=imageRect.y+imageRect.height);
	}

	
	public void draw(SpriteBatch batcher, TextureRegion normalTexture) {
		batcher.draw(normalTexture, imageRect.x,imageRect.y,imageRect.width,imageRect.height);
	}

	public void drawCollisions(ShapeRenderer shapeRenderer) {
		
		shapeRenderer.rect(collisonRect.x, collisonRect.y, collisonRect.width, collisonRect.height);
		shapeRenderer.circle(collisonEllipse1.x, collisonEllipse1.y, collisonEllipse1.radius);
		shapeRenderer.circle(collisonEllipse2.x, collisonEllipse2.y, collisonEllipse2.radius);
	}



	
}
