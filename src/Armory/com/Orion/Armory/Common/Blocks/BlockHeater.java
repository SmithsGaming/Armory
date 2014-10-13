package com.Orion.Armory.Common.Blocks;
/*
 *   BlockHeater
 *   Created by: Orion
 *   Created on: 12-10-2014
 */

import com.Orion.Armory.Common.Logic.Multiblock.IMultiBlockPart;
import com.Orion.Armory.Common.TileEntity.TileEntityFirePit;
import com.Orion.Armory.Util.References;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockHeater extends BlockContainer implements IMultiBlockPart
{
    protected String iInternalName = References.InternalNames.Blocks.Heater;

    protected BlockHeater(Material p_i45386_1_) {
        super(Material.iron);
    }

    public String getInternalName() {
        return this.iInternalName;
    }

    @Override
    public boolean validatePart(Integer pXCoord, Integer pYCoord, Integer pZCoord, World pWorld) {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntityFirePit();
    }

    public void onBlockPlacedBy(World pWorld, int pX, int pY, int pZ, EntityLivingBase pPlacingEntity, ItemStack pItemStack)
    {
        int l = MathHelper.floor_double((double) (pPlacingEntity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        TileEntityFirePit tTE = (TileEntityFirePit) pWorld.getTileEntity(pX, pY, pZ);

        if (l == 0)
        {
            tTE.setDirection(ForgeDirection.NORTH);
        }

        if (l == 1)
        {
            tTE.setDirection(ForgeDirection.EAST);
        }

        if (l == 2)
        {
            tTE.setDirection(ForgeDirection.SOUTH);
        }

        if (l == 3)
        {
            tTE.setDirection(ForgeDirection.WEST);
        }

        if (pItemStack.hasDisplayName())
        {
            tTE.setDisplayName(pItemStack.getDisplayName());
        }
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return References.RenderIDs.HeaterID;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }
}
