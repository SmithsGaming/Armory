package com.Orion.Armory.API.Materials;

import com.Orion.Armory.Util.Client.Color;
import net.minecraft.util.EnumChatFormatting;

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

     boolean getPartState(String pUpgradeInternalName);

     void setBaseDamageAbsorption(String pTargetArmorInternalName, Float pBaseDamageAbsorption);

     Float getBaseDamageAbsorption(String pTargetArmorInternalName);

     void setBaseDurability(String pTargetArmorInternalName, int pBaseDurability);

     int getBaseDurability(String pTargetArmorInternalName);

     void setMaxModifiersOnPart(String pTargetArmorInternalName, int pMaxModifiers);

     int getMaxModifiersOnPart(String pTargetArmorInternalName);

     String getType();

     void setColor(Color pColor);

     Color getColor();

     String getOreDicName();

     String getVisibleName();

     EnumChatFormatting getVisibleNameColor();

     boolean isBaseArmorMaterial();

}
