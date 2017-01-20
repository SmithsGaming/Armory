package com.smithsmodding.armory.api.material.core;

import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by marcf on 1/14/2017.
 */
public final class RegistryMaterialWrapper extends IForgeRegistryEntry.Impl<RegistryMaterialWrapper> {

    @Nonnull
    private final IMaterial wrapped;

    public RegistryMaterialWrapper(@Nonnull IMaterial wrapped) {
        this.wrapped = wrapped;

    }

    /**
     * Method to get the wrapped entry of a other registry.
     * @return The wrapped nonnull entry.
     */
    @Nonnull
    public IMaterial getWrapped() {
        return wrapped;
    }
}
