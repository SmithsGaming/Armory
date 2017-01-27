package com.smithsmodding.armory.common.item;

import com.smithsmodding.armory.api.util.references.ModBlocks;
import com.smithsmodding.armory.api.util.references.ModCreativeTabs;
import com.smithsmodding.armory.api.util.references.ModMaterials;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.tileentity.TileEntityBlackSmithsAnvil;
import com.smithsmodding.smithscore.util.CoreReferences;
import com.smithsmodding.smithscore.util.common.WorldUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Orion
 * Created on 16.05.2015
 * 13:28
 * <p>
 * Copyrighted according to Project specific license
 */
public class ItemHammer extends Item {
    public ItemHammer() {
        setMaxStackSize(1);
        setMaxDamage(150);
        setCreativeTab(ModCreativeTabs.generalTab);
        setUnlocalizedName(References.InternalNames.Items.IN_HAMMER);
        setRegistryName(References.InternalNames.Items.IN_HAMMER.toLowerCase());
        addPropertyOverride(CoreReferences.IItemProperties.MODELTYPE, new IItemPropertyGetter() {
            @Override
            public float apply(ItemStack stack, World worldIn, EntityLivingBase entityIn) {
                return stack.getTagCompound().getString(CoreReferences.NBT.IItemProperties.TARGET).equals(References.InternalNames.Items.IN_HAMMER) ? 1f : 0f;
            }
        });
    }

    @Override
    public double getDurabilityForDisplay(ItemStack pStack) {
        return 1 - ((pStack.getItemDamage()) / (float) 150);
    }

    @Override
    public boolean getHasSubtypes() {
        return true;
    }

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        ItemStack hammerStack = new ItemStack(itemIn, 1, 150);
        subItems.add(hammerStack);
    }

    /**
     * This is called when the item is used, before the block is activated.
     *
     * @param player The Player that used the item
     * @param world  The Current World
     * @param pos    Target position
     * @param side   The side of the target hit
     * @param hitX
     * @param hitY
     * @param hitZ
     * @param hand   Which hand the item is being held in.  @return Return PASS to allow vanilla handling, any other to skip normal code.
     */
    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        if (!world.isRemote) {
            IBlockState blockState = world.getBlockState(pos);

            if (blockState == null)
                return EnumActionResult.PASS;

            if (blockState.getBlock() != Blocks.COBBLESTONE)
                return EnumActionResult.PASS;

            if (side == EnumFacing.UP || side == EnumFacing.DOWN)
                return EnumActionResult.PASS;

            BlockPos centerPos = pos;
            for (int rx = 0; rx < 3; rx++) {
                IBlockState centerState = world.getBlockState(centerPos);
                IBlockState leftState = WorldUtil.getBlockStateForSideAndFacing(world, centerPos, side, EnumFacing.EAST);
                IBlockState rightState = WorldUtil.getBlockStateForSideAndFacing(world, centerPos, side, EnumFacing.WEST);
                IBlockState topState = WorldUtil.getBlockStateForSideAndFacing(world, centerPos, side, EnumFacing.UP);
                IBlockState bottomState = WorldUtil.getBlockStateForSideAndFacing(world, centerPos, side, EnumFacing.DOWN);
                IBlockState topLeftState = WorldUtil.getBlockStateForSideAndFacing(world, WorldUtil.getBlockPosForPerspective(centerPos, side, EnumFacing.UP), side, EnumFacing.EAST);
                IBlockState topRightState = WorldUtil.getBlockStateForSideAndFacing(world, WorldUtil.getBlockPosForPerspective(centerPos, side, EnumFacing.UP), side, EnumFacing.WEST);
                IBlockState bottomLeftState = WorldUtil.getBlockStateForSideAndFacing(world, WorldUtil.getBlockPosForPerspective(centerPos, side, EnumFacing.DOWN), side, EnumFacing.EAST);
                IBlockState bottomRightState = WorldUtil.getBlockStateForSideAndFacing(world, WorldUtil.getBlockPosForPerspective(centerPos, side, EnumFacing.DOWN), side, EnumFacing.WEST);

                if (centerState.getBlock() != Blocks.COBBLESTONE || leftState.getBlock() != Blocks.COBBLESTONE || leftState.getBlock() != Blocks.COBBLESTONE || rightState.getBlock() != Blocks.COBBLESTONE || topState.getBlock() != Blocks.COBBLESTONE || bottomState.getBlock() != Blocks.COBBLESTONE || topLeftState.getBlock() != Blocks.COBBLESTONE || topRightState.getBlock() != Blocks.COBBLESTONE || bottomLeftState.getBlock() != Blocks.COBBLESTONE || bottomRightState.getBlock() != Blocks.COBBLESTONE)
                    return EnumActionResult.PASS;

                centerPos = WorldUtil.getBlockPosForPerspective(centerPos, side, EnumFacing.NORTH);
            }

            BlockPos anvilPos = WorldUtil.getBlockPosForPerspective(pos, side, EnumFacing.NORTH);
            anvilPos = WorldUtil.getBlockPosForPerspective(anvilPos, side, EnumFacing.DOWN);

            centerPos = pos;
            for (int rx = 0; rx < 3; rx++) {
                BlockPos leftPos = WorldUtil.getBlockPosForPerspective(centerPos, side, EnumFacing.EAST);
                BlockPos rightPos = WorldUtil.getBlockPosForPerspective(centerPos, side, EnumFacing.WEST);
                BlockPos topPos = WorldUtil.getBlockPosForPerspective(centerPos, side, EnumFacing.UP);
                BlockPos bottomPos = WorldUtil.getBlockPosForPerspective(centerPos, side, EnumFacing.DOWN);
                BlockPos topLeftPos = WorldUtil.getBlockPosForPerspective(WorldUtil.getBlockPosForPerspective(centerPos, side, EnumFacing.UP), side, EnumFacing.EAST);
                BlockPos topRightPos = WorldUtil.getBlockPosForPerspective(WorldUtil.getBlockPosForPerspective(centerPos, side, EnumFacing.UP), side, EnumFacing.WEST);
                BlockPos bottomLeftPos = WorldUtil.getBlockPosForPerspective(WorldUtil.getBlockPosForPerspective(centerPos, side, EnumFacing.DOWN), side, EnumFacing.EAST);
                BlockPos bottomRightPos = WorldUtil.getBlockPosForPerspective(WorldUtil.getBlockPosForPerspective(centerPos, side, EnumFacing.DOWN), side, EnumFacing.WEST);

                world.setBlockToAir(centerPos);
                world.setBlockToAir(leftPos);
                world.setBlockToAir(rightPos);
                world.setBlockToAir(topPos);
                world.setBlockToAir(bottomPos);
                world.setBlockToAir(topLeftPos);
                world.setBlockToAir(topRightPos);
                world.setBlockToAir(bottomLeftPos);
                world.setBlockToAir(bottomRightPos);

                centerPos = WorldUtil.getBlockPosForPerspective(centerPos, side, EnumFacing.NORTH);
            }

            world.setBlockState(anvilPos, ModBlocks.blockBlackSmithsAnvil.getStateForPlacement(world, anvilPos, EnumFacing.UP, hitX, hitY, hitZ, 0, player));

            TileEntityBlackSmithsAnvil tileEntityBlackSmithsAnvil = (TileEntityBlackSmithsAnvil) world.getTileEntity(anvilPos);
            tileEntityBlackSmithsAnvil.getState().setMaterial(ModMaterials.Anvil.STONE);

            tileEntityBlackSmithsAnvil.markDirty();

            return EnumActionResult.SUCCESS;
        }

        return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
    }
}
