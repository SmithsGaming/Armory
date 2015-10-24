package com.SmithsModding.Armory.Common.Material;
/*
*   ArmorMaterial
*   Created by: Orion
*   Created on: 6-4-2014
*/


import com.SmithsModding.Armory.API.Materials.IArmorMaterial;
import com.SmithsModding.Armory.Armory;
import com.SmithsModding.Armory.Common.Addons.MedievalAddonRegistry;
import com.SmithsModding.Armory.Common.Factory.HeatedItemFactory;
import com.SmithsModding.Armory.Common.Registry.GeneralRegistry;
import com.SmithsModding.Armory.Util.Client.Color.Color;
import com.SmithsModding.Armory.Util.Client.Color.ColorSampler;
import com.SmithsModding.Armory.Util.Client.Colors;
import com.SmithsModding.Armory.Util.References;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import java.security.InvalidParameterException;
import java.util.HashMap;

public class ArmorMaterial implements IArmorMaterial {
    private static int iLastUsedID = 0;

    int iMaterialID;
    private String iOreDicName;
    private String iInternalName;
    private String iVisibleName;
    private EnumChatFormatting iVisibleNameColor = EnumChatFormatting.RESET;
    private boolean iBaseArmorMaterial;
    private Color iColor = Colors.DEFAULT;
    private HashMap<String, Boolean> iActiveParts = new HashMap<String, Boolean>();
    private HashMap<String, Float> iBaseDamageAbsorption = new HashMap<String, Float>();
    private HashMap<String, Integer> iBaseDurability = new HashMap<String, Integer>();
    private HashMap<String, Integer> iPartModifiers = new HashMap<String, Integer>();
    private float iMeltingPoint;
    private float iHeatCoefficient;
    private ItemStack iBaseStack;

    //Constructor
    public ArmorMaterial(String pInternalName, String pVisibleName, String pOreDicName, boolean pBaseArmorMaterial, float pMeltingPoint, float pHeatCoefficient, ItemStack pBaseItemStack) {
        iOreDicName = pOreDicName;
        iInternalName = pInternalName;
        iVisibleName = pVisibleName;

        if (Armory.iSide == Side.CLIENT) {
            iColor = ColorSampler.getColorSampleFromItemStack(pBaseItemStack);
            iVisibleNameColor = ColorSampler.getChatColorSample(iColor);
        }

        iBaseArmorMaterial = pBaseArmorMaterial;
        iMeltingPoint = pMeltingPoint;
        iHeatCoefficient = pHeatCoefficient;

        iBaseStack = pBaseItemStack;
        if (!HeatedItemFactory.getInstance().isHeatable(pBaseItemStack)) {
            HeatedItemFactory.getInstance().addHeatableItemstack(pInternalName, pBaseItemStack);
        }

        setMaterialID(iLastUsedID);
        iLastUsedID++;

        if (iColor != null) {
            GeneralRegistry.iLogger.info("Initialized Material: " + iInternalName + ", with ItemColor: " + iColor.toString() + ", with EnumChatFormatting: " + iVisibleNameColor.name());
        } else {
            GeneralRegistry.iLogger.info("Initialized Material: " + iInternalName);
        }
    }

    public ArmorMaterial(String pInternalName, String pVisibleName, String pOreDicName, EnumChatFormatting pVisibileNameColor, boolean pBaseArmorMaterial, float pMeltingPoint, float pHeatCoefficient, Color pMaterialColor, ItemStack pBaseItemStack) {
        iOreDicName = pOreDicName;
        iInternalName = pInternalName;
        iVisibleName = pVisibleName;
        iColor = pMaterialColor;
        iVisibleNameColor = pVisibileNameColor;
        iBaseArmorMaterial = pBaseArmorMaterial;
        iMeltingPoint = pMeltingPoint;
        iHeatCoefficient = pHeatCoefficient;

        iBaseStack = pBaseItemStack;
        if (!HeatedItemFactory.getInstance().isHeatable(pBaseItemStack)) {
            HeatedItemFactory.getInstance().addHeatableItemstack(pInternalName, pBaseItemStack);
        }

        setMaterialID(iLastUsedID);
        iLastUsedID++;

        GeneralRegistry.iLogger.info("Initialized Material: " + iInternalName + ", with ItemColor: " + iColor.toString() + ", with EnumChatFormatting: " + iVisibleNameColor.name());
    }

    @Override
    public int getMaterialID() {
        return iMaterialID;
    }

    @Override
    public void setMaterialID(int pNewID) {
        iMaterialID = pNewID;
    }

    @Override
    public ItemStack getRootItemStack() {
        return iBaseStack;
    }

    @Override
    public String getInternalMaterialName() {
        return iInternalName;
    }

    public void registerNewActivePart(String pUpgradeInternalName, boolean pPartState) {
        if ((iActiveParts.size() != 0) && (iActiveParts.get(pUpgradeInternalName) != null)) {
            throw new InvalidParameterException("The given upgrade: " + MedievalAddonRegistry.getInstance().getUpgrade(pUpgradeInternalName).iVisibleName + ", is already registered  for this material: " + iVisibleName + ". The upgrades will automatically register them self's to the material.");
        }

        iActiveParts.put(pUpgradeInternalName, pPartState);
    }

    public void modifyPartState(String pUpgradeInternalName, boolean pPartState) {
        if (iActiveParts.get(pUpgradeInternalName) == null) {
            throw new InvalidParameterException("The given upgrade: " + MedievalAddonRegistry.getInstance().getUpgrade(pUpgradeInternalName).iVisibleName + ", is not registered for the following material: " + iVisibleName + ". Something went wrong as this should have happened when registering the material!");
        }
        iActiveParts.put(pUpgradeInternalName, pPartState);
    }

    public boolean getPartState(String pUpgradeInternalName) {
        if (!iActiveParts.containsKey(pUpgradeInternalName))
            return false;

        return iActiveParts.get(pUpgradeInternalName);
    }

    @Override
    public HashMap<String, Boolean> getAllPartStates() {
        return iActiveParts;
    }

    public void setBaseDamageAbsorption(String pTargetArmorInternalName, Float pBaseDamageAbsorption) {
        iBaseDamageAbsorption.put(pTargetArmorInternalName, pBaseDamageAbsorption);
    }

    public Float getBaseDamageAbsorption(String pTargetArmorInternalName) {
        return iBaseDamageAbsorption.get(pTargetArmorInternalName);
    }

    @Override
    public HashMap<String, Float> getAllBaseDamageAbsorbtionValues() {
        return iBaseDamageAbsorption;
    }

    public void setBaseDurability(String pTargetArmorInternalName, int pBaseDurability) {
        iBaseDurability.put(pTargetArmorInternalName, pBaseDurability);
    }

    public int getBaseDurability(String pTargetArmorInternalName) {
        if (iBaseDurability.containsKey(pTargetArmorInternalName)) return iBaseDurability.get(pTargetArmorInternalName);

        return 100;
    }

    @Override
    public HashMap<String, Integer> getAllBaseDurabilityValues() {
        return iBaseDurability;
    }

    public void setMaxModifiersOnPart(String pTargetArmorInternalName, int pMaxModifiers) {
        iPartModifiers.put(pTargetArmorInternalName, pMaxModifiers);
    }

    public int getMaxModifiersOnPart(String pTargetArmorInternalName) {
        return iPartModifiers.get(pTargetArmorInternalName);
    }

    @Override
    public HashMap<String, Integer> getAllMaxModifiersAmounts() {
        return iPartModifiers;
    }

    @Override
    public String getType() {
        return References.InternalNames.Tiers.MEDIEVAL;
    }

    public Color getColor() {
        return this.iColor;
    }

    public void setColor(Color pColor) {
        this.iColor = pColor;
    }

    public String getOreDicName() {
        return iOreDicName;
    }

    public String getVisibleName() {
        return iVisibleName;
    }

    public EnumChatFormatting getVisibleNameColor() {
        return iVisibleNameColor;
    }

    @Override
    public void setVisibleNameColor(EnumChatFormatting pFormatting) {
        iVisibleNameColor = pFormatting;
    }

    @Override
    public boolean getIsBaseArmorMaterial() {
        return iBaseArmorMaterial;
    }

    @Override
    public void setIsBaseArmorMaterial(boolean pNewState) {
        iBaseArmorMaterial = pNewState;
    }

    @Override
    public float getMeltingPoint() {
        return iMeltingPoint;
    }

    @Override
    public void setMeltingPoint(float pNewMeltingPoint) {
        iMeltingPoint = pNewMeltingPoint;
    }

    @Override
    public float getHeatCoefficient() {
        return iHeatCoefficient;
    }

    @Override
    public void setHeatCoefficient(float pNewCoefficient) {
        iHeatCoefficient = pNewCoefficient;
    }
}
