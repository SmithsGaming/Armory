package com.Orion.Armory.Client.Event;
/*
 *   ClientEventHandler
 *   Created by: Orion
 *   Created on: 24-1-2015
 */

import com.Orion.Armory.Client.Logic.CoreIconProvider;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.client.event.TextureStitchEvent;

public class ClientEventHandler
{

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void textureHook(TextureStitchEvent.Pre pEvent)
    {
        if (pEvent.map.getTextureType() == 1)
        {
            CoreIconProvider.getInstance().registerIcons(pEvent.map);
        }
    }
}
