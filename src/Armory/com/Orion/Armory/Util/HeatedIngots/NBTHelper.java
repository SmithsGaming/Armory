package com.Orion.Armory.Util.HeatedIngots;
/*
/  NBTHelper
/  Created by : Orion
/  Created on : 03/10/2014
*/

import com.Orion.Armory.Common.Item.ItemHeatedIngot;
import com.Orion.Armory.Util.References;
import net.minecraft.item.ItemStack;

import java.security.InvalidParameterException;

public class NBTHelper
{
    public static ItemStack heatHeatedItem(ItemStack pHeatedItemStack, Integer pTempDiffer)
    {
        if (!(pHeatedItemStack.getItem() instanceof ItemHeatedIngot))
        {
            throw new InvalidParameterException("The given parameter is not a heatable item. Please report this to the modder!");
        }

        pHeatedItemStack.getTagCompound().setInteger(References.NBTTagCompoundData.HeatedIngot.Temperature, pHeatedItemStack.getTagCompound().getInteger(References.NBTTagCompoundData.HeatedIngot.Temperature) + pTempDiffer);

        return pHeatedItemStack;
    }

    public static Integer getTemperatureOfIngot(ItemStack pHeatedStack)
    {
        if (!(pHeatedStack.getItem() instanceof ItemHeatedIngot))
        {
            throw new InvalidParameterException("The given parameter is not a heatable item. Please report this to the modder!");
        }

        return pHeatedStack.getTagCompound().getInteger(References.NBTTagCompoundData.HeatedIngot.Temperature);

    }
}
