package com.smithsmodding.armory.api.util.common;

import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.common.material.core.IMaterial;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

/**
 * Created by marcf on 1/11/2017.
 */
public final class MaterialHelper {

    /**
     * Method to getCreationRecipe a Material from a given name.
     * Checks all Armory Registries and retrieves it from the correct one of it is registered.
     *
     * @param name The name of the material.
     * @return The material registered under that name, or null if none is registered.
     */
    @Nullable
    public static IMaterial getMaterialFromRegistries(ResourceLocation name) {
        return IArmoryAPI.Holder.getInstance().getRegistryManager().getCombinedMaterialRegistry().getValue(name).getWrapped();
    }
}
