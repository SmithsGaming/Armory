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
import com.smithsmodding.armory.api.util.common.Triple;
import com.smithsmodding.armory.api.util.references.ModCapabilities;
import com.smithsmodding.armory.api.util.references.ModItems;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.smithscore.common.capability.SmithsCoreCapabilityDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

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
        ItemStack originalStack;
        if (IArmoryAPI.Holder.getInstance().getHelpers().getHeatableOverrideManager().hasOverride(object, type, material))
            originalStack = IArmoryAPI.Holder.getInstance().getHelpers().getHeatableOverrideManager().getHeatedOverride(object, type, material);
        else
            originalStack = type.generateItemStackForMaterial(material);

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
        if (!originalStack.hasCapability(ModCapabilities.MOD_HEATABLEOBJECT_CAPABILITY, null) &&
                !IArmoryAPI.Holder.getInstance().getHelpers().getHeatableOverrideManager().isOverride(originalStack))
            return originalStack;

        ItemStack createdStack = new ItemStack(ModItems.IT_HEATEDITEM, 1);
        SmithsCoreCapabilityDispatcher createdStackCapDispatcher = createdStack.getCapability(SmithsCoreCapabilityDispatcher.INSTANCE_CAPABILITY, null).getDispatcher();

        IHeatedObjectCapability heatedObjectCapability = wrapHeatableData(originalStack)
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

    @Nonnull
    @Override
    public FluidStack generateFluid(@Nonnull IMaterial material, @Nonnull Integer amount) {
        NBTTagCompound fluidCompound = new NBTTagCompound();
        fluidCompound.setString(References.NBTTagCompoundData.Fluids.MoltenMetal.MATERIAL, material.getRegistryName().toString());
        return new FluidStack(material.getFluidForMaterial(), amount, fluidCompound);
    }

    private IHeatedObjectCapability wrapHeatableData(ItemStack stack) {
        if (stack.hasCapability(ModCapabilities.MOD_HEATABLEOBJECT_CAPABILITY, null))
            return wrapHeatableData(stack.getCapability(ModCapabilities.MOD_HEATABLEOBJECT_CAPABILITY, null));
        if (IArmoryAPI.Holder.getInstance().getHelpers().getHeatableOverrideManager().isOverride(stack)) {
            Triple<IHeatableObject, IHeatedObjectType, IMaterial> data = IArmoryAPI.Holder.getInstance().getHelpers().getHeatableOverrideManager().getStackData(stack);
            return wrapHeatableData(data.getSecond(), data.getFirst(), data.getThrid());
        }

        return null;
    }

    private IHeatedObjectCapability wrapHeatableData(IHeatableObjectCapability heatable) {
        return wrapHeatableData(heatable.getType(), heatable.getObject(), heatable.getMaterial());
    }

    private IHeatedObjectCapability wrapHeatableData(IHeatedObjectType type, IHeatableObject object, IMaterial heatable) {
        return new IHeatedObjectCapability.Impl().setType(type).setObject(object).setMaterial(heatable);
    }

}

