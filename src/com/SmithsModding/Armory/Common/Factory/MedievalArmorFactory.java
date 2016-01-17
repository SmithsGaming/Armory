package com.smithsmodding.Armory.Common.Factory;
/*
/  StandardMLAFactory
/  Created by : Orion
/  Created on : 04/07/2014
*/

import com.smithsmodding.Armory.API.Armor.*;
import com.smithsmodding.Armory.Util.Armor.*;
import com.smithsmodding.Armory.Util.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;

import java.util.*;

public class MedievalArmorFactory implements IMLAFactory {
    private static MedievalArmorFactory iInstance;

    public static MedievalArmorFactory getInstance() {
        if (iInstance == null) iInstance = new MedievalArmorFactory();
        return iInstance;
    }

    @Override
    public ItemStack buildMLAArmor(MultiLayeredArmor pBaseArmor, ItemStack pBaseItemStack, HashMap<MLAAddon, Integer> pNewAddons, Integer pNewTotalDurability, String pInternalMaterialName, Object... pData) {
        HashMap<MLAAddon, Integer> tAddonMap = ArmorNBTHelper.getAddonMap(pBaseItemStack);
        Integer tOldTotalDurability = pBaseItemStack.getTagCompound().getCompoundTag(References.NBTTagCompoundData.ArmorData).getInteger(References.NBTTagCompoundData.Armor.TotalDurability);
        Integer tOldCurrentDurability = pBaseItemStack.getTagCompound().getCompoundTag(References.NBTTagCompoundData.ArmorData).getInteger(References.NBTTagCompoundData.Armor.CurrentDurability);

        if (!(validateOldAgainstNewAddons(tAddonMap, pNewAddons) && validateNewAgainstNewAddons(pNewAddons)))
            return null;

        Iterator tNewAddonIterator = pNewAddons.entrySet().iterator();
        while (tNewAddonIterator.hasNext()) {
            Map.Entry tNewEntry = (Map.Entry) tNewAddonIterator.next();
            MLAAddon tNewAddon = (MLAAddon) tNewEntry.getKey();

            if (tAddonMap.containsKey(tNewAddon)) {
                tAddonMap.put(tNewAddon, tAddonMap.get(tNewAddon) + (Integer) tNewEntry.getValue());
            } else {
                tAddonMap.put(tNewAddon, (Integer) tNewEntry.getValue());
            }
        }

        NBTTagCompound tNewStackCompound = new NBTTagCompound();
        tNewStackCompound.setTag(References.NBTTagCompoundData.InstalledAddons, ArmorNBTHelper.createAddonListCompound(tAddonMap));

        NBTTagCompound tNewDataCompound = new NBTTagCompound();
        tNewDataCompound.setInteger(References.NBTTagCompoundData.Armor.TotalDurability, pNewTotalDurability);
        tNewDataCompound.setInteger(References.NBTTagCompoundData.Armor.CurrentDurability, tOldCurrentDurability + (pNewTotalDurability - tOldTotalDurability));
        tNewDataCompound.setString(References.NBTTagCompoundData.Armor.MaterialID, pInternalMaterialName);
        tNewDataCompound.setInteger(References.NBTTagCompoundData.Armor.Addons, tAddonMap.size());
        tNewDataCompound.setInteger(References.NBTTagCompoundData.Armor.ArmorPart, pBaseArmor.getArmorIndex());
        tNewDataCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, pBaseArmor.getUniqueID());
        tNewDataCompound.setString(References.NBTTagCompoundData.Armor.ArmorTier, References.InternalNames.Tiers.MEDIEVAL);
        tNewStackCompound.setTag(References.NBTTagCompoundData.ArmorData, tNewDataCompound);

        ItemStack tReturnItemStack = new ItemStack(pBaseArmor);
        tReturnItemStack.setTagCompound(tNewStackCompound);

        return tReturnItemStack;
    }

    @Override
    public ItemStack buildNewMLAArmor(MultiLayeredArmor pBaseArmor, HashMap<MLAAddon, Integer> pAddons, Integer pTotalDurability, String pInternalMaterialName, Object... pData) {
        if (!(validateNewAgainstNewAddons(pAddons)))
            return null;

        HashMap<MLAAddon, Integer> completeList = new HashMap<MLAAddon, Integer>();
        completeList.put(pBaseArmor.getAddon(pBaseArmor.getUniqueID() + "-" + pInternalMaterialName), 1);
        completeList.putAll(pAddons);

        NBTTagCompound tNewStackCompound = new NBTTagCompound();
        tNewStackCompound.setTag(References.NBTTagCompoundData.InstalledAddons, ArmorNBTHelper.createAddonListCompound(completeList));

        NBTTagCompound tNewDataCompound = new NBTTagCompound();
        tNewDataCompound.setInteger(References.NBTTagCompoundData.Armor.TotalDurability, pTotalDurability);
        tNewDataCompound.setInteger(References.NBTTagCompoundData.Armor.CurrentDurability, pTotalDurability);
        tNewDataCompound.setString(References.NBTTagCompoundData.Armor.MaterialID, pInternalMaterialName);
        tNewDataCompound.setInteger(References.NBTTagCompoundData.Armor.Addons, pAddons.size());
        tNewDataCompound.setInteger(References.NBTTagCompoundData.Armor.ArmorPart, pBaseArmor.getArmorIndex());
        tNewDataCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, pBaseArmor.getUniqueID());
        tNewDataCompound.setString(References.NBTTagCompoundData.Armor.ArmorTier, References.InternalNames.Tiers.MEDIEVAL);
        tNewStackCompound.setTag(References.NBTTagCompoundData.ArmorData, tNewDataCompound);

        ItemStack tReturnItemStack = new ItemStack(pBaseArmor);
        tReturnItemStack.setTagCompound(tNewStackCompound);

        return tReturnItemStack;
    }

    @Override
    public String getArmorGivenName(ItemStack pStack) {
        return null;
    }

    private boolean validateOldAgainstNewAddons(HashMap<MLAAddon, Integer> pOldAddonMap, HashMap<MLAAddon, Integer> pNewAddonMap) {
        boolean tContinueCrafting = true;
        Iterator tInstalledIterator = pOldAddonMap.entrySet().iterator();

        while (tContinueCrafting && tInstalledIterator.hasNext()) {
            Map.Entry tInstalledEntry = (Map.Entry) tInstalledIterator.next();
            MLAAddon tInstalledAddon = (MLAAddon) tInstalledEntry.getKey();

            Iterator tNewIterator = pNewAddonMap.entrySet().iterator();
            while (tContinueCrafting && tNewIterator.hasNext()) {
                Map.Entry tNewEntry = (Map.Entry) tNewIterator.next();
                MLAAddon tNewAddon = (MLAAddon) tNewEntry.getKey();

                if (tInstalledAddon.getUniqueID() == tNewAddon.getUniqueID()) {
                    if (((Integer) tInstalledEntry.getValue() + (Integer) tNewEntry.getValue()) > tInstalledAddon.getMaxInstalledAmount()) {
                        tContinueCrafting = false;
                    }
                } else {
                    tContinueCrafting = (tInstalledAddon.validateCrafting(tNewAddon.getUniqueID(), true) && tNewAddon.validateCrafting(tInstalledAddon.getUniqueID(), false));
                }
            }
        }

        return tContinueCrafting;
    }

    private boolean validateNewAgainstNewAddons(HashMap<MLAAddon, Integer> pNewAddonMap) {
        boolean tContinueCrafting = true;
        Iterator tExternalIterator = (Iterator) pNewAddonMap.entrySet().iterator();

        while (tContinueCrafting && tExternalIterator.hasNext()) {
            Iterator tInternalIterator = (Iterator) pNewAddonMap.entrySet().iterator();
            MLAAddon tExternalAddon = ((MLAAddon) ((Map.Entry) tExternalIterator.next()).getKey());

            while (tContinueCrafting && tInternalIterator.hasNext()) {
                MLAAddon tInternalAddon = ((MLAAddon) ((Map.Entry) tInternalIterator.next()).getKey());
                tContinueCrafting = tExternalAddon.validateCrafting(tInternalAddon.getUniqueID(), false);
            }
        }

        return tContinueCrafting;
    }

}
