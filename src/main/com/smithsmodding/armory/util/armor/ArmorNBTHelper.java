package com.smithsmodding.armory.util.armor;
/*
 *   ArmorNBTHelper
 *   Created by: Orion
 *   Created on: 14-9-2014
 */

import com.smithsmodding.armory.api.armor.ArmorAddonPosition;
import com.smithsmodding.armory.api.armor.MLAAddon;
import com.smithsmodding.armory.api.armor.MultiLayeredArmor;
import com.smithsmodding.armory.api.materials.IArmorMaterial;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.addons.ArmorUpgradeMedieval;
import com.smithsmodding.armory.common.registry.MaterialRegistry;
import com.smithsmodding.armory.common.registry.MedievalAddonRegistry;
import com.smithsmodding.smithscore.util.common.helper.NBTHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ArmorNBTHelper {
    @NotNull
    public static HashMap<MLAAddon, Integer> getAddonMap(@NotNull ItemStack pItemStack) {
        HashMap<MLAAddon, Integer> tAddonMap = new HashMap<MLAAddon, Integer>();
        NBTTagCompound tStackCompound = pItemStack.getTagCompound();
        String tTier = tStackCompound.getCompoundTag(References.NBTTagCompoundData.ArmorData).getString(References.NBTTagCompoundData.Armor.ArmorTier);
        NBTTagCompound tAddonsCompound = tStackCompound.getCompoundTag(References.NBTTagCompoundData.InstalledAddons);

        for (ArmorAddonPosition tPossiblePosition : (((MultiLayeredArmor) pItemStack.getItem()).getAllowedAddonPositions())) {
            NBTTagCompound tPositionCompound = tAddonsCompound.getCompoundTag(tPossiblePosition.getInternalName());

            if (!tPositionCompound.hasNoTags()) {
                tAddonMap.put(getMLAAddon(tPositionCompound.getString(References.NBTTagCompoundData.Addons.AddonID), tTier), tPositionCompound.getInteger(References.NBTTagCompoundData.Addons.AddonInstalledAmount));
            }
        }

        return tAddonMap;
    }

    public static MLAAddon getMLAAddon(String pAddonID, @NotNull String pTier) {
        if (pTier.equals(References.InternalNames.Tiers.MEDIEVAL)) {
            return MedievalAddonRegistry.getInstance().getUpgrade(pAddonID);
        } else {
            return null;
        }
    }

    @NotNull
    public static NBTTagCompound createAddonListCompound(@NotNull HashMap<MLAAddon, Integer> pAddonMap) {
        NBTTagCompound tAddonsCompound = new NBTTagCompound();

        for (Map.Entry<MLAAddon, Integer> entry : pAddonMap.entrySet()) {
            NBTTagCompound tPositionCompound = new NBTTagCompound();
            tPositionCompound.setString(References.NBTTagCompoundData.Addons.AddonID, entry.getKey().getUniqueID());
            tPositionCompound.setString(References.NBTTagCompoundData.Addons.ParentID, entry.getKey().getUniqueArmorID());
            tPositionCompound.setString(References.NBTTagCompoundData.Addons.AddonPositionID, entry.getKey().getAddonPositionID());
            tPositionCompound.setInteger(References.NBTTagCompoundData.Addons.AddonInstalledAmount, entry.getValue());
            tPositionCompound.setInteger(References.NBTTagCompoundData.Addons.AddonMaxInstalledAmount, entry.getKey().getMaxInstalledAmount());

            tAddonsCompound.setTag(entry.getKey().getAddonPositionID(), tPositionCompound);
        }

        return tAddonsCompound;
    }

    public static String getRegisteredInternalName(@NotNull ItemStack pItemStack) {
        return pItemStack.getTagCompound().getCompoundTag(References.NBTTagCompoundData.ArmorData).getString(References.NBTTagCompoundData.Armor.ArmorID);
    }

    public static String getArmorBaseMaterialName(@NotNull ItemStack pItemStack) {
        return pItemStack.getTagCompound().getCompoundTag(References.NBTTagCompoundData.ArmorData).getString(References.NBTTagCompoundData.Armor.MaterialID);
    }

    @NotNull
    public static HashMap<ArmorUpgradeMedieval, Integer> getInstalledArmorMedievalUpgradesOnItemStack(@NotNull ItemStack pBaseArmor) {
        HashMap<MLAAddon, Integer> tInstalledAddons = ArmorNBTHelper.getAddonMap(pBaseArmor);
        HashMap<ArmorUpgradeMedieval, Integer> tInstalledUpgrades = new HashMap<ArmorUpgradeMedieval, Integer>();
        Iterator tIterator = tInstalledAddons.entrySet().iterator();

        while (tIterator.hasNext()) {
            Map.Entry<MLAAddon, Integer> tEntry = (Map.Entry<MLAAddon, Integer>) tIterator.next();
            if (tEntry.getKey() instanceof ArmorUpgradeMedieval) {
                tInstalledUpgrades.put((ArmorUpgradeMedieval) tEntry.getKey(), tEntry.getValue());
            }
        }

        return tInstalledUpgrades;
    }

    public static IArmorMaterial getBaseMaterialOfItemStack(@NotNull ItemStack pArmorStack) {
        if (!(pArmorStack.getItem() instanceof MultiLayeredArmor))
            return null;

        return MaterialRegistry.getInstance().getMaterial(pArmorStack.getTagCompound().getCompoundTag(References.NBTTagCompoundData.ArmorData).getString(References.NBTTagCompoundData.Armor.MaterialID));
    }

    public static boolean checkIfStackIsBroken(@NotNull ItemStack armorStack) {
        return NBTHelper.getBoolean(armorStack, References.NBTTagCompoundData.Armor.IsBroken);
    }
}
