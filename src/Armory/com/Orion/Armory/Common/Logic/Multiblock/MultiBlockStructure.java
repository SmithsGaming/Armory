package com.Orion.Armory.Common.Logic.Multiblock;
/*
/  MultiBlockStructure
/  Created by : Orion
/  Created on : 02/10/2014
*/

import net.minecraft.world.World;

import java.util.HashMap;

public class MultiBlockStructure
{
    protected String iInternalName;
    private HashMap<Integer[], IMultiBlockPart> iParts = new HashMap<Integer[], IMultiBlockPart>();

    public MultiBlockStructure(String pInternalName)
    {
        this.iInternalName = pInternalName;
    }

    public String getInternalName()
    {
        return this.iInternalName;
    }

    public void registerPart(IMultiBlockPart pPart, Integer pRelativeX, Integer pRelativeY, Integer pRelativeZ) {
        Integer[] tRelativeArray = new Integer[3];
        tRelativeArray[0] = pRelativeX;
        tRelativeArray[1] = pRelativeY;
        tRelativeArray[2] = pRelativeZ;

        this.iParts.put(tRelativeArray, pPart);
    }

    public IMultiBlockPart getPartOnLocation(Integer pRelativeX, Integer pRelativeY, Integer pRelativeZ)
    {
        Integer[] tRelativeArray = new Integer[3];
        tRelativeArray[0] = pRelativeX;
        tRelativeArray[1] = pRelativeY;
        tRelativeArray[2] = pRelativeZ;

        return this.iParts.get(tRelativeArray);
    }

    public boolean validateStructure(Integer pXCoord, Integer pYCoord, Integer pZCoord, World pWorld)
    {
        for (Integer[] tRelateArray : iParts.keySet())
        {
            IMultiBlockPart tPart = iParts.get(tRelateArray);
            if (!tPart.validatePart((pXCoord + tRelateArray[0]), (pYCoord + tRelateArray[1]), (pZCoord + tRelateArray[2]), pWorld))
            {
                return false;
            }
        }

        return true;
    }
}
