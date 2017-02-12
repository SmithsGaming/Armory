package com.smithsmodding.armory.api.util.common.armor;
/*
 *   ArmorNBTHelper
 *   Created by: Orion
 *   Created on: 14-9-2014
 */

import com.smithsmodding.armory.api.common.armor.IMultiComponentArmor;
import com.smithsmodding.armory.api.common.armor.IMultiComponentArmorExtensionInformation;
import com.smithsmodding.armory.api.common.capability.IMultiComponentArmorCapability;
import com.smithsmodding.armory.api.util.references.ModCapabilities;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.api.ArmoryAPI;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

public class ArmorNBTHelper {

    @Nullable
    public static IMultiComponentArmorCapability getArmorDataFromStack(@Nonnull ItemStack stack) {
        if (!stack.hasCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null))
            return null;

        return stack.getCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null);
    }

    @Nonnull
    public static IMultiComponentArmor getArmorFromStack(@Nonnull ItemStack stack) {
        if (!stack.hasCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null))
            return ArmoryAPI.getInstance().getRegistryManager().getMultiComponentArmorRegistry().getValue(new ResourceLocation(References.General.MOD_ID.toLowerCase(), References.InternalNames.Armor.MEDIEVALCHESTPLATE));

        return stack.getCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null).getArmorType();
    }

    @Nonnull
    public static ArrayList<IMultiComponentArmorExtensionInformation> getAddonMap(@Nonnull ItemStack stack) {
        if (!stack.hasCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null))
            return new ArrayList<>();

        return stack.getCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null).getInstalledExtensions();
    }

    @Nonnull
    public static NBTTagList createExtensionListCompound(@Nonnull ArrayList<IMultiComponentArmorExtensionInformation> extensions) {
        NBTTagList extensionList = new NBTTagList();

        for (IMultiComponentArmorExtensionInformation entry : extensions) {
            NBTTagCompound extensionCompound = new NBTTagCompound();
            extensionCompound.setString(References.NBTTagCompoundData.Addons.AddonID, entry.getExtension().getRegistryName().toString());
            extensionCompound.setString(References.NBTTagCompoundData.Addons.AddonPositionID, entry.getPosition().getRegistryName().toString());
            extensionCompound.setInteger(References.NBTTagCompoundData.Addons.AddonInstalledAmount, entry.getCount());

            extensionList.appendTag(extensionCompound);
        }

        return extensionList;
    }

    @Nonnull
    public static ArrayList<IMultiComponentArmorExtensionInformation> getExtensionMap(@Nonnull NBTTagList extensionList) {
        ArrayList<IMultiComponentArmorExtensionInformation> extensions = new ArrayList<>();

        for(int i = 0; i < extensionList.tagCount(); i ++) {
            NBTTagCompound compound = extensionList.getCompoundTagAt(i);

            extensions.add(new IMultiComponentArmorExtensionInformation.Impl()
                    .setExtension(ArmoryAPI.getInstance().getRegistryManager().getMultiComponentArmorExtensionRegistry().getValue(new ResourceLocation(compound.getString(References.NBTTagCompoundData.Addons.AddonID))))
                    .setPosition(ArmoryAPI.getInstance().getRegistryManager().getMultiComponentArmorExtensionPositionRegistry().getValue(new ResourceLocation(compound.getString(References.NBTTagCompoundData.Addons.AddonPositionID))))
                    .setCount(compound.getInteger(References.NBTTagCompoundData.Addons.AddonInstalledAmount)));
        }

        return extensions;
    }

    public static boolean checkIfStackIsBroken(@Nonnull ItemStack armorStack) {
        if (!armorStack.hasCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null))
            return false;

        return armorStack.getCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null).isBroken();
    }
}
