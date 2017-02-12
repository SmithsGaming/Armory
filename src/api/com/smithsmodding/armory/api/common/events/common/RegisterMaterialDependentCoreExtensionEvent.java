package com.smithsmodding.armory.api.common.events.common;

import com.smithsmodding.armory.api.common.armor.IMultiComponentArmorExtension;
import com.smithsmodding.smithscore.common.events.SmithsCoreEvent;
import net.minecraftforge.fml.common.registry.IForgeRegistry;

/**
 * Created by marcf on 1/24/2017.
 */
public class RegisterMaterialDependentCoreExtensionEvent extends SmithsCoreEvent {

    private final IForgeRegistry<IMultiComponentArmorExtension> registry;

    public RegisterMaterialDependentCoreExtensionEvent(IForgeRegistry<IMultiComponentArmorExtension> registry) {
        this.registry = registry;
    }

    public IForgeRegistry<IMultiComponentArmorExtension> getRegistry() {
        return registry;
    }
}
