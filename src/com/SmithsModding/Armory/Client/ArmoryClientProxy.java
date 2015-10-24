package com.SmithsModding.Armory.Client;

import com.SmithsModding.Armory.Armory;
import com.SmithsModding.Armory.Client.Event.ClientDisconnectedFromServerEventHandler;
import com.SmithsModding.Armory.Client.Event.ClientEventHandler;
import com.SmithsModding.Armory.Client.Handler.ArmoryClientTickHandler;
import com.SmithsModding.Armory.Client.Logic.ArmoryClientInitializer;
import com.SmithsModding.Armory.Common.ArmoryCommonProxy;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created by Orion on 26-3-2014.
 */
public class ArmoryClientProxy extends ArmoryCommonProxy {
    @Override
    public void preInitializeArmory() {
        Armory.iSide = Side.CLIENT;
    }

    @Override
    public void initializeArmory() {
        Armory.iSide = Side.CLIENT;
        ArmoryClientInitializer.InitializeClient();
    }

    @Override
    public EntityPlayer getPlayer(MessageContext pContext) {
        return Minecraft.getMinecraft().thePlayer;
    }

    @Override
    public void registerEventHandlers() {
        super.registerEventHandlers();

        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
        FMLCommonHandler.instance().bus().register(new ClientDisconnectedFromServerEventHandler());
        FMLCommonHandler.instance().bus().register(new ArmoryClientTickHandler());
    }
}
