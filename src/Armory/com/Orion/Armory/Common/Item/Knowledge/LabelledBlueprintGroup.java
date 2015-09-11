/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Common.Item.Knowledge;

import com.Orion.Armory.Common.Registry.GeneralRegistry;
import com.Orion.Armory.Util.References;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;

public class LabelledBlueprintGroup {
    public ItemStack LabelStack;
    public ArrayList<ItemStack> Stacks;

    public LabelledBlueprintGroup(NBTTagCompound pStoreCompound) {
        if (pStoreCompound == null) {
            Stacks = new ArrayList<ItemStack>();
            LabelStack = new ItemStack(GeneralRegistry.Items.iLabel, 1);
            return;
        }

        if (!pStoreCompound.hasKey(References.NBTTagCompoundData.Item.SmithingsGuide.LABELSTACKS) || !pStoreCompound.hasKey(References.NBTTagCompoundData.Item.ItemInventory.STACK)) {
            Stacks = new ArrayList<ItemStack>();
            LabelStack = new ItemStack(GeneralRegistry.Items.iLabel, 1);
            return;
        }

        LabelStack = ItemStack.loadItemStackFromNBT(pStoreCompound.getCompoundTag(References.NBTTagCompoundData.Item.ItemInventory.STACK));
        Stacks = new ArrayList<ItemStack>();

        NBTTagList tStacks = pStoreCompound.getTagList(References.NBTTagCompoundData.Item.SmithingsGuide.LABELSTACKS, 10);
        for (int tStackCounter = 0; tStackCounter < tStacks.tagCount(); tStackCounter++) {
            Stacks.add(ItemStack.loadItemStackFromNBT(tStacks.getCompoundTagAt(tStackCounter)));
        }
    }

    public LabelledBlueprintGroup() {
        this(new NBTTagCompound());
    }

    public NBTTagCompound writeToCompound() {
        NBTTagCompound tStoreCompound = new NBTTagCompound();
        tStoreCompound.setTag(References.NBTTagCompoundData.Item.ItemInventory.STACK, LabelStack.writeToNBT(new NBTTagCompound()));

        NBTTagList tStacks = new NBTTagList();
        for (ItemStack tStack : Stacks) {
            tStacks.appendTag(tStack.writeToNBT(new NBTTagCompound()));
        }

        tStoreCompound.setTag(References.NBTTagCompoundData.Item.SmithingsGuide.LABELSTACKS, tStacks);
        return tStoreCompound;
    }
}
