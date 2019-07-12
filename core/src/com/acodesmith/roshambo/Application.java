package com.acodesmith.roshambo;

import com.acodesmith.roshambo.screens.*;
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

	public OrthographicCamera Camera = new OrthographicCamera();
	public ScreenManager ScreenManager = new ScreenManager(this);
	
	@Override
	public void create ()
	{
		Camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		ScreenManager.setScreen(GameScreen.Loading);
	}

	@Override
	public void dispose ()
	{
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
