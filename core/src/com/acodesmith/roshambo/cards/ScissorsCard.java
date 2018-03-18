package com.acodesmith.roshambo.cards;

import com.acodesmith.roshambo.Application;

public class ScissorsCard extends Card
{
    private static final String name = "scissors";
    static
    {
        CardFactory.Register(name, ScissorsCard.class);
    }

    public ScissorsCard()
    {
        super(name, "rock");
    }
}
