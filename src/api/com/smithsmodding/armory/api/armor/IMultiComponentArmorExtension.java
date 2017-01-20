package com.smithsmodding.armory.api.armor;

import com.smithsmodding.armory.api.capability.armor.IArmorCapability;
import com.smithsmodding.armory.api.util.client.ITranslateable;
import net.minecraft.util.ResourceLocation;
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
public interface IMultiComponentArmorExtension extends IForgeRegistryEntry<IMultiComponentArmorExtension>, ITranslateable, ICapabilityProvider {

    /**
     * Getter for the material independent texture that displays the extension as a whole.
     * @return A resource location pointing to the texture of the extension when it is whole.
     *
     * @implNote This method only exists on the Client side.
     */
    @Nonnull
    @SideOnly(Side.CLIENT)
    ResourceLocation getItemTextureWholeLocation();

    /**
     * Getter for the material independent texture that displays the extension when it is broken
     * @return A resource location pointing to the texture of the extension when it is broken.
     *
     * @implNote  This method only exists on the Client side.
     */
    @Nonnull
    @SideOnly(Side.CLIENT)
    ResourceLocation getItemTextureBrokenLocation();

    /**
     * Getter for the MultiComponentArmor that this Extension can be installed on.
     * @return The Armor that this Extension can be installed on.
     */
    @Nonnull
    IMultiComponentArmor getArmor();

    /**
     * Getter for the MultiComponentArmorExtensionPosition that this Extension can be installed in.
     * @return The Position that is Extension can be installed in.
     */
    @Nonnull
    IMultiComponentArmorExtensionPosition getPosition();

    /**
     * Method to get the maximum count that a Extension can be installed on a ArmorInstance.
     * @return the maximum count that a Extension can be installed on a ArmorInstance.
     */
    @Nonnull
    Integer getMaximalInstallationCount();

    /**
     * Method to get the additional durability this extension provides.
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
     * Method to get all the default capabilities this Component provides.
     * The Capabilities stored here override those stored in the Armor and in its CoreMaterial.
     * @return All the default capabilities this Component provides.
     */
    @Nonnull
    HashMap<Capability<? extends IArmorCapability>, Object> getDefaultComponentCapabilities();
}
