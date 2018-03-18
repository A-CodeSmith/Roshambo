package com.acodesmith.roshambo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class AssetManager extends com.badlogic.gdx.assets.AssetManager
{
    private static AssetManager instance;

    private AssetManager(){}

    public static AssetManager getInstance()
    {
        if(instance == null)
            instance = new AssetManager();
        return instance;
    }

    public Texture getTexture(String fileName)
    {
        return this.get(fileName, Texture.class);
    }

    public BitmapFont getFont(String fileName)
    {
        return this.get(fileName);
    }

    public TextureAtlas.AtlasRegion getRegionFromAtlas(String regionName)
    {
        return ((TextureAtlas)(this.get("img/play.atlas"))).findRegion(regionName);
    }
}
