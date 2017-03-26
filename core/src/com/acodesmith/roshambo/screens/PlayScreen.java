package com.acodesmith.roshambo.screens;

import com.acodesmith.roshambo.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.Random;

/**
 * Created by Sylace on 3/25/2017.
 */
public class PlayScreen implements Screen {

    private enum Shape {
        ROCK,
        PAPER,
        SCISSORS
    }
    private enum MatchResult {
        VICTORY,
        DEFEAT,
        DRAW
    }

    private final Application app;
    private final Stage stage;

    private Shape userChoice;

    public PlayScreen(Application app) {
        this.app = app;
        stage = new Stage(new FitViewport(Application.VIRTUAL_WIDTH, Application.VIRTUAL_HEIGHT, app.camera));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        final Image backgroundImage = new Image(app.assets.get("img/bg.png", Texture.class));
        backgroundImage.setPosition(stage.getWidth()/2f - backgroundImage.getWidth()/2, 0);

        final Label.LabelStyle chooseStyle = new Label.LabelStyle();
        chooseStyle.font = app.assets.get("fonts/GeosansLight.ttf");
        final Label chooseLabel = new Label("Choose:", chooseStyle);
        chooseLabel.setPosition(stage.getWidth()/2f, stage.getHeight()*0.85f, Align.center);

        // 10% 20% 10% 20% 10% 20% 10%
        final TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("img/play.atlas"));
        final ClickListener onShapeClick = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                userChoice = (Shape)event.getListenerActor().getUserObject();
                showResult();
            }
        };

        final Image rockImage = new Image(atlas.findRegion("rock"));
        rockImage.setPosition(stage.getWidth()*0.1f, stage.getHeight()*0.25f);
        rockImage.setUserObject(Shape.ROCK);
        rockImage.addListener(onShapeClick);

        final Image paperImage = new Image(atlas.findRegion("paper"));
        paperImage.setPosition(stage.getWidth()*0.4f, stage.getHeight()*0.25f);
        paperImage.setUserObject(Shape.PAPER);
        paperImage.addListener(onShapeClick);

        final Image scissorsImage = new Image(atlas.findRegion("scissors"));
        scissorsImage.setPosition(stage.getWidth()*0.7f, stage.getHeight()*0.25f);
        scissorsImage.setUserObject(Shape.SCISSORS);
        scissorsImage.addListener(onShapeClick);

        stage.addActor(backgroundImage);
        stage.addActor(chooseLabel);
        stage.addActor(rockImage);
        stage.addActor(paperImage);
        stage.addActor(scissorsImage);
    }

    private void showResult() {
        Random rng = new Random();
        Shape cpuChoice = Shape.values()[rng.nextInt(3)];

        MatchResult result = MatchResult.DRAW;
        switch (userChoice)
        {
            case ROCK:
                if (cpuChoice == Shape.ROCK) result = MatchResult.DRAW;
                if (cpuChoice == Shape.PAPER) result = MatchResult.DEFEAT;
                if (cpuChoice == Shape.SCISSORS) result = MatchResult.VICTORY;
                break;
            case PAPER:
                if (cpuChoice == Shape.ROCK) result = MatchResult.VICTORY;
                if (cpuChoice == Shape.PAPER) result = MatchResult.DRAW;
                if (cpuChoice == Shape.SCISSORS) result = MatchResult.DEFEAT;
                break;
            case SCISSORS:
                if (cpuChoice == Shape.ROCK) result = MatchResult.DEFEAT;
                if (cpuChoice == Shape.PAPER) result = MatchResult.VICTORY;
                if (cpuChoice == Shape.SCISSORS) result = MatchResult.DRAW;
                break;
        }

        System.out.println(String.format("User %s - Cpu %s - %s", userChoice, cpuChoice, result));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .1f, .1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        stage.draw();
    }

    private void update(float delta) {
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
