package com.smithsmodding.armory.common.block;

import com.smithsmodding.armory.Armory;
import com.smithsmodding.armory.api.util.references.ModCreativeTabs;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.tileentity.TileEntityFireplace;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

/**
 * Created by Marc on 27.02.2016.
 */
public class BlockFirePlace extends BlockArmoryTileEntity {

    public BlockFirePlace() {
        super(References.InternalNames.Blocks.Fireplace, Material.ROCK);
        setCreativeTab(ModCreativeTabs.generalTab);
        this.setDefaultState(this.blockState.getBaseState());
    }


    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     *
     * @param worldIn
     * @param meta
     */
    @Nonnull
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityFireplace();
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this);
    }

    @Nonnull
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return layer == BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isTranslucent(IBlockState state) {
        return true;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullyOpaque(IBlockState state) {
        return false;
    }

    /**
     * Called when the block is right clicked by a player.
     *
     * @param worldIn
     * @param pos
     * @param state
     * @param playerIn
     * @param hand
     * @param facing
     * @param hitX
     * @param hitY
     * @param hitZ
     */
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
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
