package com.smithsmodding.armory.common.block;

import com.google.common.collect.Lists;
import com.smithsmodding.armory.Armory;
import com.smithsmodding.armory.api.common.material.anvil.IAnvilMaterial;
import com.smithsmodding.armory.api.util.references.ModCreativeTabs;
import com.smithsmodding.armory.api.util.references.ModMaterials;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.api.ArmoryAPI;
import com.smithsmodding.armory.common.block.properties.PropertyAnvilMaterial;
import com.smithsmodding.armory.common.tileentity.TileEntityBlackSmithsAnvil;
import com.smithsmodding.smithscore.SmithsCore;
import com.smithsmodding.smithscore.client.block.ICustomDebugInformationBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.ArrayList;

/**
 * Created by Marc on 14.02.2016.
 */
public class BlockBlackSmithsAnvil extends BlockArmoryTileEntity implements ICustomDebugInformationBlock {

    public static final PropertyAnvilMaterial PROPERTY_ANVIL_MATERIAL = new PropertyAnvilMaterial("CoreMaterial");
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    @Nonnull
    private ExtendedBlockState state = new ExtendedBlockState(this, new IProperty[]{FACING}, new IUnlistedProperty[]{OBJModel.OBJProperty.INSTANCE, PROPERTY_ANVIL_MATERIAL});


    public BlockBlackSmithsAnvil() {
        super(References.InternalNames.Blocks.ArmorsAnvil, Material.ANVIL);
        setCreativeTab(ModCreativeTabs.generalTab);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @Nonnull
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityBlackSmithsAnvil();
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

    /**
     * Gets the localized name of this block. Used for the statistics page.
     */
    @Nonnull
    @Override
    public String getLocalizedName() {
        return super.getLocalizedName();
    }

    /**
     * Gets the {@link IBlockState} to place
     *
     * @param world  The world the block is being placed in
     * @param pos    The position the block is being placed at
     * @param facing The side the block is being placed on
     * @param hitX   The X coordinate of the hit vector
     * @param hitY   The Y coordinate of the hit vector
     * @param hitZ   The Z coordinate of the hit vector
     * @param meta   The metadata of {@link ItemStack} as processed by {@link Item#getMetadata(int)}
     * @param placer The entity placing the block
     * @param hand   The player hand used to place this block
     * @return The state to be placed in the world
     */
    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public java.util.List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        java.util.List<ItemStack> ret = new ArrayList<>();
        TileEntityBlackSmithsAnvil te = world.getTileEntity(pos) instanceof TileEntityBlackSmithsAnvil ? (TileEntityBlackSmithsAnvil) world.getTileEntity(pos) : null;
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
    public void onBlockPlacedBy(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityLivingBase placer, @Nonnull ItemStack stack) {
        String materialID = stack.getTagCompound().getString(References.NBTTagCompoundData.TE.Anvil.MATERIAL);

        ((TileEntityBlackSmithsAnvil) worldIn.getTileEntity(pos)).getState().setMaterial(ArmoryAPI.getInstance().getRegistryManager().getAnvilMaterialRegistry().getValue(new ResourceLocation(materialID)));

        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);

        worldIn.getTileEntity(pos).markDirty();
    }

    @Nullable
    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return generateItemStackFromWorldPos(world, pos, state);
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     *
     * @param itemIn
     * @param tab
     * @param list
     */
    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list) {
        for (IAnvilMaterial material : ArmoryAPI.getInstance().getRegistryManager().getAnvilMaterialRegistry()) {
            ItemStack stack = new ItemStack(Item.getItemFromBlock(this));

            NBTTagCompound compound = new NBTTagCompound();
            compound.setString(References.NBTTagCompoundData.TE.Anvil.MATERIAL, material.getRegistryName().toString());

            stack.setTagCompound(compound);

            list.add(stack);
        }
    }

    /**
     * Get the actual Block state of this Block at the given position. This applies properties not visible in the
     * metadata, such as fence connections.
     *
     * @param state
     * @param world
     * @param pos
     */
    @Override
    public IBlockState getExtendedState(IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
        if (world.getTileEntity(pos) == null) return this.state.getBaseState();

        EnumFacing facing = state.getValue(FACING);
        TRSRTransformation transformation = new TRSRTransformation(facing);

        OBJModel.OBJState objState = new OBJModel.OBJState(Lists.newArrayList(OBJModel.Group.ALL), true, transformation);

        IAnvilMaterial material = ((TileEntityBlackSmithsAnvil) world.getTileEntity(pos)).getState().getMaterial();
        if (material == null)
            material = ModMaterials.Anvil.IRON;

        return ((IExtendedBlockState) state).withProperty(PROPERTY_ANVIL_MATERIAL, material.getRegistryName().toString()).withProperty(OBJModel.OBJProperty.INSTANCE, objState);
    }

    /**
     * Checks if an IBlockState represents a block that is opaque and a full cube.
     *
     * @param state
     */
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
                if (worldIn.getTileEntity(pos) instanceof TileEntityBlackSmithsAnvil) {
                    playerIn.openGui(Armory.instance, References.GuiIDs.ANVILID, worldIn, pos.getX(), pos.getY(), pos.getZ());
                }
            }
            return true;
        }
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(@Nonnull IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new ExtendedBlockState(this, new IProperty[]{FACING}, new IUnlistedProperty[]{OBJModel.OBJProperty.INSTANCE, PROPERTY_ANVIL_MATERIAL});
    }

    @Override
    public void handleDebugInformation(@Nonnull RenderGameOverlayEvent.Text event, @Nonnull World worldIn, @Nonnull BlockPos pos) {
        if (!SmithsCore.isInDevenvironment() && !Minecraft.getMinecraft().gameSettings.showDebugInfo)
            return;

        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (!(tileEntity instanceof TileEntityBlackSmithsAnvil))
            return;

        TileEntityBlackSmithsAnvil blackSmithsAnvil = (TileEntityBlackSmithsAnvil) tileEntity;
        if (blackSmithsAnvil.getState().getMaterial() == null) {
            event.getRight().add("AnvilMaterial: UNKNOWN");
        } else {
            event.getRight().add("AnvilMaterial: " + blackSmithsAnvil.getState().getMaterial().getRegistryName().toString());
        }
    }

    private ItemStack generateItemStackFromWorldPos(IBlockAccess world, BlockPos pos, IBlockState state) {
        state = getExtendedState(state, world, pos);

        ItemStack stack = new ItemStack(Item.getItemFromBlock(state.getBlock()));
        NBTTagCompound compound = new NBTTagCompound();

        compound.setString(References.NBTTagCompoundData.TE.Anvil.MATERIAL, ((IExtendedBlockState) state).getValue(PROPERTY_ANVIL_MATERIAL));
        stack.setTagCompound(compound);

        return stack;
    }
}
