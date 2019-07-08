package com.acodesmith.roshambo;

import com.acodesmith.roshambo.screens.LoadingScreen;
import com.acodesmith.roshambo.screens.MainMenuScreen;
import com.acodesmith.roshambo.screens.PlayScreen;
import com.acodesmith.roshambo.screens.SplashScreen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Application extends Game {

	public static final String TITLE = "Roshambo";
	public static final float VERSION = 1.1f;
	public static final int VIRTUAL_WIDTH = 1280;
	public static final int VIRTUAL_HEIGHT = 720;

	public static AssetManager Assets = new AssetManager();
	public static OrthographicCamera Camera = new OrthographicCamera();

	public LoadingScreen loadingScreen;
	public SplashScreen splashScreen;
	public MainMenuScreen mainMenuScreen;
	public PlayScreen playScreen;
	
	@Override
	public void create ()
	{
		Camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

		loadingScreen = new LoadingScreen(this);
		splashScreen = new SplashScreen(this);
		mainMenuScreen = new MainMenuScreen(this);
		playScreen = new PlayScreen(this);

		setScreen(loadingScreen);
	}

	@Override
	public void dispose ()
	{
		splashScreen.dispose();
	}

	@Override
	public void render ()
	{
		super.render();
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
		{
			Gdx.app.exit();
		}
	}
}
