/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.armory.common.registry;

import com.smithsmodding.armory.api.crafting.blacksmiths.recipe.AnvilRecipe;

import java.util.HashMap;

public class AnvilRecipeRegistry {
    private static AnvilRecipeRegistry INSTANCE;

    private HashMap<String, AnvilRecipe> mappedRecipes = new HashMap<String, AnvilRecipe>();
    private HashMap<AnvilRecipe, String> reverseMappedRecipeds = new HashMap<>();

    public static AnvilRecipeRegistry getInstance() {
        if (INSTANCE == null)
            INSTANCE = new AnvilRecipeRegistry();

        return INSTANCE;
    }

    public void addRecipe(String pID, AnvilRecipe pNewRecipe) {
        pNewRecipe.setInternalName(pID);

        mappedRecipes.put(pID, pNewRecipe);
    }

    public AnvilRecipe getRecipe(String pID) {
        return mappedRecipes.get(pID);
    }

    public String getID(AnvilRecipe recipe) {
        return reverseMappedRecipeds.get(recipe);
    }

    public HashMap<String, AnvilRecipe> getRecipes() {
        return mappedRecipes;
    }

    public void clearAllStoredRecipes() {
        mappedRecipes.clear();
    }
}
