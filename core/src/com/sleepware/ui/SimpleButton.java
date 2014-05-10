package com.sleepware.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.sleepware.GameWorld.GameWorld;
import com.sleepware.GameWorld.GameWorld.GameState;
import com.sleepware.ZBHelpers.AssetLoader;

public class SimpleButton {

	final private int x, y, width, height;

	private TextureRegion buttonUp;
	private TextureRegion buttonDown;

	private Rectangle bounds;

	private boolean isPressed = false;

	private ActionFunction actionFunction;

	private GameState gamestate;

	private String title;

	final private boolean clickable;

	public interface ActionFunction {
	    void onClick(SimpleButton button, GameWorld world);
	    void onStateChange(SimpleButton button);
	}
	
	
	public SimpleButton(int x, int y, int width, int height,
			TextureRegion buttonUp, TextureRegion buttonDown, ActionFunction actionFunction, GameState gamestate, String title, boolean clickable) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.buttonUp = buttonUp;
		this.buttonDown = buttonDown;
		this.actionFunction=actionFunction;
		this.gamestate=gamestate;
		this.title=title;
		this.clickable=clickable;

		bounds = new Rectangle(x, y, width, height);
	}

	public boolean isClicked(int screenX, int screenY) {
		return (!clickable || bounds.contains(screenX, screenY));
	}

	
	public void draw(SpriteBatch batcher) {
		if (isPressed) {
			if(buttonDown!=null)
				batcher.draw(buttonDown, x, y, width, height);
		} else {
			if(buttonUp!=null)
				batcher.draw(buttonUp, x, y, width, height);
		}
		AssetLoader.drawText(batcher,title,x+5,y+5);
	}

	public boolean isTouchDown(int screenX, int screenY) {

		if (bounds.contains(screenX, screenY)) {
			isPressed = true;
			return true;
		}

		return false;
	}

	public boolean isTouchUp(int screenX, int screenY) {
		
		// It only counts as a touchUp if the button is in a pressed state.
		if (bounds.contains(screenX, screenY) && isPressed) {
			isPressed = false;
			return true;
		}
		
		// Whenever a finger is released, we will cancel any presses.
		isPressed = false;
		return false;
	}

	
	public void doAction(GameWorld world) {
		if(actionFunction!=null)
			actionFunction.onClick(this, world);
	}
	
	public void onStateChange() {
		if(actionFunction!=null)
			actionFunction.onStateChange(this);
	}
	
	
	public GameState getGameState() {
		return gamestate;
	}
	
	public void setTitle(String title) {
		this.title=title;
	}
}
