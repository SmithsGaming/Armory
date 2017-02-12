package com.smithsmodding.armory.common.heatable;

import com.smithsmodding.armory.api.common.capability.IHeatedObjectCapability;
import com.smithsmodding.armory.api.common.heatable.IHeatableObject;
import net.minecraft.entity.Entity;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/26/2017.
 */
public class HeatableObject extends IHeatableObject.Impl<IHeatableObject> implements IHeatableObject {


    /**
     * Method to getCreationRecipe the change factor while an instance of this HeatableObject is
     * in ItemEntity form. Its internal temperature changes with this factor.
     *
     * @param biome    The biome the ItemEntity is in.
     * @param instance The capability stored on the stack in the ItemEntity, which can be seen as
     *                 the instance of the Object that is being changed.
     * @return The change that should be applied to the Stack inside the ItemEntity.
     */
    @Nonnull
    @Override
    public Float getChangeFactorForBiome(Biome biome, IHeatedObjectCapability instance) {
        return -1* instance.getMaterial().getHeatCoefficient();
    }

    /**
     * Method to getCreationRecipe the change factor while an instance of this HeatableObject is
     * in an Inventory of an Entity. Its internal temperature with this factor.
     *
     * @param entity   The entity in whoms inventory this ItemStack is in.
     * @param instance The capability stored on the stack in the inventory, which can be seen as
     *                 the instance of the Object that is being changed.
     * @return The change that should be applied to the Stack inside the Inventory of the Entity.
     */
    @Nonnull
    @Override
    public Float getChangeFactorForEntity(Entity entity, IHeatedObjectCapability instance) {
        return -1* instance.getMaterial().getHeatCoefficient();
    }
}
