package com.acodesmith.roshambo.screens;

import com.acodesmith.roshambo.Application;
import com.acodesmith.roshambo.AssetManager;
import com.acodesmith.roshambo.cards.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import java.util.EnumSet;
import java.util.Random;

/**
 * Created by Sylace on 3/25/2017.
 */
public class PlayScreen implements Screen {

    private final Application app;
    private final AssetManager assets;
    private final Stage stage;

    private float stage_xMiddle;
    private float stage_yMiddle;
    private float cards_xSize;
    private float cards_xPadding;
    private float cards_yPos;
    private float font_yPos;
    private float menu_yPadding;

    private Music bgMusic;
    private Sound drumRollSound;
    private Sound awwwSound;
    private Sound cheerSound;
    private Sound murmurSound;
    private Sound resultSound;

    private Card[] choices;
    private Card userChoice;
    private Card cpuChoice;

    public PlayScreen(Application app) {
        this.app = app;
        this.assets = AssetManager.getInstance();
        stage = new Stage(new FitViewport(Application.VIRTUAL_WIDTH, Application.VIRTUAL_HEIGHT, app.camera));
    }

    @Override
    public void show() {
        stage.clear();
        Gdx.input.setInputProcessor(stage);

        bgMusic = Gdx.audio.newMusic(Gdx.files.internal("music/magicClockShop.ogg"));
        bgMusic.setLooping(true);
        bgMusic.play();

        drumRollSound = Gdx.audio.newSound(Gdx.files.internal("sound/drumRoll.ogg"));
        awwwSound = Gdx.audio.newSound(Gdx.files.internal("sound/awww.ogg"));
        cheerSound = Gdx.audio.newSound(Gdx.files.internal("sound/cheer.ogg"));
        murmurSound = Gdx.audio.newSound(Gdx.files.internal("sound/murmur.ogg"));

        stage_xMiddle = stage.getWidth() / 2f;
        stage_yMiddle = stage.getHeight() / 2f;

        // 10% 20% 10% 20% 10% 20% 10%
        cards_xSize = stage.getWidth() * 0.2f;
        cards_xPadding = stage.getWidth() * 0.1f;

        cards_yPos = stage.getHeight() * 0.25f;
        font_yPos = stage.getHeight() * 0.8f;
        menu_yPadding = stage.getHeight() * 0.15f;

        final Image backgroundImage = new Image(assets.getTexture("img/bg.png"));
        backgroundImage.setPosition(stage_xMiddle - backgroundImage.getWidth()/2, 0);
        stage.addActor(backgroundImage);

        final Label chooseLabel = new Label("Choose:", new LabelStyle(assets.getFont("fonts/GeosansLight.ttf"), null));
        chooseLabel.setPosition(stage_xMiddle, font_yPos, Align.center);
        stage.addActor(chooseLabel);

        final ClickListener onCardClick = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                chooseLabel.remove();
                onCardChosen((Card)event.getListenerActor());
            }
        };

        userChoice = null;
        cpuChoice = null;
        choices = new Card[]{ new PaperCard(), new RockCard(), new ScissorsCard()};
        for (int index = 0; index < choices.length; ++index )
        {
            Card card = choices[index];
            card.setPosition((cards_xSize * index) + (cards_xPadding * (index + 1)), cards_yPos);
            card.addListener(onCardClick);
            stage.addActor(card);
        }
    }

    private void onCardChosen(Card chosenCard)
    {
        for (Card card : choices)
        {
            if (card.getName() != chosenCard.getName())
                card.remove();
        }
        userChoice = chosenCard;
        userChoice.addAction(
                sequence(
                        moveTo(cards_xPadding, cards_yPos, 0.75f),
                        run((new Runnable() {
                            @Override
                            public void run() {
                                showCountdown();
                            }
                        }))
                )
        );
    }

    private void showCountdown()
    {
        bgMusic.stop();

        final float lastCard_xPos = ((choices.length - 1) * cards_xSize) + (choices.length * cards_xPadding);
        final Image questionCard = new Image(assets.getRegionFromAtlas("question"));
        questionCard.setPosition(lastCard_xPos, cards_yPos);
        questionCard.setVisible(false);
        stage.addActor(questionCard);

        cpuChoice = null;
        try
        {
            cpuChoice = CardFactory.CreateRandom();
        }
        catch (Exception ex)
        {
            cpuChoice = new RockCard();
        }
        cpuChoice.setPosition(questionCard.getX(), questionCard.getY());
        cpuChoice.setVisible(false);
        stage.addActor(cpuChoice);

        final Label countdownLabel = new Label("RO", new LabelStyle(assets.getFont("fonts/Meatloaf.ttf"), null));
        countdownLabel.setPosition(stage_xMiddle, stage_yMiddle, Align.center);
        countdownLabel.setAlignment(Align.center);
        countdownLabel.setVisible(false);
        stage.addActor(countdownLabel);

        float totalDuration = 5.25f;
        float fadeInDuration = 0.25f;
        float countStepDuration = 1.5f;
        float revealDuration = 2.0f;

        userChoice.addAction(sequence(
                delay(totalDuration),
                Actions.hide()
        ));
        questionCard.addAction(sequence(
                alpha(0f),
                Actions.show(),
                fadeIn(fadeInDuration),
                delay(totalDuration - revealDuration - fadeInDuration),
                Actions.hide()
        ));
        cpuChoice.addAction(sequence(
                delay(totalDuration - revealDuration),
                Actions.show(),
                delay(revealDuration),
                Actions.hide()
        ));
        countdownLabel.addAction(sequence(
                run(new Runnable() {
                    @Override
                    public void run() {
                        drumRollSound.play();
                    }
                }),
                delay(fadeInDuration),
                Actions.show(),
                delay(countStepDuration),
                run(new Runnable() {
                    @Override
                    public void run() { countdownLabel.setText("SHAM"); }
                }),
                delay(countStepDuration),
                run(new Runnable() {
                    @Override
                    public void run() { countdownLabel.setText("BO!"); }
                }),
                delay(revealDuration),
                Actions.hide(),
                run(new Runnable() {
                    @Override
                    public void run() { showResult(); }
                }),
                run(new Runnable() {
                    @Override
                    public void run() { resultSound.play(); }
                })
        ));
    }

    private void showResult()
    {
        final AssetManager assets = AssetManager.getInstance();
        String resultText = "";
        Image resultImage = null;

        if (userChoice.equals(cpuChoice))
        {
            resultText = "DRAW!";
            resultImage = new Image(assets.getRegionFromAtlas("draw"));
            resultSound = murmurSound;
        }
        else if (userChoice.isCounteredBy(cpuChoice))
        {
            resultText = "DEFEAT!";
            resultImage = new Image(assets.getRegionFromAtlas("defeat"));
            resultSound = awwwSound;
        }
        else
        {
            resultText = "VICTORY!";
            resultImage = new Image(assets.getRegionFromAtlas("victory"));
            resultSound = cheerSound;
        }

        //resultSound.play();

        final Label resultLabel = new Label(resultText, new LabelStyle(assets.getFont("fonts/Meatloaf.ttf"), null));
        resultLabel.setPosition(stage_xMiddle, font_yPos, Align.center);
        stage.addActor(resultLabel);

        resultImage.setPosition(stage_xMiddle, stage_yMiddle, Align.center);
        stage.addActor(resultImage);

        final LabelStyle menuItemStyle = new LabelStyle(assets.getFont("fonts/GeosansLight.ttf"),  null);
        final Label playAgainLabel = new Label("Play Again", menuItemStyle);
        playAgainLabel.setPosition(stage_xMiddle, stage_yMiddle - menu_yPadding, Align.center);
        playAgainLabel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) { onPlayAgain();
            }
        });
        stage.addActor(playAgainLabel);

        final Label quitToMenuLabel = new Label("Quit to Menu", menuItemStyle);
        quitToMenuLabel.setPosition(stage_xMiddle, stage_yMiddle - (menu_yPadding * 2), Align.center);
        quitToMenuLabel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) { onQuitToMenu();
            }
        });
        stage.addActor(quitToMenuLabel);
    }

    public void onPlayAgain()
    {
        resultSound.stop();
        app.setScreen(app.playScreen);
    }

    public void onQuitToMenu()
    {
        resultSound.stop();
        app.setScreen(app.mainMenuScreen);
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
        bgMusic.dispose();
        drumRollSound.dispose();
        cheerSound.dispose();
        awwwSound.dispose();
        murmurSound.dispose();
        resultSound = null;
    }
}
