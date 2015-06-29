package com.Orion.Armory.Common.TileEntity.FirePit;

import net.minecraft.item.ItemStack;

/**
 * Created by Orion
 * Created on 24.06.2015
 * 22:53
 * <p/>
 * Copyrighted according to Project specific license
 */
public interface IInWorldFirePitInfluence
{
    float getPositiveHeatInflunce(TileEntityFirePit pSourceEntity, int pDistance, float pCurrentPositiveComponent);

    float getNegativeHeatInfluence(TileEntityFirePit pSourceEntity, int pDistance, float pCurrentNegativeComponent);

    boolean shouldSlotBurn(TileEntityFirePit pSourceEntity, int pSlotIndex);

    boolean processIngotStack(TileEntityFirePit pSourceEntity, ItemStack pStack);
}
