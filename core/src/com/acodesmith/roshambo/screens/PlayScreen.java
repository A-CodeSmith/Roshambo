package com.acodesmith.roshambo.screens;

import com.acodesmith.roshambo.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import java.util.EnumSet;
import java.util.Random;

/**
 * Created by Sylace on 3/25/2017.
 */
public class PlayScreen implements Screen {

    private enum Card {
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

    private Card userChoice;
    private Card cpuChoice;
    private TextureAtlas atlas;
    private Image rockImage;
    private Image paperImage;
    private Image scissorsImage;

    public PlayScreen(Application app) {
        this.app = app;
        stage = new Stage(new FitViewport(Application.VIRTUAL_WIDTH, Application.VIRTUAL_HEIGHT, app.camera));
    }

    @Override
    public void show() {
        stage.clear();
        Gdx.input.setInputProcessor(stage);

        final Image backgroundImage = new Image(app.assets.get("img/bg.png", Texture.class));
        backgroundImage.setPosition(stage.getWidth()/2f - backgroundImage.getWidth()/2, 0);

        final Label.LabelStyle chooseStyle = new Label.LabelStyle();
        chooseStyle.font = app.assets.get("fonts/GeosansLight.ttf");
        final Label chooseLabel = new Label("Choose:", chooseStyle);
        chooseLabel.setPosition(stage.getWidth()/2f, stage.getHeight()*0.85f, Align.center);

        atlas = app.assets.get("img/play.atlas");
        final ClickListener onShapeClick = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                chooseLabel.remove();
                onCardPick((Image)event.getListenerActor());
            }
        };

        // 10% 20% 10% 20% 10% 20% 10%
        rockImage = new Image(atlas.findRegion(Card.ROCK.name().toLowerCase()));
        rockImage.setPosition(stage.getWidth()*0.1f, stage.getHeight()*0.25f);
        rockImage.setName(Card.ROCK.name());
        rockImage.addListener(onShapeClick);

        paperImage = new Image(atlas.findRegion(Card.PAPER.name().toLowerCase()));
        paperImage.setPosition(stage.getWidth()*0.4f, stage.getHeight()*0.25f);
        paperImage.setName(Card.PAPER.name());
        paperImage.addListener(onShapeClick);

        scissorsImage = new Image(atlas.findRegion(Card.SCISSORS.name().toLowerCase()));
        scissorsImage.setPosition(stage.getWidth()*0.7f, stage.getHeight()*0.25f);
        scissorsImage.setName(Card.SCISSORS.name());
        scissorsImage.addListener(onShapeClick);

        stage.addActor(backgroundImage);
        stage.addActor(chooseLabel);
        stage.addActor(rockImage);
        stage.addActor(paperImage);
        stage.addActor(scissorsImage);
    }

    private void onCardPick(Image pickedCard) {
        userChoice = Card.valueOf(pickedCard.getName());

        EnumSet<Card> remainingCards = EnumSet.complementOf(EnumSet.of(userChoice));
        for (Card card : remainingCards)
        {
            stage.getRoot().findActor(card.name()).remove();
        }

        Image userCard = stage.getRoot().findActor(userChoice.name());
        userCard.addAction(
                sequence(
                        moveTo(stage.getWidth()*0.1f, stage.getHeight()*0.25f, 0.75f),
                        run((new Runnable() {
                            @Override
                            public void run() {
                                showCountdown();
                            }
                        })),
                        delay(5.25f),
                        Actions.hide()
                )
        );
    }

    private void showCountdown() {
        final Image questionCard = new Image(atlas.findRegion("question"));
        questionCard.setPosition(stage.getWidth()*0.7f, stage.getHeight()*0.25f);
        questionCard.setVisible(false);

        cpuChoice = Card.values()[(new Random()).nextInt(3)];
        final Image cpuCard = new Image(atlas.findRegion(cpuChoice.name().toLowerCase()));
        cpuCard.setPosition(stage.getWidth()*0.7f, stage.getHeight()*0.25f);
        cpuCard.setVisible(false);

        final Label countdownLabel = new Label("RO",
                new Label.LabelStyle(app.assets.get("fonts/Meatloaf.ttf", BitmapFont.class), null));
        countdownLabel.setPosition(stage.getWidth()/2, stage.getHeight()*0.5f, Align.center);
        countdownLabel.setAlignment(Align.center);
        countdownLabel.setVisible(false);

        countdownLabel.addAction(sequence(
                delay(0.25f),
                Actions.show(),
                delay(1.5f),
                run(new Runnable() {
                    @Override
                    public void run() { countdownLabel.setText("SHAM"); }
                }),
                delay(1.5f),
                run(new Runnable() {
                    @Override
                    public void run() { countdownLabel.setText("BO!"); }
                }),
                delay(2.0f),
                Actions.hide(),
                run(new Runnable() {
                    @Override
                    public void run() {
                        showResult();
                    }
                })
        ));
        questionCard.addAction(sequence(
                alpha(0f),
                Actions.show(),
                fadeIn(0.25f),
                delay(3f),
                Actions.hide()
        ));
        cpuCard.addAction(sequence(
                delay(3.25f),
                Actions.show(),
                delay(2.0f),
                Actions.hide()
        ));

        stage.addActor(cpuCard);
        stage.addActor(questionCard);
        stage.addActor(countdownLabel);
    }

    private void showResult() {
        String resultText = "";
        String resultImageRegion = "";
        switch (GetMatchResult())
        {
            case VICTORY:
                resultText = "VICTORY!";
                resultImageRegion = "victory";
                break;
            case DRAW:
                resultText = "DRAW!";
                resultImageRegion = "draw";
                break;
            case DEFEAT:
                resultText = "DEFEAT!";
                resultImageRegion = "defeat";
                break;
        }

        final Label resultLabel = new Label(resultText,
                new Label.LabelStyle(app.assets.get("fonts/Meatloaf.ttf", BitmapFont.class), null));
        resultLabel.setPosition(stage.getWidth()/2, stage.getHeight()*0.75f, Align.center);

        final Image resultImage = new Image(atlas.findRegion(resultImageRegion));
        resultImage.setPosition(stage.getWidth()/2, stage.getHeight()*0.50f, Align.center);

        final Label playAgainLabel = new Label("Play Again",
                new Label.LabelStyle(app.assets.get("fonts/GeosansLight.ttf", BitmapFont.class), null));
        playAgainLabel.setPosition(stage.getWidth()/2, stage.getHeight()*0.35f, Align.center);
        playAgainLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.playScreen);
            }
        });

        final Label quitToMenuLabel = new Label("Quit to Menu",
                new Label.LabelStyle(app.assets.get("fonts/GeosansLight.ttf", BitmapFont.class), null));
        quitToMenuLabel.setPosition(stage.getWidth()/2, stage.getHeight()*0.20f, Align.center);
        quitToMenuLabel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.mainMenuScreen);
            }
        });

        stage.addActor(resultImage);
        stage.addActor(resultLabel);
        stage.addActor(playAgainLabel);
        stage.addActor(quitToMenuLabel);
    }

    private MatchResult GetMatchResult() {
        MatchResult result = MatchResult.DRAW;
        switch (userChoice)
        {
            case ROCK:
                if (cpuChoice == Card.ROCK) result = MatchResult.DRAW;
                if (cpuChoice == Card.PAPER) result = MatchResult.DEFEAT;
                if (cpuChoice == Card.SCISSORS) result = MatchResult.VICTORY;
                break;
            case PAPER:
                if (cpuChoice == Card.ROCK) result = MatchResult.VICTORY;
                if (cpuChoice == Card.PAPER) result = MatchResult.DRAW;
                if (cpuChoice == Card.SCISSORS) result = MatchResult.DEFEAT;
                break;
            case SCISSORS:
                if (cpuChoice == Card.ROCK) result = MatchResult.DEFEAT;
                if (cpuChoice == Card.PAPER) result = MatchResult.VICTORY;
                if (cpuChoice == Card.SCISSORS) result = MatchResult.DRAW;
                break;
        }
        return result;
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
