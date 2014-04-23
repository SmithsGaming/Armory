package com.Orion.Armory.Client;
/*
*   AClientRegistry
*   Created by: Orion
*   Created on: 4-4-2014
*/

import com.Orion.Armory.Common.ARegistry;
import com.Orion.Armory.Common.Armor.ArmorCore;
import com.Orion.Armory.Common.Armor.ArmorMaterial;
import com.Orion.Armory.Common.Armor.ArmorUpgrade;
import com.Orion.Armory.Common.Armor.Modifiers.ArmorModifier;

public class AClientRegistry extends ARegistry
{
    public static void registerRenderMappings()
    {
        for(ArmorCore tArmor: iInstance.getAllArmorMappings())
        {
            for(ArmorMaterial tMaterial : iInstance.getArmorMaterials())
            {
                tArmor.registerResource(iInstance.getMaterialTextureID(tMaterial.iInternalName), tMaterial.getResource(tArmor.iArmorPart));

                for(ArmorUpgrade tUpgrade : iInstance.getUpgrades())
                {
                    tArmor.registerResource(iInstance.getUpgradeTextureID(tMaterial.iInternalName, tUpgrade.iInternalName), tUpgrade.getResource());
                }
            }

            for(ArmorModifier tModifier: iInstance.getModifiers())
            {
                tArmor.registerResource(iInstance.getModifierTextureID(tModifier.iInternalName), tModifier.getResource());
            }
        }
    }

}
