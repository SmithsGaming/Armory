package com.smithsmodding.armory.common.block;

import com.smithsmodding.armory.api.util.references.ModCreativeTabs;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.block.types.EnumPumpType;
import com.smithsmodding.armory.common.tileentity.TileEntityPump;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * Author Orion (Created on: 11.10.2016)
 */
public class BlockPump extends BlockArmoryTileEntity {

    public static final PropertyDirection DIRECTION = BlockHorizontal.FACING;
    public static final PropertyEnum<EnumPumpType> TYPE = PropertyEnum.create("type", EnumPumpType.class);

    public BlockPump() {
        super(References.InternalNames.Blocks.ConduitPump, Material.IRON);
        this.setDefaultState(this.blockState.getBaseState().withProperty(DIRECTION, EnumFacing.NORTH).withProperty(TYPE, EnumPumpType.HORIZONTAL));
        setCreativeTab(ModCreativeTabs.GENERAL);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityPump();
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list) {
        list.add(new ItemStack(this, 1, 1));
        list.add(new ItemStack(this, 1, 2));
    }

    @Override
    public void onBlockPlacedBy(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, EntityLivingBase placer, @Nonnull ItemStack stack) {
        state = state.withProperty(TYPE, EnumPumpType.byMetadata(stack.getItemDamage() - 1));

        state = state.withProperty(DIRECTION, placer.getHorizontalFacing().getOpposite());

        worldIn.setBlockState(pos, state, 2);
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TYPE, EnumPumpType.byMetadata(meta));
    }

    @Override
    public int getMetaFromState(@Nonnull IBlockState state) {
        return state.getValue(TYPE).getMetadata();
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, DIRECTION, TYPE);
    }
}