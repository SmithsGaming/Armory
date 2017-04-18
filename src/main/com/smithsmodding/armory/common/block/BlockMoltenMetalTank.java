package com.smithsmodding.armory.common.block;

import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.common.material.core.IMaterial;
import com.smithsmodding.armory.api.common.material.core.RegistryMaterialWrapper;
import com.smithsmodding.armory.api.util.references.ModCreativeTabs;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.block.types.EnumTankType;
import com.smithsmodding.armory.common.tileentity.TileEntityBlackSmithsAnvil;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
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
        ArrayList<String> materialOreDicNames = new ArrayList<>();

        for (RegistryMaterialWrapper wrapper : IArmoryAPI.Holder.getInstance().getRegistryManager().getCombinedMaterialRegistry()) {
            if (materialOreDicNames.contains(wrapper.getWrapped().getOreDictionaryIdentifier()))
                continue;

            list.add(generateItemStackForMaterial(wrapper.getWrapped(), EnumTankType.NORMAL));

            materialOreDicNames.add(wrapper.getWrapped().getOreDictionaryIdentifier());
        }

        list.add(new ItemStack(this, 1, EnumTankType.NORMAL.getMetadata()));
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

    @Override
    public java.util.List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        java.util.List<ItemStack> ret = new ArrayList<>();
        TileEntityMoltenMetalTank te = world.getTileEntity(pos) instanceof TileEntityBlackSmithsAnvil ? (TileEntityMoltenMetalTank) world.getTileEntity(pos) : null;
        if (te != null)
            ret.add(generateItemStackFromWorldPos(world, pos, state));
        return ret;
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        if (willHarvest) return true; //If it will harvest, delay deletion of the block until after getDrops
        return super.removedByPlayer(state, world, pos, player, willHarvest);
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack tool) {
        super.harvestBlock(world, player, pos, state, te, tool);
        world.setBlockToAir(pos);
    }

    @Nullable
    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return generateItemStackFromWorldPos(world, pos, state);
    }

    private ItemStack generateItemStackFromWorldPos(IBlockAccess world, BlockPos pos, IBlockState state) {
        ItemStack stack = new ItemStack(Item.getItemFromBlock(state.getBlock()), 1, getMetaFromState(state));
        NBTTagCompound compound = new NBTTagCompound();

        TileEntityMoltenMetalTank tank = (TileEntityMoltenMetalTank) world.getTileEntity(pos);

        if (tank.getTankForSide(null).drain(Integer.MAX_VALUE, false) != null) {
            compound.setTag(References.NBTTagCompoundData.TE.MoltenMetalTank.CONTENTS, ((TileEntityMoltenMetalTank) world.getTileEntity(pos)).getTankForSide(null).drain(Integer.MAX_VALUE, false).writeToNBT(new NBTTagCompound()));
        }

        stack.setTagCompound(compound);

        return stack;
    }

    private ItemStack generateItemStackForFluid(@Nonnull FluidStack fluidStack, EnumTankType type) {
        ItemStack stack = new ItemStack(Item.getItemFromBlock(this), 1, type.getMetadata());
        NBTTagCompound compound = new NBTTagCompound();

        compound.setTag(References.NBTTagCompoundData.TE.MoltenMetalTank.CONTENTS, fluidStack.writeToNBT(new NBTTagCompound()));

        stack.setTagCompound(compound);

        return stack;
    }

    private ItemStack generateItemStackForMaterial(@Nonnull IMaterial material, EnumTankType type) {
        NBTTagCompound fluidCompound = new NBTTagCompound();
        fluidCompound.setString(References.NBTTagCompoundData.Fluids.MoltenMetal.MATERIAL, material.getRegistryName().toString());
        FluidStack fluidStack = new FluidStack(material.getFluidForMaterial(), type.getTankContents(), fluidCompound);

        return generateItemStackForFluid(fluidStack, type);
    }

}
