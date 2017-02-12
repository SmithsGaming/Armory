package com.smithsmodding.armory.api.common.material.anvil;

import com.smithsmodding.armory.api.common.crafting.blacksmiths.recipe.IAnvilRecipe;
import com.smithsmodding.armory.api.common.material.core.IMaterial;
import com.smithsmodding.smithscore.client.textures.ITextureController;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by marcf on 1/2/2017.
 */
public interface IAnvilMaterial extends IMaterial<IAnvilMaterial> {

    /**
     * Method to getCreationRecipe the getDurability of the Anvil when it is made out of this Material
     * @return A value bigger then 0, Integer.MaxValue means unbreakable.
     */
    @Nonnull
    Integer getDurability();

    /**
     * Method used to getCreationRecipe the RenderInfo used to change the Texture of the Model if need be.
     * @return The RenderInfo used to modify the Texture of the model.
     */
    @SideOnly(Side.CLIENT)
    @Nonnull
    ITextureController getRenderInfo();

    /**
     * Setter for the RenderInfo used to change the Texture of the Model if need be.
     * @param info The RenderInfo used to modify the Texture of the Model.
     * @return The instance this method was called on.
     */
    @SideOnly(Side.CLIENT)
    @Nonnull
    IAnvilMaterial setRenderInfo(@Nonnull ITextureController info);

    /**
     * The recipe that is used to craft an Anvil with this as a Main material.
     * @return The recipe for the anvil with this as a main Material.
     */
    @Nullable
    IAnvilRecipe getRecipeForAnvil();

}
