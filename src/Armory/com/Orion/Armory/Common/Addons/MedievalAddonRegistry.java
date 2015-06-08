package com.Orion.Armory.Common.Addons;

import com.Orion.Armory.API.Armor.MLAAddon;
import com.Orion.Armory.API.Registries.IMLAAddonRegistry;
import com.Orion.Armory.Common.Material.MaterialRegistry;

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

    public static MedievalAddonRegistry getInstance()
    {
        return iInstance;
    }

    public HashMap<String, MLAAddon> getUpgrades()
    {
        HashMap<String, MLAAddon> tMedievalUpgrades = new HashMap<String, MLAAddon>();

        for (MLAAddon tAddon : this.iArmorUpgrades.values())
        {
            if (tAddon instanceof ArmorUpgradeMedieval)
            {
                tMedievalUpgrades.put(tAddon.getInternalName(), tAddon);
            }
        }

        return tMedievalUpgrades;
    }

    public void registerUpgrade(MLAAddon pUpgrade)
    {
        iArmorUpgrades.put(pUpgrade.getInternalName(), pUpgrade);

        MaterialRegistry.getInstance().getArmor(pUpgrade.getParentName()).registerAddon(pUpgrade);
    }

    public ArmorUpgradeMedieval getUpgrade(String pUpgradeInternalName)
    {
        return (ArmorUpgradeMedieval) this.iArmorUpgrades.get(pUpgradeInternalName);
    }
}
