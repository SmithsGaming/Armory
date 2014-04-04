package com.Orion.Armory.Client;

import com.Orion.Armory.Client.Render.RenderPlayerEventHook;
import com.Orion.Armory.Common.ArmoryCommonProxy;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created by Marc on 26-3-2014.
 */
public class ArmoryClientProxy extends ArmoryCommonProxy
{
    @Override
    public void registerEvents()
    {
        MinecraftForge.EVENT_BUS.register(new RenderPlayerEventHook());
    }
}
