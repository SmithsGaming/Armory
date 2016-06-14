package com.smithsmodding.armory.common.item;

import com.smithsmodding.armory.common.registry.GeneralRegistry;
import com.smithsmodding.armory.util.References;
import net.minecraft.item.Item;

/**
 * @Author Marc (Created on: 11.06.2016)
 */
public class ItemSmithingsGuide extends Item {

    public ItemSmithingsGuide() {
        this.setMaxStackSize(1);
        this.setCreativeTab(GeneralRegistry.CreativeTabs.componentsTab);
        this.setUnlocalizedName(References.InternalNames.Items.ItemSmithingsGuide);
        this.setRegistryName(References.General.MOD_ID, References.InternalNames.Items.ItemSmithingsGuide);
    }
}
