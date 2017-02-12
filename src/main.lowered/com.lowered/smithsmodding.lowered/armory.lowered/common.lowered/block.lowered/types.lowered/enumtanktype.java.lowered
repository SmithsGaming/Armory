package com.smithsmodding.armory.common.block.types;

import com.smithsmodding.armory.api.util.references.References;
import net.minecraft.util.IStringSerializable;

/**
 * Author Orion (Created on: 26.07.2016)
 */
public enum EnumTankType implements IStringSerializable {
    LIGHT(References.General.FLUID_INGOT * 15, 0, "light"),
    NORMAL(References.General.FLUID_INGOT * 45, 1, "normal");

    private static final EnumTankType[] META_LOOKUP = new EnumTankType[values().length];

    static {
        for (EnumTankType type : values()) {
            META_LOOKUP[type.getMetadata()] = type;
        }
    }

    private final int tankContents;
    private final int meta;
    private final String name;

    EnumTankType(int tankContents, int meta, String name) {
        this.tankContents = tankContents;
        this.meta = meta;
        this.name = name;
    }

    public static EnumTankType byMetadata(int meta) {
        if (meta < 0 || meta >= META_LOOKUP.length) {
            meta = 0;
        }

        return META_LOOKUP[meta];
    }

    public int getMetadata() {
        return this.meta;
    }

    public String toString() {
        return this.name;
    }

    public int getTankContents() {
        return tankContents;
    }

    public String getName() {
        return this.name;
    }
}