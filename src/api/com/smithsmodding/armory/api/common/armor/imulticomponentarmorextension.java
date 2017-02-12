package com.smithsmodding.armory.api.common.armor;

import com.smithsmodding.armory.api.client.armor.IInWorldRenderableArmorComponent;
import com.smithsmodding.armory.api.common.armor.callback.IExtensionRecipeRetrievalCallback;
import com.smithsmodding.armory.api.common.capability.armor.IArmorCapability;
import com.smithsmodding.armory.api.util.client.ITranslateable;
import com.smithsmodding.armory.api.util.client.ModelTransforms;
import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.HashMap;

/**
 * Created by marcf on 1/2/2017.
 */
public interface IMultiComponentArmorExtension extends IForgeRegistryEntry<IMultiComponentArmorExtension>, ITranslateable, ICapabilityProvider, IInWorldRenderableArmorComponent<IMultiComponentArmorExtension> {

    /**
     * Getter for the MultiComponentArmor that this Extension can be installed on.
     * @return The Armor that this Extension can be installed on.
     */
    @Nonnull
    IMultiComponentArmor getArmor();

    /**
     * Setter for the MultiComponentArmor that this Extension can be installed on.
     * @param armor The new Armor type this Extension can be installed on.
     * @return The instance this method was called on.
     */
    @Nonnull
    IMultiComponentArmorExtension setArmor(@Nonnull IMultiComponentArmor armor);

    /**
     * Getter for the MultiComponentArmorExtensionPosition that this Extension can be installed in.
     * @return The Position that is Extension can be installed in.
     */
    @Nonnull
    IMultiComponentArmorExtensionPosition getPosition();

    /**
     * Method to getCreationRecipe the maximum count that a Extension can be installed on a ArmorInstance.
     * @return the maximum count that a Extension can be installed on a ArmorInstance.
     */
    @Nonnull
    Integer getMaximalInstallationCount();

    /**
     * Method to getCreationRecipe the additional durability this extension provides.
     * @return The extra durability.
     */
    @Nonnull
    Integer getAdditionalDurability();

    /**
     * Method to check weither a other Extension can be installed together with this extension.
     * @param other The other extension.
     * @param alreadyInstalled True when this extension is already installed on the armor, false when not.
     * @return True when allowed, false when not.
     */
    @Nonnull
    Boolean validateCrafting(IMultiComponentArmorExtension other, Boolean alreadyInstalled);

    /**
     * Method to check whether or not this Extension has an ItemStack.
     * @return True when the a stack is buildable for this Extension. False when not.
     */
    @Nonnull
    Boolean hasItemStack();

    /**
     * Setter for the hasItemStack property of this Extension.
     * @param hasItemStack True when a stack is buildable for this extension. False when not.
     * @return The instance this method was called on.
     */
    @Nonnull
    IMultiComponentArmorExtension setHasItemStack(@Nonnull Boolean hasItemStack);

    /**
     * Method to getCreationRecipe all the default capabilities this Component provides.
     * The Capabilities stored here override those stored in the Armor and in its CoreMaterial.
     * @return All the default capabilities this Component provides.
     */
    @Nonnull
    HashMap<Capability<? extends IArmorCapability>, Object> getDefaultComponentCapabilities();

    /**
     * Method to getCreationRecipe the renderer that is used to render the Armor on the entity.
     *
     * @return The in world renderer.
     */
    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    default ModelRenderer getRendererForArmor() {
        return getArmor().getRendererForArmor();
    }

    /**
     * Method to getCreationRecipe the transforms for the in world rendering.
     *
     * @return The transforms for the in world rendering.
     */
    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    default ModelTransforms getRenderTransforms() {
        return getArmor().getRenderTransforms();
    }

    /**
     * Method to set the renderer of this Renderable.
     *
     * @param renderer The new renderer of this Renderable
     * @return The instance this method was called on.
     */
    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    default IMultiComponentArmorExtension setRendererForArmor(@Nonnull ModelRenderer renderer) {
        getArmor().setRendererForArmor(renderer);
        return this;
    }

    /**
     * Method to set the transforms of this Renderable
     *
     * @param transforms The new transforms of this Renderable.
     * @return The instance this method was called on.
     */
    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    default IMultiComponentArmorExtension setRenderTransforms(@Nonnull ModelTransforms transforms) {
        getArmor().setRenderTransforms(transforms);
        return this;
    }

    /**
     * Method used to retrieve tha recipe creation callback used during initialization to create recipes
     * of this extension and its subkinds if applicable. Method is only called when hasItemStack is true.
     *
     * If the callback returns null on this getCreationRecipe method then no recipe will be registered.
     */
    @Nonnull
    IExtensionRecipeRetrievalCallback getRecipeCallback();
}
