/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.armory.common.item;

import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.common.capability.IMaterializedStackCapability;
import com.smithsmodding.armory.api.common.heatable.IHeatableObjectWrapper;
import com.smithsmodding.armory.api.common.material.core.RegistryMaterialWrapper;
import com.smithsmodding.armory.api.util.references.ModCapabilities;
import com.smithsmodding.armory.util.CapabilityHelper;
import com.smithsmodding.smithscore.client.proxy.CoreClientProxy;
import com.smithsmodding.smithscore.common.capability.SmithsCoreCapabilityDispatcher;
import com.smithsmodding.smithscore.util.CoreReferences;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;

public abstract class ItemHeatableResource extends Item implements IHeatableObjectWrapper {

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

    /**
     * Called from ItemStack.setItem, will hold extra data for the life of this ItemStack.
     * Can be retrieved from stack.getCapabilities()
     * The NBT can be null if this is not called from readNBT or if the item the stack is
     * changing FROM is different then this item, or the previous item had no capabilities.
     * <p>
     * This is called BEFORE the stacks item is set so you can use stack.getItem() to see the OLD item.
     * Remember that getItem CAN return null.
     *
     * @param stack The ItemStack
     * @param nbt   NBT of this item serialized, or null.
     * @return A holder instance associated with this ItemStack where you can hold capabilities for the life of this item.
     */
    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        if (nbt == null || stack.getItem() == null)
            return null;

        NBTTagCompound parentCompound = nbt.getCompoundTag(new ResourceLocation(CoreReferences.General.MOD_ID.toLowerCase(), CoreReferences.CapabilityManager.DEFAULT).toString());

        SmithsCoreCapabilityDispatcher internalParentDispatcher = new SmithsCoreCapabilityDispatcher();
        internalParentDispatcher.registerNewInstance(ModCapabilities.MOD_HEATABLEOBJECT_CAPABILITY);
        internalParentDispatcher.registerNewInstance(ModCapabilities.MOD_MATERIALIZEDSSTACK_CAPABIITY);

        internalParentDispatcher.deserializeNBT(parentCompound);

        return internalParentDispatcher;
    }
}
