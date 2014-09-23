package com.Orion.Armory.Common.Events;
/*
*   ModifyMaterialEvent
*   Created by: Orion
*   Created on: 13-4-2014
*/

import cpw.mods.fml.common.eventhandler.Event;

public class ModifyMaterialEvent extends Event
{
    private String iArmorMaterialName = "";
    private String iMaterialInternalName = "";

    public ModifyMaterialEvent(String pArmorInternalName, String pMaterialInternalName)
    {
        iArmorMaterialName = pArmorInternalName;
        iMaterialInternalName = pMaterialInternalName;
    }

    public String getArmorInternalName()
    {
        return iArmorMaterialName;
    }

    public String getMaterialInternalName()
    {
        return iMaterialInternalName;
    }
}
