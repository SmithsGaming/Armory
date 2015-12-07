package com.SmithsModding.Armory.Common.Addons;

import com.SmithsModding.Armory.API.Armor.MLAAddon;
import com.SmithsModding.Armory.API.Registries.IMLAAddonRegistry;
import com.SmithsModding.Armory.Common.Material.MaterialRegistry;

import java.util.HashMap;

/**
 * Created by Orion
 * Created on 08.06.2015
 * 13:45
 * <p/>
 * Copyrighted according to Project specific license
 */
public class MedievalAddonRegistry implements IMLAAddonRegistry {

    protected static MedievalAddonRegistry iInstance = new MedievalAddonRegistry();
    //ArrayList for storing all the modifiers and upgrades
    protected HashMap<String, MLAAddon> iArmorUpgrades = new HashMap<String, MLAAddon>();

    public static MedievalAddonRegistry getInstance() {
        return iInstance;
    }

    public HashMap<String, MLAAddon> getUpgrades() {
        HashMap<String, MLAAddon> tMedievalUpgrades = new HashMap<String, MLAAddon>();

        for (MLAAddon tAddon : this.iArmorUpgrades.values()) {
            if (tAddon instanceof ArmorUpgradeMedieval) {
                tMedievalUpgrades.put(tAddon.getUniqueID(), tAddon);
            }
        }

        return tMedievalUpgrades;
    }

    public void registerUpgrade(MLAAddon pUpgrade) {
        iArmorUpgrades.put(pUpgrade.getUniqueID(), pUpgrade);

        MaterialRegistry.getInstance().getArmor(pUpgrade.getUniqueArmorID()).registerAddon(pUpgrade);
    }

    public MLAAddon getUpgrade (String pUpgradeInternalName) {
        return this.iArmorUpgrades.get(pUpgradeInternalName);
    }
}
