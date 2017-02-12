package com.smithsmodding.armory.api.heatable;

import com.smithsmodding.armory.api.material.core.IMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/7/2017.
 */
public interface IHeatableObjectType extends IForgeRegistryEntry<IHeatableObjectType> {

    /**
     * Method to get the amount in millibuckets that this HeatableObjectType produces when it melts.
     * @return The amount in millibuckets produced when an HeatableObject of this type melts.
     */
    @Nonnull
    Integer getMoltenAmount();

    /**
     * Method to generate an instance of this HeatableObjectType for a given material.
     * @param material The Material to generate the Stack for.
     * @return An ItemStack with a HeatableObject of this type.
     * @throws IllegalArgumentException Thrown when this ObjectType is not capable of generating an instance.
     */
    @Nonnull
    ItemStack generateItemStackForMaterial(IMaterial material) throws IllegalArgumentException;
}
