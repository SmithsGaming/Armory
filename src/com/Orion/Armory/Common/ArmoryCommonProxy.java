package com.Orion.Armory.Common;

import com.Orion.Armory.Client.Render.RenderPlayerEventHook;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created by Orion on 26-4-2014
 *
 * com.Orion.Armory.Common proxy for Armory
 */
public class ArmoryCommonProxy
{
    public void registerEvents()
    {
        MinecraftForge.EVENT_BUS.register(new RenderPlayerEventHook());
    }
}
