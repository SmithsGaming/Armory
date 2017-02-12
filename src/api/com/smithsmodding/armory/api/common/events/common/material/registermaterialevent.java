package com.smithsmodding.armory.api.common.events.common.material;

import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.common.helpers.IMaterialConstructionHelper;
import com.smithsmodding.smithscore.common.events.SmithsCoreEvent;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

/**
 * Created by marcf on 1/14/2017.
 */
public class RegisterMaterialEvent<T extends IForgeRegistryEntry<T>> extends SmithsCoreEvent {
    protected final IForgeRegistry<T> registry;

    public RegisterMaterialEvent(IForgeRegistry<T> registry) {
        this.registry = registry;
    }

    /**
     * Method to getCreationRecipe the registry for the ICoreArmorMaterial.
     * @return The registry for the ICoreArmorMaterial.
     */
    public IForgeRegistry<T> getRegistry() {
        return registry;
    }

    /**
     * Method to getCreationRecipe the construction helpers for materials inside Armory.
     * @return The IMaterialConstructionHelper used to create materials for Armory.
     */
    public IMaterialConstructionHelper getConstructionHelper() {
        return IArmoryAPI.Holder.getInstance().getHelpers().getMaterialConstructionHelper();
    }
}
