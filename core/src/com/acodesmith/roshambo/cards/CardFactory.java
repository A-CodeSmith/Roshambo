package com.acodesmith.roshambo.cards;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class CardFactory
{
    private static Map<String, Class<? extends Card>> registeredClasses = new LinkedHashMap<>();

    public static Card Create(String name)
    {
        try
        {
            Class<? extends Card> classType = registeredClasses.get(name);
            return classType.newInstance();
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static Card CreateRandom()
    {
        int count = registeredClasses.size();
        int randomIndex = (new Random()).nextInt(count);
        String[] keys = registeredClasses.keySet().toArray(new String[count]);
        return Create(keys[randomIndex]);
    }

    public static void Register(String name, Class<? extends Card> classType)
    {
        registeredClasses.put(name, classType);
    }
}
