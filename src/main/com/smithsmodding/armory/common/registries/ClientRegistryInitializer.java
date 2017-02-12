package com.smithsmodding.armory.common.registries;

import com.smithsmodding.armory.api.textures.creation.ICreationController;
import com.smithsmodding.armory.api.util.references.ModRegistries;
import net.minecraftforge.fml.common.registry.RegistryBuilder;

/**
 * Created by marcf on 1/14/2017.
 */
public class ClientRegistryInitializer extends CommonRegistryInitializer {

    @Override
    public void initialize() {
        super.initialize();

        RegistryManager.getInstance().textureCreationControllerRegistry  = new RegistryBuilder<ICreationController>()
                .setName(ModRegistries.TEXTURECREATIONCONTROLLER)
                .setType(ICreationController.class)
                .setIDRange(0, 255)
                .addCallback(new RegistryManager.RegistryCallbackToEventConverter<ICreationController>())
                .create();
    }
}
