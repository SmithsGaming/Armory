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
    public static int iMaxUpgrade;

    public ArmorModifier(Item pMaterial, String pIconLocation, String pModelTextureLocation, String pAddedEffect, int pMaxUpgrade)
    {
        iMaterial = pMaterial;
        iIconLocation = pIconLocation;
        iModelTextureLocation = pModelTextureLocation;
        iAddedEffect = pAddedEffect;
        iMaxUpgrade = pMaxUpgrade;
    }

    public Item getMaterial()
    {
        return iMaterial;
    }

    public String getIconLocation()
    {
        return iIconLocation;
    }

    public String getModelTextureLocation() {return iModelTextureLocation; }

    public String getAddedEffect()
    {
        return iAddedEffect;
    }

    public int getMaxUpgrade()
    {
        return iMaxUpgrade;
    }

}
