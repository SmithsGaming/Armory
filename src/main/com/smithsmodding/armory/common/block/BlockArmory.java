package com.smithsmodding.armory.common.block;

import com.smithsmodding.armory.api.util.references.References;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

/**
 * Author Orion (Created on: 24.07.2016)
 */
public class BlockArmory extends Block {

    public BlockArmory(@Nonnull String blockName, @Nonnull Material blockMaterial) {
        super(blockMaterial);
        setUnlocalizedName(blockName);
        setHardness(5F);
        setResistance(10F);
        setRegistryName(References.General.MOD_ID.toLowerCase(), blockName);
    }
}
