package com.acodesmith.roshambo.cards;

import com.acodesmith.roshambo.Application;

public class RockCard extends Card
{
    private static final String name = "rock";
    static
    {
        CardFactory.Register(name, RockCard.class);
    }

    public RockCard()
    {
        super(name, "paper");
    }
}