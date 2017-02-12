package com.smithsmodding.armory.api.common.heatable;

import com.smithsmodding.armory.api.common.capability.IHeatedObjectCapability;
import net.minecraft.entity.Entity;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/7/2017.
 */
public interface IHeatableObject extends IForgeRegistryEntry<IHeatableObject> {

    /**
     * Method to getCreationRecipe the change factor while an instance of this HeatableObject is
     * in ItemEntity form. Its internal temperature changes with this factor.
     * @param biome The biome the ItemEntity is in.
     * @param instance The capability stored on the stack in the ItemEntity, which can be seen as
     *                 the instance of the Object that is being changed.
     * @return The change that should be applied to the Stack inside the ItemEntity.
     */
    @Nonnull
    Float getChangeFactorForBiome(Biome biome, IHeatedObjectCapability instance);

    /**
     * Method to getCreationRecipe the change factor while an instance of this HeatableObject is
     * in an Inventory of an Entity. Its internal temperature with this factor.
     * @param entity The entity in whoms inventory this ItemStack is in.
     * @param instance The capability stored on the stack in the inventory, which can be seen as
     *                 the instance of the Object that is being changed.
     * @return The change that should be applied to the Stack inside the Inventory of the Entity.
     */
    @Nonnull
    Float getChangeFactorForEntity(Entity entity, IHeatedObjectCapability instance);
}
