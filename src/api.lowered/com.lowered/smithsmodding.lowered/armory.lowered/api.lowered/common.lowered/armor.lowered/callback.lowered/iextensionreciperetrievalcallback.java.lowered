package com.smithsmodding.armory.api.common.armor.callback;

import com.smithsmodding.armory.api.common.armor.IMultiComponentArmor;
import com.smithsmodding.armory.api.common.armor.IMultiComponentArmorExtension;
import com.smithsmodding.armory.api.common.crafting.blacksmiths.recipe.IAnvilRecipe;
import com.smithsmodding.armory.api.common.material.armor.ICoreArmorMaterial;

import javax.annotation.Nullable;

/**
 * Created by marcf on 1/25/2017.
 */
public interface IExtensionRecipeRetrievalCallback {

    /**
     * Method used during initialization to register a Recipe to create this Extension.
     * Method is only called when hasItemStack is true.
     * Returning null will cause this extension to not have a recipe. Eg. Creative Extensions.
     *
     * @param extension The extension the recipe should be created for.
     *
     * @return The recipe for this extension if it has one. Null else.
     */
    @Nullable
    IAnvilRecipe getCreationRecipe(IMultiComponentArmorExtension extension);

    /**
     * Method used during initialization to register a Recipe that is used to attach the extension to the given armor
     * of the given ICoreArmorMaterial
     * @param extension
     * @param armor
     * @param coreMaterial
     * @return
     */
    @Nullable
    IAnvilRecipe getAttachingRecipe(IMultiComponentArmorExtension extension, IMultiComponentArmor armor, ICoreArmorMaterial coreMaterial);
}
