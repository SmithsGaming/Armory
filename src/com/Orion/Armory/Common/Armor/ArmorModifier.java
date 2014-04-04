package com.Orion.Armory.Common.Armor;


import net.minecraft.item.Item;

/**
 * Created by Marc on 27-3-2014.
 */
public class ArmorModifier
{
    public static Item iMaterial;
    public static String iIconLocation;
    public static String iModelTextureLocation;
    public static String iAddedEffect;
    public static float iEffectStrength;
    public static int iUpgradeAmount;
    public static int iMaxUpgrade;
    public static Item iBaseItem;
    public static int iTargetArmorID;

    public ArmorModifier(Item pMaterial, String pIconLocation, String pModelTextureLocation, String pAddedEffect, float pEffectStrength, int pUpgradeAmount, int pMaxUpgrade, Item pBaseItem, int pTargetArmorID)
    {
        iMaterial = pMaterial;
        iIconLocation = pIconLocation;
        iModelTextureLocation = pModelTextureLocation;
        iAddedEffect = pAddedEffect;
        iEffectStrength = pEffectStrength;
        iUpgradeAmount = pUpgradeAmount;
        iMaxUpgrade = pMaxUpgrade;
        iBaseItem = pBaseItem;
        iTargetArmorID = pTargetArmorID;
    }

}
