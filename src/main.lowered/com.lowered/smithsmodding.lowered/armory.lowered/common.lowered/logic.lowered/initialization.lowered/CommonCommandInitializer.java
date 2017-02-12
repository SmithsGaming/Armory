package com.smithsmodding.armory.common.logic.initialization;

import com.smithsmodding.armory.api.common.initialization.IInitializationComponent;
import com.smithsmodding.armory.common.command.CommandArmory;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/25/2017.
 */
public class CommonCommandInitializer extends IInitializationComponent.Impl {

    private static final CommonCommandInitializer INSTANCE = new CommonCommandInitializer();

    public static CommonCommandInitializer getInstance() {
        return INSTANCE;
    }

    private CommonCommandInitializer() {
    }

    @Override
    public void onServerStarting(@Nonnull FMLServerStartingEvent serverStartingEvent) {
        serverStartingEvent.registerServerCommand(new CommandArmory());
    }
}
