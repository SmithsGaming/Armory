package com.smithsmodding.armory.common.creativetabs;

import com.smithsmodding.armory.api.util.client.TranslationKeys;
import com.smithsmodding.armory.api.util.references.*;
import com.smithsmodding.armory.common.factories.HeatedItemFactory;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Author Marc (Created on: 14.06.2016)
 */
public class HeatedItemTab extends CreativeTabs {

    public HeatedItemTab() {
        super(TranslationKeys.CreativeTabs.HeatedItems);
    }

    @Nonnull
    @Override
    public String getTranslatedTabLabel() {
        return getTabLabel();
    }

    @Override
    public ItemStack getTabIconItem() {
        return HeatedItemFactory.getInstance().generateHeatedItemFromMaterial(ModMaterials.Armor.Core.IRON, ModHeatableObjects.ITEMSTACK, ModHeatedObjectTypes.INGOT, 350f);
    }
}
