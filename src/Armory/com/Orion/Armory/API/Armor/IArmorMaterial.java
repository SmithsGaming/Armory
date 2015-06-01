package com.Orion.Armory.API.Armor;

import com.Orion.Armory.Util.Client.Color;

/**
 * Created by Orion
 * Created on 01.06.2015
 * 11:12
 * <p/>
 * Copyrighted according to Project specific license
 */
public interface IArmorMaterial
{
     String getInternalMaterialName();

     void registerNewActivePart(String pUpgradeInternalName, boolean pPartState);

     void modifyPartState(String pUpgradeInternalName, boolean pPartState);

     void setBaseDamageAbsorption(String pTargetArmorInternalName, Float pBaseDamageAbsorption);

     Float getBaseDamageAbsorption(String pTargetArmorInternalName);

     void setBaseDurability(String pTargetArmorInternalName, int pBaseDurability);

     int getBaseDurability(String pTargetArmorInternalName);

     void setMaxModifiersOnPart(String pTargetArmorInternalName, int pMaxModifiers);

     int getMaxModifiersOnPart(String pTargetArmorInternalName);

     String getType();

     void setColor(Color pColor);

     Color getColor();

}
