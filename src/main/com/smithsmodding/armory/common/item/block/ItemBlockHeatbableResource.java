package com.smithsmodding.armory.common.item.block;

import com.smithsmodding.armory.api.common.capability.IMaterializedStackCapability;
import com.smithsmodding.armory.api.util.references.ModCapabilities;
import com.smithsmodding.smithscore.common.capability.SmithsCoreCapabilityDispatcher;
import com.smithsmodding.smithscore.util.CoreReferences;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;

/**
 * Created by marcf on 2/2/2017.
 */
public class ItemBlockHeatbableResource extends ItemBlock {

    public ItemBlockHeatbableResource(Block block) {
        super(block);
        this.setRegistryName(block.getRegistryName());
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

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if (!stack.hasCapability(ModCapabilities.MOD_MATERIALIZEDSSTACK_CAPABIITY, null))
            return super.getItemStackDisplayName(stack);

        IMaterializedStackCapability capability = stack.getCapability(ModCapabilities.MOD_MATERIALIZEDSSTACK_CAPABIITY, null);
        return I18n.format(this.getUnlocalizedName() + ".name") + capability.getMaterial().getTextFormatting() + I18n.format(capability.getMaterial().getTranslationKey()) + TextFormatting.RESET;
    }
}
