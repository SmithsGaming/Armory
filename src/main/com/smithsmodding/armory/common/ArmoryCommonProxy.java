package com.smithsmodding.armory.common;


import com.smithsmodding.armory.Armory;
import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.util.references.ModLogger;
import com.smithsmodding.armory.common.handlers.GuiHandler;
import com.smithsmodding.armory.common.handlers.config.ArmoryDataSyncerEventHandler;
import com.smithsmodding.armory.common.handlers.config.ConfigSyncCompletedEventHandler;
import com.smithsmodding.armory.common.handlers.config.MaterialPropertyValueEventHandler;
import com.smithsmodding.armory.common.logic.ArmoryInitializer;
import com.smithsmodding.armory.common.registry.GeneralRegistry;
import com.smithsmodding.armory.common.structure.forge.StructureFactoryForge;
import com.smithsmodding.smithscore.SmithsCore;
import com.smithsmodding.smithscore.common.structures.StructureRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

/**
 * Created by Orion on 26-4-2014
 * <p>
 * com.Orion.armory.common proxy for armory
 */
public class ArmoryCommonProxy {
    public void preInitializeArmory() {
        Armory.side = Side.SERVER;
        ArmoryInitializer.InitializeServer();
    }

    public void initializeArmory() {
    }

    public void onLoadComplete() {
        ArmoryInitializer.onLoadCompleted();
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

    public EntityPlayer getPlayer(@NotNull MessageContext pContext) {
        return pContext.getServerHandler().playerEntity;
    }

    public void callAPIRequests() {
        ModLogger.getInstance().info("Notifying requesters of API init completion.");
        for (String s : GeneralRegistry.getInstance().getRequestedAPICallbacks().keySet()) {
            this.callbackRegistration(s, GeneralRegistry.getInstance().getRequestedAPICallbacks().get(s));
        }
    }

    private void callbackRegistration(@NotNull String method, String modname) {
        String[] splitName = method.split("\\.");
        String methodName = splitName[splitName.length - 1];
        String className = method.substring(0, method.length() - methodName.length() - 1);
        ModLogger.getInstance().info(String.format("Trying to reflect %s %s to call the API Callback", className, methodName));
        try {
            Class reflectClass = Class.forName(className);
            Method reflectMethod = reflectClass.getDeclaredMethod(methodName, IArmoryAPI.class);
            reflectMethod.invoke(null, (IArmoryAPI) GeneralRegistry.getInstance());
            ModLogger.getInstance().info(String.format("Success in registering %s", modname));
        } catch (ClassNotFoundException e) {
            ModLogger.getInstance().warn(String.format("Could not find class %s", className));
        } catch (NoSuchMethodException e) {
            ModLogger.getInstance().warn(String.format("Could not find method %s", methodName));
        } catch (Exception e) {
            ModLogger.getInstance().warn(String.format("Exception while trying to access the method : %s", e.toString()));
        }
    }

}
