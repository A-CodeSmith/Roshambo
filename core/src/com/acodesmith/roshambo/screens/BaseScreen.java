package com.acodesmith.roshambo.screens;

import com.acodesmith.roshambo.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public abstract class BaseScreen extends Stage implements Screen {

    BaseScreen()
    {
        super( new FitViewport(Application.VIRTUAL_WIDTH, Application.VIRTUAL_HEIGHT, ((Application)Gdx.app.getApplicationListener()).Camera ));
    }

    @Override public void hide() {}
    @Override public void pause() {}

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.act(delta);
        super.draw();
    }

    @Override
    public void resize(int width, int height)
    {
        getViewport().update(width, height, false);
    }

    @Override public void resume() {}

    @Override
    public void show()
    {
        clear();
        Gdx.input.setInputProcessor(this);
    }
}
