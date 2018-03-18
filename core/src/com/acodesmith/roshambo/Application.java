package com.acodesmith.roshambo;

import com.acodesmith.roshambo.screens.LoadingScreen;
import com.acodesmith.roshambo.screens.MainMenuScreen;
import com.acodesmith.roshambo.screens.PlayScreen;
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
	public static final float VERSION = 1.0f;
	public static final int VIRTUAL_WIDTH = 1280;
	public static final int VIRTUAL_HEIGHT = 720;

	public OrthographicCamera camera;
	public SpriteBatch batch;
	public LoadingScreen loadingScreen;
	public SplashScreen splashScreen;
	public MainMenuScreen mainMenuScreen;
	public PlayScreen playScreen;
	
	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		batch = new SpriteBatch();
		loadingScreen = new LoadingScreen(this);
		splashScreen = new SplashScreen(this);
		mainMenuScreen = new MainMenuScreen(this);
		playScreen = new PlayScreen(this);
		this.setScreen(loadingScreen);
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
