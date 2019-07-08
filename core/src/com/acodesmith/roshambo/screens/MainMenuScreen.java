package com.acodesmith.roshambo.screens;

import com.acodesmith.roshambo.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

/**
 * Created by Sylace on 3/22/2017.
 */
public class MainMenuScreen implements Screen {

    private final Application app;

    private Stage stage;
    private Music bgMusic;

    public MainMenuScreen(Application app){
        this.app = app;
        this.stage = new Stage(new FitViewport(Application.VIRTUAL_WIDTH, Application.VIRTUAL_HEIGHT, Application.Camera));
    }

    @Override
    public void show() {
        stage.clear();
        Gdx.input.setInputProcessor(stage);

        bgMusic = Application.Assets.get("music/boardwalkArcade.ogg");
        bgMusic.setLooping(true);
        bgMusic.play();

        final Image backgroundImage = new Image(Application.Assets.<Texture>get("img/bg.png"));
        backgroundImage.setPosition(stage.getWidth()/2 - backgroundImage.getWidth()/2, stage.getHeight() - backgroundImage.getHeight());
        backgroundImage.addAction(alpha(0f));
        backgroundImage.addAction(parallel(
                fadeIn(1f),
                moveBy(0f, backgroundImage.getHeight() - stage.getHeight(), 5f)
        ));
        stage.addActor(backgroundImage);

        final Label titleLabel = new Label("ROSHAMBO", new LabelStyle(Application.Assets.<BitmapFont>get("fonts/Meatloaf.ttf"), null));
        titleLabel.setPosition(stage.getWidth()/2, stage.getHeight() - 120, Align.center);
        stage.addActor(titleLabel);

        final LabelStyle menuItemStyle = new LabelStyle(Application.Assets.<BitmapFont>get("fonts/GeosansLight.ttf"),  null);
        final Label playLabel = new Label("Play", menuItemStyle);
        playLabel.setPosition(stage.getWidth()/2 - 100, stage.getHeight()/2 + 20, Align.left);
        playLabel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                bgMusic.stop();
                app.setScreen(app.playScreen);
            }
        });
        stage.addActor(playLabel);

        final Label quitLabel = new Label("Quit", menuItemStyle);
        quitLabel.setPosition(stage.getWidth()/2 - 100, stage.getHeight()/2 - 80, Align.left);
        quitLabel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
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
