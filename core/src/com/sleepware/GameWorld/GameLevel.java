package com.sleepware.GameWorld;

import com.sleepware.ZBHelpers.AssetLoader;


public class GameLevel {

	class LevelParam {
		private final boolean ALWAYS_MAX_LEVEL=false;
		//private final boolean ALWAYS_MAX_LEVEL=true;
		
		public final float min, max, inc;
		
		public LevelParam(int min, int max, float inc) {
			this.min=min;
			this.max=max;
			this.inc=inc;
		}
		
		public float getCurrent(int level) {
			float current = min + (inc*level);
			
			if(inc>0) {
				if (current>max || ALWAYS_MAX_LEVEL)
					return max;
			} else {
				if (current<max || ALWAYS_MAX_LEVEL)
					return max;
			}
			return current;
		}
	}
	
	private final LevelParam HORIZONTAL_PIPE_GAP = new LevelParam(145,100,-3); //Num Levels = 15
	private final LevelParam MAX_SCROLL_SPEED = new LevelParam(79,150,3); //Num Levels = 24
	private final LevelParam MAX_BIRD_POSITION = new LevelParam(0,130,5); //Num Levels = 26
	private final LevelParam MAX_BIRD_SPEED = new LevelParam(50,100,2); // Num Levels = 25
	private final LevelParam MAX_BIRD_MAX_MOVEMENT = new LevelParam(79,92,0.5f); // Num Levels = 26 (make sure no more than MAX_BIRD_POSITION)
	
			
	public enum GameType {
		STORY, ARCADE;
	}
	
	public enum BirdMotion {
		LINEAR, SINE, NONE, DYING, DEAD;
		
		public static final int numBirdMotions = BirdMotion.values().length;

		public static BirdMotion getEnum(int value) {
		     return BirdMotion.values()[value % numBirdMotions];
		}
	}
	
	private int progress;
	private int level;
	private GameWorld gameWorld;
	GameType gameType;
	
	public GameLevel(GameWorld gameWorld) {
		this.gameWorld=gameWorld;
		gameType=GameType.ARCADE;
		onRestart();
	}
	
	//Number of pipes in a level
	public int getMaxProgress() {
		return 5 + level;
	}
	
	//Gap between the two spoons on one row
	public float getHorizontalPipeGap() {
		return HORIZONTAL_PIPE_GAP.getCurrent(level);
	}
	
	//Speed at which walls and spoons scroll
	public float getScrollSpeed() {
		return -MAX_SCROLL_SPEED.getCurrent(level);
	}

    //How far down the screen the bird is
	public float getBirdYPosition() {
		return MAX_BIRD_POSITION.getCurrent(level);
	}
	
	//Max movement left and right of the center screen
	public float getBirdMaxMovement() {
		return MAX_BIRD_MAX_MOVEMENT.getCurrent(level);
	}
	
	//Speed in which the bird moves to and fro
	public float getBirdSpeed() {
		return MAX_BIRD_SPEED.getCurrent(level);
	}
	
	//Way in which the bird moves to and fro
	public BirdMotion getBirdMotion() {
		if(level==0) return BirdMotion.NONE;
		
		if(level%10>=5) return BirdMotion.SINE;
		
		return BirdMotion.LINEAR;
	}
	
	
	public int getLevel() {
		return level;
	}
	
	public void incLevel() {
		level++;
	}
	
	public void incProgress() {
		progress++;
		
		if (progress>getMaxProgress()) {
			progress=0;
			gameWorld.endLevel();
		}
	}

	public void setProgress(int progress) {
		this.progress+=progress;
	}

	public void setGameType(GameType gameType) {
		this.gameType=gameType;
		progress=0;
	}
	
	public void onRestart() {
		switch(gameType) {
		case ARCADE:
			level=1;
			break;
		case STORY:
			level=AssetLoader.getHighLevel();
			break;
		default:
			break;
		
		}
		progress=0;
	}

	public int getDeathVelocity() {
		return 120;
	}



}
