package com.smithsmodding.armory.common.block.types;

import net.minecraft.util.IStringSerializable;

/**
 * Author Orion (Created on: 24.07.2016)
 */
public enum EnumConduitType implements IStringSerializable {
    LIGHT(0, "light"),
    NORMAL(1, "normal"),
    VERTICAL(2, "vertical");

    private static final EnumConduitType[] META_LOOKUP = new EnumConduitType[values().length];

    static {
        for (EnumConduitType type : values()) {
            META_LOOKUP[type.getMetadata()] = type;
        }
    }

    private final int meta;
    private final String name;

    EnumConduitType(int meta, String name) {
        this.meta = meta;
        this.name = name;
    }

    public static EnumConduitType byMetadata(int meta) {
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
