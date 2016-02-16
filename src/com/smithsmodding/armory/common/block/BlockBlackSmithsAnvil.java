package com.smithsmodding.armory.common.block;

import com.smithsmodding.armory.*;
import com.smithsmodding.armory.common.tileentity.*;
import com.smithsmodding.armory.util.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

/**
 * Created by Marc on 14.02.2016.
 */
public class BlockBlackSmithsAnvil extends BlockArmoryInventory
{
    public BlockBlackSmithsAnvil () {
        super(References.InternalNames.Blocks.ArmorsAnvil, Material.anvil);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityBlackSmithsAnvil();
    }

    public void onBlockPlacedBy(World pWorld, int pX, int pY, int pZ, EntityLivingBase pPlacingEntity, ItemStack pItemStack) {
        int l = MathHelper.floor_double((double) (pPlacingEntity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        TileEntityBlackSmithsAnvil tTE = (TileEntityBlackSmithsAnvil) pWorld.getTileEntity(new BlockPos(pX, pY, pZ));

        if (l == 0) {
            tTE.setDirection(EnumFacing.NORTH);
        }

        if (l == 1) {
            tTE.setDirection(EnumFacing.EAST);
        }

        if (l == 2) {
            tTE.setDirection(EnumFacing.SOUTH);
        }

        if (l == 3) {
            tTE.setDirection(EnumFacing.WEST);
        }

        if (pItemStack.hasDisplayName()) {
            tTE.setDisplayName(pItemStack.getDisplayName());
        }


    }

    @Override
    public int getRenderType() {
        return 3;
    }

    @Override
    public boolean canRenderInLayer (EnumWorldBlockLayer layer) {
        return layer == EnumWorldBlockLayer.CUTOUT;
    }

    @Override
    public boolean isTranslucent () {
        return true;
    }

    @Override
    public boolean isOpaqueCube () {
        return false;
    }

    @Override
    public boolean isFullCube () {
        return false;
    }

    @Override
    public boolean isVisuallyOpaque () {
        return false;
    }

    @Override
    public boolean onBlockActivated (World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (playerIn.isSneaking()) {
            return false;
        } else {
            if (!worldIn.isRemote) {
                if (worldIn.getTileEntity(pos) instanceof TileEntityBlackSmithsAnvil) {
                    playerIn.openGui(Armory.instance, References.GuiIDs.ANVILID, worldIn, pos.getX(), pos.getY(), pos.getZ());
                }
            }
            return true;
        }
    }
}
