package com.smithsmodding.armory.api.common.heatable;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/31/2017.
 */
public interface IHeatableObjectItemStackOverrideManager {

    @Nonnull
    ItemStack getItemStackOverride(@Nonnull IHeatableObject object, @Nonnull IHeatedObjectType type);

    void registerItemStackOverride(@Nonnull IHeatableObject object, @Nonnull IHeatedObjectType type, @Nonnull );
}
