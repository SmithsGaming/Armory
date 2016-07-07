/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.armory.common.registry;

import com.smithsmodding.armory.api.crafting.blacksmiths.recipe.AnvilRecipe;
import com.smithsmodding.armory.api.registries.IAnvilRecipeRegistry;

import java.util.HashMap;

public class AnvilRecipeRegistry implements IAnvilRecipeRegistry {
    private static AnvilRecipeRegistry instance = new AnvilRecipeRegistry();

    private HashMap<String, AnvilRecipe> mappedRecipes = new HashMap<String, AnvilRecipe>();
    private HashMap<AnvilRecipe, String> reverseMappedRecipeds = new HashMap<>();

    private AnvilRecipeRegistry () {}

    public static IAnvilRecipeRegistry getInstance() {
        return instance;
    }

    @Override
    public void addRecipe(String pID, AnvilRecipe pNewRecipe) {
        pNewRecipe.setInternalName(pID);

        mappedRecipes.put(pID, pNewRecipe);
    }

    @Override
    public AnvilRecipe getRecipe(String pID) {
        return mappedRecipes.get(pID);
    }

    @Override
    public String getID(AnvilRecipe recipe) {
        return reverseMappedRecipeds.get(recipe);
    }

    @Override
    public HashMap<String, AnvilRecipe> getRecipes() {
        return mappedRecipes;
    }

    @Override
    public void clearAllStoredRecipes() {
        mappedRecipes.clear();
    }
}
