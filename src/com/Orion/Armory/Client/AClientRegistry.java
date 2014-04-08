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

import java.util.Iterator;
import java.util.Map;

public class AClientRegistry extends ARegistry
{
    //TODO: Create the rendermapping system with the new Registry system.

    public void registerRenderMappings()
    {
        for(ArmorCore tArmor: iInstance.getAllArmorMappings())
        {
            for(ArmorMaterial tMaterial: iInstance.getArmorMaterials())
            {
                if (tMaterial.iBaseArmorMaterial)
                {
                    tArmor.registerBaseTexture(iInstance.getMaterialID(tMaterial), new String[] {"tconstruct-armory:multiarmor/base/"+tMaterial.iInternalName+"_base", "tconstruct-armory:models/multimarmor/base/"+tMaterial.iInternalName+"_base"});
                }

                for (ArmorUpgrade tUpgrade: iInstance.getUpgrades())
                {
                    if (tMaterial.iActiveParts.get(iInstance.getUpgradeID(tUpgrade)))
                    {
                        tArmor.registerUpgradeTexture(iInstance.getUpgradeTextureID(tMaterial.iInternalName, tUpgrade.iInternalName), new String[]{"tconstruct-armory:multiarmor/upgrades/"+tMaterial.iInternalName+iInstance.getUpgradeTextureSuffix(tUpgrade), "tconstruct-armory:models/multiarmor/upgrades/"+tMaterial.iInternalName+iInstance.getUpgradeTextureSuffix(tUpgrade)});
                    }
                }

                for (ArmorModifier tModifier: iInstance.getModifiers())
                {
                    if (tArmor.iArmorPart == tModifier.iTargetArmorID)
                    {
                        tArmor.registerModifierTexture(iInstance.getModifierTextureID(tMaterial.iInternalName, tModifier.iInternalName), new String[] {"tconstruct-armory:multiarmor/modifiers/"+tMaterial.iInternalName+iInstance.getModifierTextureSuffix(tModifier),"tconstruct-armory:models/multiarmor/modifiers/"+tMaterial.iInternalName+ iInstance.getModifierTextureSuffix(tModifier)});
                    }
                }
            }
        }
    }

}
