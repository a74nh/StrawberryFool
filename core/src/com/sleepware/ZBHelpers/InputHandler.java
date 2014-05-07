package com.sleepware.ZBHelpers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.sleepware.GameObjects.Bird;
import com.sleepware.GameObjects.ButtonHandler;
import com.sleepware.GameObjects.ScrollHandler;
import com.sleepware.GameObjects.Title;
import com.sleepware.GameWorld.GameWorld;


public class InputHandler implements InputProcessor {
	private ScrollHandler myScrollHandler;
	private GameWorld myWorld;
	private Bird myBird;
	private Title title;
	private ButtonHandler buttonhandler;


	private float scaleFactorX;
	private float scaleFactorY;

	public InputHandler(GameWorld myWorld, float screenWidth, float gameWidth, float screenHeight, float gameHeight) {
		this.myWorld = myWorld;
		myScrollHandler = myWorld.getScrollHandler();

		myBird = myWorld.getBird();
		title = myWorld.getTitle();
		buttonhandler = myWorld.getButtonhandler();

		this.scaleFactorX = screenWidth / gameWidth;
		this.scaleFactorY = screenHeight / gameHeight;
	}

	
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		screenX = scaleX(screenX);
		screenY = scaleY(screenY);

		switch(myWorld.getState()) {
		
		case GAMEOVER:
		case HIGHSCORE:
			myWorld.restart();
			break;
			
		case PAUSED:
		case OPTIONS:
			return buttonhandler.touchDown(screenX, screenY);
			
		case MENU:
			if(buttonhandler.touchDown(screenX, screenY)) {
				return true;
			}
			if(myBird.onClick(screenX,screenY)) {
				return true;
			}
			return title.onClick(screenX,screenY);
			
		case READY:
			myWorld.start();
			break;
			
		case RUNNING:
			myScrollHandler.onClick(screenX,screenY);
			break;
			
		default:
			break;
		}

		return true;
	}
	

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		screenX = scaleX(screenX);
		screenY = scaleY(screenY);

		switch(myWorld.getState()) {
		
		case MENU:
		case PAUSED:
		case OPTIONS:
			return buttonhandler.touchUp(screenX, screenY);
			
		default:
			break;
		}

		return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.BACK || keycode == Keys.ESCAPE){
			
			switch(myWorld.getState()) {
			
			case GAMEOVER:
			case HIGHSCORE:
			case PAUSED:
			case READY:
			case OPTIONS:
				myWorld.quitToMenu();
				return true;
				
			case MENU:
				Gdx.app.exit();
				break;
				
			case RUNNING:
				myWorld.pause();
				return true;
								
			default:
				break;
			}
			
	    }
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	private int scaleX(int screenX) {
		return (int) (screenX / scaleFactorX);
	}

	private int scaleY(int screenY) {
		return (int) (screenY / scaleFactorY);
	}

}
