package com.smithsmodding.armory.client.logic.initialization;

import com.smithsmodding.armory.api.common.initialization.IInitializationComponent;
import com.smithsmodding.armory.common.structure.forge.StructureFactoryForge;
import com.smithsmodding.smithscore.common.structures.StructureRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/25/2017.
 */
public class ClientStructureInitializer extends IInitializationComponent.Impl {

    private static final ClientStructureInitializer INSTANCE = new ClientStructureInitializer();

    public static ClientStructureInitializer getInstance() {
        return INSTANCE;
    }

    private ClientStructureInitializer() {
    }

    @Override
    public void onPreInit(@Nonnull FMLPreInitializationEvent preInitializationEvent) {
        StructureRegistry.getClientInstance().registerStructureFactory(new StructureFactoryForge());
    }
}
