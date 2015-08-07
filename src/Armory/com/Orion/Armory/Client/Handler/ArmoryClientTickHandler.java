/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Client.Handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class ArmoryClientTickHandler {
    private static float iCurrentPartialRenderTick = 0F;

    public static float getPartialRenderTick() {
        return iCurrentPartialRenderTick;
    }

    @SubscribeEvent
    public void renderTick(TickEvent.RenderTickEvent event) {
        if (event.phase == TickEvent.Phase.START)
            iCurrentPartialRenderTick = event.renderTickTime;
    }
}
