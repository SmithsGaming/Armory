package com.smithsmodding.armory.common.item;
/*
 *   ItemHeatableMetalRing
 *   Created by: Orion
 *   Created on: 25-9-2014
 */

import com.smithsmodding.armory.api.heatable.IHeatableObjectType;
import com.smithsmodding.armory.api.util.references.ModCreativeTabs;
import com.smithsmodding.armory.api.util.references.ModHeatedObjectTypes;
import com.smithsmodding.armory.api.util.references.References;


public class ItemHeatableMetalRing extends ItemHeatableResource {

    public ItemHeatableMetalRing() {
        this.setMaxStackSize(64);
        this.setCreativeTab(ModCreativeTabs.componentsTab);
        this.setUnlocalizedName(References.InternalNames.Items.ItemMetalRing);
        this.setRegistryName(References.InternalNames.Items.ItemMetalRing);
    }

    @Override
    public IHeatableObjectType getHeatableObjectType() {
        return ModHeatedObjectTypes.RING;
    }
}
