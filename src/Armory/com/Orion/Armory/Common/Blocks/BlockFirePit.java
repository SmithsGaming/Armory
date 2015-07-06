package com.Orion.Armory.Common.Blocks;
/*
/  BlockFirePit
/  Created by : Orion
/  Created on : 02/10/2014
*/

import com.Orion.Armory.Armory;
import com.Orion.Armory.Common.Registry.GeneralRegistry;
import com.Orion.Armory.Common.TileEntity.Core.Multiblock.IStructureComponent;
import com.Orion.Armory.Common.TileEntity.Core.Multiblock.StructureManager;
import com.Orion.Armory.Common.TileEntity.FirePit.TileEntityFirePit;
import com.Orion.Armory.Util.Core.Coordinate;
import com.Orion.Armory.Util.Core.Rectangle;
import com.Orion.Armory.Util.References;
import com.sun.javaws.exceptions.InvalidArgumentException;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.Random;

public class BlockFirePit extends BlockContainer
{
    public BlockFirePit() {
        super(Material.iron);
        setBlockName(References.InternalNames.Blocks.FirePit);
        setHardness(5F);
        setResistance(10F);
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
    public void onBlockPlacedBy(World pWorld, int pX, int pY, int pZ, EntityLivingBase pPlacingEntity, ItemStack pItemStack)
    {
        TileEntityFirePit tTE = (TileEntityFirePit) pWorld.getTileEntity(pX, pY, pZ);

        if (pItemStack.hasDisplayName())
        {
            tTE.setDisplayName(pItemStack.getDisplayName());
        }

        if (!pWorld.isRemote) {
            if (pWorld.getTileEntity(pX, pY, pZ) instanceof TileEntityFirePit) {
                StructureManager.createStructureComponent(tTE);
            }
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
        return References.RenderIDs.FirePitID;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean onBlockActivated(World pWorld, int pX, int pY, int pZ, EntityPlayer pPlayer, int pFaceHit, float par7, float par8, float par9)
    {
        if (pPlayer.isSneaking())
        {
            return false;
        }
        else
        {
            if (!pWorld.isRemote) {
                if (pWorld.getTileEntity(pX, pY, pZ) instanceof TileEntityFirePit) {
                    pPlayer.openGui(Armory.instance, References.GuiIDs.FIREPITID, pWorld, pX, pY, pZ);
                }
            }
            return true;
        }
    }
}
