package com.SmithsModding.Armory.API.Events.Common;
/*
*   ModifyMaterialEvent
*   Created by: Orion
*   Created on: 13-4-2014
*/

import com.SmithsModding.Armory.API.Armor.MultiLayeredArmor;
import com.SmithsModding.Armory.API.Materials.IArmorMaterial;
import cpw.mods.fml.common.eventhandler.Event;

public class ModifyMaterialEvent extends Event {
    public IArmorMaterial iArmorMaterial;
    public MultiLayeredArmor iArmor;

    public ModifyMaterialEvent(IArmorMaterial pMaterial, MultiLayeredArmor pArmor) {
        iArmorMaterial = pMaterial;
        iArmor = pArmor;
    }

}
