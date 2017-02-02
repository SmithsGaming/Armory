package com.smithsmodding.armory.common.item;
/*
 *   ItemHeatableMetalRing
 *   Created by: Orion
 *   Created on: 25-9-2014
 */

import com.smithsmodding.armory.api.common.heatable.IHeatedObjectType;
import com.smithsmodding.armory.api.util.references.ModCreativeTabs;
import com.smithsmodding.armory.api.util.references.ModHeatedObjectTypes;
import com.smithsmodding.armory.api.util.references.References;


public class ItemHeatableMetalRing extends ItemHeatableResource {

    public ItemHeatableMetalRing() {
        this.setMaxStackSize(64);
        this.setCreativeTab(ModCreativeTabs.COMPONENTS);
        this.setUnlocalizedName(References.InternalNames.Items.IN_METALRING);
        this.setRegistryName(References.InternalNames.Items.IN_METALRING);
    }

    @Override
    public IHeatedObjectType getHeatableObjectType() {
        return ModHeatedObjectTypes.RING;
    }
}
