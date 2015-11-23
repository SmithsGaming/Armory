package com.SmithsModding.Armory.Common.Item;

import com.SmithsModding.Armory.Common.Registry.GeneralRegistry;
import com.SmithsModding.Armory.Util.Client.TextureAddressHelper;
import com.SmithsModding.Armory.Util.References;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

/**
 * Created by Orion
 * Created on 22.04.2015
 * 22:18
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ItemFan extends Item {
    IIcon iFanIcon;

    public ItemFan() {
        setMaxStackSize(1);
        setMaxDamage(Short.MAX_VALUE);
        setCreativeTab(GeneralRegistry.iTabArmoryComponents);
        setUnlocalizedName(References.InternalNames.Items.ItemFan);
    }


    @Override
    public void registerIcons(IIconRegister pIconRegister) {
        iFanIcon = pIconRegister.registerIcon(TextureAddressHelper.getTextureAddress("16x Fan"));
    }

    //Function for getting the Icon from a render pass.
    @Override
    public IIcon getIcon(ItemStack pStack, int pRenderPass) {
        return iFanIcon;
    }

    @Override
    public IIcon getIconIndex(ItemStack pStack) {
        return getIcon(pStack, 0);
    }


    @Override
    public double getDurabilityForDisplay(ItemStack pStack) {
        return 1 - (((float) pStack.getItemDamage()) / Short.MAX_VALUE);
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
    public void getSubItems(Item pFan, CreativeTabs pCreativeTab, List pItemStacks) {
        ItemStack tFanStack = new ItemStack(pFan, 1, Short.MAX_VALUE);
        pItemStacks.add(tFanStack);
    }
}
