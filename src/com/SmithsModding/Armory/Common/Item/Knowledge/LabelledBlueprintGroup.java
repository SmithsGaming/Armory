/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.Armory.Common.Item.Knowledge;

import com.smithsmodding.Armory.Util.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;

import java.util.*;

public class LabelledBlueprintGroup {
    public ItemStack LabelStack;
    public ArrayList<ItemStack> Stacks;

    public LabelledBlueprintGroup (NBTTagCompound pStoreCompound) {
        if (pStoreCompound == null) {
            Stacks = new ArrayList<ItemStack>();
            LabelStack = null;
            return;
        }

        if (!pStoreCompound.hasKey(References.NBTTagCompoundData.Item.SmithingsGuide.LABELSTACKS) || !pStoreCompound.hasKey(References.NBTTagCompoundData.Item.ItemInventory.STACK)) {
            Stacks = new ArrayList<ItemStack>();
            LabelStack = null;
            return;
        }

        LabelStack = ItemStack.loadItemStackFromNBT(pStoreCompound.getCompoundTag(References.NBTTagCompoundData.Item.ItemInventory.STACK));
        Stacks = new ArrayList<ItemStack>();

        NBTTagList tStacks = pStoreCompound.getTagList(References.NBTTagCompoundData.Item.SmithingsGuide.LABELSTACKS, 10);
        for (int tStackCounter = 0; tStackCounter < tStacks.tagCount(); tStackCounter++) {
            Stacks.add(ItemStack.loadItemStackFromNBT(tStacks.getCompoundTagAt(tStackCounter)));
        }
    }

    public LabelledBlueprintGroup () {
        this(new NBTTagCompound());
    }

    public NBTTagCompound writeToCompound () {
        NBTTagCompound tStoreCompound = new NBTTagCompound();
        //tStoreCompound.setTag(References.NBTTagCompoundData.Item.ItemInventory.STACK, LabelStack.writeToNBT(new NBTTagCompound()));

        NBTTagList tStacks = new NBTTagList();
        for (ItemStack tStack : Stacks) {
            tStacks.appendTag(tStack.writeToNBT(new NBTTagCompound()));
        }

        tStoreCompound.setTag(References.NBTTagCompoundData.Item.SmithingsGuide.LABELSTACKS, tStacks);
        return tStoreCompound;
    }
}
