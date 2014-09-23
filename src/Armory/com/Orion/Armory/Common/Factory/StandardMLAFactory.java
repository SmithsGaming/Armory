package com.Orion.Armory.Common.Factory;
/*
/  StandardMLAFactory
/  Created by : Orion
/  Created on : 04/07/2014
*/

import com.Orion.Armory.Common.Armor.Core.MLAAddon;
import com.Orion.Armory.Common.Armor.Core.MultiLayeredArmor;
import com.Orion.Armory.Util.Armor.NBTHelper;
import com.Orion.Armory.Util.References;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class StandardMLAFactory implements IMLAFactory {
    public static final StandardMLAFactory iInstance = new StandardMLAFactory();

    @Override
    public ItemStack buildMLAArmor(MultiLayeredArmor pBaseArmor, ItemStack pBaseItemStack, HashMap<MLAAddon, Integer> pNewAddons, Integer pNewTotalDurability, String pInternalMaterialName, Object... pData) {
        HashMap<MLAAddon, Integer> tAddonMap = NBTHelper.getAddonMap(pBaseItemStack);
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
        tNewStackCompound.setTag(References.NBTTagCompoundData.InstalledAddons, NBTHelper.createAddonListCompound(tAddonMap));

        NBTTagCompound tNewDataCompound = new NBTTagCompound();
        tNewDataCompound.setInteger(References.NBTTagCompoundData.Armor.TotalDurability, pNewTotalDurability);
        tNewDataCompound.setInteger(References.NBTTagCompoundData.Armor.CurrentDurability, tOldCurrentDurability + (pNewTotalDurability - tOldTotalDurability));
        tNewDataCompound.setString(References.NBTTagCompoundData.Armor.MaterialID, pInternalMaterialName);
        tNewDataCompound.setInteger(References.NBTTagCompoundData.Armor.Addons, tAddonMap.size());
        tNewDataCompound.setInteger(References.NBTTagCompoundData.Armor.ArmorPart, pBaseArmor.getArmorPart());
        tNewDataCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, pBaseArmor.getInternalName());
        tNewStackCompound.setTag(References.NBTTagCompoundData.ArmorData, tNewDataCompound);

        NBTTagCompound tNewRenderCompound = new NBTTagCompound();
        NBTTagList tResourceList = NBTHelper.createRenderTagList(pInternalMaterialName, tAddonMap);
        tNewRenderCompound.setTag(References.NBTTagCompoundData.Rendering.ResourceIDs, tResourceList);
        tNewRenderCompound.setInteger(References.NBTTagCompoundData.Rendering.MaxRenderPasses, tResourceList.tagCount());
        tNewStackCompound.setTag(References.NBTTagCompoundData.RenderCompound, tNewRenderCompound);

        ItemStack tReturnItemStack = new ItemStack(pBaseArmor);
        tReturnItemStack.setTagCompound(tNewStackCompound);

        return tReturnItemStack;
    }

    @Override
    public ItemStack buildNewMLAArmor(MultiLayeredArmor pBaseArmor, HashMap<MLAAddon, Integer> pAddons, Integer pTotalDurability, String pInternalMaterialName, Object... pData) {
        if (!(validateNewAgainstNewAddons(pAddons)))
            return null;

        NBTTagCompound tNewStackCompound = new NBTTagCompound();
        tNewStackCompound.setTag(References.NBTTagCompoundData.InstalledAddons, NBTHelper.createAddonListCompound(pAddons));

        NBTTagCompound tNewDataCompound = new NBTTagCompound();
        tNewDataCompound.setInteger(References.NBTTagCompoundData.Armor.TotalDurability, pTotalDurability);
        tNewDataCompound.setInteger(References.NBTTagCompoundData.Armor.CurrentDurability, pTotalDurability);
        tNewDataCompound.setString(References.NBTTagCompoundData.Armor.MaterialID, pInternalMaterialName);
        tNewDataCompound.setInteger(References.NBTTagCompoundData.Armor.Addons, pAddons.size());
        tNewDataCompound.setInteger(References.NBTTagCompoundData.Armor.ArmorPart, pBaseArmor.getArmorPart());
        tNewDataCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, pBaseArmor.getInternalName());
        tNewStackCompound.setTag(References.NBTTagCompoundData.ArmorData, tNewDataCompound);

        NBTTagCompound tNewRenderCompound = new NBTTagCompound();
        NBTTagList tResourceList = NBTHelper.createRenderTagList(pInternalMaterialName, pAddons);
        tNewRenderCompound.setTag(References.NBTTagCompoundData.Rendering.ResourceIDs, tResourceList);
        tNewRenderCompound.setInteger(References.NBTTagCompoundData.Rendering.MaxRenderPasses, tResourceList.tagCount());
        tNewStackCompound.setTag(References.NBTTagCompoundData.RenderCompound, tNewRenderCompound);

        ItemStack tReturnItemStack = new ItemStack(pBaseArmor);
        tReturnItemStack.setTagCompound(tNewStackCompound);

        return tReturnItemStack;
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

                if (tInstalledAddon.getInternalName() == tNewAddon.getInternalName()) {
                    if (((Integer) tInstalledEntry.getValue() + (Integer) tNewEntry.getValue()) > tInstalledAddon.getMaxInstalledAmount()) {
                        tContinueCrafting = false;
                    }
                } else {
                    tContinueCrafting = (tInstalledAddon.validateCrafting(tNewAddon.getInternalName(), true) && tNewAddon.validateCrafting(tInstalledAddon.getInternalName(), false));
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
                tContinueCrafting = tExternalAddon.validateCrafting(tInternalAddon.getInternalName(), false);
            }
        }

        return tContinueCrafting;
    }

}
