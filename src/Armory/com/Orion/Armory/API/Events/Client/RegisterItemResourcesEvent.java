package com.Orion.Armory.API.Events.Client;

import com.Orion.Armory.API.Materials.IArmorMaterial;
import com.Orion.Armory.API.Item.IResourceContainer;
import cpw.mods.fml.common.eventhandler.Event;

/**
 * Created by Orion
 * Created on 01.06.2015
 * 13:05
 * <p/>
 * Copyrighted according to Project specific license
 */
public class RegisterItemResourcesEvent extends Event
{
    public IArmorMaterial iMaterial;
    public IResourceContainer iContainer;

    public RegisterItemResourcesEvent(IArmorMaterial pMaterial, IResourceContainer pContainer)
    {
        iMaterial = pMaterial;
        iContainer = pContainer;
    }


}
