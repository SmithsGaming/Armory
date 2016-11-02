package com.smithsmodding.armory.common.creativetabs;

import com.smithsmodding.armory.api.util.client.TranslationKeys;
import com.smithsmodding.armory.api.util.references.ModItems;
import com.smithsmodding.armory.api.util.references.References;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.jetbrains.annotations.NotNull;

/**
 * Author Marc (Created on: 14.06.2016)
 */
public class ComponentsTab extends CreativeTabs {


    public ComponentsTab() {
        super(TranslationKeys.CreativeTabs.Components);
    }

    @NotNull
    @Override
    public String getTranslatedTabLabel() {
        return getTabLabel();
    }

    @NotNull
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
        return ModItems.armorComponent;
    }
}
