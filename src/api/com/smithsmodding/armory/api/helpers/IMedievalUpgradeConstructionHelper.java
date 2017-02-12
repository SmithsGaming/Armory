package com.smithsmodding.armory.api.helpers;

import com.smithsmodding.armory.api.armor.IMaterialDependantMultiComponentArmorExtension;
import com.smithsmodding.armory.api.armor.IMultiComponentArmor;
import com.smithsmodding.armory.api.armor.IMultiComponentArmorExtension;
import com.smithsmodding.armory.api.armor.IMultiComponentArmorExtensionPosition;
import com.smithsmodding.armory.api.material.armor.IAddonArmorMaterial;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Author Orion (Created on: 07.07.2016)
 */
public interface IMedievalUpgradeConstructionHelper {

    /**
     * Method to build an armor extension that has no real special properties.
     *
     * @param armor The armor the extension is for.
     * @param position The position this extension should be installed in.
     * @param maximalInstallationCount The maximal installation count.
     * @param additionalDurability The additional durability this extension gives to a piece of Armor.
     * @param textureWholeLocation The texture when this extension is whole.
     * @param textureBrokenLocation The texture when this extension is broken.
     *
     * @return A completely setup extension. However the caller will need to set the RegistryName and register it.
     */
    @Nonnull
    IMultiComponentArmorExtension buildStandardExtension(IMultiComponentArmor armor, IMultiComponentArmorExtensionPosition position, Integer maximalInstallationCount, Integer additionalDurability, ResourceLocation textureWholeLocation, ResourceLocation textureBrokenLocation, String translationKey);

    /**
     * Method to build an armor extension that wraps an existing combination and a material.
     * Making the new extension material based.
     *
     * @param armor The armor the extension is for.
     * @param position The position this extension should be installed in.
     * @param maximalInstallationCount The maximal installation count.
     * @param additionalDurability The additional durability this extension gives to a piece of Armor.
     * @param material The material the new extension should dependant on.
     * @param materialIndependentExtension The extension should dependant on.
     * @param textureWholeLocation The texture when this extension is whole.
     * @param textureBrokenLocation The texture when this extension is broken.
     *
     * @return A completely setup extension that wraps the existing extension and a material. However the caller will need to set the RegistryNam and register it.
     */
    @Nonnull
    IMaterialDependantMultiComponentArmorExtension buildMaterialDependantExtension(IMultiComponentArmor armor, IMultiComponentArmorExtensionPosition position, Integer maximalInstallationCount, Integer additionalDurability, IAddonArmorMaterial material, IMultiComponentArmorExtension materialIndependentExtension, ResourceLocation textureWholeLocation, ResourceLocation textureBrokenLocation, String translationKey);
}
