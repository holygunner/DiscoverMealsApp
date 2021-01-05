package com.achyzh.discovermeals2020._archive.discover_meals.save;

import android.content.Context;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

public class Saver {
    public static final String CHOSEN_INGREDIENTS_KEY = "chosen_ingredients_key";

    public static Set<String> readIngredients(Context context, String key){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getStringSet(key, new HashSet<String>());
    }

    public static Set<String> readChosenIngredientsNamesInLowerCase(Context context, String key){
        Set<String> originalNames = readIngredients(context, key);
        Set<String> lowerCaseNames = new HashSet<>();

        for (String name: originalNames){
            lowerCaseNames.add(name.toLowerCase());
        }

        return lowerCaseNames;
    }
}
