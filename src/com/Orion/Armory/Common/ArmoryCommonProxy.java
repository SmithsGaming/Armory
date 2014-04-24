package com.Orion.Armory.Common;

import com.Orion.Armory.Client.Render.RenderPlayerEventHook;
import com.Orion.Armory.Common.Logic.ArmoryInitializer;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created by Orion on 26-4-2014
 *
 * com.Orion.Armory.Common proxy for Armory
 */
public class ArmoryCommonProxy
{
    public void initializeArmory()
    {
        ArmoryInitializer tInitializer = new ArmoryInitializer();
        tInitializer.iInstance.Initialize(Side.SERVER);
    }

    public void registerEvents()
    {
        MinecraftForge.EVENT_BUS.register(new RenderPlayerEventHook());
    }

    public void registerRenderers()
    {
        return;
    }
}
