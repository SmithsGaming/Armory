package com.Orion.Armory.Common.Armor;
/*
*   ArmorLeggings
*   Created by: Orion
*   Created on: 4-4-2014
*/

public class ArmorLeggings extends ArmorCore
{

    public ArmorLeggings(int ArmorPart) {
        super(ArmorPart);
    }

    @Override
    public String getModifierTextureSuffix(int pModifierID) {
        switch (pModifierID)
        {
            case 14: return "_Speed";
            case 15: return "_Jump_Assist";
            case 16: return "_UpHill_Assist";
            case 17: return "_Thorns";
            case 18: return "_Auto_Repair";
            case 19: return "_Reinforced";
            case 20: return "_Electric";
            default: return "";
        }
    }

    @Override
    public String getUpgradeTextureSuffix(int pUpgradeID) {
        switch (pUpgradeID)
        {
            case 5: return "_Legs_Front_Protection";
            case 6: return "_Legs_Back_Protection";
            default: return "";
        }
    }
}
