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

public class ItemHeatableMetalChain extends ItemHeatableResource {

    public ItemHeatableMetalChain() {
        this.setMaxStackSize(16);
        this.setCreativeTab(ModCreativeTabs.componentsTab);
        this.setUnlocalizedName(References.InternalNames.Items.ItemMetalChain);
        this.setRegistryName(References.InternalNames.Items.ItemMetalChain);
    }

    @Override
    public IHeatableObjectType getHeatableObjectType() {
        return ModHeatedObjectTypes.CHAIN;
    }
}
