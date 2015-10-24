/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.Armory.Common.TileEntity.FirePit;

import com.SmithsModding.Armory.Util.Core.Coordinate;

public interface IFirePitComponent {

    float getPositiveInflunce();

    float getNegativeInfluece();

    int getMaxTempInfluence();

    boolean canInfluenceTE(Coordinate tTECoordinate);
}
