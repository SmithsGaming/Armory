package com.SmithsModding.Armory.Common.Factory;
/*
/  HeatedItemFactory
/  Created by : Orion
/  Created on : 03/10/2014
*/

import com.SmithsModding.Armory.API.Item.*;
import com.SmithsModding.Armory.*;
import com.SmithsModding.Armory.Common.Item.*;
import com.SmithsModding.Armory.Common.Registry.*;
import com.SmithsModding.Armory.Util.*;
import com.SmithsModding.SmithsCore.Util.Common.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;

import java.util.*;

public class HeatedItemFactory {
    public static HeatedItemFactory iInstance = null;
    protected ArrayList<ItemStack> iHeatableItems = new ArrayList<ItemStack>();
    protected ArrayList<String> iMappedNames = new ArrayList<String>();
    protected ArrayList<String> iMappedTypes = new ArrayList<String>();

    public static HeatedItemFactory getInstance () {
        if (iInstance == null) {
            iInstance = new HeatedItemFactory();
        }

        return iInstance;
    }

    public ItemStack generateHeatedItem (String pMaterialID, String pInternalTypeID, float pTemp) {
        ItemStack pBaseStack = getBaseStack(pMaterialID, pInternalTypeID);
        if (pBaseStack == null)
            return null;

        ItemStack pHeatedStack = convertToHeatedIngot(pBaseStack);
        ItemHeatedItem.setItemTemperature(pHeatedStack, pTemp);

        return pHeatedStack;
    }

    public ItemStack getBaseStack (String pMaterialID, String pInternalTypeID) {
        for (int tStackIndex = 0; tStackIndex < iHeatableItems.size(); tStackIndex++) {
            if (( pMaterialID.equals(iMappedNames.get(tStackIndex)) ) && ( pInternalTypeID.equals(iMappedTypes.get(tStackIndex)) )) {
                return iHeatableItems.get(tStackIndex);
            }
        }

        return null;
    }

    public ItemStack convertToHeatedIngot (ItemStack pCooledIngotStack) {
        if (!HeatableItemRegistry.getInstance().isHeatable(pCooledIngotStack)) {
            Armory.getLogger().info("Got a not convertable item!:");
            Armory.getLogger().info(ItemStackHelper.toString(pCooledIngotStack));
            return null;
        }

        ItemStack tReturnStack = new ItemStack(GeneralRegistry.Items.heatedItem);
        NBTTagCompound tStackCompound = new NBTTagCompound();

        tStackCompound.setTag(References.NBTTagCompoundData.HeatedIngot.ORIGINALITEM, pCooledIngotStack.writeToNBT(new NBTTagCompound()));
        tStackCompound.setString(References.NBTTagCompoundData.HeatedIngot.MATERIALID, getMaterialIDFromItemStack(pCooledIngotStack));
        tStackCompound.setInteger(References.NBTTagCompoundData.HeatedIngot.CURRENTTEMPERATURE, 20);

        if (pCooledIngotStack.getItem() instanceof IHeatableItem) {
            tStackCompound.setString(References.NBTTagCompoundData.HeatedIngot.TYPE, ( (IHeatableItem) pCooledIngotStack.getItem() ).getInternalType());
        } else {
            tStackCompound.setString(References.NBTTagCompoundData.HeatedIngot.TYPE, References.InternalNames.HeatedItemTypes.INGOT);
        }

        tReturnStack.setTagCompound(tStackCompound);

        return tReturnStack;
    }

    public ItemStack convertToCooledIngot (ItemStack pHeatedItemStack) {
        if (!( pHeatedItemStack.getItem() instanceof ItemHeatedItem )) {
            return pHeatedItemStack;
        }

        ItemStack tReturnStack = ItemStack.loadItemStackFromNBT(pHeatedItemStack.getTagCompound().getCompoundTag(References.NBTTagCompoundData.HeatedIngot.ORIGINALITEM));

        return tReturnStack;
    }

}

