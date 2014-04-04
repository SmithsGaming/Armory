package com.Orion.Armory.Common.Armor;
/*
*   ArmorShoes
*   Created by: Orion
*   Created on: 4-4-2014
*/

public class ArmorShoes extends ArmorCore
{
    public ArmorShoes(ArmorMaterial material, int ArmorPart) {
        super(material, ArmorPart);
    }

    @Override
    public String getModifierTextureSuffix(int pModifierID) {
        switch (pModifierID)
        {
            case 21: return "_Fall_Assist";
            case 22: return "_Swimm_Assist";
            case 23: return "_Auto_Repair";
            case 24: return "_Reinforced";
            case 25: return "_Electric";
            default: return "";
        }
    }

    @Override
    public String getUpgradeTextureSuffix(int pUpgradeID) {
        switch (pUpgradeID)
        {
            case 7: return "_Shoe_Protection";
            default: return "";
        }
    }
}
