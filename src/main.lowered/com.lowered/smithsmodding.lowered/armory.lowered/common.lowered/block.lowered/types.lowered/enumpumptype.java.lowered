package com.smithsmodding.armory.common.block.types;

import net.minecraft.util.IStringSerializable;

/**
 * Author Orion (Created on: 11.10.2016)
 */
public enum EnumPumpType implements IStringSerializable {
    HORIZONTAL(0, "horizontal"),
    VERTICAL(1, "vertical");

    private static final EnumPumpType[] META_LOOKUP = new EnumPumpType[values().length];

    static {
        for (EnumPumpType type : values()) {
            META_LOOKUP[type.getMetadata()] = type;
        }
    }

    private final int meta;
    private final String name;

    EnumPumpType(int meta, String name) {
        this.meta = meta;
        this.name = name;
    }

    public static EnumPumpType byMetadata(int meta) {
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

    public String getName() {
        return this.name;
    }
}
