package com.smithsmodding.armory.common.creativetabs;

import com.smithsmodding.armory.api.util.client.TranslationKeys;
import com.smithsmodding.armory.api.util.references.ModBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Author Marc (Created on: 14.06.2016)
 */
public class GeneralTabs extends CreativeTabs {
    public GeneralTabs() {
        super(TranslationKeys.CreativeTabs.General);
    }

    @NotNull
    @Override
    public String getTranslatedTabLabel() {
        return getTabLabel();
    }

    @Nullable
    @Override
    public Item getTabIconItem() {
        return Item.getItemFromBlock(ModBlocks.blockForge);
    }
}
