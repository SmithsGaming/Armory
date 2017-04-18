package com.smithsmodding.armory.api.util.common;

import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.common.material.core.IMaterial;
import com.smithsmodding.armory.api.common.material.core.RegistryMaterialWrapper;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

/**
 * Created by marcf on 1/11/2017.
 */
public final class MaterialHelper {

    /**
     * Method to get Material from a given name.
     * Checks all Armory Registries and retrieves it from the correct one of it is registered.
     *
     * @param name The name of the material.
     * @return The material registered under that name, or null if none is registered.
     */
    @Nullable
    public static IMaterial getMaterialFromRegistries(ResourceLocation name) {
        return IArmoryAPI.Holder.getInstance().getRegistryManager().getCombinedMaterialRegistry().getValue(name).getWrapped();
    }

    /**
     * Method to get a material from a oredic name
     * @param oreDicName The oredictionary identifier to search for.
     * @return The first material (if registered) found with the given oredicname.
     */
    @Nullable
    public static IMaterial getMaterialFromOreDicName(String oreDicName) {
        for(RegistryMaterialWrapper wrapper : IArmoryAPI.Holder.getInstance().getRegistryManager().getCombinedMaterialRegistry())
            if (wrapper.getWrapped().getOreDictionaryIdentifier().equals(oreDicName))
                return wrapper.getWrapped();

        return null;
    }
}
