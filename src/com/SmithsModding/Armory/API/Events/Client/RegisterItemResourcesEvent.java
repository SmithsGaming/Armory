package com.SmithsModding.Armory.API.Events.Client;

import com.SmithsModding.Armory.API.Materials.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * Created by Orion
 * Created on 01.06.2015
 * 13:05
 * <p/>
 * Copyrighted according to Project specific license
 */
public class RegisterItemResourcesEvent extends Event {
    public IArmorMaterial iMaterial;
    public Item iContainer;

    public RegisterItemResourcesEvent (IArmorMaterial pMaterial, Item pContainer) {
        iMaterial = pMaterial;
        iContainer = pContainer;
    }


}
