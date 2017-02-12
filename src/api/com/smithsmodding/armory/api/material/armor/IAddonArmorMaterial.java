package com.smithsmodding.armory.api.material.armor;

import com.smithsmodding.armory.api.armor.IMultiComponentArmorExtension;
import com.smithsmodding.armory.api.capability.armor.IArmorCapability;
import com.smithsmodding.armory.api.material.core.IMaterial;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;
import java.util.HashMap;

/**
 * Created by marcf on 1/2/2017.
 */
public interface IAddonArmorMaterial extends IMaterial<IAddonArmorMaterial> {


    /**
     * Method to get all the default capabilities this ArmorMaterial provides.
     * @return All the default capabilities this ArmorMaterial provides.
     */
    @Nonnull
    HashMap<Capability<? extends IArmorCapability>, Object> getOverrideAddonMaterialCapabilities(IMultiComponentArmorExtension extension);
}
