package com.smithsmodding.armory.api.events.common;
/*
*   ModifyMaterialEvent
*   Created by: Orion
*   Created on: 13-4-2014
*/

import com.smithsmodding.armory.api.armor.*;
import com.smithsmodding.armory.api.materials.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class ModifyMaterialEvent extends Event {
    public IArmorMaterial iArmorMaterial;
    public MultiLayeredArmor iArmor;

    public ModifyMaterialEvent(IArmorMaterial pMaterial, MultiLayeredArmor pArmor) {
        iArmorMaterial = pMaterial;
        iArmor = pArmor;
    }

}
