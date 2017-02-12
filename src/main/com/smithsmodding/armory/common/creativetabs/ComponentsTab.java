package com.smithsmodding.armory.common.creativetabs;

import com.smithsmodding.armory.api.common.capability.IArmorComponentStackCapability;
import com.smithsmodding.armory.api.util.client.TranslationKeys;
import com.smithsmodding.armory.api.util.references.ModItems;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.api.ArmoryAPI;
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

        //TODO!

        IArmorComponentStackCapability capability = new IArmorComponentStackCapability.Impl()
                .setExtension(ArmoryAPI.getInstance().getRegistryManager().getMultiComponentArmorExtensionRegistry().getValue(new ResourceLocation(References.General.MOD_ID.toLowerCase())));



        return stack;
    }
}
