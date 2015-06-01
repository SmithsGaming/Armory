package com.Orion.Armory.API.Events.Common;
/*
*   ModifyMaterialEvent
*   Created by: Orion
*   Created on: 13-4-2014
*/

import com.Orion.Armory.API.Armor.IArmorMaterial;
import com.Orion.Armory.API.Armor.MLAAddon;
import com.Orion.Armory.API.Armor.MultiLayeredArmor;
import com.Orion.Armory.Util.Client.Textures;
import cpw.mods.fml.common.eventhandler.Event;

public class ModifyMaterialEvent extends Event
{
    public IArmorMaterial iArmorMaterial;
    public MultiLayeredArmor iArmor;

    public ModifyMaterialEvent(IArmorMaterial pMaterial, MultiLayeredArmor pArmor)
    {
        iArmorMaterial = pMaterial;
        iArmor = pArmor;
    }

}
