package com.Orion.Armory.Common.Factory;
/*
/  HeatedIngotFactory
/  Created by : Orion
/  Created on : 03/10/2014
*/

import com.Orion.Armory.Common.Item.ItemHeatedIngot;
import com.Orion.Armory.Common.Registry.GeneralRegistry;
import com.Orion.Armory.Util.Core.ItemStackHelper;
import com.Orion.Armory.Util.References;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Iterator;

public class HeatedIngotFactory
{
    public static HeatedIngotFactory iInstance = null;
    protected ArrayList<ItemStack> iHeatableItems = new ArrayList<ItemStack>();
    protected ArrayList<String> iMappedNames = new ArrayList<String>();

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
        if (!isHeatable(pCooledIngotStack))
        {
            GeneralRegistry.iLogger.info("Got a not convertable item!:");
            GeneralRegistry.iLogger.info(ItemStackHelper.toString(pCooledIngotStack));
            return null;
        }

        ItemStack tReturnStack = new ItemStack(GeneralRegistry.Items.iHeatedIngot);
        NBTTagCompound tStackCompound = new NBTTagCompound();

        tStackCompound.setTag(References.NBTTagCompoundData.HeatedIngot.ORIGINALITEM, pCooledIngotStack.writeToNBT(new NBTTagCompound()));
        tStackCompound.setString(References.NBTTagCompoundData.HeatedIngot.MATERIALID, getMaterialIDFromItemStack(pCooledIngotStack));
        tStackCompound.setInteger(References.NBTTagCompoundData.HeatedIngot.CURRENTTEMPERATURE, 20);

        tReturnStack.setTagCompound(tStackCompound);

        return tReturnStack;
    }

    public ItemStack convertToCooledIngot(ItemStack pHeatedItemStack)
    {
        if (!(pHeatedItemStack.getItem() instanceof ItemHeatedIngot))
        {
            throw new InvalidParameterException("The given parameter is not a heatable item. Please report this to the modder!");
        }

        ItemStack tReturnStack = ItemStack.loadItemStackFromNBT(pHeatedItemStack.getTagCompound().getCompoundTag(References.NBTTagCompoundData.HeatedIngot.ORIGINALITEM));

        return tReturnStack;
    }

    public void addHeatableItemstack(String pMaterialName, ItemStack pNewItemStack)
    {
        ItemStack tSingledItemStack = pNewItemStack.copy();
        tSingledItemStack.stackSize = 1;

        if (!isHeatable(tSingledItemStack))
        {
            this.iHeatableItems.add(tSingledItemStack);
            this.iMappedNames.add(pMaterialName);
        }
    }

    public String getMaterialIDFromItemStack(ItemStack pItemStack)
    {
        if (pItemStack.getItem() instanceof ItemHeatedIngot)
        {
            return pItemStack.getTagCompound().getString(References.NBTTagCompoundData.HeatedIngot.MATERIALID);
        }

        ItemStack tSingledItemStack = pItemStack.copy();
        tSingledItemStack.stackSize = 1;

        if (isHeatable(tSingledItemStack))
        {
            return iMappedNames.get(getIndexOfStack(tSingledItemStack));
        }

        return "";
    }

    public float getMeltingPointFromMaterial(String pMaterialName)
    {
        return GeneralRegistry.iMeltingPoints.get(pMaterialName);
    }

    public ArrayList<ItemStack> getAllMappedStacks()
    {
        return iHeatableItems;
    }

    public float getMeltingPointFromMaterial(ItemStack pItemStack)
    {
        return this.getMeltingPointFromMaterial(this.getMaterialIDFromItemStack(pItemStack));
    }

    public boolean isHeatable(ItemStack pItemStack)
    {
        ItemStack tSingledItemStack = pItemStack.copy();
        tSingledItemStack.stackSize = 1;

        Iterator<ItemStack> tStackIter = getAllMappedStacks().iterator();

        while (tStackIter.hasNext())
        {
            if (ItemStackHelper.equalsIgnoreStackSize(tStackIter.next(), tSingledItemStack))
            {
                return true;
            }
        }

        return false;
    }

    private int getIndexOfStack(ItemStack pStack) {
        Iterator<ItemStack> tStackIter = getAllMappedStacks().iterator();
        int tIndex = 0;

        while (tStackIter.hasNext()) {
            if (ItemStackHelper.equalsIgnoreStackSize(tStackIter.next(), pStack)) {
                return tIndex;
            }

            tIndex++;
        }

        return -1;
    }
}
