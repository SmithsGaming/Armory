/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.Armory.Common.Blocks;
/*
/  BlockFirePit
/  Created by : Orion
/  Created on : 02/10/2014
*/

import com.SmithsModding.Armory.API.Structures.StructureManager;
import com.SmithsModding.Armory.Armory;
import com.SmithsModding.Armory.Common.TileEntity.FirePit.TileEntityFirePit;
import com.SmithsModding.Armory.Util.References;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockFirePit extends BlockArmoryInventory {
    public BlockFirePit() {
        super(References.InternalNames.Blocks.FirePit, Material.iron);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntityFirePit();
    }

    @Override
    public void breakBlock(World pWorld, int pX, int pY, int pZ, Block p_149749_5_, int p_149749_6_) {
        TileEntityFirePit tTEFirePit = (TileEntityFirePit) pWorld.getTileEntity(pX, pY, pZ);

        if (!pWorld.isRemote) {
            if (pWorld.getTileEntity(pX, pY, pZ) instanceof TileEntityFirePit) {
                StructureManager.destroyStructureComponent(tTEFirePit);
            }
        }

        super.breakBlock(pWorld, pX, pY, pZ, p_149749_5_, p_149749_6_);
    }

    @Override
    public void onNeighborBlockChange(World pWorld, int pX, int pY, int pZ, Block pBlock) {
        TileEntityFirePit tEntity = (TileEntityFirePit) pWorld.getTileEntity(pX, pY, pZ);

        for (ForgeDirection tDirection : ForgeDirection.values()) {
            if (tDirection == ForgeDirection.UNKNOWN || tDirection == ForgeDirection.DOWN || tDirection == ForgeDirection.UP)
                continue;

            tEntity.setSideValid(tDirection, pWorld.getBlock(pX + tDirection.offsetX, pY + tDirection.offsetY, pZ + tDirection.offsetZ) instanceof BlockFirePit);
        }

        super.onNeighborBlockChange(pWorld, pX, pY, pZ, pBlock);
    }

    @Override
    public void onBlockPlacedBy(World pWorld, int pX, int pY, int pZ, EntityLivingBase pPlacingEntity, ItemStack pItemStack) {
        TileEntityFirePit tTE = (TileEntityFirePit) pWorld.getTileEntity(pX, pY, pZ);

        if (pItemStack.hasDisplayName()) {
            tTE.setDisplayName(pItemStack.getDisplayName());
        }

        if (!pWorld.isRemote) {
            if (pWorld.getTileEntity(pX, pY, pZ) instanceof TileEntityFirePit) {
                StructureManager.createStructureComponent(tTE);
            }
        }

        onNeighborBlockChange(pWorld, pX, pY, pZ, this);
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return References.RenderIDs.FirePitID;
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
                if (pWorld.getTileEntity(pX, pY, pZ) instanceof TileEntityFirePit) {
                    pPlayer.openGui(Armory.instance, References.GuiIDs.FIREPITID, pWorld, pX, pY, pZ);
                }
            }
            return true;
        }
    }
}
