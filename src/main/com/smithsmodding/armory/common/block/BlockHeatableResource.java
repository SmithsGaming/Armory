package com.smithsmodding.armory.common.block;

import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.common.heatable.IHeatableObjectWrapper;
import com.smithsmodding.armory.api.common.heatable.IHeatedObjectType;
import com.smithsmodding.armory.api.common.material.core.IMaterial;
import com.smithsmodding.armory.api.common.material.core.RegistryMaterialWrapper;
import com.smithsmodding.armory.api.util.references.*;
import com.smithsmodding.armory.common.block.properties.PropertyHeatableMaterial;
import com.smithsmodding.armory.common.tileentity.TileEntityHeatableBlock;
import com.smithsmodding.armory.api.util.common.CapabilityHelper;
import com.smithsmodding.smithscore.SmithsCore;
import com.smithsmodding.smithscore.client.block.ICustomDebugInformationBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by marcf on 1/31/2017.
 */
public class BlockHeatableResource extends BlockArmoryTileEntity implements ICustomDebugInformationBlock, IHeatableObjectWrapper {

    public static final PropertyHeatableMaterial PROPERTY_HEATABLE_MATERIAL = new PropertyHeatableMaterial("Material");

    @Nonnull
    private ExtendedBlockState state = new ExtendedBlockState(this, new IProperty[]{}, new IUnlistedProperty[]{PROPERTY_HEATABLE_MATERIAL});


    public BlockHeatableResource() {
        super(References.InternalNames.Blocks.Resource, Material.IRON);
        setCreativeTab(ModCreativeTabs.GENERAL);
        this.setDefaultState(this.blockState.getBaseState());
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     *
     * @param worldIn
     * @param meta
     */
    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityHeatableBlock();
    }

    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new ExtendedBlockState(this, new IProperty[]{}, new IUnlistedProperty[]{PROPERTY_HEATABLE_MATERIAL});
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

        IMaterial material = ((TileEntityHeatableBlock) world.getTileEntity(pos)).getMaterial();
        if (material == null)
            material = ModMaterials.Anvil.IRON;

        return ((IExtendedBlockState) state).withProperty(PROPERTY_HEATABLE_MATERIAL, material);
    }

    /**
     * This returns a complete list of items dropped from this block.
     *
     * @param world   The current world
     * @param pos     Block position in world
     * @param state   Current state
     * @param fortune Breakers fortune level
     * @return A ArrayList containing all items this block drops
     */
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        return Collections.singletonList(generateItemStackFromWorldPos(world, pos, state));
    }


    @Override
    public void handleDebugInformation(@Nonnull RenderGameOverlayEvent.Text event, @Nonnull World worldIn, @Nonnull BlockPos pos) {
        if (!SmithsCore.isInDevenvironment() && !Minecraft.getMinecraft().gameSettings.showDebugInfo)
            return;

        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (!(tileEntity instanceof TileEntityHeatableBlock))
            return;

        TileEntityHeatableBlock heatedBlock = (TileEntityHeatableBlock) tileEntity;
        if (heatedBlock.getMaterial() == null) {
            event.getRight().add("Material: UNKNOWN");
        } else {
            event.getRight().add("Material: " + heatedBlock.getMaterial().getRegistryName().toString());
        }
    }

    private ItemStack generateItemStackFromWorldPos(IBlockAccess world, BlockPos pos, IBlockState state) {
        TileEntity worldEntity = world.getTileEntity(pos);

        if(!(worldEntity instanceof TileEntityHeatableBlock))
            return ItemStack.EMPTY;

        TileEntityHeatableBlock heatableEntity = (TileEntityHeatableBlock) worldEntity;

        if (IArmoryAPI.Holder.getInstance().getHelpers().getHeatableOverrideManager().hasOverride(ModHeatableObjects.ITEMSTACK, ModHeatedObjectTypes.BLOCK, heatableEntity.getMaterial())) {
            return IArmoryAPI.Holder.getInstance().getHelpers().getHeatableOverrideManager().getHeatedOverride(ModHeatableObjects.ITEMSTACK, ModHeatedObjectTypes.BLOCK, heatableEntity.getMaterial());
        } else {
            return CapabilityHelper.forceGenerateMaterializedStack(this, heatableEntity.getMaterial(), 1, ModHeatedObjectTypes.BLOCK);
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (!stack.hasCapability(ModCapabilities.MOD_MATERIALIZEDSSTACK_CAPABIITY, null))
            throw new IllegalArgumentException("The given stack cannot set the Material! No Capability available!");

        ((TileEntityHeatableBlock) worldIn.getTileEntity(pos)).setMaterial(stack.getCapability(ModCapabilities.MOD_MATERIALIZEDSSTACK_CAPABIITY, null).getMaterial());

        worldIn.getTileEntity(pos).markDirty();
    }

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
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list) {
        HashMap<String, ItemStack> mappedOreDictionaryStacks = new HashMap<>();

        for(RegistryMaterialWrapper wrapper : IArmoryAPI.Holder.getInstance().getRegistryManager().getCombinedMaterialRegistry()) {
            if (mappedOreDictionaryStacks.containsKey(wrapper.getWrapped().getOreDictionaryIdentifier()))
                continue;

            ItemStack stack = CapabilityHelper.generateMaterializedStack(this, wrapper.getWrapped(), 1);

            mappedOreDictionaryStacks.put(wrapper.getWrapped().getOreDictionaryIdentifier(), stack);
        }

        list.addAll(mappedOreDictionaryStacks.values());
    }

    @Override
    public IHeatedObjectType getHeatableObjectType() {
        return ModHeatedObjectTypes.BLOCK;
    }
}
