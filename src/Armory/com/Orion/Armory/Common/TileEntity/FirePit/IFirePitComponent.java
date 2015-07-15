/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Common.TileEntity.FirePit;

import com.Orion.Armory.Util.Core.Coordinate;

public interface IFirePitComponent
{

    float getPositiveInflunce();

    float getNegativeInfluece();

    int getMaxTempInfluence();

    boolean canInfluenceTE(Coordinate tTECoordinate);
}
