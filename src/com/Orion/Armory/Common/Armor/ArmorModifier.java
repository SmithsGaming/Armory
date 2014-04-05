package com.Orion.Armory.Common.Armor;


import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;

/**
 * Created by Marc on 27-3-2014.
 */
public class ArmorModifier
{
    public static String iName;
    public static String iAddedEffect;
    public static float iEffectStrength;
    public static int iUpgradeAmount;
    public static int iMaxUpgrade;
    public static Item iBaseItem;
    public static int iTargetArmorID;

    public ArmorModifier(String pName, String pAddedEffect, float pEffectStrength, int pUpgradeAmount, int pMaxUpgrade, Item pBaseItem, int pTargetArmorID)
    {
        iName = pName;
        iAddedEffect = pAddedEffect;
        iEffectStrength = pEffectStrength;
        iUpgradeAmount = pUpgradeAmount;
        iMaxUpgrade = pMaxUpgrade;
        iBaseItem = pBaseItem;
        iTargetArmorID = pTargetArmorID;
    }


    public ArmorModifier(NBTTagCompound pBaseCompound)
    {
        iName = pBaseCompound.getString("Name");
        iAddedEffect = pBaseCompound.getString("AddedEffect");
        iEffectStrength = pBaseCompound.getFloat("EffectStrength");
        iUpgradeAmount = pBaseCompound.getInteger("UpgradedAmount");
        iMaxUpgrade = pBaseCompound.getInteger("MaxUpgrade");
        iBaseItem = Item.getItemById(pBaseCompound.getInteger("BaseItemID"));
        iTargetArmorID = pBaseCompound.getInteger("TargetArmorID");
    }

    public NBTTagCompound createNBTTagCompound()
    {
        NBTTagCompound tBaseCompound = new NBTTagCompound();

        //Adding data to the compound
        tBaseCompound.setString("Name", iName);
        tBaseCompound.setInteger("BaseItemID", Item.getIdFromItem(iBaseItem));
        tBaseCompound.setString("AddedEffect", iAddedEffect);
        tBaseCompound.setFloat("EffectStrength", iEffectStrength);
        tBaseCompound.setInteger("UpgradedAmount", iUpgradeAmount);
        tBaseCompound.setInteger("MaxUpgrade", iMaxUpgrade);
        tBaseCompound.setInteger("TargetArmorID", iTargetArmorID);

        return tBaseCompound;
    }


}
