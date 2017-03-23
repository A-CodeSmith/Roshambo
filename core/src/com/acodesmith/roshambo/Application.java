package com.acodesmith.roshambo;

import com.acodesmith.roshambo.screens.LoadingScreen;
import com.acodesmith.roshambo.screens.MainMenuScreen;
import com.acodesmith.roshambo.screens.SplashScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
//import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Application extends Game {

	public static final String TITLE = "Roshambo";
	public static final float VERSION = 0.1f;
	public static final int VIRTUAL_WIDTH = 960;
	public static final int VIRTUAL_HEIGHT = 600;

	public OrthographicCamera camera;
	public SpriteBatch batch;

	public BitmapFont logoFont;
	public BitmapFont titleFont;

	public AssetManager assets;

	public LoadingScreen loadingScreen;
	public SplashScreen splashScreen;
	public MainMenuScreen mainMenuScreen;
	
	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		batch = new SpriteBatch();

		assets = new AssetManager();
		initFonts();

		loadingScreen = new LoadingScreen(this);
		splashScreen = new SplashScreen(this);
		mainMenuScreen = new MainMenuScreen(this);
		this.setScreen(loadingScreen);
	}

	private void initFonts() {
		FreeTypeFontGenerator logoFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/KGShePersisted.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter logoFontParams = new FreeTypeFontGenerator.FreeTypeFontParameter();

		logoFontParams.size = 36;
		logoFontParams.color = Color.WHITE;
		logoFont = logoFontGenerator.generateFont(logoFontParams);

		FreeTypeFontGenerator titleFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Meatloaf.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter titleFontParams = new FreeTypeFontGenerator.FreeTypeFontParameter();

		titleFontParams.size = 256;
		titleFontParams.color = Color.WHITE;
		titleFont = titleFontGenerator.generateFont(titleFontParams);
	}

	@Override
	public void render () {
		super.render();
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
			Gdx.app.exit();
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		splashScreen.dispose();
	}
}
