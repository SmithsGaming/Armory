package com.smithsmodding.armory.api.client.armor;

import com.smithsmodding.armory.api.util.client.ModelTransforms;
import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/24/2017.
 */
public interface IInWorldRenderableArmorComponent<T> {
    /**
     * Method to getCreationRecipe the renderer that is used to render the Armor on the entity.
     * @return The in world renderer.
     */
    @Nonnull
    @SideOnly(Side.CLIENT)
    ModelRenderer getRendererForArmor();

    /**
     * Method to getCreationRecipe the transforms for the in world rendering.
     * @return The transforms for the in world rendering.
     */
    @Nonnull
    @SideOnly(Side.CLIENT)
    ModelTransforms getRenderTransforms();

    /**
     * Method to set the renderer of this Renderable.
     * @param renderer The new renderer of this Renderable
     * @return The instance this method was called on.
     */
    @SideOnly(Side.CLIENT)
    @Nonnull
    T setRendererForArmor(@Nonnull ModelRenderer renderer);

    /**
     * Method to set the transforms of this Renderable
     * @param transforms The new transforms of this Renderable.
     * @return The instance this method was called on.
     */
    @SideOnly(Side.CLIENT)
    @Nonnull
    T setRenderTransforms(@Nonnull ModelTransforms transforms);
}
