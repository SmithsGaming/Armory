package com.Orion.Armory.Common;

import com.Orion.Armory.Client.Render.Old.ArmorItemRenderer;
import com.Orion.Armory.Client.Render.Old.RenderPlayerEventHook;
import com.Orion.Armory.Common.Armor.ArmorCore;
import com.Orion.Armory.Common.Logic.ArmoryInitializer;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.client.MinecraftForgeClient;
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
        tInitializer.iInstance.Initialize(Side.CLIENT);
    }

    public void registerEvents()
    {
        MinecraftForge.EVENT_BUS.register(new RenderPlayerEventHook());
    }

    public void registerRenderers()
    {
        for (ArmorCore tCore: ARegistry.iInstance.getAllArmorMappings())
        {
            MinecraftForgeClient.registerItemRenderer(tCore, new ArmorItemRenderer());
        }
    }
}
