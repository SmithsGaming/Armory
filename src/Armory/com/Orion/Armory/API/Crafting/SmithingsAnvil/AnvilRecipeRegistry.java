/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.API.Crafting.SmithingsAnvil;

import com.Orion.Armory.API.Crafting.SmithingsAnvil.Recipe.AnvilRecipe;

import java.util.HashMap;

public class AnvilRecipeRegistry {
    private static AnvilRecipeRegistry INSTANCE;
    private HashMap<String, AnvilRecipe> iRecipes = new HashMap<String, AnvilRecipe>();

    public static AnvilRecipeRegistry getInstance() {
        if (INSTANCE == null)
            INSTANCE = new AnvilRecipeRegistry();

        return INSTANCE;
    }

    public void addRecipe(String pID, AnvilRecipe pNewRecipe) {
        pNewRecipe.setInternalName(pID);

        iRecipes.put(pID, pNewRecipe);
    }

    public AnvilRecipe getRecipe(String pID) {
        return iRecipes.get(pID);
    }

    public HashMap<String, AnvilRecipe> getRecipes() {
        return iRecipes;
    }

    public void clearAllStoredRecipes() {
        iRecipes.clear();
    }
}
