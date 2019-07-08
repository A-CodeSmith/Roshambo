package com.acodesmith.roshambo.screens;

import com.acodesmith.roshambo.Application;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class SplashScreen extends BaseScreen {

    private final Application app;
    private Label companyLabel;
    private Image companyLogo;

    public SplashScreen(Application app)
    {
        this.app = app;
    }

    @Override
    public void show()
    {
        super.show();

        companyLogo = new Image(app.Assets.<Texture>get("img/zuzu.png"));
        companyLogo.setVisible(false);

        LabelStyle labelStyle = new LabelStyle(app.Assets.get("fonts/KGShePersisted.ttf"), null);
        companyLabel = new Label("Zuzu Studios", labelStyle);
        companyLabel.setVisible(false);

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(companyLogo);
        table.row();
        table.add(companyLabel);

        addActor(table);
        addAction(sequence(
                delay(0.5f),
                run(this::showCompanyLogo),
                delay(1.25f),
                run(this::showCompanyLabel),
                delay(3.25f),
                run(this::advanceToMainMenu)
        ));
    }

    private void advanceToMainMenu()
    {
        app.setScreen(app.mainMenuScreen);
    }

    private void showCompanyLabel()
    {
        app.Assets.<Sound>get("sound/coin.ogg").play();
        companyLabel.setVisible(true);
        companyLabel.addAction(sequence(
                delay(2.5f),
                fadeOut(0.75f)
        ));
    }

    private void showCompanyLogo()
    {
        companyLogo.setVisible(true);
        companyLogo.addAction(sequence(
                alpha(0f),
                fadeIn(1f, Interpolation.pow2),
                delay(2.75f),
                fadeOut(0.75f)
        ));
    }
}
