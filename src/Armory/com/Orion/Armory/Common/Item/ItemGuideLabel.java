/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Common.Item;

import com.Orion.Armory.Common.Registry.GeneralRegistry;
import com.Orion.Armory.Util.Client.Textures;
import com.Orion.Armory.Util.Client.TranslationKeys;
import com.Orion.Armory.Util.References;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ItemGuideLabel extends Item {
    public ItemGuideLabel() {
        setMaxStackSize(1);
        setCreativeTab(GeneralRegistry.iTabArmoryComponents);
        setUnlocalizedName(References.InternalNames.Items.ItemGuideLabel);
    }

    @Override
    public void registerIcons(IIconRegister pIconRegister) {
        Textures.Items.TabSmithingsGuide.addIcon(pIconRegister.registerIcon(Textures.Items.TabSmithingsGuide.getPrimaryLocation()));
    }

    //Function for getting the Icon from a render pass.
    @Override
    public IIcon getIcon(ItemStack pStack, int pRenderPass) {
        return Textures.Items.TabSmithingsGuide.getIcon();
    }

    @Override
    public IIcon getIconIndex(ItemStack pStack) {
        return getIcon(pStack, 0);
    }

    @Override
    public void addInformation(ItemStack pStack, EntityPlayer pPlayer, List pInformation, boolean pAdvancedInformation) {
        super.addInformation(pStack, pPlayer, pInformation, pAdvancedInformation);

        if (pStack.getTagCompound() != null) {
            if (pStack.getTagCompound().hasKey(References.NBTTagCompoundData.CustomName))
                pInformation.add(StatCollector.translateToLocal(TranslationKeys.Items.Label.LabelName) + " " + pStack.getTagCompound().getString(References.NBTTagCompoundData.CustomName));

            if (pStack.getTagCompound().hasKey(References.NBTTagCompoundData.Item.Labels.LOGOSTACK)) {
                ItemStack tGroupLogoStack = ItemStack.loadItemStackFromNBT(pStack.getTagCompound().getCompoundTag(References.NBTTagCompoundData.Item.Labels.LOGOSTACK));
                pInformation.add(StatCollector.translateToLocal(TranslationKeys.Items.Label.Logo) + " " + tGroupLogoStack.getDisplayName());
            }
        }

    }
}
