/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Client.Handler;

import com.Orion.Armory.Client.GUI.ArmoryBaseGui;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class ArmoryClientTickHandler {
    private static float iCurrentPartialRenderTick = 0F;
    private static int iTickInGame = 0;

    private static int iTicksWithSmithingsGuideOpen = 0;
    private static int iPageFlipTicks = 0;

    public static float getPartialRenderTick() {
        return iCurrentPartialRenderTick;
    }

    public static int getTicksInGame() {
        return iTickInGame;
    }

    public static int getTickWithSmithingsGuideOpen() {
        return iTicksWithSmithingsGuideOpen;
    }

    public static int getPageFlipTicks() {
        return iPageFlipTicks;
    }

    public static void notifyPageChange() {
        if (iPageFlipTicks == 0)
            iPageFlipTicks = 5;
    }

    @SubscribeEvent
    public void renderTick(TickEvent.RenderTickEvent event) {
        if (event.phase == TickEvent.Phase.START)
            iCurrentPartialRenderTick = event.renderTickTime;
    }

    @SubscribeEvent
    public void clientTickEnd(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            GuiScreen gui = Minecraft.getMinecraft().currentScreen;
            if (gui == null || !gui.doesGuiPauseGame()) {
                iTickInGame++;
            }

            int ticksToOpen = 10;

            if (gui instanceof ArmoryBaseGui) {
                if (iTicksWithSmithingsGuideOpen < 0)
                    iTicksWithSmithingsGuideOpen = 0;
                if (iTicksWithSmithingsGuideOpen < ticksToOpen)
                    iTicksWithSmithingsGuideOpen++;
                if (iPageFlipTicks > 0)
                    iPageFlipTicks--;
            } else {
                iPageFlipTicks = 0;
                if (iTicksWithSmithingsGuideOpen > 0) {
                    if (iTicksWithSmithingsGuideOpen > ticksToOpen)
                        iTicksWithSmithingsGuideOpen = ticksToOpen;
                    iTicksWithSmithingsGuideOpen--;
                }
            }
        }
    }

}
