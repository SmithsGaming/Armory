package com.smithsmodding.armory.common.block.properties;

import com.smithsmodding.armory.common.registry.AnvilMaterialRegistry;
import net.minecraftforge.common.property.IUnlistedProperty;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Marc on 22.02.2016.
 */
public class PropertyAnvilMaterial implements IUnlistedProperty<String> {

    private String name;

    public PropertyAnvilMaterial(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isValid(String value) {
        return AnvilMaterialRegistry.getInstance().getAnvilMaterial(value) != null;
    }

    @NotNull
    @Override
    public Class<String> getType() {
        return String.class;
    }

    @Override
    public String valueToString(String value) {
        return value;
    }
}
