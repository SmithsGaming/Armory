/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.Armory.Common.Blocks;
/*
 *   BlockHeater
 *   Created by: Orion
 *   Created on: 12-10-2014
 */

import com.SmithsModding.Armory.Armory;
import com.SmithsModding.Armory.Common.Item.ItemFan;
import com.SmithsModding.Armory.Common.TileEntity.FirePit.TileEntityHeater;
import com.SmithsModding.Armory.Util.References;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockHeater extends BlockArmoryInventory {
    public BlockHeater() {
        super(References.InternalNames.Blocks.Heater, Material.iron);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntityHeater();
    }

    public void onBlockPlacedBy(World pWorld, int pX, int pY, int pZ, EntityLivingBase pPlacingEntity, ItemStack pItemStack) {
        TileEntityHeater tTE = (TileEntityHeater) pWorld.getTileEntity(pX, pY, pZ);

        tTE.setDirection(ForgeDirection.UP);

        if (pItemStack.hasDisplayName()) {
            tTE.setDisplayName(pItemStack.getDisplayName());
        }
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return References.RenderIDs.HeaterID;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean canPlaceBlockAt(World pWorld, int pX, int pY, int pZ) {
        return (pWorld.getBlock(pX, pY + 1, pZ) instanceof BlockFirePit);
    }

    @Override
    public boolean onBlockActivated(World pWorld, int pX, int pY, int pZ, EntityPlayer pPlayer, int pFaceHit, float par7, float par8, float par9) {
        if (pPlayer.isSneaking()) {
            return false;
        } else {
            if (!pWorld.isRemote) {
                TileEntityHeater tEntity = (TileEntityHeater) pWorld.getTileEntity(pX, pY, pZ);
                if (tEntity.getStackInSlot(0) == null) {
                    if (!(pPlayer.getCurrentEquippedItem() == null)) {
                        if ((pPlayer.getCurrentEquippedItem().getItem() instanceof ItemFan)) {

                            tEntity.setInventorySlotContents(0, pPlayer.getCurrentEquippedItem());
                            pPlayer.inventory.mainInventory[pPlayer.inventory.currentItem] = null;

                            return true;
                        }
                    }

                }

                if (pWorld.getTileEntity(pX, pY, pZ) instanceof TileEntityHeater) {
                    pPlayer.openGui(Armory.instance, References.GuiIDs.HEATERID, pWorld, pX, pY, pZ);
                }
            }
            return true;
        }
    }
}
