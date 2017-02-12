package com.smithsmodding.armory.common.block;

import com.smithsmodding.armory.api.util.references.ModCapabilities;
import com.smithsmodding.armory.api.util.references.ModCreativeTabs;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.block.types.EnumConduitType;
import com.smithsmodding.armory.common.block.types.EnumPumpType;
import com.smithsmodding.armory.common.tileentity.TileEntityConduit;
import com.smithsmodding.armory.common.tileentity.TileEntityPump;
import com.smithsmodding.smithscore.SmithsCore;
import com.smithsmodding.smithscore.client.block.ICustomDebugInformationBlock;
import com.smithsmodding.smithscore.common.structures.StructureRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Author Orion (Created on: 24.07.2016)
 */
public class BlockConduit extends BlockArmoryTileEntity implements ICustomDebugInformationBlock {
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
        setCreativeTab(ModCreativeTabs.GENERAL);
    }

    @Nonnull
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
    public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list) {
        list.add(new ItemStack(this, 1, 1));
        list.add(new ItemStack(this, 1, 2));
        list.add(new ItemStack(this, 1, 3));
    }

    @Override
    public void onBlockPlacedBy(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, EntityLivingBase placer, @Nonnull ItemStack stack) {
        if (stack.getMetadata() == 3) {
            worldIn.setBlockState(pos, state.withProperty(TYPE, EnumConduitType.VERTICAL));
        } else if (stack.getMetadata() == 2) {
            worldIn.setBlockState(pos, state.withProperty(TYPE, EnumConduitType.LIGHT));
        } else {
            worldIn.setBlockState(pos, state.withProperty(TYPE, EnumConduitType.NORMAL));
        }

        if (!worldIn.isRemote) {
            TileEntityConduit conduit = (TileEntityConduit) worldIn.getTileEntity(pos);

            if (conduit instanceof TileEntityConduit) {
                conduit.setWorld(worldIn);
                StructureRegistry.getInstance().onStructurePartPlaced(conduit);

                worldIn.markChunkDirty(pos, conduit);
            }
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            if (worldIn.getTileEntity(pos) instanceof TileEntityConduit) {
                TileEntityConduit conduit = (TileEntityConduit) worldIn.getTileEntity(pos);

                conduit.getStructure().getController().onPartDestroyed(conduit);
            }
        }

        super.breakBlock(worldIn, pos, state);
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TYPE, EnumConduitType.byMetadata(meta));
    }

    @Override
    public int getMetaFromState(@Nonnull IBlockState state) {
        return state.getValue(TYPE).getMetadata();
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, NORTH, EAST, SOUTH, WEST, UP, DOWN, TYPE);
    }

    @Override
    public IBlockState getActualState(IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
        return handleEnvironmentChange(world, pos, state);
    }

    private IBlockState handleEnvironmentChange(@Nonnull IBlockAccess world, @Nonnull BlockPos pos, IBlockState state) {
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

    private IBlockState handleStandardEnvironmentChange(@Nonnull IBlockAccess world, @Nonnull BlockPos pos, IBlockState state) {
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
                if (world.getBlockState(target).getValue(BlockPump.TYPE) == EnumPumpType.HORIZONTAL) {
                    if (world.getBlockState(target).getValue(BlockPump.DIRECTION).getOpposite() == facing ||
                            world.getBlockState(target).getValue(BlockPump.DIRECTION) == facing) {
                        connectedSides.add(facing);
                        state = state.withProperty(SIDEPROPERTIES.get(facing), true);
                    }
                } else {
                    if (world.getBlockState(target).getValue(BlockPump.DIRECTION).getOpposite() == facing) {
                        connectedSides.add(facing);
                        state = state.withProperty(SIDEPROPERTIES.get(facing), true);
                    }
                }

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

    private IBlockState handleVerticalEnvironmentChange(@Nonnull IBlockAccess world, @Nonnull BlockPos pos, IBlockState state) {
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
            } else if (tileEntityDown instanceof TileEntityPump) {
                if (world.getBlockState(targetDown).getValue(BlockPump.TYPE) == EnumPumpType.VERTICAL) {
                    connectedSides.add(EnumFacing.DOWN);
                    state = state.withProperty(SIDEPROPERTIES.get(EnumFacing.DOWN), false);
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

    /**
     * Method to handle displaying or removing of additional information on the F3 Screen.
     *
     * @param event   The event with the displayed data.
     * @param worldIn The world
     * @param pos     Position of the block the player is looking at.
     */
    @Override
    public void handleDebugInformation(@Nonnull RenderGameOverlayEvent.Text event, @Nonnull World worldIn, @Nonnull BlockPos pos) {
        if (!SmithsCore.isInDevenvironment() && !Minecraft.getMinecraft().gameSettings.showDebugInfo)
            return;

        TileEntityConduit conduit = (TileEntityConduit) worldIn.getTileEntity(pos);

        if (conduit.getStructure() == null)
            return;

        TextFormatting slaveCount;
        int count;
        if (!conduit.getStructure().getMasterLocation().equals(conduit.getLocation())) {
            slaveCount = TextFormatting.STRIKETHROUGH;
            count = -2;
        } else if (conduit.getStructure().getPartLocations() == null) {
            slaveCount = TextFormatting.UNDERLINE;
            count = -1;
        } else if (conduit.getStructure().getPartLocations().size() == 1) {
            slaveCount = TextFormatting.RED;
            count = 0;
        } else {
            slaveCount = TextFormatting.GREEN;
            count = conduit.getStructure().getPartLocations().size() - 1;
        }

        TextFormatting masterTeLocation;
        String location;
        if (conduit.getStructure().getMasterLocation().equals(conduit.getLocation())) {
            masterTeLocation = TextFormatting.STRIKETHROUGH;
            location = "current";
        } else if (conduit.getStructure().getMasterLocation() == null) {
            masterTeLocation = TextFormatting.RED;
            location = "unknown";
        } else {
            masterTeLocation = TextFormatting.GREEN;
            location = conduit.getStructure().getMasterLocation().toString();
        }

        event.getRight().add("slave count:" + slaveCount + count + TextFormatting.RESET);
        event.getRight().add("masterlocation:" + masterTeLocation + location + TextFormatting.RESET);
    }
}
