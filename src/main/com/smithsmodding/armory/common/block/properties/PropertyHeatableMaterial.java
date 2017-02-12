package com.smithsmodding.armory.common.block.properties;

import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.common.material.core.IMaterial;
import net.minecraftforge.common.property.IUnlistedProperty;

/**
 * Created by marcf on 1/31/2017.
 */
public class PropertyHeatableMaterial implements IUnlistedProperty<IMaterial>{

    private final String name;

    public PropertyHeatableMaterial(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isValid(IMaterial value) {
        return IArmoryAPI.Holder.getInstance().getRegistryManager().getCombinedMaterialRegistry().containsKey(value.getRegistryName());
    }

    @Override
    public Class<IMaterial> getType() {
        return IMaterial.class;
    }

    @Override
    public String valueToString(IMaterial value) {
        return value.getRegistryName().toString();
    }
}
