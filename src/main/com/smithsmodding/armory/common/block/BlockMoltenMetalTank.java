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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.List;

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
        setCreativeTab(ModCreativeTabs.generalTab);
    }

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
    public boolean isVisuallyOpaque() {
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
            worldIn.setBlockState(pos, state.withProperty(TYPE, EnumTankType.NORMAL));
        } else {
            worldIn.setBlockState(pos, state.withProperty(TYPE, EnumTankType.LIGHT));
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TYPE, EnumTankType.byMetadata(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE).getMetadata();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{NORTH, EAST, SOUTH, WEST, TYPE});
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        return handleEnvironmentChange(world, pos, state);
    }

    private IBlockState handleEnvironmentChange(IBlockAccess world, BlockPos pos, IBlockState state) {
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
