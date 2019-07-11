package com.acodesmith.roshambo;

import com.acodesmith.roshambo.cards.*;

public class GameRules
{
    public static class GameResult
    {
        public String Text;
        public String RegionName;
        public String SoundName;
    }

    public static GameResult DetermineResult(Card userChoice, Card cpuChoice)
    {
        GameResult result = new GameResult();
        if (userChoice.equals(cpuChoice))
        {
            result.Text = "DRAW!";
            result.RegionName = "draw";
            result.SoundName = "sound/murmur.ogg";
        }
        else if (userChoice.isCounteredBy(cpuChoice))
        {
            result.Text = "DEFEAT!";
            result.RegionName = "defeat";
            result.SoundName = "sound/awww.ogg";
        }
        else
        {
            result.Text = "VICTORY!";
            result.RegionName = "victory";
            result.SoundName = "sound/cheer.ogg";
        }
        return result;
    }
}
