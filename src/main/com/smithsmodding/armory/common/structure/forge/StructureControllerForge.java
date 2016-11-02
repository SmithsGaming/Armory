package com.smithsmodding.armory.common.structure.forge;

import com.smithsmodding.armory.common.tileentity.TileEntityForge;
import com.smithsmodding.smithscore.common.structures.IStructureController;
import net.minecraft.util.EnumFacing;
import org.jetbrains.annotations.NotNull;

/**
 * Author Orion (Created on: 25.06.2016)
 */
public class StructureControllerForge implements IStructureController<StructureForge, TileEntityForge> {
    @NotNull
    @Override
    public EnumFacing[] getPossibleConnectionSides() {
        return new EnumFacing[]{EnumFacing.EAST, EnumFacing.WEST, EnumFacing.NORTH, EnumFacing.SOUTH};
    }
}
