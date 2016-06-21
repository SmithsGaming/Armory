package com.smithsmodding.armory.common.creativetabs;

import com.smithsmodding.armory.common.factory.HeatedItemFactory;
import com.smithsmodding.armory.common.material.MaterialRegistry;
import com.smithsmodding.armory.common.registry.GeneralRegistry;
import com.smithsmodding.armory.common.registry.HeatableItemRegistry;
import com.smithsmodding.armory.util.References;
import com.smithsmodding.armory.util.client.TranslationKeys;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * @Author Marc (Created on: 14.06.2016)
 */
public class HeatedItemTab extends CreativeTabs {

    public HeatedItemTab() {
        super(I18n.format(TranslationKeys.CreativeTabs.HeatedItems));
    }

    @Override
    public String getTranslatedTabLabel() {
        return getTabLabel();
    }

    @Override
    public ItemStack getIconItemStack() {
        ItemStack cooledStack = HeatableItemRegistry.getInstance().getBaseStack(MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.OBSIDIAN), References.InternalNames.HeatedItemTypes.INGOT);
        ItemStack heatedStack = HeatedItemFactory.getInstance().convertToHeatedIngot(cooledStack);

        return heatedStack;
    }

    @Override
    public Item getTabIconItem() {
        return GeneralRegistry.Items.heatedItem;
    }
}
