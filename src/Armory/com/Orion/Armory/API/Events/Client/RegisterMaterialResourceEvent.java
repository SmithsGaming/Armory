package com.Orion.Armory.API.Events.Client;

import com.Orion.Armory.API.Armor.IArmorMaterial;
import com.Orion.Armory.API.Armor.MultiLayeredArmor;
import com.Orion.Armory.Util.Client.GUI.MultiComponentTexture;
import cpw.mods.fml.common.eventhandler.Event;

/**
 * Created by Orion
 * Created on 01.06.2015
 * 12:57
 * <p/>
 * Copyrighted according to Project specific license
 */
public class RegisterMaterialResourceEvent extends Event
{
    public IArmorMaterial iArmorMaterial;
    public MultiLayeredArmor iArmor;

    public RegisterMaterialResourceEvent(IArmorMaterial pMaterial, MultiLayeredArmor pArmor)
    {
        iArmorMaterial = pMaterial;
        iArmor = pArmor;
    }
}
