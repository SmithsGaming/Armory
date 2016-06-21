package com.smithsmodding.armory.common.creativetabs;

import com.smithsmodding.armory.common.registry.GeneralRegistry;
import com.smithsmodding.armory.util.client.TranslationKeys;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * @Author Marc (Created on: 14.06.2016)
 */
public class GeneralTabs extends CreativeTabs {
    public GeneralTabs() {
        super(I18n.format(TranslationKeys.CreativeTabs.General));
    }

    @Override
    public String getTranslatedTabLabel() {
        return getTabLabel();
    }

    @Override
    public Item getTabIconItem() {
        return Item.getItemFromBlock(GeneralRegistry.Blocks.blockFirePit);
    }
}
