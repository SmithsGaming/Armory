/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.Armory.Common.TileEntity;

import com.SmithsModding.SmithsCore.Util.Common.Postioning.*;

public interface IFirePitComponent {

    float getPositiveInflunce ();

    float getNegativeInfluece ();

    int getMaxTempInfluence ();

    boolean canInfluenceTE (Coordinate3D tTECoordinate);
}
