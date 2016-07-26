package com.smithsmodding.armory.common.block;

import com.smithsmodding.armory.api.util.references.ModCreativeTabs;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.block.types.EnumConduitType;
import com.smithsmodding.armory.common.tileentity.TileEntityConduit;
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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.List;

/**
 * Author Orion (Created on: 24.07.2016)
 */
public class BlockConduit extends BlockArmoryTileEntity {
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");
    public static final PropertyBool DOWN = PropertyBool.create("down");
    public static final PropertyBool UP = PropertyBool.create("up");

    public static final PropertyEnum<EnumConduitType> TYPE = PropertyEnum.create("type", EnumConduitType.class);

    private static final HashMap<EnumFacing, IProperty> sideProperties = new HashMap<>();

    static {
        sideProperties.put(EnumFacing.NORTH, NORTH);
        sideProperties.put(EnumFacing.SOUTH, SOUTH);
        sideProperties.put(EnumFacing.EAST, EAST);
        sideProperties.put(EnumFacing.WEST, WEST);
        sideProperties.put(EnumFacing.UP, UP);
        sideProperties.put(EnumFacing.DOWN, DOWN);
    }

    public BlockConduit() {
        super(References.InternalNames.Blocks.Conduit, Material.IRON);
        this.setDefaultState(this.blockState.getBaseState().withProperty(DOWN, Boolean.valueOf(false)).withProperty(UP, Boolean.valueOf(false)).withProperty(NORTH, Boolean.valueOf(false)).withProperty(EAST, Boolean.valueOf(false)).withProperty(SOUTH, Boolean.valueOf(false)).withProperty(WEST, Boolean.valueOf(false)).withProperty(TYPE, EnumConduitType.NORMAL));
        setCreativeTab(ModCreativeTabs.generalTab);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityConduit();
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
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        list.add(new ItemStack(this, 1, 1));
        list.add(new ItemStack(this, 1, 2));
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (stack.getMetadata() == 1) {
            worldIn.setBlockState(pos, state.withProperty(TYPE, EnumConduitType.NORMAL));
        } else {
            worldIn.setBlockState(pos, state.withProperty(TYPE, EnumConduitType.LIGHT));
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TYPE, EnumConduitType.byMetadata(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE).getMetadata();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{NORTH, EAST, SOUTH, WEST, UP, DOWN, TYPE});
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        return handleEnvironmentChange(world, pos, state);
    }

    private IBlockState handleEnvironmentChange(IBlockAccess world, BlockPos pos, IBlockState state) {
        for (EnumFacing facing : EnumFacing.values()) {
            BlockPos target = pos.add(facing.getDirectionVec());

            TileEntity tileEntity = world.getTileEntity(target);

            if (tileEntity instanceof IFluidContainingEntity && !(tileEntity instanceof TileEntityConduit)) {
                state = state.withProperty(sideProperties.get(facing), true);
            } else if (tileEntity instanceof TileEntityConduit) {
                IBlockState targetConduitState = world.getBlockState(pos);

                if (targetConduitState.getValue(TYPE) == state.getValue(TYPE)) {
                    state = state.withProperty(sideProperties.get(facing), true);
                } else {
                    state = state.withProperty(sideProperties.get(facing), false);
                }
            } else {
                state = state.withProperty(sideProperties.get(facing), false);
            }
        }

        return state;
    }
}
