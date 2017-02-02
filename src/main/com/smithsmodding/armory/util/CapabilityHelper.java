package com.smithsmodding.armory.util;

import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.common.armor.IMultiComponentArmorExtension;
import com.smithsmodding.armory.api.common.capability.IArmorComponentStackCapability;
import com.smithsmodding.armory.api.common.capability.IHeatableObjectCapability;
import com.smithsmodding.armory.api.common.heatable.IHeatableObjectWrapper;
import com.smithsmodding.armory.api.common.heatable.IHeatedObjectType;
import com.smithsmodding.armory.api.common.material.core.IMaterial;
import com.smithsmodding.armory.api.common.material.core.RegistryMaterialWrapper;
import com.smithsmodding.armory.api.util.references.ModCapabilities;
import com.smithsmodding.armory.api.util.references.ModHeatableObjects;
import com.smithsmodding.smithscore.common.capability.SmithsCoreCapabilityDispatcher;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.FMLControlledNamespacedRegistry;

/**
 * Created by marcf on 1/24/2017.
 */
public class CapabilityHelper {

    public static ItemStack generateMaterializedStack(Item item, IMaterial material, Integer count) {
        if (!(item instanceof IHeatableObjectWrapper))
            throw new IllegalArgumentException("Not a wrapper item");

        IHeatableObjectWrapper wrapperItem = (IHeatableObjectWrapper) item;

        return forceGenerateMaterializedStack(item, material, count, wrapperItem.getHeatableObjectType());
    }

    public static ItemStack generateMaterializedStack(Block block, IMaterial material, Integer count) {
        if (!(block instanceof IHeatableObjectWrapper))
            throw new IllegalArgumentException("Not a wrapper block");

        IHeatableObjectWrapper wrapperBlock = (IHeatableObjectWrapper) block;

        return forceGenerateMaterializedStack(block, material, count, wrapperBlock.getHeatableObjectType());
    }

    public static ItemStack forceGenerateMaterializedStack(Item item, IMaterial material, Integer count, IHeatedObjectType type) {
        final FMLControlledNamespacedRegistry<RegistryMaterialWrapper> controlledNamespacedRegistry = (FMLControlledNamespacedRegistry<RegistryMaterialWrapper>) IArmoryAPI.Holder.getInstance().getRegistryManager().getCombinedMaterialRegistry();

        ItemStack stack = new ItemStack(item, count, controlledNamespacedRegistry.getId(material.getRegistryName()));
        IHeatableObjectCapability capability = new IHeatableObjectCapability.Impl()
                .setMaterial(material)
                .setObject(ModHeatableObjects.ITEMSTACK)
                .setType(type);

        stack.getCapability(SmithsCoreCapabilityDispatcher.INSTANCE_CAPABILITY, null).getDispatcher()
                .registerCapability(ModCapabilities.MOD_MATERIALIZEDSSTACK_CAPABIITY, capability);
        stack.getCapability(SmithsCoreCapabilityDispatcher.INSTANCE_CAPABILITY, null).getDispatcher()
                .registerCapability(ModCapabilities.MOD_HEATABLEOBJECT_CAPABILITY, capability);

        return stack;
    }


    public static ItemStack forceGenerateMaterializedStack(Block block, IMaterial material, Integer count, IHeatedObjectType type) {
        final FMLControlledNamespacedRegistry<RegistryMaterialWrapper> controlledNamespacedRegistry = (FMLControlledNamespacedRegistry<RegistryMaterialWrapper>) IArmoryAPI.Holder.getInstance().getRegistryManager().getCombinedMaterialRegistry();

        ItemStack stack = new ItemStack(block, count, controlledNamespacedRegistry.getId(material.getRegistryName()));
        IHeatableObjectCapability capability = new IHeatableObjectCapability.Impl()
                .setMaterial(material)
                .setObject(ModHeatableObjects.ITEMSTACK)
                .setType(type);

        stack.getCapability(SmithsCoreCapabilityDispatcher.INSTANCE_CAPABILITY, null).getDispatcher()
                .registerCapability(ModCapabilities.MOD_MATERIALIZEDSSTACK_CAPABIITY, capability);
        stack.getCapability(SmithsCoreCapabilityDispatcher.INSTANCE_CAPABILITY, null).getDispatcher()
                .registerCapability(ModCapabilities.MOD_HEATABLEOBJECT_CAPABILITY, capability);

        return stack;
    }


    public static IMaterial getMaterialFromMaterializedStack(ItemStack stack) {
        if (!stack.hasCapability(ModCapabilities.MOD_HEATABLEOBJECT_CAPABILITY, null))
            return null;

        return stack.getCapability(ModCapabilities.MOD_HEATABLEOBJECT_CAPABILITY, null).getMaterial();
    }

    public static ItemStack generateArmorComponentStack(Item item, IMultiComponentArmorExtension extension) {
        ItemStack stack = new ItemStack(item);

        IArmorComponentStackCapability capability = new IArmorComponentStackCapability.Impl()
                .setExtension(extension);

        stack.getCapability(SmithsCoreCapabilityDispatcher.INSTANCE_CAPABILITY, null).getDispatcher()
                .registerCapability(ModCapabilities.MOD_ARMORCOMPONENT_CAPABILITY, capability);

        return  stack;
    }

    public static IMultiComponentArmorExtension getExtensionFromArmorComponentStack(ItemStack stack) {
        if (!stack.hasCapability(ModCapabilities.MOD_ARMORCOMPONENT_CAPABILITY, null))
            return null;

        return stack.getCapability(ModCapabilities.MOD_ARMORCOMPONENT_CAPABILITY, null).getExtension();
    }
}
