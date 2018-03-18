package com.acodesmith.roshambo.screens;

import com.acodesmith.roshambo.Application;
import com.acodesmith.roshambo.AssetManager;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class SplashScreen implements Screen {

    private final Application app;
    private Stage stage;

    private Sound logoSound;

    public SplashScreen(final Application app) {
        this.app = app;
        this.stage = new Stage(new FitViewport(Application.VIRTUAL_WIDTH, Application.VIRTUAL_HEIGHT, app.camera));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        logoSound = Gdx.audio.newSound(Gdx.files.internal("sound/coin.ogg"));

        final Image companyLogo = new Image(AssetManager.getInstance().getTexture("img/zuzu.png"));
        companyLogo.setPosition(stage.getWidth() / 2 - companyLogo.getWidth() / 2, stage.getHeight() / 2 - companyLogo.getHeight() / 2 + 50);

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = AssetManager.getInstance().getFont("fonts/KGShePersisted.ttf");
        final Label companyLabel = new Label("Zuzu Studios", style);
        companyLabel.setPosition(stage.getWidth() / 2, companyLogo.getY() - 50, Align.center);
        companyLabel.setVisible(false);

        final Runnable showLabel = new Runnable() {
            @Override
            public void run() {
                companyLabel.setVisible(true);
            }
        };
        final Runnable fadeOutLabel = new Runnable() {
            @Override
            public void run() {
                companyLabel.addAction(fadeOut(0.75f));
            }
        };
        final Runnable transitionScreen = new Runnable() {
            @Override
            public void run() {
                app.setScreen(app.mainMenuScreen);
            }
        };

        companyLogo.addAction(sequence(
                alpha(0f),
                delay(0.5f),
                fadeIn(1f, Interpolation.pow2),
                delay(0.25f),
                run(showLabel),
                run(new Runnable() {
                    @Override
                    public void run() {
                        logoSound.play();
                    }
                }),
                delay(2.5f),
                run(fadeOutLabel),
                fadeOut(0.75f),
                run(transitionScreen)));

        stage.addActor(companyLogo);
        stage.addActor(companyLabel);
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
