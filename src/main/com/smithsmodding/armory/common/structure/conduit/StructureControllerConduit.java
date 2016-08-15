package com.smithsmodding.armory.common.structure.conduit;

import com.smithsmodding.armory.common.tileentity.TileEntityConduit;
import com.smithsmodding.smithscore.common.structures.IStructureController;
import net.minecraft.util.EnumFacing;

/**
 * Author Orion (Created on: 07.08.2016)
 */
public class StructureControllerConduit implements IStructureController<StructureConduit, TileEntityConduit> {

    @Override
    public EnumFacing[] getPossibleConnectionSides() {
        return EnumFacing.HORIZONTALS;
    }
}
