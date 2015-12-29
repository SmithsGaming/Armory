package com.SmithsModding.Armory.Common.Factory;
/*
/  HeatedItemFactory
/  Created by : Orion
/  Created on : 03/10/2014
*/

import com.SmithsModding.Armory.API.Item.*;
import com.SmithsModding.Armory.API.Materials.*;
import com.SmithsModding.Armory.*;
import com.SmithsModding.Armory.Common.Item.*;
import com.SmithsModding.Armory.Common.Registry.*;
import com.SmithsModding.Armory.Util.*;
import com.SmithsModding.SmithsCore.SmithsCore;
import com.SmithsModding.SmithsCore.Util.Common.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;

public class HeatedItemFactory {
    private static HeatedItemFactory INSTANCE = null;

    public static HeatedItemFactory getInstance () {
        if (INSTANCE == null) {
            INSTANCE = new HeatedItemFactory();
        }

        return INSTANCE;
    }

    public ItemStack generateHeatedItem (IArmorMaterial material, String type, float temp) {
        ItemStack pBaseStack = HeatableItemRegistry.getInstance().getBaseStack(material, type);
        if (pBaseStack == null)
            return null;

        ItemStack pHeatedStack = convertToHeatedIngot(pBaseStack);
        ItemHeatedItem.setItemTemperature(pHeatedStack, temp);

        return pHeatedStack;
    }

    public ItemStack convertToHeatedIngot (ItemStack pCooledIngotStack) {
        if(SmithsCore.isInDevenvironment())
        {
            //Armory.getLogger().info("Converting " + ItemStackHelper.toString(pCooledIngotStack) + " to a Heated Stack.");
        }

        if (!HeatableItemRegistry.getInstance().isHeatable(pCooledIngotStack)) {
            Armory.getLogger().info("Got a not convertable item!:");
            Armory.getLogger().info(ItemStackHelper.toString(pCooledIngotStack));
            return null;
        }

        ItemStack tReturnStack = new ItemStack(GeneralRegistry.Items.heatedItem);
        NBTTagCompound tStackCompound = new NBTTagCompound();

        tStackCompound.setTag(References.NBTTagCompoundData.HeatedIngot.ORIGINALITEM, pCooledIngotStack.writeToNBT(new NBTTagCompound()));
        tStackCompound.setString(References.NBTTagCompoundData.HeatedIngot.MATERIALID, HeatableItemRegistry.getInstance().getMaterialFromCooledStack(pCooledIngotStack).getUniqueID());
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

