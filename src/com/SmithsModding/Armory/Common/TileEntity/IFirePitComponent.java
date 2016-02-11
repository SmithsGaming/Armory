/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.armory.common.tileentity;

import com.smithsmodding.smithscore.util.common.positioning.*;

public interface IFirePitComponent {

    float getPositiveInflunce ();

    float getNegativeInfluece ();

    int getMaxTempInfluence ();

    boolean canInfluenceTE (Coordinate3D tTECoordinate);
}
