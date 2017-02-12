package com.smithsmodding.armory.api.common.armor;

import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/2/2017.
 */
public interface IMultiComponentArmorExtensionPosition extends IForgeRegistryEntry<IMultiComponentArmorExtensionPosition> {

    @Nonnull
    Integer getArmorLayer();
}
