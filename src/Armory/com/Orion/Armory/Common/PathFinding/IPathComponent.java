/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Common.PathFinding;

import com.Orion.Armory.Util.Core.Coordinate;

import java.util.ArrayList;

public interface IPathComponent
{
    Coordinate getLocation();

    ArrayList<IPathComponent> getValidPathableNeighborComponents();
}
