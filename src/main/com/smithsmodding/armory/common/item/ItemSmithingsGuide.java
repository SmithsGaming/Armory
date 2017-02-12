package com.smithsmodding.armory.common.item;

import com.smithsmodding.armory.api.util.references.ModCreativeTabs;
import com.smithsmodding.armory.api.util.references.References;
import net.minecraft.item.Item;

/**
 * Author Marc (Created on: 11.06.2016)
 */
public class ItemSmithingsGuide extends Item {

    public ItemSmithingsGuide() {
        this.setMaxStackSize(1);
        this.setCreativeTab(ModCreativeTabs.componentsTab);
        this.setUnlocalizedName(References.InternalNames.Items.ItemSmithingsGuide);
        this.setRegistryName(References.General.MOD_ID.toLowerCase(), References.InternalNames.Items.ItemSmithingsGuide);
    }
}
