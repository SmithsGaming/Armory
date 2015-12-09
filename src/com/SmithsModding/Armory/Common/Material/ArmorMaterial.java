package com.SmithsModding.Armory.Common.Material;
/*
*   ArmorMaterial
*   Created by: Orion
*   Created on: 6-4-2014
*/

import com.SmithsModding.Armory.API.Materials.*;
import com.SmithsModding.Armory.*;
import com.SmithsModding.Armory.Common.Addons.*;
import com.SmithsModding.Armory.Common.Factory.*;
import com.SmithsModding.Armory.Util.*;
import com.SmithsModding.SmithsCore.Util.Client.Color.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.relauncher.*;

import java.security.*;
import java.util.*;

public class ArmorMaterial implements IArmorMaterial {
    private static int iLastUsedID = 0;

    int iMaterialID;
    private String iOreDicName;
    private String iInternalName;
    private String iVisibleName;
    private EnumChatFormatting iVisibleNameColor = EnumChatFormatting.RESET;
    private boolean iBaseArmorMaterial;
    private IMaterialRenderInfo renderInfo;
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

        if (Armory.side == Side.CLIENT) {
            MinecraftColor metalColor = ColorSampler.getColorSampleFromItemStack(pBaseItemStack);

            renderInfo = new IMaterialRenderInfo.Metal(metalColor.getRGB());
            iVisibleNameColor = ColorSampler.getSimpleChatMinecraftColor(metalColor);
        }

        iBaseArmorMaterial = pBaseArmorMaterial;
        iMeltingPoint = pMeltingPoint;
        iHeatCoefficient = pHeatCoefficient;

        iBaseStack = pBaseItemStack;
        if (pBaseItemStack != null && !HeatedItemFactory.getInstance().isHeatable(pBaseItemStack)) {
            HeatedItemFactory.getInstance().addHeatableItemstack(pInternalName, pBaseItemStack);
        }

        setItemDamageMaterialID(iLastUsedID);
        iLastUsedID++;

        if (renderInfo != null) {
            Armory.getLogger().info("Initialized Material: " + iInternalName + ", with ItemColor: " + renderInfo.getVertexColor().toString() + ", with EnumChatFormatting: " + iVisibleNameColor.name());
        } else {
            Armory.getLogger().info("Initialized Material: " + iInternalName);
        }
    }

    public ArmorMaterial (String pInternalName, String pVisibleName, String pOreDicName, EnumChatFormatting pVisibileNameColor, boolean pBaseArmorMaterial, float pMeltingPoint, float pHeatCoefficient, ItemStack pBaseItemStack) {
        iOreDicName = pOreDicName;
        iInternalName = pInternalName;
        iVisibleName = pVisibleName;
        iVisibleNameColor = pVisibileNameColor;
        iBaseArmorMaterial = pBaseArmorMaterial;
        iMeltingPoint = pMeltingPoint;
        iHeatCoefficient = pHeatCoefficient;

        iBaseStack = pBaseItemStack;
        if (pBaseItemStack != null && !HeatedItemFactory.getInstance().isHeatable(pBaseItemStack)) {
            HeatedItemFactory.getInstance().addHeatableItemstack(pInternalName, pBaseItemStack);
        }

        setItemDamageMaterialID(iLastUsedID);
        iLastUsedID++;

        Armory.getLogger().info("Initialized Material: " + iInternalName + ", with unknown ItemColor.");
    }

    @Override
    public int getItemDamageMaterialID () {
        return iMaterialID;
    }

    @Override
    public void setItemDamageMaterialID (int pNewID) {
        iMaterialID = pNewID;
    }

    @Override
    public ItemStack getRootItemStack() {
        return iBaseStack;
    }

    @Override
    public String getUniqueID () {
        return iInternalName;
    }

    public void registerNewActivePart(String pUpgradeInternalName, boolean pPartState) {
        if ((iActiveParts.size() != 0) && (iActiveParts.get(pUpgradeInternalName) != null)) {
            throw new InvalidParameterException("The given upgrade: " + MedievalAddonRegistry.getInstance().getUpgrade(pUpgradeInternalName).getUniqueID() + ", is already registered  for this material: " + iVisibleName + ". The upgrades will automatically register them self's to the material.");
        }

        iActiveParts.put(pUpgradeInternalName, pPartState);
    }

    public void modifyPartState(String pUpgradeInternalName, boolean pPartState) {
        if (iActiveParts.get(pUpgradeInternalName) == null) {
            throw new InvalidParameterException("The given upgrade: " + MedievalAddonRegistry.getInstance().getUpgrade(pUpgradeInternalName).getUniqueID() + ", is not registered for the following material: " + iVisibleName + ". Something went wrong as this should have happened when registering the material!");
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

    @Override
    public IMaterialRenderInfo getRenderInfo () {
        return renderInfo;
    }

    @Override
    public void setRenderInfo (IMaterialRenderInfo newInfo) {
        renderInfo = newInfo;
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
