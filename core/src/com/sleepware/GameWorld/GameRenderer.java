package com.sleepware.GameWorld;

import java.util.List;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.sleepware.GameObjects.Bird;
import com.sleepware.GameObjects.ButtonHandler;
import com.sleepware.GameObjects.Fruit;
import com.sleepware.GameObjects.Hud;
import com.sleepware.GameObjects.ScoreBoard;
import com.sleepware.GameObjects.ScrollHandler;
import com.sleepware.GameObjects.StaticImage;
import com.sleepware.GameObjects.Title;
import com.sleepware.GameObjects.Yoghurt;
import com.sleepware.TweenAccessors.Value;
import com.sleepware.TweenAccessors.ValueAccessor;
import com.sleepware.ZBHelpers.AssetLoader;


public class GameRenderer {
	
	private int gameWidth;
	private int gameHeight;
	
	private GameWorld myWorld;
	private GameLevel level;

	private OrthographicCamera cam;
	private ShapeRenderer shapeRenderer;

	private SpriteBatch batcher;

	private int midPointX;
	private int midPointY;
	private int scoreX;

	// Game Objects
	private Bird bird;
	private ScrollHandler scroller;
	private StaticImage background;
	private Yoghurt yoghurt;
	private Hud hud;
	private Title title;
	private ButtonHandler buttonhandler;
	private ScoreBoard scoreBoard;

	// Game Assets
	private TextureRegion bg, grassTex, skullUp, skullDown, bar,
			zbLogo, noStar, forkup, forkdown, yoghurtImage ;
	private Animation fingerAnimation;
	private Fruit[] fruit;

	// Tween stuff
	private TweenManager manager;
	private Value alpha = new Value();

	// Buttons
	//private List<SimpleButton> menuButtons;
	private Color transitionColor;
	private final int minX;
	private final int maxX;


	public GameRenderer(GameWorld world, int gameWidth, int gameHeight, int minX, int maxX) {
		
		this.gameWidth=gameWidth;
		this.gameHeight=gameHeight;
		this.minX=minX;
		this.maxX=maxX;
		myWorld = world;
		level = world.getLevel();

		midPointX= gameWidth/2;
		midPointY = gameHeight/2;
		scoreX = (gameWidth/10) * 9;
		
		//this.menuButtons = ((InputHandler) Gdx.input.getInputProcessor()).getMenuButtons();

		cam = new OrthographicCamera();
		cam.setToOrtho(true, gameWidth, gameHeight);

		batcher = new SpriteBatch();
		batcher.setProjectionMatrix(cam.combined);
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(cam.combined);

		initGameObjects();
		initAssets();

		transitionColor = new Color();
		prepareTransition(255, 255, 255, .5f);
	}

	private void initGameObjects() {
		bird = myWorld.getBird();
		scroller = myWorld.getScroller();
		background = myWorld.getBackground();
		yoghurt = myWorld.getYoghurt();
		hud = myWorld.getHud();
		title = myWorld.getTitle();
		buttonhandler = myWorld.getButtonhandler();
		scoreBoard = myWorld.getScoreBoard();
	}

	private void initAssets() {
		bg = AssetLoader.stars;
		grassTex = AssetLoader.grass;
		skullUp = AssetLoader.skullUp;
		skullDown = AssetLoader.skullDown;
		bar = AssetLoader.bar;
		zbLogo = AssetLoader.zbLogo;
		noStar = AssetLoader.noStar;
		forkup = AssetLoader.forkup;
		forkdown = AssetLoader.forkdown;
		fruit = AssetLoader.fruit;
		yoghurtImage = AssetLoader.yoghurt;
		fingerAnimation = AssetLoader.fingerAnimation;
	}



	public void render(float delta, float runTime) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shapeRenderer.begin(ShapeType.Filled);

		// Draw Background colour
		shapeRenderer.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
		shapeRenderer.rect(0, 0, gameHeight, midPointY + 66);

		shapeRenderer.end();

		batcher.begin();
		background.draw(batcher,bg);

		scroller.draw(batcher,bar,skullUp,skullDown,forkup,forkdown,fruit);
		
		bird.draw(batcher,fruit);
		
		switch(myWorld.getState()) {
		
		case RUNNING:
			hud.draw(batcher,delta);
			break;
			
		case MENU:
		case OPTIONS:
			title.draw(batcher,bar,skullUp,skullDown,fingerAnimation.getKeyFrame(runTime),fruit[bird.getFruitValue()]);
			buttonhandler.draw(batcher);
			break;
			
		case READY:
			buttonhandler.draw(batcher);
			break;
		
		case PAUSED:
			hud.draw(batcher,delta);
			buttonhandler.draw(batcher);
			break;
		
		case GAMEOVER:
		case HIGHSCORE:
			buttonhandler.draw(batcher);
			scoreBoard.draw(batcher, fruit, noStar);
			break;
			
		default:
			break;
		}
		
		scroller.drawFg(batcher,grassTex);
		
		batcher.end();

		yoghurt.draw(batcher, shapeRenderer, yoghurtImage, fruit);

		
		/*
		shapeRenderer.begin(ShapeType.Filled);
		Gdx.gl.glEnable(GL10.GL_BLEND);
		shapeRenderer.setColor(125 / 255.0f, 125 / 255.0f, 125 / 255.0f, 0.5f);
		//scroller.drawCollisions(shapeRenderer);
		shapeRenderer.circle(bird.getBoundingCircle().x, bird.getBoundingCircle().y, bird.getBoundingCircle().radius);
		shapeRenderer.end();
		*/

		drawTransition(delta);

	}


	public void prepareTransition(int r, int g, int b, float duration) {
		transitionColor.set(r / 255.0f, g / 255.0f, b / 255.0f, 1);
		alpha.setValue(1);
		Tween.registerAccessor(Value.class, new ValueAccessor());
		manager = new TweenManager();
		Tween.to(alpha, -1, duration).target(0).ease(TweenEquations.easeOutQuad).start(manager);
	}

	private void drawTransition(float delta) {
		if (alpha.getValue() > 0) {
			manager.update(delta);
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(transitionColor.r, transitionColor.g,
					transitionColor.b, alpha.getValue());
			shapeRenderer.rect(0, 0, gameWidth, gameHeight);
			shapeRenderer.end();
			Gdx.gl.glDisable(GL20.GL_BLEND);

		}
	}

}
