package com.smithsmodding.armory.client.logic.initialization;

import com.smithsmodding.armory.api.client.textures.creation.ICreationController;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.client.textures.creators.AddonTextureCreator;
import com.smithsmodding.armory.client.textures.creators.AnvilTextureCreator;
import com.smithsmodding.armory.client.textures.creators.CoreTextureCreator;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/29/2017.
 */
@Mod.EventBusSubscriber
public class ClientCreationControllerRegistratitionInitializer {

    @SubscribeEvent
    public static void onTextureCreationRegistration(@Nonnull RegistryEvent.Register<ICreationController> creationControllerRegisterEvent) {
        creationControllerRegisterEvent.getRegistry().register(new CoreTextureCreator().setRegistryName(References.InternalNames.TextureCreation.TCN_CORE));
        creationControllerRegisterEvent.getRegistry().register(new AddonTextureCreator().setRegistryName(References.InternalNames.TextureCreation.TCN_ADDON));
        creationControllerRegisterEvent.getRegistry().register(new AnvilTextureCreator().setRegistryName(References.InternalNames.TextureCreation.TCN_ANVIL));
    }
}
