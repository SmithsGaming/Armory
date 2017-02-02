package com.smithsmodding.armory.common.block;

import com.smithsmodding.armory.api.util.references.ModCreativeTabs;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.block.types.EnumTankType;
import com.smithsmodding.armory.common.tileentity.TileEntityMoltenMetalTank;
import com.smithsmodding.smithscore.common.fluid.IFluidContainingEntity;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.HashMap;

/**
 * Author Orion (Created on: 26.07.2016)
 */
public class BlockMoltenMetalTank extends BlockArmoryTileEntity {

    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");

    public static final PropertyEnum<EnumTankType> TYPE = PropertyEnum.create("type", EnumTankType.class);

    private static final HashMap<EnumFacing, IProperty> sideProperties = new HashMap<>();

    static {
        sideProperties.put(EnumFacing.NORTH, NORTH);
        sideProperties.put(EnumFacing.SOUTH, SOUTH);
        sideProperties.put(EnumFacing.EAST, EAST);
        sideProperties.put(EnumFacing.WEST, WEST);
    }

    public BlockMoltenMetalTank() {
        super(References.InternalNames.Blocks.Tank, Material.IRON);
        this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, Boolean.valueOf(false)).withProperty(EAST, Boolean.valueOf(false)).withProperty(SOUTH, Boolean.valueOf(false)).withProperty(WEST, Boolean.valueOf(false)).withProperty(TYPE, EnumTankType.NORMAL));
        setCreativeTab(ModCreativeTabs.GENERAL);
    }

    @Nonnull
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityMoltenMetalTank(EnumTankType.byMetadata(meta));
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
    public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list) {
        list.add(new ItemStack(this, 1, 1));
        list.add(new ItemStack(this, 1, 2));
    }

    @Override
    public void onBlockPlacedBy(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, EntityLivingBase placer, @Nonnull ItemStack stack) {
        if (stack.getMetadata() == 1) {
            worldIn.setBlockState(pos, state.withProperty(TYPE, EnumTankType.NORMAL));
        } else {
            worldIn.setBlockState(pos, state.withProperty(TYPE, EnumTankType.LIGHT));
        }
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TYPE, EnumTankType.byMetadata(meta));
    }

    @Override
    public int getMetaFromState(@Nonnull IBlockState state) {
        return state.getValue(TYPE).getMetadata();
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, NORTH, EAST, SOUTH, WEST, TYPE);
    }

    @Override
    public IBlockState getActualState(IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
        return handleEnvironmentChange(world, pos, state);
    }

    private IBlockState handleEnvironmentChange(@Nonnull IBlockAccess world, @Nonnull BlockPos pos, IBlockState state) {
        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
            BlockPos target = pos.add(facing.getDirectionVec());

            TileEntity tileEntity = world.getTileEntity(target);

            if (tileEntity instanceof IFluidContainingEntity) {
                state = state.withProperty(sideProperties.get(facing), true);
            } else {
                state = state.withProperty(sideProperties.get(facing), false);
            }
        }

        return state;
    }
}
