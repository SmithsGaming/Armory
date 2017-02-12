package com.smithsmodding.armory.api.armor;

import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Author Marc (Created on: 12.06.2016)
 */
public interface ISingleComponentItem {

    @NotNull String getComponentTypeFromItemStack(ItemStack stack);
}
