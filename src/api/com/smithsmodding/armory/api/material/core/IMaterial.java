package com.smithsmodding.armory.api.material.core;

import com.smithsmodding.armory.api.model.renderinfo.IRenderInfoProvider;
import com.smithsmodding.armory.api.util.client.ITranslateable;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by marcf on 1/9/2017.
 */
public interface IMaterial<T> extends IForgeRegistryEntry<T>, IRenderInfoProvider<T>, ITranslateable {

    /**
     * Method to get the Identifier inside the OreDictionary for a Material.
     * @return The String that identifies this material in the OreDictionary. IE: Iron, Stone etc.
     */
    @Nullable
    String getOreDictionaryIdentifier();

    /**
     * Getter for the Melting Point of this material.
     * @return The melting point of this material.
     */
    @Nonnull
    Float getMeltingPoint();

    /**
     * Getter for the VaporizingPoint of this material.
     * @return The vaporizing point og this material.
     */
    @Nonnull
    Float getVaporizingPoint();

}
