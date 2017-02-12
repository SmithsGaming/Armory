package com.smithsmodding.armory.common.logic.initialization;

import com.smithsmodding.armory.api.common.initialization.IInitializationComponent;
import com.smithsmodding.armory.common.structure.conduit.StructureFactoryConduit;
import com.smithsmodding.armory.common.structure.forge.StructureFactoryForge;
import com.smithsmodding.smithscore.common.structures.StructureRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/25/2017.
 */
public class CommonStructureInitializer extends IInitializationComponent.Impl {

    private static final CommonStructureInitializer INSTANCE = new CommonStructureInitializer();

    public static CommonStructureInitializer getInstance() {
        return INSTANCE;
    }

    private CommonStructureInitializer() {
    }

    @Override
    public void onPreInit(@Nonnull FMLPreInitializationEvent preInitializationEvent) {
        StructureRegistry.getServerInstance().registerStructureFactory(new StructureFactoryForge());
        StructureRegistry.getServerInstance().registerStructureFactory(new StructureFactoryConduit());
    }
}
