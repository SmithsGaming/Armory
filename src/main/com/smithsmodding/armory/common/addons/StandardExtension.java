package com.smithsmodding.armory.common.addons;

import com.smithsmodding.armory.api.armor.IMultiComponentArmor;
import com.smithsmodding.armory.api.armor.IMultiComponentArmorExtension;
import com.smithsmodding.armory.api.armor.IMultiComponentArmorExtensionPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/12/2017.
 */
public class StandardExtension extends IForgeRegistryEntry.Impl<IMultiComponentArmorExtension> implements IMultiComponentArmorExtension {

    private final IMultiComponentArmor armor;
    private final IMultiComponentArmorExtensionPosition position;
    private final Integer maximalInstallationCount;
    private final Integer additionalDurability;
    private Boolean hasItemStack = true;

    private final ResourceLocation textureWholeLocation;
    private final ResourceLocation textureBrokenLocation;
    private final String translationKey;

    public StandardExtension(IMultiComponentArmor armor, IMultiComponentArmorExtensionPosition position, Integer maximalInstallationCount, Integer additionalDurability, ResourceLocation textureWholeLocation, ResourceLocation textureBrokenLocation, String translationKey) {
        this.armor = armor;
        this.position = position;
        this.maximalInstallationCount = maximalInstallationCount;
        this.additionalDurability = additionalDurability;
        this.textureWholeLocation = textureWholeLocation;
        this.textureBrokenLocation = textureBrokenLocation;
        this.translationKey = translationKey;
    }


    /**
     * Getter for the material independent texture that displays the extension as a whole.
     *
     * @return A resource location pointing to the texture of the extension when it is whole.
     * @implNote This method only exists on the Client side.
     */
    @Nonnull
    @Override
    @SideOnly(Side.CLIENT)
    public ResourceLocation getItemTextureWholeLocation() {
        return textureWholeLocation;
    }

    /**
     * Getter for the material independent texture that displays the extension when it is broken
     *
     * @return A resource location pointing to the texture of the extension when it is broken.
     * @implNote This method only exists on the Client side.
     */
    @Nonnull
    @Override
    @SideOnly(Side.CLIENT)
    public ResourceLocation getItemTextureBrokenLocation() {
        return textureBrokenLocation;
    }

    /**
     * Getter for the MultiComponentArmor that this Extension can be installed on.
     *
     * @return The Armor that this Extension can be installed on.
     */
    @Nonnull
    @Override
    public IMultiComponentArmor getArmor() {
        return armor;
    }

    /**
     * Getter for the MultiComponentArmorExtensionPosition that this Extension can be installed in.
     *
     * @return The Position that is Extension can be installed in.
     */
    @Nonnull
    @Override
    public IMultiComponentArmorExtensionPosition getPosition() {
        return position;
    }

    /**
     * Method to get the maximum count that a Extension can be installed on a ArmorInstance.
     *
     * @return the maximum count that a Extension can be installed on a ArmorInstance.
     */
    @Nonnull
    @Override
    public Integer getMaximalInstallationCount() {
        return maximalInstallationCount;
    }

    /**
     * Method to get the additional durability this extension provides.
     *
     * @return The extra durability.
     */
    @Nonnull
    @Override
    public Integer getAdditionalDurability() {
        return additionalDurability;
    }

    /**
     * Method to check weither a other Extension can be installed together with this extension.
     *
     * @param other            The other extension.
     * @param alreadyInstalled True when this extension is already installed on the armor, false when not.
     * @return True when allowed, false when not.
     */
    @Nonnull
    @Override
    public Boolean validateCrafting(IMultiComponentArmorExtension other, Boolean alreadyInstalled) {
        return !((this.getPosition().equals(other.getPosition())) && (!this.equals(other)));
    }

    /**
     * Method to check whether or not this Extension has an ItemStack.
     *
     * @return True when the a stack is buildable for this Extension. False when not.
     */
    @Nonnull
    @Override
    public Boolean hasItemStack() {
        return hasItemStack;
    }

    /**
     * Setter for the hasItemStack property of this Extension.
     *
     * @param hasItemStack True when a stack is buildable for this extension. False when not.
     * @return The instance this method was called on.
     */
    @Nonnull
    @Override
    public IMultiComponentArmorExtension setHasItemStack(@Nonnull Boolean hasItemStack) {
        this.hasItemStack = hasItemStack;
        return this;
    }

    /**
     * Method to get the translation Key.
     *
     * @return The key to translate.
     */
    @Nonnull
    @Override
    public String getTranslationKey() {
        return translationKey;
    }
}