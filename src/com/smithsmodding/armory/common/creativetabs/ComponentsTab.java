package com.smithsmodding.armory.common.creativetabs;

import com.smithsmodding.armory.common.registry.GeneralRegistry;
import com.smithsmodding.armory.util.References;
import com.smithsmodding.armory.util.client.TranslationKeys;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @Author Marc (Created on: 14.06.2016)
 */
public class ComponentsTab extends CreativeTabs {


    public ComponentsTab() {
        super(I18n.format(TranslationKeys.CreativeTabs.Components));
    }

    @Override
    public String getTranslatedTabLabel() {
        return getTabLabel();
    }

    @Override
    public ItemStack getIconItemStack() {
        ItemStack stack = new ItemStack(getTabIconItem());
        NBTTagCompound data = new NBTTagCompound();

        data.setString(References.NBTTagCompoundData.Item.ItemComponent.TYPE, References.InternalNames.Upgrades.Helmet.TOP);
        data.setString(References.NBTTagCompoundData.Item.ItemComponent.MATERIAL, References.InternalNames.Materials.Vanilla.OBSIDIAN);

        stack.setTagCompound(data);
        return stack;
    }

    @Override
    public Item getTabIconItem() {
        return GeneralRegistry.Items.armorComponent;
    }
}
