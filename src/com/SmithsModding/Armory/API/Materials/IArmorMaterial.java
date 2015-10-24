package com.SmithsModding.Armory.API.Materials;

import com.SmithsModding.Armory.Util.Client.Color.Color;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import java.util.HashMap;

/**
 * Created by Orion
 * Created on 01.06.2015
 * 11:12
 * <p/>
 * Copyrighted according to Project specific license
 */
public interface IArmorMaterial {
    int getMaterialID();

    void setMaterialID(int pNewID);

    ItemStack getRootItemStack();

    String getInternalMaterialName();

    void registerNewActivePart(String pUpgradeInternalName, boolean pPartState);

    void modifyPartState(String pUpgradeInternalName, boolean pPartState);

    boolean getPartState(String pUpgradeInternalName);

    HashMap<String, Boolean> getAllPartStates();

    void setBaseDamageAbsorption(String pTargetArmorInternalName, Float pBaseDamageAbsorption);

    Float getBaseDamageAbsorption(String pTargetArmorInternalName);

    HashMap<String, Float> getAllBaseDamageAbsorbtionValues();

    void setBaseDurability(String pTargetArmorInternalName, int pBaseDurability);

    int getBaseDurability(String pTargetArmorInternalName);

    HashMap<String, Integer> getAllBaseDurabilityValues();

    void setMaxModifiersOnPart(String pTargetArmorInternalName, int pMaxModifiers);

    int getMaxModifiersOnPart(String pTargetArmorInternalName);

    HashMap<String, Integer> getAllMaxModifiersAmounts();

    String getType();

    Color getColor();

    void setColor(Color pColor);

    String getOreDicName();

    String getVisibleName();

    EnumChatFormatting getVisibleNameColor();

    void setVisibleNameColor(EnumChatFormatting pFormatting);

    boolean getIsBaseArmorMaterial();

    void setIsBaseArmorMaterial(boolean pNewState);

    float getMeltingPoint();

    void setMeltingPoint(float pNewMeltingPoint);

    float getHeatCoefficient();

    void setHeatCoefficient(float pNewCoefficient);
}
