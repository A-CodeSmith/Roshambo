package com.acodesmith.roshambo.screens;

import com.acodesmith.roshambo.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class MainMenuScreen extends BaseScreen {

    private final Application app;
    private Music music;

    public MainMenuScreen(Application app)
    {
        this.app = app;
    }

    @Override
    public void show() {
        super.show();

        music = app.Assets.get("music/boardwalkArcade.ogg");
        music.setLooping(true);
        music.play();

        Image background = new Image(Application.Assets.<Texture>get("img/bg.png"));
        background.setPosition((getWidth() - background.getWidth())/2, getHeight() - background.getHeight());
        background.addAction(alpha(0f));
        background.addAction(parallel(
                fadeIn(1f),
                moveBy(0f, background.getHeight() - getHeight(), 5f)
        ));

        Label titleLabel = new Label("ROSHAMBO", new LabelStyle(Application.Assets.get("fonts/Meatloaf.ttf"), null));

        Table table = new Table();
        table.setFillParent(true);
        table.center().top();
        table.add().expandY();
        table.row();
        table.add(titleLabel);
        table.row();
        table.add(buildMenu());
        table.row();
        table.add().expandY();
        table.row();
        table.add().expandY();

        addActor(background);
        addActor(table);
    }

    private void advanceToPlayScreen()
    {
        music.stop();
        app.setScreen(app.playScreen);
    }

    private Table buildMenu()
    {
        LabelStyle menuItemStyle = new LabelStyle(Application.Assets.get("fonts/GeosansLight.ttf"), null);

        Label playLabel = new Label("Play", menuItemStyle);
        playLabel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                advanceToPlayScreen();
            }
        });

        Label quitLabel = new Label("Quit", menuItemStyle);
        quitLabel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                quitGame();
            }
        });

        Table menu = new Table();
        menu.defaults().pad(10f);
        menu.add(playLabel);
        menu.row();
        menu.add(quitLabel);

        return menu;
    }

    private void quitGame()
    {
        Gdx.app.exit();
    }
}
