package com.smithsmodding.armory.common.block;

import com.smithsmodding.armory.Armory;
import com.smithsmodding.armory.api.util.references.ModCreativeTabs;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.tileentity.TileEntityMoltenMetalMixer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * Created by Marc on 27.02.2016.
 */
public class BlockMoltenMetalMixer extends BlockArmoryTileEntity {

    public static final PropertyDirection DIRECTION = BlockHorizontal.FACING;

    public BlockMoltenMetalMixer() {
        super(References.InternalNames.Blocks.MoltenMetalMixer, Material.IRON);
        setCreativeTab(ModCreativeTabs.GENERAL);
        this.setDefaultState(this.blockState.getBaseState().withProperty(DIRECTION, EnumFacing.NORTH));
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
        return new TileEntityMoltenMetalMixer();
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, DIRECTION);
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     *
     * @param meta
     */
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(DIRECTION, EnumFacing.getHorizontal(meta));
    }

    /**
     * Convert the BlockState into the correct metadata value
     *
     * @param state
     */
    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(DIRECTION).getHorizontalIndex();
    }

    /**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     *
     * @param worldIn
     * @param pos
     * @param state
     * @param placer
     * @param stack
     */
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        state = state.withProperty(DIRECTION, placer.getHorizontalFacing().getOpposite());

        worldIn.setBlockState(pos, state, 2);

        ((TileEntityMoltenMetalMixer) worldIn.getTileEntity(pos)).setFacing(placer.getHorizontalFacing().getOpposite());
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

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (playerIn.isSneaking()) {
            return false;
        } else {
            if (!worldIn.isRemote) {
                if (worldIn.getTileEntity(pos) instanceof TileEntityMoltenMetalMixer) {
                    playerIn.openGui(Armory.instance, References.GuiIDs.MOLTENMETALMIXER, worldIn, pos.getX(), pos.getY(), pos.getZ());
                }
            }
            return true;
        }
    }

    public static EnumFacing getFacing(IBlockAccess world, BlockPos pos) {
        return ((TileEntityMoltenMetalMixer) world.getTileEntity(pos)).getFacing();
    }
}
