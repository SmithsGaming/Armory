package com.smithsmodding.armory.common.tileentity.conduit;

import net.minecraft.util.EnumFacing;

/**
 * Created by marcf on 7/28/2016.
 */
public interface IConduitTankProvider {

    boolean canExtractFrom(EnumFacing direction);

    boolean canInsertFrom(EnumFacing direction);

    void onExtractionFrom(EnumFacing direction);

    void onInsertionFrom(EnumFacing direction);
}
