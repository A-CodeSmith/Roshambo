package com.acodesmith.roshambo.screens;

import com.acodesmith.roshambo.Application;
import com.acodesmith.roshambo.GameRules;
import com.acodesmith.roshambo.cards.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class PlayScreen extends BaseScreen {

    private final Application app;

    private Music music;
    private Sound countdownSound;
    private Sound resultSound;

    private Card[] choices;
    private Card userChoice;
    private Card cpuChoice;
    private Image questionCard;

    private Table rootTable;

    public PlayScreen(Application app)
    {
        this.app = app;
        rootTable = new Table();
    }

    @Override
    public void show() {
        super.show();

        initAudio();
        initCards();

        Image backgroundImage = new Image(Application.Assets.<Texture>get("img/bg.png"));
        backgroundImage.setPosition((getWidth() - backgroundImage.getWidth())/2, 0);

        Label chooseLabel = new Label("Choose:", new LabelStyle(Application.Assets.get("fonts/GeosansLight.ttf"), null));

        rootTable.clear();
        rootTable.setFillParent(true);
        rootTable.center();
        rootTable.add(chooseLabel).colspan(3).expandY().row();
        rootTable.add(choices[0]).expandX();
        rootTable.add(choices[1]).expandX();
        rootTable.add(choices[2]).expandX();
        rootTable.row();
        rootTable.add().colspan(3).expandY();

        addActor(backgroundImage);
        addActor(rootTable);
    }

    private void initAudio()
    {
        music = Application.Assets.get("music/magicClockShop.ogg");
        music.setLooping(true);
        music.play();

        countdownSound = Application.Assets.get("sound/drumRoll.ogg");
    }

    private void initCards()
    {
        choices = new Card[]{ new PaperCard(), new RockCard(), new ScissorsCard()};
        for (Card card : choices)
        {
            card.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    onCardChosen((Card)event.getListenerActor());
                }
            });
        }

        questionCard = new Image(Application.Assets.<TextureAtlas>get("img/play.atlas").findRegion("question"));
        questionCard.setVisible(false);

        cpuChoice = CardFactory.CreateRandom();
        cpuChoice.setVisible(false);
    }

    private void onCardChosen(Card chosenCard)
    {
        rootTable.clearChildren();

        userChoice = chosenCard;
        userChoice.addAction(
                sequence(
                        moveTo(choices[0].getX(), choices[0].getY(), 0.75f),
                        run((this::showCountdown))
                )
        );

        questionCard.setPosition(choices[2].getX(), choices[2].getY());
        cpuChoice.setPosition(questionCard.getX(), questionCard.getY());

        addActor(userChoice);
        addActor(questionCard);
        addActor(cpuChoice);
    }

    private void onPlayAgain()
    {
        resultSound.stop();
        app.setScreen(app.playScreen);
    }

    private void onQuitToMenu()
    {
        resultSound.stop();
        app.setScreen(app.mainMenuScreen);
    }

    private void showCountdown()
    {
        music.stop();
        countdownSound.play();

        Label countdownLabel = new Label("RO", new LabelStyle(Application.Assets.get("fonts/Meatloaf.ttf"), null));
        countdownLabel.setPosition(getWidth()/2, getHeight()/2, Align.center);
        countdownLabel.setAlignment(Align.center);
        countdownLabel.setVisible(false);
        addActor(countdownLabel);

        float fadeInDuration = 0.25f;
        float countStepDuration = 1.5f;
        float revealDuration = 2.0f;

        questionCard.addAction(sequence(
                alpha(0f),
                Actions.show(),
                fadeIn(fadeInDuration)
        ));
        countdownLabel.addAction(sequence(
                delay(fadeInDuration),
                Actions.show(),
                delay(countStepDuration),
                run(() -> countdownLabel.setText("SHAM")),
                delay(countStepDuration),
                run(() -> {
                    countdownLabel.setText("BO!");
                    questionCard.setVisible(false);
                    cpuChoice.setVisible(true);
                }),
                delay(revealDuration),
                Actions.hide(),
                run(this::showResult)
        ));
    }

    private void showResult()
    {
        userChoice.setVisible(false);
        cpuChoice.setVisible(false);
        questionCard.setVisible(false);

        GameRules.GameResult gameResult = GameRules.DetermineResult(userChoice, cpuChoice);

        Image resultImage = new Image(Application.Assets.<TextureAtlas>get("img/play.atlas").findRegion(gameResult.RegionName));
        addActor(resultImage);
        resultImage.setPosition(getWidth()/2, getHeight()/2, Align.center);
        resultImage.setZIndex(1);

        resultSound = Application.Assets.get(gameResult.SoundName);
        resultSound.play();

        Label resultLabel = new Label(gameResult.Text, new LabelStyle(Application.Assets.get("fonts/Meatloaf.ttf"), null));

        LabelStyle menuItemStyle = new LabelStyle(Application.Assets.get("fonts/GeosansLight.ttf"),  null);
        Label playAgainLabel = new Label("Play Again", menuItemStyle);
        playAgainLabel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) { onPlayAgain();
            }
        });
        Label quitToMenuLabel = new Label("Quit to Menu", menuItemStyle);
        quitToMenuLabel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) { onQuitToMenu();
            }
        });

        rootTable.add().expandY().row();
        rootTable.add(resultLabel).row();
        rootTable.add().expandY().row();
        rootTable.add().expandY().row();
        rootTable.add(playAgainLabel).pad(10f).row();
        rootTable.add(quitToMenuLabel).pad(10f).row();
        rootTable.add().expandY().row();
        rootTable.add().expandY();
        rootTable.setZIndex(2);
    }
}
