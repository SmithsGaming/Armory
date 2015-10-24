/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.Armory.Common.Event;

import com.SmithsModding.Armory.Common.Item.ItemGuideLabel;
import com.SmithsModding.Armory.Util.References;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.AnvilUpdateEvent;

public class ArmoryStandardEventHandler {

    @SubscribeEvent
    public void onAnvilInputChanged(AnvilUpdateEvent pEvent) {
        int tLevelsCost = 0;

        if (pEvent.left.getItem() instanceof ItemGuideLabel && pEvent.right != null) {
            if (pEvent.output == null)
                pEvent.output = pEvent.left.copy();

            if (pEvent.output.getTagCompound() == null)
                pEvent.output.setTagCompound(new NBTTagCompound());

            pEvent.output.getTagCompound().setTag(References.NBTTagCompoundData.Item.Labels.LOGOSTACK, pEvent.right.writeToNBT(new NBTTagCompound()));
            pEvent.materialCost = 0;

            tLevelsCost = 3;
        }

        if (!pEvent.name.equals(pEvent.left.getDisplayName()) && !pEvent.name.isEmpty()) {
            if (pEvent.output == null)
                pEvent.output = pEvent.left.copy();

            if (pEvent.output.getTagCompound() == null)
                pEvent.output.setTagCompound(new NBTTagCompound());

            pEvent.output.setStackDisplayName(pEvent.name);

            if (tLevelsCost == 3)
                tLevelsCost = 4;
            else
                tLevelsCost = 2;
        }

        if (tLevelsCost > 0) {
            pEvent.cost = tLevelsCost;
        }

    }
}
