package com.Orion.Armory.Common.Registry;
/*
/  IngotRegistry
/  Created by : Orion
/  Created on : 03/10/2014
*/

import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class IngotRegistry
{
    protected static IngotRegistry iInstance = new IngotRegistry();
    protected HashMap<String, Object[]> iRegisteredIngots  = new HashMap<String, Object[]>();

    public static IngotRegistry geInstance()
    {
        return iInstance;
    }

    public String registerNewIngot(String pInternalName, String pModID, ItemStack pIngotStack)
    {
        iRegisteredIngots.put(pInternalName, new Object[]{pModID, pIngotStack});
        return pInternalName;
    }

    public boolean isHeatable(String pInternalName)
    {
        return (iRegisteredIngots.get(pInternalName) != null);
    }

    public boolean isHeatable(ItemStack pStackToHeat)
    {
        for(Object[] tRegisteredIngots : iRegisteredIngots.values())
        {
            if ((ItemStack) tRegisteredIngots[1] == pStackToHeat) return true;
        }

        return false;
    }



}
