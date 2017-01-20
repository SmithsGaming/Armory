package com.smithsmodding.armory.util;

import com.smithsmodding.armory.api.material.core.IMaterial;
import com.smithsmodding.armory.common.api.ArmoryAPI;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

/**
 * Created by marcf on 1/11/2017.
 */
public final class MaterialHelper {

    /**
     * Method to get a Material from a given name.
     * Checks all Armory Registries and retrieves it from the correct one of it is registered.
     *
     * @param name The name of the material.
     * @return The material registered under that name, or null if none is registered.
     */
    @Nullable
    public static IMaterial getMaterialFromRegistries(ResourceLocation name) {
        if (ArmoryAPI.getInstance().getRegistryManager().getCoreMaterialRegistry().containsKey(name))
            return ArmoryAPI.getInstance().getRegistryManager().getCoreMaterialRegistry().getValue(name);

        if (ArmoryAPI.getInstance().getRegistryManager().getAddonArmorMaterialRegistry().containsKey(name))
            return ArmoryAPI.getInstance().getRegistryManager().getAddonArmorMaterialRegistry().getValue(name);

        return ArmoryAPI.getInstance().getRegistryManager().getAnvilMaterialRegistry().getValue(name);
    }
}
