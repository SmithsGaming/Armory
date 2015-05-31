package com.Orion.Armory.Common.Item;

import com.Orion.Armory.Common.Registry.GeneralRegistry;
import com.Orion.Armory.Util.Client.TextureAddressHelper;
import com.Orion.Armory.Util.Core.CircleHelper;
import com.Orion.Armory.Util.References;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

/**
 * Created by Orion
 * Created on 16.05.2015
 * 13:28
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ItemHammer extends Item {
    IIcon iHammerIcon;

    public ItemHammer() {
        setMaxStackSize(1);
        setMaxDamage(150);
        setCreativeTab(GeneralRegistry.iTabArmoryComponents);
        setUnlocalizedName(References.InternalNames.Items.ItemHammer);
    }


    @Override
    public void registerIcons(IIconRegister pIconRegister) {
        iHammerIcon = pIconRegister.registerIcon(TextureAddressHelper.getTextureAddress("16x Work Hammer"));
    }

    //Function for getting the Icon from a render pass.
    @Override
    public IIcon getIcon(ItemStack pStack, int pRenderPass) {
        return iHammerIcon;
    }

    @Override
    public IIcon getIconIndex(ItemStack pStack) {
        return getIcon(pStack, 0);
    }

    @Override
    public double getDurabilityForDisplay(ItemStack pStack) {
        return 1 - ((pStack.getItemDamage()) / (float) 150);
    }
}
