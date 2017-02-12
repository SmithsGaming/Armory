package com.smithsmodding.armory.api.common.factories;

import com.smithsmodding.armory.api.common.heatable.IHeatableObject;
import com.smithsmodding.armory.api.common.heatable.IHeatedObjectType;
import com.smithsmodding.armory.api.common.material.core.IMaterial;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by marcf on 1/12/2017.
 */
public interface IHeatedItemFactory {
    @Nullable
    ItemStack generateHeatedItemFromMaterial(IMaterial material, IHeatableObject object, IHeatedObjectType type, float temp);

    @Nonnull
    ItemStack convertToHeatedIngot(@Nonnull ItemStack originalStack);

    @Nonnull
    ItemStack convertToHeatedIngot(@Nonnull ItemStack originalStack, float temp);

    @Nonnull
    ItemStack convertToCooledIngot(@Nonnull ItemStack heatedStack);
}
