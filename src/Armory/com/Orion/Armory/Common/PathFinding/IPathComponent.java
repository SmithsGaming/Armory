package com.Orion.Armory.Common.PathFinding;

import com.Orion.Armory.API.Materials.IArmorMaterial;
import com.Orion.Armory.Util.Core.Coordinate;

import java.util.ArrayList;

/**
 * Created by Orion
 * Created on 03.07.2015
 * 14:34
 * <p/>
 * Copyrighted according to Project specific license
 */
public interface IPathComponent extends Comparable<IPathComponent>
{

    float getHeuristicDistance();

    void setHeuristicDistance(float pNewDistance);

    Coordinate getLocation();

    ArrayList<IPathComponent> getValidPathableNeighborComponents();
}
