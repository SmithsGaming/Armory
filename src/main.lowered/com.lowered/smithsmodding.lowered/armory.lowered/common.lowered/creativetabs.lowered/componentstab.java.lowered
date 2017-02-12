package com.smithsmodding.armory.common.creativetabs;

import com.smithsmodding.armory.api.common.capability.IArmorComponentStackCapability;
import com.smithsmodding.armory.api.util.client.TranslationKeys;
import com.smithsmodding.armory.api.util.references.*;
import com.smithsmodding.armory.common.api.ArmoryAPI;
import com.smithsmodding.smithscore.common.capability.SmithsCoreCapabilityDispatcher;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Author Marc (Created on: 14.06.2016)
 */
public class ComponentsTab extends CreativeTabs {


    public ComponentsTab() {
        super(TranslationKeys.CreativeTabs.Components);
    }

    @Nonnull
    @Override
    public String getTranslatedTabLabel() {
        return getTabLabel();
    }

    @Override
    public ItemStack getTabIconItem() {
        ItemStack stack = new ItemStack(ModItems.IT_COMPONENT);

        IArmorComponentStackCapability capability = new IArmorComponentStackCapability.Impl()
                .setExtension(ArmoryAPI.getInstance().getRegistryManager().getMultiComponentArmorExtensionRegistry().getValue(new ResourceLocation(References.General.MOD_ID.toLowerCase(), ModExtensions.Medieval.ChestPlate.STOMACHLEFT.getRegistryName().getResourcePath() + "-" + ModMaterials.Armor.Addon.GOLD.getRegistryName().getResourcePath())));

        stack.getCapability(SmithsCoreCapabilityDispatcher.INSTANCE_CAPABILITY, null).getDispatcher().registerCapability(ModCapabilities.MOD_ARMORCOMPONENT_CAPABILITY, capability);

        return stack;
    }
}
