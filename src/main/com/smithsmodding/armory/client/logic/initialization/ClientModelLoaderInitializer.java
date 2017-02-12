package com.smithsmodding.armory.client.logic.initialization;

import com.smithsmodding.armory.api.common.initialization.IInitializationComponent;
import com.smithsmodding.armory.client.model.loaders.*;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/25/2017.
 */
public class ClientModelLoaderInitializer extends IInitializationComponent.Impl {

    @Nonnull
    private static ArmorComponentModelLoader armorComponentModelLoader = new ArmorComponentModelLoader();
    @Nonnull
    private static MultiLayeredArmorModelLoader multiLayeredArmorModelLoader = new MultiLayeredArmorModelLoader();
    @Nonnull
    private static HeatedItemModelLoader heatedItemModelLoader = new HeatedItemModelLoader();
    @Nonnull
    private static AnvilModelLoader anvilBlockModelLoader = new AnvilModelLoader();
    @Nonnull
    private static MaterializedItemModelLoader materializedItemModelLoader = new MaterializedItemModelLoader();

    private static final ClientModelLoaderInitializer INSTANCE = new ClientModelLoaderInitializer();

    public static ClientModelLoaderInitializer getInstance() {
        return INSTANCE;
    }

    private ClientModelLoaderInitializer() {
    }

    @Override
    public void onPreInit(@Nonnull FMLPreInitializationEvent preInitializationEvent) {
        ModelLoaderRegistry.registerLoader(multiLayeredArmorModelLoader);
        ModelLoaderRegistry.registerLoader(heatedItemModelLoader);
        ModelLoaderRegistry.registerLoader(anvilBlockModelLoader);
        ModelLoaderRegistry.registerLoader(armorComponentModelLoader);
        ModelLoaderRegistry.registerLoader(materializedItemModelLoader);
    }
}
