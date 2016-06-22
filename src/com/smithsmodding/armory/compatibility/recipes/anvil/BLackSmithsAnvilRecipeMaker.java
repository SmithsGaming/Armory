package com.smithsmodding.armory.compatibility.recipes.anvil;

import com.smithsmodding.armory.api.crafting.blacksmiths.recipe.AnvilRecipe;
import com.smithsmodding.armory.common.registry.AnvilRecipeRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * Author Orion (Created on: 21.06.2016)
 */
public class BlackSmithsAnvilRecipeMaker {

    public static List<BlackSmithsAnvilRecipeWrapper> getRecipes() {
        ArrayList<BlackSmithsAnvilRecipeWrapper> wrapperArrayList = new ArrayList<>();

        for (AnvilRecipe recipe : AnvilRecipeRegistry.getInstance().getRecipes().values())
            wrapperArrayList.add(new BlackSmithsAnvilRecipeWrapper(recipe));

        return wrapperArrayList;
    }
}
