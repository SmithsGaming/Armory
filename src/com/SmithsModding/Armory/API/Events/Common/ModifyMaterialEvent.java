package com.smithsmodding.Armory.API.Events.Common;
/*
*   ModifyMaterialEvent
*   Created by: Orion
*   Created on: 13-4-2014
*/

import com.smithsmodding.Armory.API.Armor.*;
import com.smithsmodding.Armory.API.Materials.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class ModifyMaterialEvent extends Event {
    public IArmorMaterial iArmorMaterial;
    public MultiLayeredArmor iArmor;

    public ModifyMaterialEvent(IArmorMaterial pMaterial, MultiLayeredArmor pArmor) {
        iArmorMaterial = pMaterial;
        iArmor = pArmor;
    }

}
