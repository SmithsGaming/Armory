package com.smithsmodding.armory.common.addons;

import com.smithsmodding.armory.api.armor.MLAAddon;
import com.smithsmodding.armory.api.armor.MaterialDependentMLAAddon;
import com.smithsmodding.armory.api.materials.IArmorMaterial;
import com.smithsmodding.armory.api.registries.IArmorPartRegistry;
import com.smithsmodding.armory.api.registries.IMLAAddonRegistry;
import com.smithsmodding.armory.common.material.MaterialRegistry;

import java.util.HashMap;

/**
 * Created by Orion
 * Created on 08.06.2015
 * 13:45
 * <p/>
 * Copyrighted according to Project specific license
 */
public class MedievalAddonRegistry implements IArmorPartRegistry, IMLAAddonRegistry {

    protected static MedievalAddonRegistry INSTANCE = new MedievalAddonRegistry();

    //ArrayList for storing all the modifiers and upgrades
    protected HashMap<String, MLAAddon> addonHashMap = new HashMap<String, MLAAddon>();

    protected HashMap<IArmorMaterial, HashMap<MLAAddon, Boolean>> upgradeStates = new HashMap<IArmorMaterial, HashMap<MLAAddon, Boolean>>();

    public static MedievalAddonRegistry getInstance() {
        return INSTANCE;
    }

    public HashMap<String, MLAAddon> getUpgrades() {
        HashMap<String, MLAAddon> tMedievalUpgrades = new HashMap<String, MLAAddon>();

        this.addonHashMap.values().stream().filter(tAddon -> tAddon instanceof ArmorUpgradeMedieval).forEachOrdered(tAddon -> {
            tMedievalUpgrades.put(tAddon.getUniqueID(), tAddon);
        });

        return tMedievalUpgrades;
    }

    @Override
    public MLAAddon getAddonForMaterialIndependantName(String addonId) {
        for (MLAAddon addon : getUpgrades().values())
            if (addon instanceof MaterialDependentMLAAddon && addon.isMaterialDependent() && ((MaterialDependentMLAAddon) addon).getMaterialIndependentID().equals(addonId))
                return addon;
            else if (addon.getUniqueID().equals(addonId))
                return addon;

        return null;
    }

    public void registerUpgrade(MLAAddon pUpgrade) {
        addonHashMap.put(pUpgrade.getUniqueID(), pUpgrade);

        MaterialRegistry.getInstance().getArmor(pUpgrade.getUniqueArmorID()).registerAddon(pUpgrade);
    }

    public MLAAddon getUpgrade (String pUpgradeInternalName) {
        return this.addonHashMap.get(pUpgradeInternalName);
    }

    @Override
    public void setPartStateForMaterial (IArmorMaterial material, MLAAddon addon, boolean state) {
        if (!upgradeStates.containsKey(material))
            upgradeStates.put(material, new HashMap<MLAAddon, Boolean>());

        upgradeStates.get(material).put(addon, state);
    }

    @Override
    public boolean getPartStateForMaterial (IArmorMaterial material, MLAAddon addon) {
        if (!upgradeStates.containsKey(material))
            upgradeStates.put(material, new HashMap<MLAAddon, Boolean>());

        if (!upgradeStates.get(material).containsKey(addon))
            upgradeStates.get(material).put(addon, false);

        return upgradeStates.get(material).get(addon);
    }
}
