package com.smithsmodding.armory.common.block;

import com.smithsmodding.armory.Armory;
import com.smithsmodding.armory.common.tileentity.TileEntityFireplace;
import com.smithsmodding.armory.util.References;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;

/**
 * Created by Marc on 27.02.2016.
 */
public class BlockFirePlace extends BlockArmoryInventory {

    public BlockFirePlace() {
        super(References.InternalNames.Blocks.Fireplace, Material.rock);
        setCreativeTab(CreativeTabs.tabCombat);
        this.setDefaultState(this.blockState.getBaseState());
    }


    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     *
     * @param worldIn
     * @param meta
     */
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityFireplace();
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this);
    }

    @Override
    public boolean canRenderInLayer(EnumWorldBlockLayer layer) {
        return layer == EnumWorldBlockLayer.CUTOUT;
    }

    @Override
    public boolean isTranslucent() {
        return true;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    @Override
    public boolean isVisuallyOpaque() {
        return false;
    }

    @Override
    public int getRenderType() {
        return 3;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (playerIn.isSneaking()) {
            return false;
        } else {
            if (!worldIn.isRemote) {
                if (worldIn.getTileEntity(pos) instanceof TileEntityFireplace) {
                    playerIn.openGui(Armory.instance, References.GuiIDs.FIREPLACEID, worldIn, pos.getX(), pos.getY(), pos.getZ());
                }
            }
            return true;
        }
    }
}
