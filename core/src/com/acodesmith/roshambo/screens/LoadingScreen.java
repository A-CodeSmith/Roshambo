package com.acodesmith.roshambo.screens;

import com.acodesmith.roshambo.Application;
import com.acodesmith.roshambo.AssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

import java.util.concurrent.TimeUnit;

/**
 * Created by Sylace on 3/18/2017.
 */
public class LoadingScreen implements Screen {

    private final Application app;
    private final AssetManager assets;

    private ShapeRenderer shapeRenderer;
    private float progress;

    public LoadingScreen(Application app)
    {
        this.app = app;
        this.assets = AssetManager.getInstance();
        this.shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {
        this.progress = 0f;
        shapeRenderer.setProjectionMatrix(app.camera.combined);
        queueAssets();
    }

    private void queueAssets() {
        queueFonts();
        queueImages();
    }

    private void queueFonts() {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        assets.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        assets.setLoader(BitmapFont.class, new FreetypeFontLoader(resolver));

        FreetypeFontLoader.FreeTypeFontLoaderParameter logoParams = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        logoParams.fontFileName = "fonts/KGShePersisted.ttf";
        logoParams.fontParameters.size = 48;
        logoParams.fontParameters.color = Color.WHITE;
        assets.load("fonts/KGShePersisted.ttf", BitmapFont.class, logoParams);

        FreetypeFontLoader.FreeTypeFontLoaderParameter titleParams = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        titleParams.fontFileName = "fonts/Meatloaf.ttf";
        titleParams.fontParameters.size = 256;
        titleParams.fontParameters.color = Color.WHITE;
        titleParams.fontParameters.borderWidth = 5f;
        titleParams.fontParameters.borderColor = Color.BLACK;
        assets.load("fonts/Meatloaf.ttf", BitmapFont.class, titleParams);

        FreetypeFontLoader.FreeTypeFontLoaderParameter mainParams = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        mainParams.fontFileName = "fonts/GeosansLight.ttf";
        mainParams.fontParameters.size = 72;
        mainParams.fontParameters.color = Color.WHITE;
        mainParams.fontParameters.borderWidth = 2f;
        mainParams.fontParameters.borderColor = Color.BLACK;
        assets.load("fonts/GeosansLight.ttf", BitmapFont.class, mainParams);
    }

    private void queueImages() {
        assets.load("img/zuzu.png", Texture.class);
        assets.load("img/bg.png", Texture.class);
        assets.load("img/play.atlas", TextureAtlas.class);
    }

    private void update(float delta) {
        progress = MathUtils.lerp(progress, assets.getProgress(), .1f);
        if (assets.update() && progress >= assets.getProgress() - 0.001f) {
            app.setScreen(app.splashScreen);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 0.1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        float progressBarWidth = app.camera.viewportWidth * .75f;
        float progressBarHeight = app.camera.viewportHeight * .1f;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.rect(
                app.camera.viewportWidth / 2 - progressBarWidth / 2,
                app.camera.viewportHeight / 2 - progressBarHeight / 2,
                progressBarWidth,
                progressBarHeight);

        shapeRenderer.setColor(Color.LIGHT_GRAY);
        shapeRenderer.rect(
                app.camera.viewportWidth / 2 - progressBarWidth / 2 + 10,
                app.camera.viewportHeight / 2 - progressBarHeight / 2 + 10,
                progressBarWidth * progress - 20,
                progressBarHeight - 20);
        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {

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
        this.shapeRenderer.dispose();
    }
}
