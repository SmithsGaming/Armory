package com.smithsmodding.armory.api.registries;

import com.smithsmodding.armory.api.crafting.blacksmiths.recipe.AnvilRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * Author Orion (Created on: 07.07.2016)
 */
public interface IAnvilRecipeRegistry {
    void addRecipe(String pID, AnvilRecipe pNewRecipe);

    AnvilRecipe getRecipe(String pID);

    String getID(AnvilRecipe recipe);

    @NotNull HashMap<String, AnvilRecipe> getRecipes();

    void clearAllStoredRecipes();
}
