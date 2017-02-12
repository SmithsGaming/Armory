package com.smithsmodding.armory.api.common.heatable;

import com.smithsmodding.armory.api.common.material.core.IMaterial;
import com.smithsmodding.armory.util.Triple;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/31/2017.
 */
public interface IHeatedObjectOverrideManager {

    @Nonnull
    <T> T getHeatedOverride(@Nonnull IHeatableObject object, @Nonnull IHeatedObjectType type, @Nonnull IMaterial material);

    <T> void registerHeatedOverride(@Nonnull IHeatableObject object, @Nonnull IHeatedObjectType type, @Nonnull IMaterial material, @Nonnull T heated);

    @Nonnull
    Triple<IHeatableObject, IHeatedObjectType, IMaterial> getStackData(@Nonnull ItemStack stack) throws IllegalArgumentException;

    boolean hasOverride(@Nonnull IHeatableObject object, @Nonnull IHeatedObjectType type, @Nonnull IMaterial material);

    boolean isOverride(@Nonnull ItemStack stack);

    boolean isHeatable(@Nonnull ItemStack stack);
}
