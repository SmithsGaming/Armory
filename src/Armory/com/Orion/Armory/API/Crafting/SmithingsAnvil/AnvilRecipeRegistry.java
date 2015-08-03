/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.API.Crafting.SmithingsAnvil;

import com.Orion.Armory.API.Crafting.SmithingsAnvil.Recipe.AnvilRecipe;

import java.util.ArrayList;

public class AnvilRecipeRegistry {
    private static AnvilRecipeRegistry INSTANCE;
    private ArrayList<AnvilRecipe> iRecipes = new ArrayList<AnvilRecipe>();

    public static AnvilRecipeRegistry getInstance() {
        if (INSTANCE == null)
            INSTANCE = new AnvilRecipeRegistry();

        return INSTANCE;
    }

    public void addRecipe(AnvilRecipe pNewRecipe) {
        iRecipes.add(pNewRecipe);
    }

    public ArrayList<AnvilRecipe> getRecipes() {
        return iRecipes;
    }

    public void clearAllStoredRecipes() {
        iRecipes.clear();
    }
}
