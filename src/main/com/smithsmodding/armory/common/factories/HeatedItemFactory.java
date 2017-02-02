package com.smithsmodding.armory.common.factories;
/*
/  HeatedItemFactory
/  Created by : Orion
/  Created on : 03/10/2014
*/

import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.common.capability.IHeatableObjectCapability;
import com.smithsmodding.armory.api.common.capability.IHeatedObjectCapability;
import com.smithsmodding.armory.api.common.factories.IHeatedItemFactory;
import com.smithsmodding.armory.api.common.heatable.IHeatableObject;
import com.smithsmodding.armory.api.common.heatable.IHeatedObjectType;
import com.smithsmodding.armory.api.common.material.core.IMaterial;
import com.smithsmodding.armory.api.util.references.ModCapabilities;
import com.smithsmodding.armory.api.util.references.ModItems;
import com.smithsmodding.smithscore.common.capability.SmithsCoreCapabilityDispatcher;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class HeatedItemFactory implements IHeatedItemFactory {
    @Nullable
    private static HeatedItemFactory INSTANCE = null;

    @Nullable
    public static HeatedItemFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HeatedItemFactory();
        }

        return INSTANCE;
    }

    @Override
    @Nullable
    public ItemStack generateHeatedItemFromMaterial(IMaterial material, IHeatableObject object, IHeatedObjectType type, float temp) {
        ItemStack originalStack = type.generateItemStackForMaterial(material);
        SmithsCoreCapabilityDispatcher originalCapDispatcher = originalStack.getCapability(SmithsCoreCapabilityDispatcher.INSTANCE_CAPABILITY, null).getDispatcher();

        IHeatableObjectCapability heatableObjectCapability = new IHeatableObjectCapability.Impl()
                .setObject(object)
                .setMaterial(material)
                .setType(type);
        originalCapDispatcher.registerCapability(ModCapabilities.MOD_HEATABLEOBJECT_CAPABILITY, heatableObjectCapability);

        return convertToHeatedIngot(originalStack, temp);
    }

    @Override
    @Nonnull
    public ItemStack convertToHeatedIngot(@Nonnull ItemStack originalStack) {
        return convertToHeatedIngot(originalStack, 20F);
    }

    @Override
    @Nonnull
    public ItemStack convertToHeatedIngot(@Nonnull ItemStack originalStack, float temp) {
        if (!originalStack.hasCapability(ModCapabilities.MOD_HEATABLEOBJECT_CAPABILITY, null) ||
                IArmoryAPI.Holder.getInstance().getHelpers().getHeatableOverrideManager().isOverride(originalStack))
            return originalStack;

        ItemStack createdStack = new ItemStack(ModItems.IT_HEATEDITEM, 1);
        SmithsCoreCapabilityDispatcher createdStackCapDispatcher = createdStack.getCapability(SmithsCoreCapabilityDispatcher.INSTANCE_CAPABILITY, null).getDispatcher();

        IHeatedObjectCapability heatedObjectCapability = wrapHeatableData(originalStack.getCapability(ModCapabilities.MOD_HEATABLEOBJECT_CAPABILITY, null))
                .setTemperatur(temp)
                .setOriginalStack(originalStack);
        createdStackCapDispatcher.registerCapability(ModCapabilities.MOD_HEATEDOBJECT_CAPABILITY, heatedObjectCapability);

        return createdStack;
    }

    @Override
    @Nonnull
    public ItemStack convertToCooledIngot(@Nonnull ItemStack heatedStack) {
        if (!heatedStack.hasCapability(ModCapabilities.MOD_HEATEDOBJECT_CAPABILITY, null))
            throw new IllegalArgumentException("cooledStack is not Heatable");

        return heatedStack.getCapability(ModCapabilities.MOD_HEATEDOBJECT_CAPABILITY, null).getOriginalStack();
    }

    private IHeatedObjectCapability wrapHeatableData(IHeatableObjectCapability heatable) {
        return new IHeatedObjectCapability.Impl().setType(heatable.getType()).setObject(heatable.getObject()).setMaterial(heatable.getMaterial());
    }

}

