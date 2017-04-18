package com.smithsmodding.armory.api.common.material.core;

import com.smithsmodding.armory.api.client.model.renderinfo.IRenderInfoProvider;
import com.smithsmodding.armory.api.util.client.ITranslateable;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by marcf on 1/9/2017.
 */
public interface IMaterial<T> extends IForgeRegistryEntry<T>, IRenderInfoProvider<T>, ITranslateable {

    /**
     * Method to getCreationRecipe the Identifier inside the OreDictionary for a Material.
     * @return The String that identifies this material in the OreDictionary. IE: TK_ANVIL_IRON, TK_ANVIL_STONE etc.
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

    /**
     * Getter for the melting time of this Material
     * @return The melting time of this material
     */
    @Nonnull
    Integer getMeltingTime();

    /**
     * Getter for the vaporizing time of this Material
     * @return The vaporizing time of this material
     */
    @Nonnull
    Integer getVaporizingTime();

    /**
     * The heat coefficient of this material
     * @return the heat coefficient of this material.
     */
    @Nonnull
    Float getHeatCoefficient();

    /**
     * Method to get the fluid if this material is molten.
     * @return The fluid that represents this material.
     */
    @Nonnull
    Fluid getFluidForMaterial();

    /**
     * Setter for the fluid that represents this material.
     * @param fluid The new molten material.
     * @return The instance this was called upon.
     */
    @Nonnull
    T setFluidForMaterial(Fluid fluid);

}
