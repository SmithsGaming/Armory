package com.smithsmodding.armory.common.logic.initialization;

import com.smithsmodding.armory.api.common.initialization.IInitializationComponent;
import com.smithsmodding.armory.common.config.ArmoryConfig;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/25/2017.
 */
public class CommonConfigInitializer extends IInitializationComponent.Impl {

    private static final CommonConfigInitializer INSTANCE = new CommonConfigInitializer();

    public static CommonConfigInitializer getInstance() {
        return INSTANCE;
    }

    private CommonConfigInitializer() {
    }

    @Override
    public void onPreInit(@Nonnull FMLPreInitializationEvent preInitializationEvent) {
        ArmoryConfig.ConfigHandler.init(preInitializationEvent.getSuggestedConfigurationFile());
    }
}
