/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.armory.common.item;

import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.common.capability.IMaterializedStackCapability;
import com.smithsmodding.armory.api.common.material.core.RegistryMaterialWrapper;
import com.smithsmodding.armory.api.util.references.ModCapabilities;
import com.smithsmodding.armory.util.CapabilityHelper;
import com.smithsmodding.smithscore.client.proxy.CoreClientProxy;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.HashMap;

public abstract class ItemHeatableResource extends Item implements com.smithsmodding.armory.api.common.heatable.IHeatableObjectWrapperItem {

    @Override
    @SideOnly(Side.CLIENT)
    public FontRenderer getFontRenderer(ItemStack stack) {
        return CoreClientProxy.getMultiColoredFontRenderer();
    }

    @Nonnull
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if (!stack.hasCapability(ModCapabilities.MOD_MATERIALIZEDSSTACK_CAPABIITY, null))
            return super.getItemStackDisplayName(stack);

        IMaterializedStackCapability capability = stack.getCapability(ModCapabilities.MOD_MATERIALIZEDSSTACK_CAPABIITY, null);
        return capability.getMaterial().getTextFormatting() + I18n.translateToLocal(capability.getMaterial().getTranslationKey())
                + " " + I18n.translateToLocal(this.getUnlocalizedName() + ".name");
    }

    @Override
    public boolean getHasSubtypes() {
        return true;
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        HashMap<String, ItemStack> mappedOreDictionaryStacks = new HashMap<>();

        for(RegistryMaterialWrapper wrapper : IArmoryAPI.Holder.getInstance().getRegistryManager().getCombinedMaterialRegistry()) {
            if (mappedOreDictionaryStacks.containsKey(wrapper.getWrapped().getOreDictionaryIdentifier()))
                continue;

            ItemStack stack = CapabilityHelper.generateMaterializedStack(this, wrapper.getWrapped(), 1);

            mappedOreDictionaryStacks.put(wrapper.getWrapped().getOreDictionaryIdentifier(), stack);
        }

        subItems.addAll(mappedOreDictionaryStacks.values());
    }

}
