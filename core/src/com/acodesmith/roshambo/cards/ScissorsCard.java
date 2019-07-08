package com.acodesmith.roshambo.cards;

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
