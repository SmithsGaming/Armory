package com.Orion.Armory.Common.Factory;
/*
/  HeatedIngotFactory
/  Created by : Orion
/  Created on : 03/10/2014
*/

import com.Orion.Armory.Common.Item.ItemHeatedIngot;
import com.Orion.Armory.Common.Registry.GeneralRegistry;
import com.Orion.Armory.Util.References;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.security.InvalidParameterException;

public class HeatedIngotFactory
{
    protected static HeatedIngotFactory iInstance = null;

    public static HeatedIngotFactory getInstance()
    {
        if (iInstance == null)
        {
            iInstance = new HeatedIngotFactory();
        }

        return iInstance;
    }

    public ItemStack convertToHeatedIngot(ItemStack pCooledIngotStack)
    {
        ItemStack tReturnStack = new ItemStack(GeneralRegistry.Items.iHeatedIngot);
        NBTTagCompound tStackCompound = new NBTTagCompound();

        tStackCompound.setInteger(References.NBTTagCompoundData.HeatedIngot.OriginalItem, Item.getIdFromItem(pCooledIngotStack.getItem()));
        tStackCompound.setTag(References.NBTTagCompoundData.HeatedIngot.OriginalNBT, pCooledIngotStack.getTagCompound());
        tStackCompound.setInteger(References.NBTTagCompoundData.HeatedIngot.OriginalMeta, pCooledIngotStack.getItemDamage());

        tStackCompound.setInteger(References.NBTTagCompoundData.HeatedIngot.Temperature, 20);

        tReturnStack.setTagCompound(tStackCompound);

        return tReturnStack;
    }

    public ItemStack convertToCooleadIngot(ItemStack pHeatedItemStack)
    {
        if (!(pHeatedItemStack.getItem() instanceof ItemHeatedIngot))
        {
            throw new InvalidParameterException("The given parameter is not a heatable item. Please report this to the modder!");
        }

        ItemStack tReturnStack = new ItemStack(Item.getItemById(pHeatedItemStack.getTagCompound().getInteger(References.NBTTagCompoundData.HeatedIngot.OriginalItem)));
        tReturnStack.setTagCompound(pHeatedItemStack.getTagCompound().getCompoundTag(References.NBTTagCompoundData.HeatedIngot.OriginalNBT));
        tReturnStack.setItemDamage(pHeatedItemStack.getTagCompound().getInteger(References.NBTTagCompoundData.HeatedIngot.OriginalMeta));

        return tReturnStack;
    }
}
