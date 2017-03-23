package com.acodesmith.roshambo.screens;

import com.acodesmith.roshambo.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by Sylace on 3/22/2017.
 */
public class MainMenuScreen implements Screen {

    private final Application app;
    private Stage stage;

    public MainMenuScreen(Application app){
        this.app = app;
        this.stage = new Stage(new FitViewport(Application.VIRTUAL_WIDTH, Application.VIRTUAL_HEIGHT, app.camera));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = app.titleFont;
        final Label title = new Label("ROSHAMBO", style);
        title.setPosition(stage.getWidth() / 2, stage.getHeight() - 120, Align.center);

        stage.addActor(title);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .1f, .1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        stage.draw();
    }

    public void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
