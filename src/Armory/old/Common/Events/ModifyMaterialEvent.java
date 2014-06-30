package old.Common.Events;
/*
*   ModifyMaterialEvent
*   Created by: Orion
*   Created on: 13-4-2014
*/

import cpw.mods.fml.common.eventhandler.Event;

public class ModifyMaterialEvent extends Event
{
    private int iArmorID = -1;
    private int iMaterialID = -1;

    public ModifyMaterialEvent(int pAmorID, int pMaterialID)
    {
        iArmorID = pAmorID;
        iMaterialID = pMaterialID;
    }

    public int getArmorID()
    {
        return iArmorID;
    }

    public int getMaterialID()
    {
        return iMaterialID;
    }
}
