package com.smithsmodding.armory.api.armor;

import net.minecraft.item.ItemStack;

/**
 * @Author Marc (Created on: 12.06.2016)
 */
public interface ISingleComponentItem {

    String getComponentTypeFromItemStack(ItemStack stack);
}
