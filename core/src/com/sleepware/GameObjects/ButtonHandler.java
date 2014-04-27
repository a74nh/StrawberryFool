package com.sleepware.GameObjects;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sleepware.GameWorld.GameWorld;
import com.sleepware.GameWorld.GameLevel.GameType;
import com.sleepware.GameWorld.GameWorld.GameState;
import com.sleepware.ZBHelpers.AssetLoader;
import com.sleepware.ui.SimpleButton;
import com.sleepware.ui.SimpleButton.ActionFunction;

public class ButtonHandler {

	private GameWorld myWorld;

	private List<SimpleButton> menuButtons;
	

	public ButtonHandler(GameWorld myWorld, int gameWidth, int gameHeight) {

		this.myWorld = myWorld;
		
		menuButtons = new ArrayList<SimpleButton>();
		
		final int topButton = gameHeight/2;
		
		
		/**************************************************************************/
		/* MAIN MENU */
		/*************************************************************************/
				
		int buttonY = topButton;
		addButton("START", (gameWidth / 2), buttonY, GameState.MENU, new ActionFunction() {
			@Override
			public void onClick(SimpleButton button, GameWorld world) {
				world.ready(GameType.STORY);	
			}

			@Override
			public void onStateChange(SimpleButton button) {
				if(AssetLoader.getHighLevel()>0) {
					button.setTitle("CONTINUE");
				} else {
					button.setTitle("START");
				}
			}
		});
			
		buttonY+= 25 + 5;
		addButton("ARCADE", (gameWidth / 2), buttonY, GameState.MENU, new ActionFunction() {
			@Override
			public void onClick(SimpleButton button, GameWorld world) {
				world.ready(GameType.ARCADE);	
			}

			@Override
			public void onStateChange(SimpleButton button) {				
			}
		});
		
				
		buttonY+= 25 + 5;
		addButton("OPTIONS", (gameWidth / 2), buttonY, GameState.MENU, new ActionFunction() {
			@Override
			public void onClick(SimpleButton button, GameWorld world) {
				world.options();	
			}

			@Override
			public void onStateChange(SimpleButton button) {				
			}
		});
		
		
		/**************************************************************************/
		/* OPTIONS MENU */
		/*************************************************************************/
				
		buttonY = topButton;
		addButton("RESET", (gameWidth / 2), buttonY, GameState.OPTIONS, new ActionFunction() {
			
			private int numClicked = 0;
			
			@Override
			public void onClick(SimpleButton button, GameWorld world) {
				switch(numClicked) {
				case 0:
					numClicked++;
					button.setTitle("CONFIRM");
					break;
					
				case 1:
					numClicked++;
					button.setTitle("CLEARED");	
					AssetLoader.setHighScore(0);
					AssetLoader.setHighLevel(0);
				
				default:
					break;
				}
			}

			@Override
			public void onStateChange(SimpleButton button) {
				numClicked=0;
				button.setTitle("RESET");
			}
		});
				
		buttonY+= 25 + 5;
		addButton("FX OFF", (gameWidth / 2), buttonY, GameState.OPTIONS, new ActionFunction() {
			@Override
			public void onClick(SimpleButton button, GameWorld world) {
				if(AssetLoader.getSound()) {
					AssetLoader.setSound(false);
					button.setTitle("FX ON");
				} else {
					AssetLoader.setSound(true);
					button.setTitle("FX OFF");
				}
			}

			@Override
			public void onStateChange(SimpleButton button) {
				if(AssetLoader.getSound()) {
					button.setTitle("FX OFF");
				} else {
					button.setTitle("FX ON");
				}
			}
		});
		
		
		buttonY+= 25 + 5;
		addButton("BACK", (gameWidth / 2), buttonY, GameState.OPTIONS, new ActionFunction() {
			@Override
			public void onClick(SimpleButton button, GameWorld world) {
				world.quitOptions();	
			}

			@Override
			public void onStateChange(SimpleButton button) {				
			}
		});
		
		
		/**************************************************************************/
		/* PAUSE MENU */
		/*************************************************************************/
				
		buttonY = (gameHeight/10)*4;
		addButton("CONTINUE", (gameWidth / 2), buttonY, GameState.PAUSED, new ActionFunction() {
			@Override
			public void onClick(SimpleButton button, GameWorld world) {
				world.unpause();
			}

			@Override
			public void onStateChange(SimpleButton button) {
			}
		});
				
		buttonY+= 25 + 5;
		addButton("QUIT", (gameWidth / 2), buttonY, GameState.PAUSED, new ActionFunction() {
			@Override
			public void onClick(SimpleButton button, GameWorld world) {
				world.quitToMenu();
			}

			@Override
			public void onStateChange(SimpleButton button) {
			}
		});		
		

		/**************************************************************************/
		/* GAMEOVER */
		/*************************************************************************/
				
		buttonY = (gameHeight/10)*4;
		addButton("GAME OVER", (gameWidth / 2), buttonY, GameState.GAMEOVER, new ActionFunction() {
			@Override
			public void onClick(SimpleButton button, GameWorld world) {
			}

			@Override
			public void onStateChange(SimpleButton button) {
			}
		});
		
		buttonY+= (25 + 5)*3;
		addButton("RETRY?", (gameWidth / 2), buttonY, GameState.GAMEOVER, new ActionFunction() {
			@Override
			public void onClick(SimpleButton button, GameWorld world) {
			}

			@Override
			public void onStateChange(SimpleButton button) {
			}
		});
		
		

		/**************************************************************************/
		/* HIGHSCORE */
		/*************************************************************************/
				
		buttonY = (gameHeight/10)*4;
		addButton("HIGHSCORE!", (gameWidth / 2), buttonY, GameState.HIGHSCORE, new ActionFunction() {
			@Override
			public void onClick(SimpleButton button, GameWorld world) {
			}

			@Override
			public void onStateChange(SimpleButton button) {
			}
		});
		
		buttonY+= (25 + 5)*3;
		addButton("RETRY?", (gameWidth / 2), buttonY, GameState.HIGHSCORE, new ActionFunction() {
			@Override
			public void onClick(SimpleButton button, GameWorld world) {
			}

			@Override
			public void onStateChange(SimpleButton button) {
			}
		});
		
		/**************************************************************************/
		/* READY */
		/*************************************************************************/
				
		buttonY = (gameHeight/10)*4;
		addButton("READY...", (gameWidth / 2), buttonY, GameState.READY, new ActionFunction() {
			@Override
			public void onClick(SimpleButton button, GameWorld world) {
			}

			@Override
			public void onStateChange(SimpleButton button) {
			}
		});
		
	}
		

	private void addButton(String title, int x, int y, GameState gamestate, ActionFunction action) {
		
		final int width = 75;
		
		SimpleButton button = new SimpleButton(
				x - (width/2),
				y,
				width,
				25, 
				AssetLoader.buttons[0],
				AssetLoader.buttons[1],
				action,
				gamestate,
				title);
		
		menuButtons.add(button);
	}


	public boolean touchDown(int screenX, int screenY) {
		
		for (SimpleButton menuButton : menuButtons) {
			if(menuButton.getGameState()==myWorld.getState()) {
				if(menuButton.isTouchDown(screenX, screenY)) {
				return true;
				}
			}
		}
		return false;
	}
	
	
	public boolean touchUp(int screenX, int screenY) {

		for (SimpleButton menuButton : menuButtons) {
			if(menuButton.getGameState()==myWorld.getState()) {
				if(menuButton.isTouchUp(screenX, screenY)) {
					menuButton.doAction(myWorld);
					return true;
				}
			}
		}
		return false;
	}
	
	public void draw(SpriteBatch batcher) {

		for (SimpleButton button : menuButtons) {
			if(button.getGameState()==myWorld.getState()) {
				button.draw(batcher);
			}
		}
	}
	
	public void onStateChange() {

		for (SimpleButton button : menuButtons) {
			button.onStateChange();
		}
	}
}
