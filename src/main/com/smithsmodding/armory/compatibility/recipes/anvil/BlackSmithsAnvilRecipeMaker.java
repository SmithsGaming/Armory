package com.smithsmodding.armory.compatibility.recipes.anvil;

import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.common.crafting.blacksmiths.recipe.IAnvilRecipe;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Author Orion (Created on: 21.06.2016)
 */
public class BlackSmithsAnvilRecipeMaker {

    @Nonnull
    public static List<BlackSmithsAnvilRecipeWrapper> getRecipes() {
        ArrayList<BlackSmithsAnvilRecipeWrapper> wrapperArrayList = new ArrayList<>();

        for (IAnvilRecipe recipe : IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry())
            wrapperArrayList.add(new BlackSmithsAnvilRecipeWrapper(recipe));

        return wrapperArrayList;
    }
}
