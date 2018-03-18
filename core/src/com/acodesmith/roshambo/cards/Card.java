package com.acodesmith.roshambo.cards;

import com.acodesmith.roshambo.Application;
import com.acodesmith.roshambo.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public abstract class Card extends Image {

    private final String counteredBy;

    public Card(String name, String counteredBy)
    {
        super(AssetManager.getInstance().getRegionFromAtlas(name));
        this.setName(name);
        this.counteredBy = counteredBy;
    }

    public boolean isCounteredBy(Card opponent)
    {
        return opponent.getName() == counteredBy;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        Card other = (Card)obj;
        return (this.getName() == other.getName()) && (counteredBy == other.counteredBy);
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + ((this.getName() == null) ? 0 : this.getName().hashCode());
        result = prime * result + ((counteredBy == null) ? 0 : counteredBy.hashCode());
        return result;
    }
}
