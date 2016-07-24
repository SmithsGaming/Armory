package com.smithsmodding.armory.common.block;

import com.smithsmodding.armory.api.util.references.References;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * Author Orion (Created on: 24.07.2016)
 */
public class BlockArmory extends Block {

    public BlockArmory(String pBlockName, Material pBlockMaterial) {
        super(pBlockMaterial);
        setUnlocalizedName(pBlockName);
        setHardness(5F);
        setResistance(10F);
        setRegistryName(References.General.MOD_ID.toLowerCase(), pBlockName);
    }
}
