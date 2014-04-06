package com.Orion.Armory.Common.Armor;
/*
*   ArmorHelmet
*   Created by: Orion
*   Created on: 4-4-2014
*/

public class ArmorHelmet extends ArmorCore

{
    public ArmorHelmet(int ArmorPart) {
        super(ArmorPart);
    }

    public String getModifierTextureSuffix(int pModifierID)
    {
        switch (pModifierID)
        {
            case 0: return "_Aqua_Affinity";
            case 1: return "_Aqua_Breathing";
            case 2: return "_Night_Sight";
            case 3: return "_Thorns";
            case 4: return "_Auto_Repair";
            case 5: return "_Reinforced";
            case 6: return "_Electric";
            default: return "";
        }
    }

    public String getUpgradeTextureSuffix(int pUpgradeID)
    {
        switch (pUpgradeID)
        {
            case 0: return "_tophead";
            case 1: return "_earProtection";
            default: return "";
        }
    }
}
