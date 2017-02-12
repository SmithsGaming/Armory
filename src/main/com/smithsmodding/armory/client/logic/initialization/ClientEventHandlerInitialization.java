package com.smithsmodding.armory.client.logic.initialization;

import com.smithsmodding.armory.api.common.initialization.IInitializationComponent;
import com.smithsmodding.armory.client.textures.MaterializedTextureCreator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/25/2017.
 */
public class ClientEventHandlerInitialization extends IInitializationComponent.Impl {

    private static final ClientEventHandlerInitialization INSTANCE = new ClientEventHandlerInitialization();

    public static ClientEventHandlerInitialization getInstance () {
        return INSTANCE;
    }

    private ClientEventHandlerInitialization () {
    }

    @Override
    public void onPreInit(@Nonnull FMLPreInitializationEvent preInitializationEvent) {
        MaterializedTextureCreator materializedTextureCreator = new MaterializedTextureCreator();
        MinecraftForge.EVENT_BUS.register(materializedTextureCreator);
        ((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(materializedTextureCreator);

        MinecraftForge.EVENT_BUS.register(new com.smithsmodding.armory.api.util.client.Textures());
    }
}
