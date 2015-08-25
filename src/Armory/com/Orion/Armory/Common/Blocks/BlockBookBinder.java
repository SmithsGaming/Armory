/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Common.Blocks;
/*
/  BlockFirePit
/  Created by : Orion
/  Created on : 02/10/2014
*/

import com.Orion.Armory.Armory;
import com.Orion.Armory.Common.TileEntity.TileEntityBookBinder;
import com.Orion.Armory.Util.References;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockBookBinder extends BlockArmoryInventory {
    public BlockBookBinder() {
        super(References.InternalNames.Blocks.BookBinder, Material.iron);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntityBookBinder();
    }

    @Override
    public void breakBlock(World pWorld, int pX, int pY, int pZ, Block p_149749_5_, int p_149749_6_) {
        super.breakBlock(pWorld, pX, pY, pZ, p_149749_5_, p_149749_6_);
    }

    @Override
    public void onBlockPlacedBy(World pWorld, int pX, int pY, int pZ, EntityLivingBase pPlacingEntity, ItemStack pItemStack) {
        int l = MathHelper.floor_double((double) (pPlacingEntity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        TileEntityBookBinder tTE = (TileEntityBookBinder) pWorld.getTileEntity(pX, pY, pZ);

        if (l == 0) {
            tTE.setDirection(ForgeDirection.NORTH);
        }

        if (l == 1) {
            tTE.setDirection(ForgeDirection.EAST);
        }

        if (l == 2) {
            tTE.setDirection(ForgeDirection.SOUTH);
        }

        if (l == 3) {
            tTE.setDirection(ForgeDirection.WEST);
        }

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
        return References.RenderIDs.BookBinderId;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean onBlockActivated(World pWorld, int pX, int pY, int pZ, EntityPlayer pPlayer, int pFaceHit, float par7, float par8, float par9) {
        if (pPlayer.isSneaking()) {
            return false;
        } else {
            if (!pWorld.isRemote) {
                if (pWorld.getTileEntity(pX, pY, pZ) instanceof TileEntityBookBinder) {
                    pPlayer.openGui(Armory.instance, References.GuiIDs.BOOKBINDERID, pWorld, pX, pY, pZ);
                }
            }
            return true;
        }
    }
}
