package com.smithsmodding.armory.common.item;
/*
/  ItemHeatedItem
/  Created by : Orion
/  Created on : 03/10/2014
*/

import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.common.capability.IHeatedObjectCapability;
import com.smithsmodding.armory.api.common.heatable.IHeatableObject;
import com.smithsmodding.armory.api.common.heatable.IHeatedObjectType;
import com.smithsmodding.armory.api.common.material.core.IMaterial;
import com.smithsmodding.armory.api.util.client.TranslationKeys;
import com.smithsmodding.armory.api.util.references.ModCapabilities;
import com.smithsmodding.armory.api.util.references.ModCreativeTabs;
import com.smithsmodding.armory.api.util.references.ModHeatableObjects;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.config.ArmoryConfig;
import com.smithsmodding.armory.common.factories.HeatedItemFactory;
import com.smithsmodding.smithscore.common.capability.SmithsCoreCapabilityDispatcher;
import com.smithsmodding.smithscore.util.CoreReferences;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class ItemHeatedItem extends Item {

    public ItemHeatedItem() {
        setMaxStackSize(1);
        setCreativeTab(ModCreativeTabs.HEATEDITEM);
        setUnlocalizedName(References.InternalNames.Items.IN_HEATEDINGOT);
        this.setRegistryName(References.General.MOD_ID.toLowerCase(), References.InternalNames.Items.IN_HEATEDINGOT);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return false;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        if (!stack.hasCapability(ModCapabilities.MOD_HEATEDOBJECT_CAPABILITY, null))
            return 0d;

        IHeatedObjectCapability capability = stack.getCapability(ModCapabilities.MOD_HEATEDOBJECT_CAPABILITY, null);

        return (capability.getTemperature() / capability.getMaterial().getMeltingPoint());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public FontRenderer getFontRenderer(@Nonnull ItemStack stack) {
        if (!stack.hasCapability(ModCapabilities.MOD_HEATEDOBJECT_CAPABILITY, null))
            return super.getFontRenderer(stack);

        IHeatedObjectCapability capability = stack.getCapability(ModCapabilities.MOD_HEATEDOBJECT_CAPABILITY, null);

        return capability.getOriginalStack().getItem().getFontRenderer(capability.getOriginalStack());
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, @Nonnull List<String> tooltip, boolean extraInformation) {
        if (!stack.hasCapability(ModCapabilities.MOD_HEATEDOBJECT_CAPABILITY, null))
            return;

        IHeatedObjectCapability capability = stack.getCapability(ModCapabilities.MOD_HEATEDOBJECT_CAPABILITY, null);

        String temperatureLine = I18n.format(TranslationKeys.Items.HeatedIngot.TemperatureTag);
        temperatureLine = temperatureLine + ": " + Math.round(capability.getTemperature());

        tooltip.add(temperatureLine);
    }

    @Override
    public boolean getHasSubtypes() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item item, CreativeTabs tabs, NonNullList<ItemStack> list) {
        if (!Loader.instance().hasReachedState(LoaderState.POSTINITIALIZATION))
            return;

        HashMap<String, ItemStack> heatedItems = new HashMap<>();

        for (IHeatedObjectType type : IArmoryAPI.Holder.getInstance().getRegistryManager().getHeatableObjectTypeRegistry()) {
            IArmoryAPI.Holder.getInstance().getRegistryManager().getCoreMaterialRegistry().forEach(new MaterialItemStackConstructionConsumer(type, heatedItems));
            IArmoryAPI.Holder.getInstance().getRegistryManager().getAddonArmorMaterialRegistry().forEach(new MaterialItemStackConstructionConsumer(type, heatedItems));
            IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilMaterialRegistry().forEach(new MaterialItemStackConstructionConsumer(type, heatedItems));
        }

        list.addAll(heatedItems.values());
    }

    @Nonnull
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if (!stack.hasCapability(ModCapabilities.MOD_HEATEDOBJECT_CAPABILITY, null))
            return "";

        IHeatedObjectCapability capability = stack.getCapability(ModCapabilities.MOD_HEATEDOBJECT_CAPABILITY, null);
        return capability.getOriginalStack().getItem().getItemStackDisplayName(capability.getOriginalStack());
    }

    @Override
    public void onUpdate(@Nonnull ItemStack stack, World worldObj, Entity entity, int slotIndex, boolean selected) {

        if (!(entity instanceof EntityPlayer))
            return;

        if (!ArmoryConfig.enableTemperatureDecay)
            return;

        EntityPlayer player = (EntityPlayer) entity;
        IHeatedObjectCapability capability = stack.getCapability(ModCapabilities.MOD_HEATEDOBJECT_CAPABILITY, null);
        IHeatableObject object = capability.getObject();

        capability.increaseTemperatur(object.getChangeFactorForEntity(player, capability));

        if (capability.getTemperature() < 20F) {
            player.inventory.mainInventory.set(slotIndex, HeatedItemFactory.getInstance().convertToCooledIngot(stack));
        } else {
            for (ItemStack inventoryStack : player.inventory.mainInventory) {
                if (inventoryStack.isEmpty())
                    continue;

                if (inventoryStack.getItem() instanceof ItemTongs)
                    return;
            }

            player.setFire(1);
        }
    }

    private class MaterialItemStackConstructionConsumer implements Consumer<IMaterial> {

        private final IHeatedObjectType type;
        private final HashMap<String, ItemStack> heatedStacks;

        private MaterialItemStackConstructionConsumer(IHeatedObjectType type, HashMap<String, ItemStack> heatedStacks) {
            this.type = type;
            this.heatedStacks = heatedStacks;
        }

        /**
         * Performs this operation on the given argument.
         *
         * @param material the input argument
         */
        @Override
        public void accept(IMaterial material) {
            if (!heatedStacks.containsKey(material.getOreDictionaryIdentifier() + "-" + type.getRegistryName().toString()))
                heatedStacks.put(material.getOreDictionaryIdentifier() + "-" + type.getRegistryName().toString(), IArmoryAPI.Holder.getInstance().getHelpers().getFactories().getHeatedItemFactory().generateHeatedItemFromMaterial(material, ModHeatableObjects.ITEMSTACK, type, material.getMeltingPoint() / 3));
        }
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
        internalParentDispatcher.registerNewInstance(ModCapabilities.MOD_HEATEDOBJECT_CAPABILITY);

        internalParentDispatcher.deserializeNBT(parentCompound);

        return internalParentDispatcher;
    }
}

