package com.SmithsModding.Armory.Common;


import com.SmithsModding.Armory.Armory;
import com.SmithsModding.Armory.Common.Event.ArmoryDataSyncerEventHandler;
import com.SmithsModding.Armory.Common.Event.ArmoryIEEPEventHandler;
import com.SmithsModding.Armory.Common.Event.ArmoryStandardEventHandler;
import com.SmithsModding.Armory.Common.Logic.ArmoryInitializer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created by Orion on 26-4-2014
 * <p/>
 * com.Orion.Armory.Common proxy for Armory
 */
public class ArmoryCommonProxy {
    public void preInitializeArmory() {
        Armory.iSide = Side.SERVER;
    }

    public void initializeArmory() {
        ArmoryInitializer.InitializeServer();
    }

    public void registerEventHandlers() {
        FMLCommonHandler.instance().bus().register(new ArmoryDataSyncerEventHandler());
        MinecraftForge.EVENT_BUS.register(new ArmoryIEEPEventHandler());
        MinecraftForge.EVENT_BUS.register(new ArmoryStandardEventHandler());
    }

    public EntityPlayer getPlayer(MessageContext pContext) {
        return pContext.getServerHandler().playerEntity;
    }

}
