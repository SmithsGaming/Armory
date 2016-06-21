package com.smithsmodding.armory.compatibility.recipes.anvil;

import com.smithsmodding.armory.api.crafting.blacksmiths.recipe.AnvilRecipe;
import com.smithsmodding.armory.common.registry.AnvilRecipeRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by marcf on 6/21/2016.
 */
public class BlackSmithsAnvilRecipeMaker {

    public static List<BlackSmithsAnvilRecipeWrapper> getRecipes() {
        return AnvilRecipeRegistry.getInstance().getRecipes().values().stream().map(BlackSmithsAnvilRecipeWrapper::new).collect(Collectors.toCollection(ArrayList::new));
    }
}
