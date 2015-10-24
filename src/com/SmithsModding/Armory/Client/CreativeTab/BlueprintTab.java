/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.Armory.Client.CreativeTab;

import com.SmithsModding.Armory.Common.Registry.GeneralRegistry;
import com.SmithsModding.Armory.Util.Client.TranslationKeys;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class BlueprintTab extends CreativeTabs {
    private ItemStack iItemStack;

    public BlueprintTab() {
        super(CreativeTabs.getNextID(), "Armory - Blueprints");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getIconItemStack() {
        if (iItemStack == null) {
            iItemStack = new ItemStack(GeneralRegistry.Items.iBlueprints, 1);
        }

        return iItemStack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getTranslatedTabLabel() {
        return StatCollector.translateToLocal(TranslationKeys.CreativeTabs.Blueprint);
    }

    @Override
    public Item getTabIconItem() {
        return null;
    }
}
