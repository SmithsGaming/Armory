package com.SmithsModding.Armory.Util.Armor;
/*
 *   NBTHelper
 *   Created by: Orion
 *   Created on: 14-9-2014
 */

import com.SmithsModding.Armory.API.Armor.ArmorAddonPosition;
import com.SmithsModding.Armory.API.Armor.MLAAddon;
import com.SmithsModding.Armory.API.Armor.MultiLayeredArmor;
import com.SmithsModding.Armory.API.Materials.IArmorMaterial;
import com.SmithsModding.Armory.Common.Addons.ArmorUpgradeMedieval;
import com.SmithsModding.Armory.Common.Addons.MedievalAddonRegistry;
import com.SmithsModding.Armory.Common.Material.MaterialRegistry;
import com.SmithsModding.Armory.Util.References;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NBTHelper {
    public static HashMap<MLAAddon, Integer> getAddonMap(ItemStack pItemStack) {
        HashMap<MLAAddon, Integer> tAddonMap = new HashMap<MLAAddon, Integer>();
        NBTTagCompound tStackCompound = pItemStack.getTagCompound();
        String tTier = tStackCompound.getCompoundTag(References.NBTTagCompoundData.ArmorData).getString(References.NBTTagCompoundData.Armor.ArmorTier);
        NBTTagCompound tAddonsCompound = tStackCompound.getCompoundTag(References.NBTTagCompoundData.InstalledAddons);

        for (ArmorAddonPosition tPossiblePosition : (((MultiLayeredArmor) pItemStack.getItem()).getAllowedPositions())) {
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
            tPositionCompound.setString(References.NBTTagCompoundData.Addons.AddonID, entry.getKey().getInternalName());
            tPositionCompound.setString(References.NBTTagCompoundData.Addons.ParentID, entry.getKey().getParentName());
            tPositionCompound.setString(References.NBTTagCompoundData.Addons.AddonPositionID, entry.getKey().getAddonPositionID());
            tPositionCompound.setInteger(References.NBTTagCompoundData.Addons.AddonInstalledAmount, entry.getValue());
            tPositionCompound.setInteger(References.NBTTagCompoundData.Addons.AddonMaxInstalledAmount, entry.getKey().getMaxInstalledAmount());

            tAddonsCompound.setTag(entry.getKey().getAddonPositionID(), tPositionCompound);
        }

        return tAddonsCompound;
    }

    public static NBTTagList createRenderTagList(String pMaterialInternalName, HashMap<MLAAddon, Integer> pAddonsMap) {
        NBTTagList tRenderList = new NBTTagList();
        tRenderList.appendTag(new NBTTagString(pMaterialInternalName));
        for (MLAAddon tAddon : pAddonsMap.keySet()) {
            tRenderList.appendTag(new NBTTagString(tAddon.getResource().getInternalName()));
        }

        return tRenderList;
    }

    public static String getRegisteredInternalName(ItemStack pItemStack) {
        return pItemStack.getTagCompound().getCompoundTag(References.NBTTagCompoundData.ArmorData).getString(References.NBTTagCompoundData.Armor.ArmorID);
    }

    public static String getArmorBaseMaterialName(ItemStack pItemStack) {
        return pItemStack.getTagCompound().getCompoundTag(References.NBTTagCompoundData.ArmorData).getString(References.NBTTagCompoundData.Armor.MaterialID);
    }

    public static HashMap<ArmorUpgradeMedieval, Integer> getInstalledArmorMedievalUpgradesOnItemStack(ItemStack pBaseArmor) {
        HashMap<MLAAddon, Integer> tInstalledAddons = NBTHelper.getAddonMap(pBaseArmor);
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
}
