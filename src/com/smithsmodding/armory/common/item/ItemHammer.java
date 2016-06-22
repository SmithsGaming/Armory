package com.smithsmodding.armory.common.item;

import com.smithsmodding.armory.api.References;
import com.smithsmodding.armory.common.registry.GeneralRegistry;
import com.smithsmodding.smithscore.util.CoreReferences;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Created by Orion
 * Created on 16.05.2015
 * 13:28
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ItemHammer extends Item {
    public ItemHammer() {
        setMaxStackSize(1);
        setMaxDamage(150);
        setCreativeTab(GeneralRegistry.CreativeTabs.generalTab);
        setUnlocalizedName(References.InternalNames.Items.ItemHammer);
        setRegistryName(References.InternalNames.Items.ItemHammer.toLowerCase());
        addPropertyOverride(CoreReferences.IItemProperties.MODELTYPE, new IItemPropertyGetter() {
            @Override
            public float apply(ItemStack stack, World worldIn, EntityLivingBase entityIn) {
                return stack.getTagCompound().getString(CoreReferences.NBT.IItemProperties.TARGET).equals(References.InternalNames.Items.ItemHammer) ? 1f : 0f;
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
    public void getSubItems(Item pHammer, CreativeTabs pCreativeTab, List pItemStacks) {
        ItemStack tHammerStack = new ItemStack(pHammer, 1, 150);
        pItemStacks.add(tHammerStack);
    }
}
