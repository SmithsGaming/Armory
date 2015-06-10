package com.Orion.Armory.Common.Material;
/*
*   ArmorMaterial
*   Created by: Orion
*   Created on: 6-4-2014
*/


import com.Orion.Armory.API.Materials.IArmorMaterial;
import com.Orion.Armory.Common.Addons.MedievalAddonRegistry;
import com.Orion.Armory.Common.Factory.HeatedItemFactory;
import com.Orion.Armory.Util.Client.Color;
import com.Orion.Armory.Util.Client.Colors;
import com.Orion.Armory.Util.References;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import java.security.InvalidParameterException;
import java.util.HashMap;

public class ArmorMaterial implements IArmorMaterial
{
    private String iOreDicName;
    private String iInternalName;
    private String iVisibleName;
    private EnumChatFormatting iVisibleNameColor;
    private boolean iBaseArmorMaterial;
    private Color iColor = Colors.Metals.IRON;
    private HashMap<String, Boolean> iActiveParts = new HashMap<String, Boolean>();
    private HashMap<String, Float> iBaseDamageAbsorption = new HashMap<String, Float>();
    private HashMap<String, Integer> iBaseDurability = new HashMap<String, Integer>();
    private HashMap<String, Integer> iPartModifiers = new HashMap<String, Integer>();
    private float iMeltingPoint;
    private float iHeatCoefficient;

    //Constructor
    public ArmorMaterial(String pInternalName, String pVisibleName, String pOreDicName, EnumChatFormatting pVisibleNameColor, boolean pBaseArmorMaterial, Color pColor, float pMeltingPoint, float pHeatCoefficient, ItemStack pBaseItemStack)
    {
        iOreDicName = pOreDicName;
        iInternalName = pInternalName;
        iVisibleName = pVisibleName;
        iVisibleNameColor = pVisibleNameColor;
        iBaseArmorMaterial = pBaseArmorMaterial;
        iColor = pColor;
        iMeltingPoint = pMeltingPoint;
        iHeatCoefficient = pHeatCoefficient;

        if (!HeatedItemFactory.getInstance().isHeatable(pBaseItemStack)){
            HeatedItemFactory.getInstance().addHeatableItemstack(pInternalName, pBaseItemStack);
        }
    }

    @Override
    public String getInternalMaterialName() {
        return iInternalName;
    }

    public void registerNewActivePart(String pUpgradeInternalName, boolean pPartState)
    {
        if ((iActiveParts.size() != 0) && (iActiveParts.get(pUpgradeInternalName) != null))
        {
            throw new InvalidParameterException("The given upgrade: " + MedievalAddonRegistry.getInstance().getUpgrade(pUpgradeInternalName).iVisibleName + ", is already registered  for this material: " + iVisibleName + ". The upgrades will automatically register them self's to the material.");
        }

        iActiveParts.put(pUpgradeInternalName, pPartState);
    }

    public void modifyPartState(String pUpgradeInternalName, boolean pPartState)
    {
        if (iActiveParts.get(pUpgradeInternalName) == null)
        {
            throw new InvalidParameterException("The given upgrade: " + MedievalAddonRegistry.getInstance().getUpgrade(pUpgradeInternalName).iVisibleName + ", is not registered for the following material: "+ iVisibleName + ". Something went wrong as this should have happened when registering the material!");
        }
        iActiveParts.put(pUpgradeInternalName, pPartState);
    }

    public boolean getPartState(String pUpgradeInternalName)
    {
        if (!iActiveParts.containsKey(pUpgradeInternalName))
            return false;

        return iActiveParts.get(pUpgradeInternalName);
    }

    @Override
    public HashMap<String, Boolean> getAllPartStates() {
        return iActiveParts;
    }

    public void setBaseDamageAbsorption(String pTargetArmorInternalName, Float pBaseDamageAbsorption)
    {
        iBaseDamageAbsorption.put(pTargetArmorInternalName, pBaseDamageAbsorption);
    }

    public Float getBaseDamageAbsorption(String pTargetArmorInternalName)
    {
        return iBaseDamageAbsorption.get(pTargetArmorInternalName);
    }

    @Override
    public HashMap<String, Float> getAllBaseDamageAbsorbtionValues() {
        return iBaseDamageAbsorption;
    }

    public void setBaseDurability(String pTargetArmorInternalName, int pBaseDurability)
    {
        iBaseDurability.put(pTargetArmorInternalName, pBaseDurability);
    }

    public int getBaseDurability(String pTargetArmorInternalName)
    {
        if (iBaseDurability.containsKey(pTargetArmorInternalName)) return iBaseDurability.get(pTargetArmorInternalName);

        return 100;
    }

    @Override
    public HashMap<String, Integer> getAllBaseDurabilityValues() {
        return iBaseDurability;
    }

    public void setMaxModifiersOnPart(String pTargetArmorInternalName, int pMaxModifiers)
    {
        iPartModifiers.put(pTargetArmorInternalName, pMaxModifiers);
    }

    public int getMaxModifiersOnPart(String pTargetArmorInternalName) { return iPartModifiers.get(pTargetArmorInternalName);}

    @Override
    public HashMap<String, Integer> getAllMaxModifiersAmounts() {
        return iPartModifiers;
    }

    @Override
    public String getType() {
        return References.InternalNames.Tiers.MEDIEVAL;
    }

    public void setColor(Color pColor)
    {
        this.iColor = pColor;
    }

    public Color getColor()
    {
        return this.iColor;
    }

    public String getOreDicName()
    {
        return iOreDicName;
    }

    public String getVisibleName()
    {
        return iVisibleName;
    }

    public EnumChatFormatting getVisibleNameColor()
    {
        return iVisibleNameColor;
    }

    @Override
    public boolean getIsBaseArmorMaterial() {
        return iBaseArmorMaterial;
    }

    @Override
    public float getMeltingPoint() {
        return iMeltingPoint;
    }

    @Override
    public float getHeatCoefficient() {
        return iHeatCoefficient;
    }

    @Override
    public void setIsBaseArmorMaterial(boolean pNewState) {
        iBaseArmorMaterial = pNewState;
    }

    @Override
    public void setMeltingPoint(float pNewMeltingPoint) {
        iMeltingPoint = pNewMeltingPoint;
    }

    @Override
    public void setHeatCoefficient(float pNewCoefficient) {
        iHeatCoefficient = pNewCoefficient;
    }
}
