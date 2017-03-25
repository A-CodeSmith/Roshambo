package com.acodesmith.roshambo.screens;

import com.acodesmith.roshambo.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

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

        final Image backgroundImage = new Image(app.assets.get("img/bg.png", Texture.class));
        backgroundImage.setPosition(stage.getWidth()/2 - backgroundImage.getWidth()/2, stage.getHeight() - backgroundImage.getHeight());
        backgroundImage.addAction(alpha(0f));
        backgroundImage.addAction(parallel(
                fadeIn(1f),
                moveBy(0f, backgroundImage.getHeight() - stage.getHeight(), 5f)
        ));

        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = app.assets.get("fonts/Meatloaf.ttf");

        final Label titleLabel = new Label("ROSHAMBO", titleStyle);
        titleLabel.setPosition(stage.getWidth()/2, stage.getHeight() - 120, Align.center);

        Label.LabelStyle menuItemStyle = new Label.LabelStyle();
        menuItemStyle.font = app.assets.get("fonts/GeosansLight.ttf");

        final Label playLabel = new Label("Play", menuItemStyle);
        playLabel.setPosition(stage.getWidth()/2 - 100, stage.getHeight()/2 + 20, Align.left);
        playLabel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //app.setScreen(app.playScreen);
            }
        });

        final Label quitLabel = new Label("Quit", menuItemStyle);
        quitLabel.setPosition(stage.getWidth()/2 - 100, stage.getHeight()/2 - 80, Align.left);
        quitLabel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        stage.addActor(backgroundImage);
        stage.addActor(titleLabel);
        stage.addActor(playLabel);
        stage.addActor(quitLabel);
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
