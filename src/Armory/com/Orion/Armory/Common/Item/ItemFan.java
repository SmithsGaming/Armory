package com.Orion.Armory.Common.Item;

import com.Orion.Armory.Common.Registry.GeneralRegistry;
import com.Orion.Armory.Util.Client.TextureAddressHelper;
import com.Orion.Armory.Util.References;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

/**
 * Created by Orion
 * Created on 22.04.2015
 * 22:18
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ItemFan extends Item
{
    IIcon iFanIcon;

    public ItemFan()
    {
        setMaxStackSize(1);
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
    public IIcon getIconIndex(ItemStack pStack)
    {
        return getIcon(pStack, 0);
    }


    @Override
    public double getDurabilityForDisplay(ItemStack pStack)
    {
        return  1 - ((pStack.getItemDamage()) / Short.MAX_VALUE);
    }
}
