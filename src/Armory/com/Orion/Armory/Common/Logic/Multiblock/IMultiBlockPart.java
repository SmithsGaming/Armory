package com.Orion.Armory.Common.Logic.Multiblock;
/*
/  IMultiBlockPart
/  Created by : Orion
/  Created on : 02/10/2014
*/

import net.minecraft.world.World;

public interface IMultiBlockPart
{
    public abstract String getInternalName();

    public abstract boolean validatePart(Integer pXCoord, Integer pYCoord, Integer pZCoord, World pWorld);

}
