package com.sleepware.GameObjects;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.sleepware.GameObjects.Spoon.Collides;
import com.sleepware.GameWorld.GameLevel;
import com.sleepware.GameWorld.GameLevel.BirdMotion;
import com.sleepware.GameWorld.GameWorld;
import com.sleepware.ZBHelpers.AssetLoader;

public class Bird {

	private enum LevelState {
		NORMAL, FINISHING_LEVEL, SPIN
	}

	//private GameWorld gameWorld;
	private GameLevel level;

	//Keep our own runtime to prevent sudden movement jumps
	private float runTime;
	
	private Vector2 position;
	private Vector2 velocity;

	private float rotation;
	
	private final int diameter;
	private final int maxMovement;

	final private float originalX;
	final private int offsetY;
	
	private boolean isAlive;

	private Circle boundingCircle;
	
	private LevelState levelState;
	
	BirdMotion birdMotion;

	boolean movingUp=false;
	
	private float remainingSpin;
	private float previousX;
	
	private Random r;

	private int deathVelocity;

	private int fruitValue;
	
	public Bird(GameWorld gameWorld, int x, int gameHeight, int diameter, int maxMovement) {
		//this.gameWorld = gameWorld;
		level = gameWorld.getLevel();
		this.diameter = diameter;
		this.maxMovement=maxMovement;
		originalX = x - (diameter/2);
		offsetY = (gameHeight/2) - 200;
		
		position = new Vector2(originalX,0);
		velocity = new Vector2(0, 0);
		boundingCircle = new Circle();
		
		fruitValue = 0;
		
		r = new Random();
		
		onRestart();
	}

	public void move(float delta) {
		
		switch(birdMotion) {
			
		case LINEAR:
			position.add(velocity.cpy().scl(delta));

			rotation -= (( position.x - previousX)*4);
			
			if(position.x>=originalX+maxMovement) {
				position.x=originalX+maxMovement;
				velocity.x*=-1;
			} else if(position.x<=originalX-maxMovement) {
				position.x=originalX-maxMovement;
				velocity.x*=-1;
			}
			
			break;
			
		case SINE:
			float movementFromX = + ((float)Math.sin(velocity.x*runTime/50) * maxMovement);

			position.x= originalX + movementFromX;

			rotation -= (( position.x - previousX)*3);
			
			break;
		
		case DYING:
			position.x += (velocity.x*delta);

			rotation += 1 * delta;
			if (rotation > 90) {
				rotation = 90;
			}

			break;

		case NONE:
		case DEAD:
		default:
			//Do nothing
			break;

		}
		
	}

    public void update(float delta) {
    	runTime += delta;
    	
		previousX = position.x;

		switch(levelState) {
		case NORMAL:
		case FINISHING_LEVEL:
			move(delta);
			break;
		case SPIN:
		default:
			break;
		}
		    			
		switch(levelState) {

		case FINISHING_LEVEL:
			
			if((previousX<originalX && position.x>=originalX)
				||  (previousX>originalX && position.x<=originalX)	) {
				//We moved to our new position
				levelState=LevelState.SPIN;
				remainingSpin = r.nextInt(360) + 360;
			}
			
			break;
			
		case SPIN:
			final float newrotation =  480 * delta;
			
			remainingSpin -= newrotation;
			rotation += newrotation;
			
			
			if(remainingSpin<=0) {
				remainingSpin=0;
				levelState=LevelState.NORMAL;
				runTime=0;
				setLevelAttributes();
			}
			break;
			
		default:
			break;
		
		}
		
		
		if(rotation<0) {
			rotation=360+rotation;
		} else if (rotation>360) {
			rotation=rotation-360;
		}
		
		
		boundingCircle.set(position.x + (diameter/2), position.y +  (diameter/2),  (diameter/2));
	}

	private void setLevelAttributes() {
		birdMotion =  level.getBirdMotion();
		position.y = offsetY + level.getBirdYPosition();
		velocity.x = level.getBirdSpeed();
		deathVelocity = level.getDeathVelocity();
	}

	public void updateReady(float runTime) {
	}
	
	public void dying(Collides collision) {
		isAlive = false;
		birdMotion=BirdMotion.DYING;
		if(collision==Collides.LEFT) {
			velocity.x = -deathVelocity;
		} else {
			velocity.x = deathVelocity;
		}
	}

	public void dead() {
		birdMotion=BirdMotion.DEAD;
		velocity.y=0;
	}

	public void onRestart() {
		rotation = 0;
		position.x = originalX;
		velocity.x = 0;
		velocity.y = 0;
		isAlive = true;
		runTime=0;
		levelState=LevelState.NORMAL;
		boundingCircle.set(position.x + (diameter/2), position.y +  (diameter/2),  (diameter/2));

		levelState=LevelState.SPIN;
		remainingSpin = r.nextInt(360) + 360;
		
		setLevelAttributes();
	}

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}
	
	public float getDiameter() {
		return diameter;
	}
	
	public float getRotation() {
		return rotation;
	}

	public Circle getBoundingCircle() {
		return boundingCircle;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void nextLevel() {
		levelState=LevelState.FINISHING_LEVEL;
		if(birdMotion==BirdMotion.NONE) {
			levelState=LevelState.SPIN;
		}
	}
	
	public void draw(SpriteBatch batcher, Fruit[] fruit) {
		
		batcher.draw(fruit[fruitValue].getImage(),
				 position.x,
			     position.y,
			     diameter / 2.0f, 
			     diameter / 2.0f,
			     diameter, 
			     diameter, 
			     1, 
			     1,
			     rotation);
	}

	public boolean collidesSide(int grassLeftStart, int grassRightStart) {
		return (position.x+(diameter/2)>=grassRightStart ||
				position.x+(diameter/2)<=grassLeftStart);
	}


	public boolean onClick(float x, float y) {
		
		if(boundingCircle.contains(x,y)) {
			fruitValue++;
			if(fruitValue>=AssetLoader.NUMBER_OF_FRUIT) {
				fruitValue=0;
			}
			return true;
		}
		return false;
	}
	
	
	public int getFruitValue() {
		return fruitValue;
	}
	
}
