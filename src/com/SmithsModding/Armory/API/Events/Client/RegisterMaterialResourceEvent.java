package com.SmithsModding.Armory.API.Events.Client;

import com.SmithsModding.Armory.API.Armor.MultiLayeredArmor;
import com.SmithsModding.Armory.API.Materials.IArmorMaterial;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * Created by Orion
 * Created on 01.06.2015
 * 12:57
 * <p/>
 * Copyrighted according to Project specific license
 */
public class RegisterMaterialResourceEvent extends Event {
    public IArmorMaterial iArmorMaterial;
    public MultiLayeredArmor iArmor;

    public RegisterMaterialResourceEvent(IArmorMaterial pMaterial, MultiLayeredArmor pArmor) {
        iArmorMaterial = pMaterial;
        iArmor = pArmor;
    }
}
