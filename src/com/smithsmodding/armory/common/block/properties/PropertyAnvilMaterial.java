package com.smithsmodding.armory.common.block.properties;

import net.minecraftforge.common.property.*;

/**
 * Created by Marc on 22.02.2016.
 */
public class PropertyAnvilMaterial implements IUnlistedProperty<String> {

    private String name;

    public PropertyAnvilMaterial (String name) {
        this.name = name;
    }

    @Override
    public String getName () {
        return name;
    }

    @Override
    public boolean isValid (String value) {
        return false;
    }

    @Override
    public Class<String> getType () {
        return String.class;
    }

    @Override
    public String valueToString (String value) {
        return value;
    }
}
