package com.smithsmodding.armory.compatibility.recipes.anvil;

import com.smithsmodding.armory.api.references.References;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

import javax.annotation.Nonnull;

/**
 * Author Orion (Created on: 21.06.2016)
 */
public class BlackSmithsAnvilRecipeHandler implements IRecipeHandler<BlackSmithsAnvilRecipeWrapper> {
    @Nonnull
    @Override
    public Class<BlackSmithsAnvilRecipeWrapper> getRecipeClass() {
        return BlackSmithsAnvilRecipeWrapper.class;
    }

    @Nonnull
    @Override
    public String getRecipeCategoryUid() {
        return null;
    }

    @Nonnull
    @Override
    public String getRecipeCategoryUid(@Nonnull BlackSmithsAnvilRecipeWrapper recipe) {
        return References.Compatibility.JEI.RecipeTypes.ANVIL;
    }

    @Nonnull
    @Override
    public IRecipeWrapper getRecipeWrapper(@Nonnull BlackSmithsAnvilRecipeWrapper recipe) {
        return recipe;
    }

    @Override
    public boolean isRecipeValid(@Nonnull BlackSmithsAnvilRecipeWrapper recipe) {
        return recipe.getInputs().size() > 0 && recipe.getOutputs().size() > 0 && recipe.getAdditionalStacks().size() > 0;
    }
}
