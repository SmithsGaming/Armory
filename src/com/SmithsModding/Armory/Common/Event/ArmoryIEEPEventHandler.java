/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.Armory.Common.Event;

import com.SmithsModding.Armory.API.Knowledge.KnowledgeEntityProperty;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent;

public class ArmoryIEEPEventHandler {

    public void onEntityContruction(EntityEvent.EntityConstructing pEvent) {
        KnowledgeEntityProperty tProperty = new KnowledgeEntityProperty();

        if (pEvent.entity instanceof EntityPlayer && tProperty.get(pEvent.entity) == null) {
            tProperty.register(pEvent.entity);
        }
    }
}
