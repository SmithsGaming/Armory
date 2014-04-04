package com.Orion.Armory.Common.Armor;
/*
*   ArmorChestPlate
*   Created by: Orion
*   Created on: 4-4-2014
*/

public class ArmorChestPlate extends ArmorCore {
    public ArmorChestPlate(ArmorMaterial material, int ArmorPart) {
        super(material, ArmorPart);
    }

    @Override
    public String getModifierTextureSuffix(int pModifierID) {
        switch (pModifierID)
        {
            case 7: return "_Strength";
            case 8: return "_Haste";
            case 9: return "_Flying";
            case 10: return "_Thorns";
            case 11: return "_Auto_Repair";
            case 12: return "_Reinforced";
            case 13: return "_Electric";
            default: return "";
        }
    }

    @Override
    public String getUpgradeTextureSuffix(int pUpgradeID) {
        switch (pUpgradeID)
        {
            case 2: return "_Shoulder_Protection";
            case 3: return "_Front_Protection";
            case 4: return "_Back_Protection";
            default: return "";
        }
    }
}
