package com.smithsmodding.armory.api.common.material.armor;

import com.smithsmodding.armory.api.common.armor.IMultiComponentArmorExtension;
import com.smithsmodding.armory.api.common.capability.armor.IArmorCapability;
import com.smithsmodding.armory.api.common.material.core.IMaterial;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;
import java.util.HashMap;

/**
 * Created by marcf on 1/2/2017.
 */
public interface IAddonArmorMaterial extends IMaterial<IAddonArmorMaterial> {


    /**
     * Method to getCreationRecipe all the default capabilities this ArmorMaterial provides.
     * @return All the default capabilities this ArmorMaterial provides.
     */
    @Nonnull
    HashMap<Capability<? extends IArmorCapability>, Object> getOverrideAddonMaterialCapabilities(IMultiComponentArmorExtension extension);
}
