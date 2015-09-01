/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Common.Event;

import com.Orion.Armory.Common.Item.ItemGuideLabel;
import com.Orion.Armory.Util.References;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.AnvilUpdateEvent;

public class ArmoryStandardEventHandler {

    public void onAnvilInputChanged(AnvilUpdateEvent pEvent) {
        boolean tMatchesEvent = false;
        int tLevelsCost = 0;

        if (pEvent.left.getItem() instanceof ItemGuideLabel && pEvent.right != null) {
            pEvent.left.getTagCompound().setTag(References.NBTTagCompoundData.Item.Labels.LOGOSTACK, pEvent.right.writeToNBT(new NBTTagCompound()));
            pEvent.materialCost = 0;


        }
    }
}
