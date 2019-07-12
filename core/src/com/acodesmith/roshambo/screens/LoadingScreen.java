package com.acodesmith.roshambo.screens;

import com.acodesmith.roshambo.Application;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class LoadingScreen extends BaseScreen {

    private final Application app;
    private float borderSize;
    private boolean loadingComplete;
    private float progress;
    private float progressBarHeight;
    private float progressBarWidth;
    private float progressBarX;
    private float progressBarY;
    private ShapeRenderer shapeRenderer;

    public LoadingScreen(Application app)
    {
        this.app = app;
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(app.Camera.combined);
        progressBarWidth = app.Camera.viewportWidth * .75f;
        progressBarHeight = app.Camera.viewportHeight * .1f;
        progressBarX = (app.Camera.viewportWidth - progressBarWidth) / 2;
        progressBarY = (app.Camera.viewportHeight - progressBarHeight) / 2;
        borderSize = 10f;
        loadingComplete = false;
    }

    @Override
    public void dispose()
    {
        this.shapeRenderer.dispose();
        super.dispose();
    }

    @Override
    public void render(float delta)
    {
        super.render(delta);
        updateProgress();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.rect(
                progressBarX,
                progressBarY,
                progressBarWidth,
                progressBarHeight
        );
        shapeRenderer.setColor(Color.LIGHT_GRAY);
        shapeRenderer.rect(
                progressBarX + borderSize,
                progressBarY + borderSize,
                (progressBarWidth - borderSize*2) * progress,
                progressBarHeight - borderSize*2
        );
        shapeRenderer.end();

        if (loadingComplete)
            advanceToSplashScreen();
    }

    @Override
    public void show()
    {
        super.show();
        progress = 0f;
        queueAssets();
    }

    private void advanceToSplashScreen()
    {
        app.ScreenManager.setScreen(GameScreen.Splash);
    }

    private void queueAssets()
    {
        queueFonts();
        queueImages();
        queueMusic();
        queueSounds();
    }

    private void queueFonts()
    {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        Application.Assets.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        Application.Assets.setLoader(BitmapFont.class, new FreetypeFontLoader(resolver));

        FreeTypeFontLoaderParameter logoParams = new FreeTypeFontLoaderParameter();
        logoParams.fontFileName = "fonts/KGShePersisted.ttf";
        logoParams.fontParameters.size = 48;
        logoParams.fontParameters.color = Color.WHITE;
        Application.Assets.load("fonts/KGShePersisted.ttf", BitmapFont.class, logoParams);

        FreeTypeFontLoaderParameter titleParams = new FreeTypeFontLoaderParameter();
        titleParams.fontFileName = "fonts/Meatloaf.ttf";
        titleParams.fontParameters.size = 256;
        titleParams.fontParameters.color = Color.WHITE;
        titleParams.fontParameters.borderWidth = 5f;
        titleParams.fontParameters.borderColor = Color.BLACK;
        Application.Assets.load("fonts/Meatloaf.ttf", BitmapFont.class, titleParams);

        FreeTypeFontLoaderParameter mainParams = new FreeTypeFontLoaderParameter();
        mainParams.fontFileName = "fonts/GeosansLight.ttf";
        mainParams.fontParameters.size = 72;
        mainParams.fontParameters.color = Color.WHITE;
        mainParams.fontParameters.borderWidth = 2f;
        mainParams.fontParameters.borderColor = Color.BLACK;
        Application.Assets.load("fonts/GeosansLight.ttf", BitmapFont.class, mainParams);
    }

    private void queueImages()
    {
        Application.Assets.load("img/zuzu.png", Texture.class);
        Application.Assets.load("img/bg.png", Texture.class);
        Application.Assets.load("img/play.atlas", TextureAtlas.class);
    }

    private void queueMusic()
    {
        Application.Assets.load("music/boardwalkArcade.ogg", Music.class);
        Application.Assets.load("music/magicClockShop.ogg", Music.class);
    }

    private void queueSounds()
    {
        Application.Assets.load("sound/coin.ogg", Sound.class);
        Application.Assets.load("sound/drumRoll.ogg", Sound.class);
        Application.Assets.load("sound/cheer.ogg", Sound.class);
        Application.Assets.load("sound/awww.ogg", Sound.class);
        Application.Assets.load("sound/murmur.ogg", Sound.class);
    }

    private void updateProgress()
    {
        // Intentionally slow the loading bar for demonstration purposes
        float latestProgress = Application.Assets.getProgress();
        progress = MathUtils.lerp(progress, latestProgress, .1f);
        if (Application.Assets.update() && progress >= latestProgress - 0.001f)
        {
            loadingComplete = true;
        }
    }
}
