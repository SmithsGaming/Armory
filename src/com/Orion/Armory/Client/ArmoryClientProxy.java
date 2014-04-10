package com.Orion.Armory.Client;

import com.Orion.Armory.Client.Render.ArmorItemRenderer;
import com.Orion.Armory.Client.Render.RenderPlayerEventHook;
import com.Orion.Armory.Common.ARegistry;
import com.Orion.Armory.Common.Armor.ArmorCore;
import com.Orion.Armory.Common.ArmoryCommonProxy;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created by Orion on 26-3-2014.
 */
public class ArmoryClientProxy extends ArmoryCommonProxy
{
    @Override
    public void registerEvents()
    {
        MinecraftForge.EVENT_BUS.register(new RenderPlayerEventHook());
    }

    @Override
    public void registerRenderers()
    {
        for (ArmorCore tCore: ARegistry.iInstance.getAllArmorMappings())
        {
            MinecraftForgeClient.registerItemRenderer(tCore, new ArmorItemRenderer());
        }
    }


}
