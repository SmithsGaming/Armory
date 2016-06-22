package com.smithsmodding.armory.common.block;

import com.google.common.collect.Lists;
import com.smithsmodding.armory.Armory;
import com.smithsmodding.armory.api.References;
import com.smithsmodding.armory.api.materials.IAnvilMaterial;
import com.smithsmodding.armory.common.block.properties.PropertyAnvilMaterial;
import com.smithsmodding.armory.common.registry.AnvilMaterialRegistry;
import com.smithsmodding.armory.common.registry.GeneralRegistry;
import com.smithsmodding.armory.common.tileentity.TileEntityBlackSmithsAnvil;
import com.smithsmodding.armory.common.tileentity.state.BlackSmithsAnvilState;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
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
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

import java.util.List;

/**
 * Created by Marc on 14.02.2016.
 */
public class BlockBlackSmithsAnvil extends BlockArmoryInventory
{

    public static final PropertyAnvilMaterial PROPERTY_ANVIL_MATERIAL = new PropertyAnvilMaterial("Material");
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    private ExtendedBlockState state = new ExtendedBlockState(this, new IProperty[]{FACING}, new IUnlistedProperty[]{OBJModel.OBJProperty.INSTANCE, PROPERTY_ANVIL_MATERIAL});


    public BlockBlackSmithsAnvil () {
        super(References.InternalNames.Blocks.ArmorsAnvil, Material.ANVIL);
        setCreativeTab(GeneralRegistry.CreativeTabs.generalTab);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityBlackSmithsAnvil();
    }

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
    @Override
    public String getLocalizedName () {
        return super.getLocalizedName();
    }

    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
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
    public void onBlockPlacedBy (World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        String materialID = stack.getTagCompound().getString(References.NBTTagCompoundData.TE.Anvil.MATERIAL);

        ((BlackSmithsAnvilState) ((TileEntityBlackSmithsAnvil) worldIn.getTileEntity(pos)).getState()).setMaterial(AnvilMaterialRegistry.getInstance().getAnvilMaterial(materialID));

        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);

        worldIn.getTileEntity(pos).markDirty();
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        state = getExtendedState(state, world, pos);

        ItemStack stack = new ItemStack(Item.getItemFromBlock(state.getBlock()));
        NBTTagCompound compound = new NBTTagCompound();

        compound.setString(References.NBTTagCompoundData.TE.Anvil.MATERIAL, ((IExtendedBlockState) state).getValue(PROPERTY_ANVIL_MATERIAL));
        stack.setTagCompound(compound);

        return stack;
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     *
     * @param itemIn
     * @param tab
     * @param list
     */
    @Override
    public void getSubBlocks (Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        for(IAnvilMaterial material : AnvilMaterialRegistry.getInstance().getAllRegisteredAnvilMaterials().values())
        {
            ItemStack stack = new ItemStack(Item.getItemFromBlock(this));

            NBTTagCompound compound = new NBTTagCompound();
            compound.setString(References.NBTTagCompoundData.TE.Anvil.MATERIAL, material.getID());

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
    public IBlockState getExtendedState (IBlockState state, IBlockAccess world, BlockPos pos) {
        if (world.getTileEntity(pos) == null) return this.state.getBaseState();

        EnumFacing facing = state.getValue(FACING);
        TRSRTransformation transformation = new TRSRTransformation(facing);

        OBJModel.OBJState objState = new OBJModel.OBJState(Lists.newArrayList(OBJModel.Group.ALL), true, transformation);

        IAnvilMaterial material = ((BlackSmithsAnvilState) ((TileEntityBlackSmithsAnvil) world.getTileEntity(pos)).getState()).getMaterial();
        if (material == null)
            material = AnvilMaterialRegistry.getInstance().getAnvilMaterial(References.InternalNames.Materials.Anvil.IRON);

        return ((IExtendedBlockState) state).withProperty(PROPERTY_ANVIL_MATERIAL, material.getID()).withProperty(OBJModel.OBJProperty.INSTANCE, objState);
    }

    @Override
    public boolean isVisuallyOpaque () {
        return false;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
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
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getIndex();
    }

    protected BlockStateContainer createBlockState()
    {
        return new ExtendedBlockState(this, new IProperty[]{FACING}, new IUnlistedProperty[]{OBJModel.OBJProperty.INSTANCE, PROPERTY_ANVIL_MATERIAL});
    }
}
