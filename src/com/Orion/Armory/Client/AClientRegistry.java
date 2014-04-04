package com.Orion.Armory.Client;
/*
*   AClientRegistry
*   Created by: Orion
*   Created on: 4-4-2014
*/

import com.Orion.Armory.Common.ARegistry;
import com.Orion.Armory.Common.Armor.ArmorCore;
import javafx.util.Pair;
import net.minecraft.item.ItemArmor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AClientRegistry extends ARegistry
{


    public void addRenderMappings()
    {
        for(ArmorCore tCore: instance.getAllArmorMappings())
        {
            int tMaterialCount = 0;

            Iterator iter = instance.armorMaterials.entrySet().iterator();
            while (iter.hasNext())
            {
                tMaterialCount++;

                Map.Entry<ItemArmor.ArmorMaterial, boolean[]> tMaterialEntry = (Map.Entry<ItemArmor.ArmorMaterial, boolean[]>) iter.next();
                if (tMaterialEntry.getValue()[0]==true)
                {
                    String tBaseTextureLocations[] = {"tconstruct-armory:multiamrmor/item/base/" + tMaterialEntry.getKey().name() + "_Base", "tconstruct-armory:models/multiamrmor/base/" + tMaterialEntry.getKey().name() + "_Base"};
                    tCore.registerBaseTexture(tMaterialCount, tBaseTextureLocations);
                }
                for(int i=1; i < tMaterialEntry.getValue().length; i++)
                {
                    String suffix = tCore.getUpgradeTextureSuffix(i-1);
                    if (!(suffix == "") && (tMaterialEntry.getValue()[i] == true))
                    {
                        String tUpgradeTextureLocations[] = {"tconstruct-armory:multiamrmor/item/upgrades/" + tMaterialEntry.getKey().name() + suffix, "tconstruct-armory:models/multiamrmor/upgrades/" + tMaterialEntry.getKey().name() + suffix};
                        tCore.registerUpgradeTexture((tMaterialCount-1)*this.armorUpgrades.length + i, tUpgradeTextureLocations);
                    }
                }
                for(int i = 1; i <= this.armorModifiers.length; i++)
                {
                   String suffix = tCore.getModifierTextureSuffix(i-1);
                   if (!(suffix == ""))
                   {
                       String tModifierTextureLocations[] = {"tconstruct-armory:multiamrmor/item/modifiers/" + tMaterialEntry.getKey().name() + suffix, "tconstruct-armory:models/multiamrmor/modifiers/" + tMaterialEntry.getKey().name() + suffix};
                       tCore.registerModifierTexture((tMaterialCount-1)*this.armorModifiers.length + i, tModifierTextureLocations);
                   }
                }
            }
        }
    }

}
