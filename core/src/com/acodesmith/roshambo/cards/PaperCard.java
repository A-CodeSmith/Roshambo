package com.acodesmith.roshambo.cards;

public class PaperCard extends Card
{
    private static final String name = "paper";
    static
    {
        CardFactory.Register(name, PaperCard.class);
    }

    public PaperCard()
    {
        super(name, "scissors");
    }
}
