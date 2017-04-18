package com.smithsmodding.armory.common.creativetabs;

import com.smithsmodding.armory.api.util.client.TranslationKeys;
import com.smithsmodding.armory.api.util.references.ModBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

/**
 * Author Marc (Created on: 14.06.2016)
 */
public class GeneralTabs extends CreativeTabs {
    public GeneralTabs() {
        super(TranslationKeys.CreativeTabs.TK_TAB_GENERAL);
    }

    @Nonnull
    @Override
    public String getTranslatedTabLabel() {
        return getTabLabel();
    }

    @Nullable
    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ModBlocks.BL_FORGE);
    }
}
