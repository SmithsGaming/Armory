package com.smithsmodding.armory.common.entity;

import com.smithsmodding.armory.api.common.capability.IHeatedObjectCapability;
import com.smithsmodding.armory.api.common.heatable.IHeatableObject;
import com.smithsmodding.armory.api.util.references.ModCapabilities;
import com.smithsmodding.armory.common.config.ArmoryConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

/**
 * Created by marcf on 1/13/2017.
 */
public class EntityItemHeatable extends EntityItem {

    public EntityItemHeatable(World worldIn, double x, double y, double z, ItemStack stack) {
        super(worldIn, x, y, z, stack);

        if (!stack.hasCapability(ModCapabilities.MOD_HEATEDOBJECT_CAPABILITY, null))
            throw new IllegalArgumentException("A heatable entity cannot be created from a non heated stack");
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate() {
        if (!ArmoryConfig.enableTemperatureDecay)
            return;

        ItemStack containedStack = getEntityItem();

        IHeatedObjectCapability capability = containedStack.getCapability(ModCapabilities.MOD_HEATEDOBJECT_CAPABILITY, null);
        IHeatableObject object = capability.getObject();

        capability.increaseTemperatur(object.getChangeFactorForBiome(getEntityWorld().getBiome(getPosition()), capability));

        if (capability.getTemperature() < 90F)
            return;

        AxisAlignedBB itemAreaBox5x5 = new AxisAlignedBB(getPosition().add(new Vec3i(-2, -2, -2)),
                getPosition().add(new Vec3i(2,2,2)));

        for(Entity entity : getEntityWorld().getEntitiesInAABBexcluding(this, itemAreaBox5x5, EntitySelectors.CAN_AI_TARGET)) {
            entity.setFire(1);
        }
    }
}
