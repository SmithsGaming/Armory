package com.Orion.Armory.Common.Armor;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.EnumHelper;

/**
 * Created by Marc on 27-3-2014.
 */
public class ArmorUpgrade
{
    public static String iMaterial;
    public static String iName;
    public static int iExtraProtection;
    public static int iMaxUpgrade;
    public static Item iBaseItem;
    public static int iTargetArmorID;


    public ArmorUpgrade(String pMaterial,String pName, int pExtraProtection, int pMaxUpgrade, Item pBaseItem, int pTargetArmorID)
    {
        iMaterial = pMaterial;
        iName = pName;
        iExtraProtection = pExtraProtection;
        iMaxUpgrade = pMaxUpgrade;
        iBaseItem = pBaseItem;
        iTargetArmorID = pTargetArmorID;
    }

    public ArmorUpgrade(NBTTagCompound pBaseCompound)
    {
        iMaterial = pBaseCompound.getString("Material");
        iName = pBaseCompound.getString("Name");
        iExtraProtection = pBaseCompound.getInteger("ExtraProtection");
        iMaxUpgrade = pBaseCompound.getInteger("MaxUpgrades");
        iBaseItem = Item.getItemById(pBaseCompound.getInteger("BaseItemID"));
        iTargetArmorID = pBaseCompound.getInteger(("TargetArmorID"));
    }

    public NBTTagCompound createNBTTagCompound()
    {
        NBTTagCompound tBaseCompound = new NBTTagCompound();

        //Adding the Upgrade data to its compound;
        tBaseCompound.setString("Material", iMaterial);
        tBaseCompound.setString("Name", iName);
        tBaseCompound.setInteger("ExtraProtection", iExtraProtection);
        tBaseCompound.setInteger("MaxUpgrades", iMaxUpgrade);
        tBaseCompound.setInteger("BaseItemID", Item.getIdFromItem(iBaseItem));
        tBaseCompound.setInteger("TargetArmorID", iTargetArmorID);

        //Returning the base compound
        return tBaseCompound;
    }


}
