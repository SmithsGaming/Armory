package com.smithsmodding.armory.common.block;

import com.smithsmodding.armory.api.util.references.ModCapabilities;
import com.smithsmodding.armory.api.util.references.ModCreativeTabs;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.block.types.EnumConduitType;
import com.smithsmodding.armory.common.tileentity.TileEntityConduit;
import com.smithsmodding.armory.common.tileentity.TileEntityPump;
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
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
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

    public static final HashMap<EnumFacing, IProperty<Boolean>> SIDEPROPERTIES = new HashMap<>();

    static {
        SIDEPROPERTIES.put(EnumFacing.NORTH, NORTH);
        SIDEPROPERTIES.put(EnumFacing.SOUTH, SOUTH);
        SIDEPROPERTIES.put(EnumFacing.EAST, EAST);
        SIDEPROPERTIES.put(EnumFacing.WEST, WEST);
        SIDEPROPERTIES.put(EnumFacing.UP, UP);
        SIDEPROPERTIES.put(EnumFacing.DOWN, DOWN);
    }

    public BlockConduit() {
        super(References.InternalNames.Blocks.Conduit, Material.IRON);
        this.setDefaultState(this.blockState.getBaseState().withProperty(DOWN, Boolean.valueOf(false)).withProperty(UP, Boolean.valueOf(false)).withProperty(NORTH, Boolean.valueOf(false)).withProperty(EAST, Boolean.valueOf(false)).withProperty(SOUTH, Boolean.valueOf(false)).withProperty(WEST, Boolean.valueOf(false)).withProperty(TYPE, EnumConduitType.NORMAL));
        setCreativeTab(ModCreativeTabs.generalTab);
    }

    @NotNull
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityConduit(EnumConduitType.byMetadata(meta));
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
        list.add(new ItemStack(this, 1, 3));
    }

    @Override
    public void onBlockPlacedBy(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, EntityLivingBase placer, @NotNull ItemStack stack) {
        if (stack.getMetadata() == 3) {
            worldIn.setBlockState(pos, state.withProperty(TYPE, EnumConduitType.VERTICAL));
        } else if (stack.getMetadata() == 2) {
            worldIn.setBlockState(pos, state.withProperty(TYPE, EnumConduitType.LIGHT));
        } else {
            worldIn.setBlockState(pos, state.withProperty(TYPE, EnumConduitType.NORMAL));
        }
    }

    @NotNull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TYPE, EnumConduitType.byMetadata(meta));
    }

    @Override
    public int getMetaFromState(@NotNull IBlockState state) {
        return state.getValue(TYPE).getMetadata();
    }

    @NotNull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, NORTH, EAST, SOUTH, WEST, UP, DOWN, TYPE);
    }

    @Override
    public IBlockState getActualState(IBlockState state, @NotNull IBlockAccess world, @NotNull BlockPos pos) {
        return handleEnvironmentChange(world, pos, state);
    }

    private IBlockState handleEnvironmentChange(@NotNull IBlockAccess world, @NotNull BlockPos pos, IBlockState state) {
        switch (state.getValue(TYPE)) {
            case LIGHT:
            case NORMAL:
                return handleStandardEnvironmentChange(world, pos, state);
            case VERTICAL:
                return handleVerticalEnvironmentChange(world, pos, state);
            default:
                return state;
        }

    }

    private IBlockState handleStandardEnvironmentChange(@NotNull IBlockAccess world, @NotNull BlockPos pos, IBlockState state) {
        ArrayList<EnumFacing> connectedSides = new ArrayList<>();

        TileEntityConduit conduit = (TileEntityConduit) world.getTileEntity(pos);

        for (EnumFacing facing : EnumFacing.VALUES) {
            if (facing == EnumFacing.DOWN)
                continue;

            BlockPos target = pos.offset(facing);

            TileEntity tileEntity = world.getTileEntity(target);
            if (tileEntity == null) {
                state = state.withProperty(SIDEPROPERTIES.get(facing), false);
                continue;
            }

            if (tileEntity instanceof TileEntityConduit) {
                TileEntityConduit neighbor = (TileEntityConduit) tileEntity;

                if (conduit.getType() == neighbor.getType()) {
                    connectedSides.add(facing);
                    state = state.withProperty(SIDEPROPERTIES.get(facing), true);
                }
            } else if (tileEntity instanceof TileEntityPump) {
                if (world.getBlockState(target).getValue(BlockPump.DIRECTION).getOpposite() == facing) {
                    connectedSides.add(facing);
                    state = state.withProperty(SIDEPROPERTIES.get(facing), true);
                }
            } else if (tileEntity.hasCapability(ModCapabilities.MOD_MOLTENMETAL_ACCEPTOR_CAPABILITY, facing.getOpposite())) {
                connectedSides.add(facing);
                state = state.withProperty(SIDEPROPERTIES.get(facing), true);
            } else {
                state = state.withProperty(SIDEPROPERTIES.get(facing), false);
            }
        }

        BlockPos target = pos.offset(EnumFacing.DOWN);

        TileEntity tileEntity = world.getTileEntity(target);
        if (tileEntity == null) {
            state = state.withProperty(SIDEPROPERTIES.get(EnumFacing.DOWN), false);
        } else {
            if (tileEntity instanceof TileEntityConduit) {
                TileEntityConduit neighbor = (TileEntityConduit) tileEntity;

                if (conduit.getType() == neighbor.getType() || neighbor.getType() == EnumConduitType.VERTICAL) {
                    connectedSides.add(EnumFacing.DOWN);
                    state = state.withProperty(SIDEPROPERTIES.get(EnumFacing.DOWN), true);
                }
            } else if (tileEntity.hasCapability(ModCapabilities.MOD_MOLTENMETAL_ACCEPTOR_CAPABILITY, EnumFacing.UP)) {
                connectedSides.add(EnumFacing.DOWN);
                state = state.withProperty(SIDEPROPERTIES.get(EnumFacing.DOWN), true);
            } else {
                state = state.withProperty(SIDEPROPERTIES.get(EnumFacing.DOWN), false);
            }
        }

        if (connectedSides.contains(EnumFacing.UP))
            connectedSides.remove(EnumFacing.UP);

        conduit.setConnectedSides(connectedSides);

        return state;
    }

    private IBlockState handleVerticalEnvironmentChange(@NotNull IBlockAccess world, @NotNull BlockPos pos, IBlockState state) {
        ArrayList<EnumFacing> connectedSides = new ArrayList<>();

        TileEntityConduit conduit = (TileEntityConduit) world.getTileEntity(pos);

        BlockPos targetUp = pos.offset(EnumFacing.UP);

        TileEntity tileEntityUp = world.getTileEntity(targetUp);
        if (tileEntityUp == null) {
            state = state.withProperty(SIDEPROPERTIES.get(EnumFacing.UP), false);
        } else {
            if (tileEntityUp instanceof TileEntityConduit) {
                TileEntityConduit neighbor = (TileEntityConduit) tileEntityUp;

                if (conduit.getType() == neighbor.getType()) {
                    connectedSides.add(EnumFacing.UP);
                    state = state.withProperty(SIDEPROPERTIES.get(EnumFacing.UP), true);
                }
            } else if (tileEntityUp.hasCapability(ModCapabilities.MOD_MOLTENMETAL_ACCEPTOR_CAPABILITY, EnumFacing.DOWN)) {
                connectedSides.add(EnumFacing.UP);
                state = state.withProperty(SIDEPROPERTIES.get(EnumFacing.UP), true);
            } else {
                state = state.withProperty(SIDEPROPERTIES.get(EnumFacing.UP), false);
            }
        }

        BlockPos targetDown = pos.offset(EnumFacing.DOWN);

        TileEntity tileEntityDown = world.getTileEntity(targetDown);
        if (tileEntityDown == null) {
            state = state.withProperty(SIDEPROPERTIES.get(EnumFacing.DOWN), false);
        } else {
            if (tileEntityDown instanceof TileEntityConduit) {
                TileEntityConduit neighbor = (TileEntityConduit) tileEntityDown;

                if (conduit.getType() == neighbor.getType()) {
                    connectedSides.add(EnumFacing.DOWN);
                    state = state.withProperty(SIDEPROPERTIES.get(EnumFacing.DOWN), true);
                }
            } else if (tileEntityDown.hasCapability(ModCapabilities.MOD_MOLTENMETAL_ACCEPTOR_CAPABILITY, EnumFacing.UP)) {
                connectedSides.add(EnumFacing.DOWN);
                state = state.withProperty(SIDEPROPERTIES.get(EnumFacing.DOWN), true);
            } else {
                state = state.withProperty(SIDEPROPERTIES.get(EnumFacing.DOWN), false);
            }
        }

        conduit.setConnectedSides(connectedSides);
        
        return state;
    }
}
