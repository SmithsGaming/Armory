package com.Orion.Armory.Common.Registry;
/*
/  StructureRegistry
/  Created by : Orion
/  Created on : 02/10/2014
*/

import com.Orion.Armory.Common.Logic.Multiblock.MultiBlockStructure;

import java.util.HashMap;

public class StructureRegistry {
    protected static StructureRegistry iInstance;
    protected HashMap<String, MultiBlockStructure> iMultiBlocks;


    public static StructureRegistry getInstance()
    {
        if (iInstance == null)
        {
            iInstance = new StructureRegistry();
        }

        return iInstance;
    }

    public HashMap<String, MultiBlockStructure> getMultiBlocks()
    {
        return this.iMultiBlocks;
    }

    public MultiBlockStructure getStructure(String pInternalName)
    {
        return this.iMultiBlocks.get(pInternalName);
    }

    public String registerMultiBlock(MultiBlockStructure pStructure)
    {
        this.iMultiBlocks.put(pStructure.getInternalName(), pStructure);
        return pStructure.getInternalName();
    }
}
