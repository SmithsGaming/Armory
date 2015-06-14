package com.Orion.Armory.Common.Factory;
/*
/  HeatedItemFactory
/  Created by : Orion
/  Created on : 03/10/2014
*/

import codechicken.nei.ItemQuantityField;
import com.Orion.Armory.API.Item.IHeatableItem;
import com.Orion.Armory.Common.Item.ItemHeatedItem;
import com.Orion.Armory.Common.Material.MaterialRegistry;
import com.Orion.Armory.Common.Registry.GeneralRegistry;
import com.Orion.Armory.Util.Core.ItemStackHelper;
import com.Orion.Armory.Util.References;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class HeatedItemFactory
{
    public static HeatedItemFactory iInstance = null;
    protected ArrayList<ItemStack> iHeatableItems = new ArrayList<ItemStack>();
    protected ArrayList<String> iMappedNames = new ArrayList<String>();
    protected ArrayList<String> iMappedTypes = new ArrayList<String>();

    public static HeatedItemFactory getInstance()
    {
        if (iInstance == null)
        {
            iInstance = new HeatedItemFactory();
        }

        return iInstance;
    }

    public ItemStack generateHeatedItem(String pMaterialID, String pInternalTypeID, float pTemp)
    {
        ItemStack pBaseStack = getBaseStack(pMaterialID, pInternalTypeID);
        if (pBaseStack == null)
            return null;

        ItemStack pHeatedStack = convertToHeatedIngot(pBaseStack);
        ItemHeatedItem.setItemTemperature(pHeatedStack, pTemp);

        return pHeatedStack;
    }

    public ItemStack getBaseStack(String pMaterialID, String pInternalTypeID)
    {
        for (int tStackIndex = 0; tStackIndex < iHeatableItems.size(); tStackIndex++)
        {
            if ((pMaterialID.equals(iMappedNames.get(tStackIndex))) && (pInternalTypeID.equals(iMappedTypes.get(tStackIndex))))
            {
                return iHeatableItems.get(tStackIndex);
            }
        }

        return null;
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

        if (pCooledIngotStack.getItem() instanceof IHeatableItem)
        {
            tStackCompound.setString(References.NBTTagCompoundData.HeatedIngot.TYPE, ((IHeatableItem) pCooledIngotStack.getItem()).getInternalType());
        }
        else
        {
            tStackCompound.setString(References.NBTTagCompoundData.HeatedIngot.TYPE, References.InternalNames.HeatedItemTypes.INGOT);
        }

        tReturnStack.setTagCompound(tStackCompound);

        return tReturnStack;
    }

    public ItemStack convertToCooledIngot(ItemStack pHeatedItemStack)
    {
        if (!(pHeatedItemStack.getItem() instanceof ItemHeatedItem))
        {
            return pHeatedItemStack;
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

            if (pNewItemStack.getItem() instanceof IHeatableItem)
            {
                iMappedTypes.add(((IHeatableItem) pNewItemStack.getItem()).getInternalType());
            }
            else
            {
                iMappedTypes.add(References.InternalNames.HeatedItemTypes.INGOT);
            }
        }

        reloadItemStackOreDic(tSingledItemStack, pMaterialName);
    }

    public void reloadAllItemStackOreDic()
    {
        ArrayList<ItemStack> tHeatableItems = new ArrayList<ItemStack>(iHeatableItems);
        ArrayList<String> tMappedNames = new ArrayList<String>(iMappedNames);

        Iterator<ItemStack> tStackIter = tHeatableItems.iterator();
        Iterator<String> tNameIter = tMappedNames.iterator();

        while(tStackIter.hasNext())
        {
            reloadItemStackOreDic(tStackIter.next(), tNameIter.next());
        }
    }

    public void reloadItemStackOreDic(ItemStack pStack, String pMaterialName)
    {
        int[] tOreIDs = OreDictionary.getOreIDs(pStack);
        for (int tOreID : tOreIDs)
        {
            for(ItemStack tStack : OreDictionary.getOres(OreDictionary.getOreName(tOreID)))
            {
                if (!isHeatable(tStack))
                {
                    this.iHeatableItems.add(tStack);
                    this.iMappedNames.add(pMaterialName);

                    if (tStack.getItem() instanceof IHeatableItem)
                    {
                        iMappedTypes.add(((IHeatableItem) tStack.getItem()).getInternalType());
                    }
                    else
                    {
                        iMappedTypes.add(References.InternalNames.HeatedItemTypes.INGOT);
                    }
                }
            }
        }
    }

    public String getMaterialIDFromItemStack(ItemStack pItemStack)
    {
        if (pItemStack.getItem() instanceof ItemHeatedItem)
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
        return MaterialRegistry.getInstance().getMaterial(pMaterialName).getMeltingPoint();
    }

    public ArrayList<ItemStack> getAllMappedStacks()
    {
        return iHeatableItems;
    }

    public ArrayList<String> getAllMappedTypes() { return  iMappedTypes; }

    public float getMeltingPointFromMaterial(ItemStack pItemStack)
    {
        return this.getMeltingPointFromMaterial(this.getMaterialIDFromItemStack(pItemStack));
    }

    public String getType(ItemStack pHeatedItemStack)
    {
        if(!(pHeatedItemStack.getItem() instanceof ItemHeatedItem))
        {
            return "";
        }

        if(!pHeatedItemStack.getTagCompound().hasKey(References.NBTTagCompoundData.HeatedIngot.TYPE))
        {
            return References.InternalNames.HeatedItemTypes.INGOT;
        }

        return pHeatedItemStack.getTagCompound().getString(References.NBTTagCompoundData.HeatedIngot.TYPE);
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

        int[] tSearchedOreDicIDs = OreDictionary.getOreIDs(pItemStack);
        tStackIter = getAllMappedStacks().iterator();

        while (tStackIter.hasNext())
        {
            ItemStack tMappedStack = tStackIter.next();
            int[] tRequestedOreDicIDs = OreDictionary.getOreIDs(tMappedStack);

            for(int tRequestID : tRequestedOreDicIDs)
            {
                for (int tSearchedID : tSearchedOreDicIDs)
                {
                    if (tRequestID != tSearchedID)
                        continue;

                    if (OreDictionary.getOreName(tRequestID).contains("heatable"))
                        continue;

                    if (!(OreDictionary.getOreName(tRequestID).contains("ingot") || OreDictionary.getOreName(tRequestID).contains("plate") || OreDictionary.getOreName(tRequestID).contains("nugget") || OreDictionary.getOreName(tRequestID).contains("block")))
                        continue;


                    this.iHeatableItems.add(tSingledItemStack);
                    this.iMappedNames.add(getMaterialIDFromItemStack(tMappedStack));

                    if (tSingledItemStack.getItem() instanceof IHeatableItem)
                    {
                        iMappedTypes.add(((IHeatableItem) tSingledItemStack.getItem()).getInternalType());
                    }
                    else
                    {
                        iMappedTypes.add(References.InternalNames.HeatedItemTypes.INGOT);
                    }

                    GeneralRegistry.iLogger.info("OreDic Support for the FirePit added: " + ItemStackHelper.toString(tSingledItemStack) + " as " + getMaterialIDFromItemStack(tMappedStack) + ".");

                    return true;
                }
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
