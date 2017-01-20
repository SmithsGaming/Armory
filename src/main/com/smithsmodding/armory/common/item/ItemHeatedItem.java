package com.smithsmodding.armory.common.item;
/*
/  ItemHeatedItem
/  Created by : Orion
/  Created on : 03/10/2014
*/

import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.capability.IHeatedObjectCapability;
import com.smithsmodding.armory.api.heatable.IHeatableObject;
import com.smithsmodding.armory.api.heatable.IHeatableObjectType;
import com.smithsmodding.armory.api.material.core.IMaterial;
import com.smithsmodding.armory.api.util.client.TranslationKeys;
import com.smithsmodding.armory.api.util.references.*;
import com.smithsmodding.armory.common.config.ArmoryConfig;
import com.smithsmodding.armory.common.factories.HeatedItemFactory;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class ItemHeatedItem extends Item {

    public ItemHeatedItem() {
        setMaxStackSize(1);
        setCreativeTab(ModCreativeTabs.heatedItemTab);
        setUnlocalizedName(References.InternalNames.Items.ItemHeatedIngot);
        this.setRegistryName(References.General.MOD_ID.toLowerCase(), References.InternalNames.Items.ItemHeatedIngot);
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
        HashMap<String, ItemStack> heatedItems = new HashMap<>();

        for (IHeatableObjectType type : IArmoryAPI.Holder.getInstance().getRegistryManager().getHeatableObjectTypeRegistry()) {
            IArmoryAPI.Holder.getInstance().getRegistryManager().getCoreMaterialRegistry().forEach(new MaterialItemStackConstructionConsumer(type, heatedItems));
            IArmoryAPI.Holder.getInstance().getRegistryManager().getAddonArmorMaterialRegistry().forEach(new MaterialItemStackConstructionConsumer(type, heatedItems));
            IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilMaterialRegistry().forEach(new MaterialItemStackConstructionConsumer(type, heatedItems));
        }
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

        private final IHeatableObjectType type;
        private final HashMap<String, ItemStack> heatedStacks;

        private MaterialItemStackConstructionConsumer(IHeatableObjectType type, HashMap<String, ItemStack> heatedStacks) {
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
            if (!heatedStacks.containsKey(material.getOreDictionaryIdentifier()))
                heatedStacks.put(material.getOreDictionaryIdentifier(), IArmoryAPI.Holder.getInstance().getHelpers().getFactories().getHeatedItemFactory().generateHeatedItemFromMaterial(material, ModHeatableObjects.ITEMSTACK, type, material.getMeltingPoint() / 3));
        }
    }
}

