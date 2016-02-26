package com.smithsmodding.armory.common.block;

import com.smithsmodding.armory.*;
import com.smithsmodding.armory.api.materials.*;
import com.smithsmodding.armory.common.block.properties.*;
import com.smithsmodding.armory.common.registry.*;
import com.smithsmodding.armory.common.tileentity.*;
import com.smithsmodding.armory.common.tileentity.state.*;
import com.smithsmodding.armory.util.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.client.model.*;
import net.minecraftforge.client.model.obj.*;
import net.minecraftforge.common.property.*;

import java.util.*;

/**
 * Created by Marc on 14.02.2016.
 */
public class BlockBlackSmithsAnvil extends BlockArmoryInventory
{

    public static final PropertyAnvilMaterial PROPERTY_ANVIL_MATERIAL = new PropertyAnvilMaterial("Material");
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    private ExtendedBlockState state = new ExtendedBlockState(this, new IProperty[]{FACING}, new IUnlistedProperty[]{OBJModel.OBJProperty.instance, PROPERTY_ANVIL_MATERIAL});


    public BlockBlackSmithsAnvil () {
        super(References.InternalNames.Blocks.ArmorsAnvil, Material.anvil);
        setCreativeTab(CreativeTabs.tabCombat);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityBlackSmithsAnvil();
    }

    @Override
    public int getRenderType() {
        return 3;
    }

    @Override
    public boolean canRenderInLayer (EnumWorldBlockLayer layer) {
        return layer == EnumWorldBlockLayer.CUTOUT;
    }

    @Override
    public boolean isTranslucent () {
        return true;
    }

    @Override
    public boolean isOpaqueCube () {
        return false;
    }

    @Override
    public boolean isFullCube () {
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

        OBJModel.OBJState objState = ( (IExtendedBlockState) state ).getValue(OBJModel.OBJProperty.instance);

        if (objState == null) {
            ArrayList<String> parts = new ArrayList<>();
            parts.add(OBJModel.Group.ALL);

            objState = new OBJModel.OBJState(parts, true);
        }

        objState.parent = new TRSRTransformation(state.getValue(FACING));

        return ( (IExtendedBlockState) state ).withProperty(PROPERTY_ANVIL_MATERIAL, ( (BlackSmithsAnvilState) ( (TileEntityBlackSmithsAnvil) world.getTileEntity(pos) ).getState() ).getMaterial().getID()).withProperty(OBJModel.OBJProperty.instance, objState);
    }

    @Override
    public boolean isVisuallyOpaque () {
        return false;
    }

    @Override
    public boolean onBlockActivated (World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
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

    protected BlockState createBlockState()
    {
        return new ExtendedBlockState(this, new IProperty[] {FACING}, new IUnlistedProperty[] {OBJModel.OBJProperty.instance, PROPERTY_ANVIL_MATERIAL});
    }
}
