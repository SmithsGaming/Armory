/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.Armory.Common.TileEntity;

import com.smithsmodding.smithscore.util.common.positioning.*;

public interface IFirePitComponent {

    float getPositiveInflunce ();

    float getNegativeInfluece ();

    int getMaxTempInfluence ();

    boolean canInfluenceTE (Coordinate3D tTECoordinate);
}
