package com.smithsmodding.armory.common;


import com.smithsmodding.armory.Armory;
import com.smithsmodding.armory.common.handlers.GuiHandler;
import com.smithsmodding.armory.common.handlers.config.ArmoryDataSyncerEventHandler;
import com.smithsmodding.armory.common.handlers.config.ConfigSyncCompletedEventHandler;
import com.smithsmodding.armory.common.handlers.config.MaterialPropertyValueEventHandler;
import com.smithsmodding.armory.common.logic.ArmoryInitializer;
import com.smithsmodding.armory.common.structure.forge.StructureFactoryForge;
import com.smithsmodding.smithscore.SmithsCore;
import com.smithsmodding.smithscore.common.structures.StructureRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by Orion on 26-4-2014
 *
 * com.Orion.armory.common proxy for armory
 */
public class ArmoryCommonProxy {
    public void preInitializeArmory() {
        Armory.side = Side.SERVER;
        ArmoryInitializer.InitializeServer();
    }

    public void initializeArmory() {
    }

    public void initializeStructures() {
        StructureRegistry.getServerInstance().registerStructureFactory(new StructureFactoryForge());
    }

    public void registerEventHandlers() {
        NetworkRegistry.INSTANCE.registerGuiHandler(Armory.instance, new GuiHandler());
        MinecraftForge.EVENT_BUS.register(Armory.instance);
        MinecraftForge.EVENT_BUS.register(new ArmoryDataSyncerEventHandler());

        SmithsCore.getRegistry().getNetworkBus().register(new ConfigSyncCompletedEventHandler());
        SmithsCore.getRegistry().getNetworkBus().register(new MaterialPropertyValueEventHandler());

    }

    public EntityPlayer getPlayer(MessageContext pContext) {
        return pContext.getServerHandler().playerEntity;
    }

}
