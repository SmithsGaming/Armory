package com.Orion.Armory.API.Events.Common;

import com.Orion.Armory.API.Materials.IArmorMaterial;
import com.Orion.Armory.API.Armor.MLAAddon;
import cpw.mods.fml.common.eventhandler.*;

/**
 * Created by Orion
 * Created on 01.06.2015
 * 11:10
 * <p/>
 * Copyrighted according to Project specific license
 */

@Cancelable
public class ActivateArmorAddonEvent extends cpw.mods.fml.common.eventhandler.Event
{
    public IArmorMaterial iArmorMaterial;
    public MLAAddon iAddon;

    public ActivateArmorAddonEvent(IArmorMaterial pMaterial, MLAAddon pAddon)
    {
        setResult(Result.ALLOW);
        iArmorMaterial = pMaterial;
        iAddon = pAddon;
    }
}
