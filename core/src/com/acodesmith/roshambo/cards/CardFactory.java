package com.acodesmith.roshambo.cards;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class CardFactory
{
    private static Map<String, Class<? extends Card>> registeredClasses = new LinkedHashMap<String, Class<? extends Card>>();

    public static Card Create(String name) throws InstantiationException, IllegalAccessException
    {
        Class<? extends Card> classType = registeredClasses.get(name);
        return (classType == null) ? null : classType.newInstance();
    }

    public static Card CreateRandom() throws InstantiationException, IllegalAccessException {
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
