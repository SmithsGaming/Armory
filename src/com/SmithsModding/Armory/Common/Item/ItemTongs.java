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
 * Created on 16.05.2015
 * 13:42
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ItemTongs extends Item {
    IIcon ITongsIcon;

    public ItemTongs() {
        setMaxStackSize(1);
        setMaxDamage(150);
        setCreativeTab(GeneralRegistry.iTabArmoryComponents);
        setUnlocalizedName(References.InternalNames.Items.ItemTongs);
    }


    @Override
    public void registerIcons(IIconRegister pIconRegister) {
        ITongsIcon = pIconRegister.registerIcon(TextureAddressHelper.getTextureAddress("16x Tongs"));
    }

    //Function for getting the Icon from a render pass.
    @Override
    public IIcon getIcon(ItemStack pStack, int pRenderPass) {
        return ITongsIcon;
    }

    @Override
    public IIcon getIconIndex(ItemStack pStack) {
        return getIcon(pStack, 0);
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
    public void getSubItems(Item pTongs, CreativeTabs pCreativeTab, List pItemStacks) {
        ItemStack tTongsStack = new ItemStack(pTongs, 1, 150);
        pItemStacks.add(tTongsStack);
    }
}
