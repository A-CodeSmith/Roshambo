package com.acodesmith.roshambo;

import com.acodesmith.roshambo.screens.*;

public class ScreenManager
{
    private Application app;
    private BaseScreen currentScreen;

    ScreenManager(Application app)
    {
        this.app = app;
    }

    public void setScreen(GameScreen nextScreen)
    {
        BaseScreen temp = currentScreen;
        BaseScreen next = null;
        switch (nextScreen)
        {
            case Loading:
                next = new LoadingScreen(app);
                break;
            case Splash:
                next = new SplashScreen(app);
                break;
            case MainMenu:
                next = new MainMenuScreen(app);
                break;
            case Play:
                next = new PlayScreen(app);
                break;
        }

        app.setScreen(next);
        currentScreen = next;
        if (temp != null)
            temp.dispose();
    }
}
