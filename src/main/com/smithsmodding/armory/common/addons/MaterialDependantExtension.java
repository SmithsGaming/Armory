package com.smithsmodding.armory.common.addons;

import com.smithsmodding.armory.api.armor.IMaterialDependantMultiComponentArmorExtension;
import com.smithsmodding.armory.api.armor.IMultiComponentArmor;
import com.smithsmodding.armory.api.armor.IMultiComponentArmorExtension;
import com.smithsmodding.armory.api.armor.IMultiComponentArmorExtensionPosition;
import com.smithsmodding.armory.api.material.armor.IAddonArmorMaterial;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/12/2017.
 */
public class MaterialDependantExtension extends StandardExtension implements IMaterialDependantMultiComponentArmorExtension {


    private final IAddonArmorMaterial material;
    private final IMultiComponentArmorExtension materialIndependentExtension;

    public MaterialDependantExtension(IMultiComponentArmor armor, IMultiComponentArmorExtensionPosition position, Integer maximalInstallationCount, Integer additionalDurability, IAddonArmorMaterial material, IMultiComponentArmorExtension materialIndependentExtension, ResourceLocation textureWholeLocation, ResourceLocation textureBrokenLocation, String translationKey) {
        super(armor, position, maximalInstallationCount, additionalDurability, textureWholeLocation, textureBrokenLocation, translationKey);

        this.material = material;
        this.materialIndependentExtension = materialIndependentExtension;
    }

    /**
     * Getter for the material independent extension.
     *
     * @return The material independent extension.
     */
    @Nonnull
    @Override
    public IMultiComponentArmorExtension getMaterialIndependentExtension() {
        return materialIndependentExtension;
    }

    /**
     * @return
     */
    @Nonnull
    @Override
    public IAddonArmorMaterial getMaterial() {
        return material;
    }
}
