package com.smithsmodding.armory.util.Armor;
/*
 *   ArmorNBTHelper
 *   Created by: Orion
 *   Created on: 14-9-2014
 */

import com.smithsmodding.armory.api.armor.*;
import com.smithsmodding.armory.api.materials.*;
import com.smithsmodding.armory.common.addons.*;
import com.smithsmodding.armory.common.material.*;
import com.smithsmodding.armory.util.*;
import com.smithsmodding.smithscore.util.common.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;

import java.util.*;

public class ArmorNBTHelper {
    public static HashMap<MLAAddon, Integer> getAddonMap(ItemStack pItemStack) {
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

    public static MLAAddon getMLAAddon(String pAddonID, String pTier) {
        if (pTier.equals(References.InternalNames.Tiers.MEDIEVAL)) {
            return MedievalAddonRegistry.getInstance().getUpgrade(pAddonID);
        } else {
            return null;
        }
    }

    public static NBTTagCompound createAddonListCompound(HashMap<MLAAddon, Integer> pAddonMap) {
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

    public static String getRegisteredInternalName(ItemStack pItemStack) {
        return pItemStack.getTagCompound().getCompoundTag(References.NBTTagCompoundData.ArmorData).getString(References.NBTTagCompoundData.Armor.ArmorID);
    }

    public static String getArmorBaseMaterialName(ItemStack pItemStack) {
        return pItemStack.getTagCompound().getCompoundTag(References.NBTTagCompoundData.ArmorData).getString(References.NBTTagCompoundData.Armor.MaterialID);
    }

    public static HashMap<ArmorUpgradeMedieval, Integer> getInstalledArmorMedievalUpgradesOnItemStack(ItemStack pBaseArmor) {
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

    public static IArmorMaterial getBaseMaterialOfItemStack(ItemStack pArmorStack) {
        if (!(pArmorStack.getItem() instanceof MultiLayeredArmor))
            return null;

        return MaterialRegistry.getInstance().getMaterial(pArmorStack.getTagCompound().getCompoundTag(References.NBTTagCompoundData.ArmorData).getString(References.NBTTagCompoundData.Armor.MaterialID));
    }

    public static boolean checkIfStackIsBroken (ItemStack armorStack) {
        return NBTHelper.getBoolean(armorStack, References.NBTTagCompoundData.Armor.IsBroken);
    }
}
