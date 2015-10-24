package com.SmithsModding.Armory.Common.TileEntity.Core.Components;

import net.minecraft.item.ItemStack;

/**
 * Created by Orion
 * Created on 30.06.2015
 * 11:18
 * <p/>
 * Copyrighted according to Project specific license
 */
public interface ITileEntityComponent {

    Class getTileEntityTargetClass();

    void updateTargetEntity(ITileEntityComponent pTargetEntity, ItemStack pUpgradeStack);

    boolean hasDurability(ItemStack pUpgradeStack);

    int getMaxDurability(ItemStack pUpgradeStack);

    ItemStack onComponentDestroyed(ItemStack pComponentStack);
}
