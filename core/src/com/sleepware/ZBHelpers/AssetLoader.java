package com.sleepware.ZBHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {
	

	public static final int NUMBER_OF_FRUIT= 4;

	private static Texture texture, logoTexture, ball1Texture, starsTexture, spoonTexture, forkTexture, stripeTexture, yoghurtTexture, fingerTexture;
	public static TextureRegion logo, zbLogo, bg, grass, bird, birdDown,
			birdUp, skullUp, skullDown, bar, playButtonUp, playButtonDown,
			ready, gameOver, highScore, scoreboard, star, noStar, retry, ball1, ball2, ball3, stars, forkup, forkdown, yoghurt, finger;
	public static Texture[] fruitTexture; //, blurredFruitTexture;
	public static TextureRegion[] fruit; //, blurredFruit;

	public static Texture buttonsTexture;
	public static TextureRegion[] buttons;

	//public static Animation birdAnimation;
	public static Sound dead, flap, coin, fall;
	public static BitmapFont font, shadow, whiteFont;
	private static Preferences prefs;

	private static void loadFruit(int index, String fruitname) {
				
		fruitTexture[index]=new Texture(Gdx.files.internal(fruitname+"small.png"));
		fruitTexture[index].setFilter(TextureFilter.Linear, TextureFilter.Linear);
		fruit[index] = new TextureRegion(fruitTexture[index], 0, 0, 32, 32);
		
		//blurredFruitTexture[index]=new Texture(Gdx.files.internal(fruitname+"small_blurred.png"));
		//blurredFruitTexture[index].setFilter(TextureFilter.Linear, TextureFilter.Linear);
		//blurredFruit[index] = new TextureRegion(blurredFruitTexture[index], 0, 0, 32, 32);
	}
	
	private static void loadButton(int x, int y) {
						
		buttons[0] = new TextureRegion(buttonsTexture, x*200, y*100, 100, 50);
		buttons[1] = new TextureRegion(buttonsTexture, (x*200), (y*100)+50, 100, 50);
		buttons[0].flip(false, true);
		buttons[1].flip(false, true);
	}
	
	public static void load() {

		logoTexture = new Texture(Gdx.files.internal("logo.png"));
		logoTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		logo = new TextureRegion(logoTexture, 0, 0, 512, 114);

		texture = new Texture(Gdx.files.internal("texture.png"));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		playButtonUp = new TextureRegion(texture, 0, 83, 29, 16);
		playButtonDown = new TextureRegion(texture, 29, 83, 29, 16);
		playButtonUp.flip(false, true);
		playButtonDown.flip(false, true);

		ready = new TextureRegion(texture, 59, 83, 34, 7);
		ready.flip(false, true);

		retry = new TextureRegion(texture, 59, 110, 33, 7);
		retry.flip(false, true);
		
		gameOver = new TextureRegion(texture, 59, 92, 46, 7);
		gameOver.flip(false, true);

		scoreboard = new TextureRegion(texture, 111, 83, 97, 37);
		scoreboard.flip(false, true);

		star = new TextureRegion(texture, 152, 70, 10, 10);
		noStar = new TextureRegion(texture, 165, 70, 10, 10);

		star.flip(false, true);
		noStar.flip(false, true);

		highScore = new TextureRegion(texture, 59, 101, 48, 7);
		highScore.flip(false, true);

		zbLogo = new TextureRegion(texture, 0, 55, 135, 24);
		zbLogo.flip(false, true);

		//bg = new TextureRegion(texture, 0, 0, 136, 43);
		//bg.flip(false, true);


		birdDown = new TextureRegion(texture, 136, 0, 17, 12);
		birdDown.flip(false, true);

		bird = new TextureRegion(texture, 153, 0, 17, 12);
		bird.flip(false, true);

		birdUp = new TextureRegion(texture, 170, 0, 17, 12);
		birdUp.flip(false, true);

		//TextureRegion[] birds = { birdDown, bird, birdUp };
		//birdAnimation = new Animation(0.06f, birds);
		//birdAnimation.setPlayMode(Animation.LOOP_PINGPONG);



		spoonTexture = new Texture(Gdx.files.internal("spoon.png"));
		forkTexture = new Texture(Gdx.files.internal("fork.png"));
		

		skullUp = new TextureRegion(spoonTexture, 62, 0, 65, 32);
		// Create by flipping existing skullUp
		skullDown = new TextureRegion(skullUp);
		skullDown.flip(true, false);

		bar = new TextureRegion(spoonTexture, 0, 10, 3, 12);
				
				//11, 96, 10, 3);
		bar.flip(false, true);
		
		forkup = new TextureRegion(forkTexture, 62, 0, 65, 32);
		forkdown = new TextureRegion(forkup);
		forkdown.flip(true, false);		
		
		
		//ball1Texture = new Texture(Gdx.files.internal("blueball1.png"));
		//ball1Texture = new Texture(Gdx.files.internal("applesmall.png"));
		ball1Texture = new Texture(Gdx.files.internal("strawberrysmall.png"));
		ball1Texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		ball1 = new TextureRegion(ball1Texture, 0, 0, 32, 32);

		/*
		ball2Texture = new Texture(Gdx.files.internal("blueball2.png"));
		ball2Texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		ball2 = new TextureRegion(ball2Texture, 0, 0, 64, 64);
		
		ball3Texture = new Texture(Gdx.files.internal("blueball3.png"));
		ball3Texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		ball3 = new TextureRegion(ball3Texture, 0, 0, 64, 64);
		*/
		
		//TextureRegion[] balls = { ball1 }; //, ball2, ball3 };
		//birdAnimation = new Animation(0.06f, balls);
		//birdAnimation.setPlayMode(Animation.LOOP_PINGPONG);
		
		fruitTexture = new Texture[NUMBER_OF_FRUIT];
		fruit = new TextureRegion[NUMBER_OF_FRUIT];
		//blurredFruitTexture = new Texture[NUMBER_OF_FRUIT];
		//blurredFruit = new TextureRegion[NUMBER_OF_FRUIT];
		
		loadFruit(0,"strawberry");
		loadFruit(1,"apple");
		loadFruit(2,"lemon");
		loadFruit(3,"grapes");
	
		buttons = new TextureRegion[2];

		buttonsTexture = new Texture(Gdx.files.internal("buttons.png"));

		loadButton(0, 0);
		//loadButton(ButtonType.CONTINUE, 0, 1);
		//loadButton(ButtonType.START, 0, 2);
		//loadButton(ButtonType.OPTIONS, 0, 3);
		
			
		fingerTexture = new Texture(Gdx.files.internal("finger.png"));
		fingerTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		finger = new TextureRegion(fingerTexture, 0, 0, 46, 64);
		finger.flip(false, true);	
		
		//starsTexture = new Texture(Gdx.files.internal("stars.png"));
		starsTexture = new Texture(Gdx.files.internal("icecream.png"));
		starsTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		stars = new TextureRegion(starsTexture, 0, 0, 408, 272);
		stars.flip(false, true);		
		
		

		stripeTexture = new Texture(Gdx.files.internal("stripe.png"));
		stripeTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		grass = new TextureRegion(stripeTexture, 0, 23, 64, 512-23);
		//grass.flip(false, true);
		
		
		yoghurtTexture = new Texture(Gdx.files.internal("yoghurt.png"));
		yoghurtTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		yoghurt = new TextureRegion(yoghurtTexture, 0, 0, 408, 272);
		yoghurt.flip(false, true);		
		

		dead = Gdx.audio.newSound(Gdx.files.internal("dead.wav"));
		flap = Gdx.audio.newSound(Gdx.files.internal("flap.wav"));
		coin = Gdx.audio.newSound(Gdx.files.internal("coin.wav"));
		fall = Gdx.audio.newSound(Gdx.files.internal("fall.wav"));

		font = new BitmapFont(Gdx.files.internal("text.fnt"));
		font.setScale(.25f, -.25f);

		whiteFont = new BitmapFont(Gdx.files.internal("whitetext.fnt"));
		whiteFont.setScale(.1f, -.1f);

		shadow = new BitmapFont(Gdx.files.internal("shadow.fnt"));
		shadow.setScale(.25f, -.25f);

		// Create (or retrieve existing) preferences file
		prefs = Gdx.app.getPreferences("ZombieBird");

		if (!prefs.contains("highScore")) {
			prefs.putInteger("highScore", 0);
		}
	}

	public static void setHighScore(int val) {
		prefs.putInteger("highScore", val);
		prefs.flush();
	}

	public static int getHighScore() {
		return prefs.getInteger("highScore");
	}

	public static void setHighLevel(int val) {
		prefs.putInteger("highLevel", val);
		prefs.flush();
	}

	public static int getHighLevel() {
		return prefs.getInteger("highLevel");
	}
	
	public static void dispose() {
		// We must dispose of the texture when we are finished.
		texture.dispose();

		// Dispose sounds
		dead.dispose();
		flap.dispose();
		coin.dispose();

		font.dispose();
		shadow.dispose();
	}

}