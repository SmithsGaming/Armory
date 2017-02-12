package com.smithsmodding.armory.common.factory;
/*
/  HeatedItemFactory
/  Created by : Orion
/  Created on : 03/10/2014
*/

import com.smithsmodding.armory.api.item.IHeatableItem;
import com.smithsmodding.armory.api.materials.IArmorMaterial;
import com.smithsmodding.armory.api.util.references.ModItems;
import com.smithsmodding.armory.api.util.references.ModLogger;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.item.ItemHeatedItem;
import com.smithsmodding.armory.common.registry.HeatableItemRegistry;
import com.smithsmodding.smithscore.util.common.helper.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HeatedItemFactory {
    @Nullable
    private static HeatedItemFactory INSTANCE = null;

    @Nullable
    public static HeatedItemFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HeatedItemFactory();
        }

        return INSTANCE;
    }

    @Nullable
    public ItemStack generateHeatedItem(IArmorMaterial material, String type, float temp) {
        ItemStack pBaseStack = HeatableItemRegistry.getInstance().getBaseStack(material, type);
        if (pBaseStack == null)
            return null;

        ItemStack pHeatedStack = convertToHeatedIngot(pBaseStack);
        HeatableItemRegistry.getInstance().setItemTemperature(pHeatedStack, temp);

        return pHeatedStack;
    }

    @Nullable
    public ItemStack convertToHeatedIngot(@NotNull ItemStack pCooledIngotStack) {
        if (!HeatableItemRegistry.getInstance().isHeatable(pCooledIngotStack)) {
            ModLogger.getInstance().info("Got a not convertable item!:");
            ModLogger.getInstance().info(ItemStackHelper.toString(pCooledIngotStack));
            return null;
        }

        ItemStack tReturnStack = new ItemStack(ModItems.heatedItem);
        NBTTagCompound tStackCompound = new NBTTagCompound();

        tStackCompound.setTag(References.NBTTagCompoundData.HeatedIngot.ORIGINALITEM, pCooledIngotStack.writeToNBT(new NBTTagCompound()));
        tStackCompound.setString(References.NBTTagCompoundData.HeatedIngot.MATERIALID, HeatableItemRegistry.getInstance().getMaterialFromCooledStack(pCooledIngotStack).getUniqueID());
        tStackCompound.setInteger(References.NBTTagCompoundData.HeatedIngot.CURRENTTEMPERATURE, 20);

        if (pCooledIngotStack.getItem() instanceof IHeatableItem) {
            tStackCompound.setString(References.NBTTagCompoundData.HeatedIngot.TYPE, ((IHeatableItem) pCooledIngotStack.getItem()).getInternalType());
        } else {
            tStackCompound.setString(References.NBTTagCompoundData.HeatedIngot.TYPE, References.InternalNames.HeatedItemTypes.INGOT);
        }

        tReturnStack.setTagCompound(tStackCompound);

        return tReturnStack;
    }

    @NotNull
    public ItemStack convertToCooledIngot(@NotNull ItemStack pHeatedItemStack) {
        if (!(pHeatedItemStack.getItem() instanceof ItemHeatedItem)) {
            return pHeatedItemStack;
        }

        ItemStack tReturnStack = ItemStack.loadItemStackFromNBT(pHeatedItemStack.getTagCompound().getCompoundTag(References.NBTTagCompoundData.HeatedIngot.ORIGINALITEM));

        return tReturnStack;
    }

}

