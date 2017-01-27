package com.smithsmodding.armory.api.common.material.core;

import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/14/2017.
 */
public final class RegistryMaterialWrapper extends IForgeRegistryEntry.Impl<RegistryMaterialWrapper> {

    @Nonnull
    private final IMaterial wrapped;

    public RegistryMaterialWrapper(@Nonnull IMaterial wrapped) {
        this.wrapped = wrapped;
        this.setRegistryName(wrapped.getRegistryName());
    }

    /**
     * Method to getCreationRecipe the wrapped entry of a other registry.
     * @return The wrapped nonnull entry.
     */
    @Nonnull
    public IMaterial getWrapped() {
        return wrapped;
    }
}
