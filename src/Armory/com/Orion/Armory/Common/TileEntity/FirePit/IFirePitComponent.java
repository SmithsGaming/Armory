package com.Orion.Armory.Common.TileEntity.FirePit;

import com.Orion.Armory.API.Structures.IStructureComponent;
import com.Orion.Armory.Util.Core.Coordinate;

/**
 * Created by Orion
 * Created on 07.07.2015
 * 11:55
 * <p/>
 * Copyrighted according to Project specific license
 */
public interface IFirePitComponent extends IStructureComponent
{

    float getPositiveInflunce();

    float getNegativeInfluece();

    int getMaxTempInfluence();

    boolean canInfluenceTE(Coordinate tTECoordinate);
}
