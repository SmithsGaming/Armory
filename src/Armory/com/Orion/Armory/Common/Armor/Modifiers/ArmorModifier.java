package com.Orion.Armory.Common.Armor.Modifiers;


import com.Orion.OrionsBelt.Client.CustomResource;
import com.Orion.Armory.Common.ARegistry;
import com.Orion.Armory.Common.Armor.ArmorCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

import java.security.InvalidParameterException;
import java.util.ArrayList;

/**
 * Created by Orion on 27-3-2014.
 */
public abstract class ArmorModifier
{
    public String iInternalName;
    public String iVisibleName;
    public String iVisibleNameColor;
    public CustomResource iResource;
    public int iTargetArmorID;
    public int iMaxModifications;
    public int iItemsPerLevel = -1;
    public Item iBaseItem;

    public ArrayList<Integer> iRequiredModifiers;
    public ArrayList<Integer> iModifierBlackList;

    //Used while validating crafting recipes
    public int iInstalledAmount = -1;

    //Constructors
    //This is used for modifiers who are a one time appliers
    public ArmorModifier(String pInternalName, String pVisibleName, String pVisibleNameColor, CustomResource pResource, int pTargetArmorID, int pMaxModifications, Item pBaseItem, ArrayList<Integer> pRequiredModifiers, ArrayList<Integer> pModifierBlacklist)
    {
        iInternalName = pInternalName;
        iVisibleName = pVisibleName;
        iVisibleNameColor = pVisibleNameColor;
        iResource = pResource;
        iTargetArmorID = pTargetArmorID;
        iMaxModifications = pMaxModifications;
        iBaseItem = pBaseItem;

        iRequiredModifiers = pRequiredModifiers;
        iModifierBlackList = pModifierBlacklist;
    }

    //This one is used for modifiers who use levels and/or require more then one item per level
    public ArmorModifier(String pInternalName, String pVisibleName, String pVisibleNameColor, CustomResource pResource, int pTargetArmorID, int pMaxModifications, int pItemsPerLevel, Item pBaseItem, ArrayList<Integer> pRequiredModifiers, ArrayList<Integer> pModifierBlacklist)
    {
        iInternalName = pInternalName;
        iVisibleName = pVisibleName;
        iVisibleNameColor = pVisibleNameColor;
        iResource = pResource;
        iTargetArmorID = pTargetArmorID;
        iMaxModifications = pMaxModifications;
        iItemsPerLevel = pItemsPerLevel;
        iBaseItem = pBaseItem;

        iRequiredModifiers = pRequiredModifiers;
        iModifierBlackList = pModifierBlacklist;
    }

    public ArmorModifier getCopy()
    {
        return this;
    }

    //Allows to add modifiers to the requirement list after init.
    public void addModifierToRequirements(int pModifierID)
    {
        iRequiredModifiers.add(pModifierID);
    }

    public void addModifierToRequirements(ArmorModifier pModifier)
    {
        int tModifierID = ARegistry.iInstance.getModifierID(pModifier);

        if (tModifierID == -1)
        {
            throw new InvalidParameterException("The given modifier: " + pModifier.iVisibleName + ", is not registered with the Armory registry. Please register your modifiers first.");
        }

        this.addModifierToRequirements(tModifierID);
    }

    //Allows to add modifiers to the blacklist after init
    public void addModifierToBlacklist(int pModifierID)
    {
        iModifierBlackList.add(pModifierID);
    }

    public void addModifierToBlacklist(ArmorModifier pModifier)
    {
        int tModifierID = ARegistry.iInstance.getModifierID(pModifier);

        if (tModifierID == -1)
        {
            throw new InvalidParameterException("The given modifier: " + pModifier.iVisibleName + ", is not registered with the Armory registry. Please register your modifiers first.");
        }

        this.addModifierToBlacklist(tModifierID);
    }

    public void removeModifierFromRequirements(int pModifierID) { iRequiredModifiers.remove(pModifierID);}

    public void removeModifierFromRequirements(ArmorModifier pModifier)
    {
        int tModifierID = ARegistry.iInstance.getModifierID(pModifier);

        if (tModifierID == -1)
        {
            throw new InvalidParameterException("The given modifier: " + pModifier.iVisibleName + ", is not registered with the Armory registry. Please register your modifiers first.");
        }

        this.removeModifierFromRequirements(tModifierID);
    }

    public void removeModifierFromBlacklist(int pModiferID) { iModifierBlackList.remove(pModiferID);}

    public void removeModifierFromBlacklist(ArmorModifier pModifier)
    {
        int tModifierID = ARegistry.iInstance.getModifierID(pModifier);

        if (tModifierID == -1)
        {
            throw new InvalidParameterException("The given modifier: " + pModifier.iVisibleName + ", is not registered with the Armory registry. Please register your modifiers first.");
        }

        this.removeModifierFromBlacklist(tModifierID);
    }

    //Has to be set before using to validate crafting:
    public void setInstalledAmount(int pAmount)
    {
        iInstalledAmount = pAmount;
    }

    //Validates the modifiers for crafting. This is done here for more flexible use of modifiers.
    public boolean validateCraftingForThisModifier(ArrayList<ArmorModifier> pInstalledModifiers, ArrayList<ArmorModifier> pNewModifiers)
    {
        if ((pInstalledModifiers.indexOf(this) == -1) && (pNewModifiers.indexOf(this)==-1))
        {
            return true;
        }

        if (iInstalledAmount == -1)
        {
            ARegistry.iInstance.iLogger.error("While validating a crafting recipe the installed amount was not set and crafting has been canceled!");
            return false;
        }

        if ((pInstalledModifiers.indexOf(this) == -1) && (pNewModifiers.indexOf(this) != -1))
        {
            for(ArmorModifier tModifier: pInstalledModifiers)
            {
                if (iModifierBlackList.indexOf(tModifier) != -1)
                {
                    return false;
                }
            }
        }
        else if (pInstalledModifiers.indexOf(this) != -1)
        {
            for (ArmorModifier tModifier: pNewModifiers)
            {
                if (iModifierBlackList.indexOf(tModifier) != -1)
                {
                    return false;
                }

                if ((iInternalName.equals(tModifier.iInternalName)) && (iInstalledAmount + tModifier.iInstalledAmount > iMaxModifications))
                {
                    return false;
                }
            }

        }

        iInstalledAmount = -1;
        return true;
    }

    public boolean equals(ArmorModifier tOtherModifier)
    {
        return (this.iInternalName == tOtherModifier.iInternalName);
    }

    public CustomResource getResource()
    {
        return iResource;
    }

    //Abstract functions and properties all modifiers need to have:
    public abstract void applyEffectToArmorPiece(ArmorCore pArmorPiece, int pModifierLevel);
    public abstract void applyEffectToPlayer(EntityPlayer pPlayer, int pModifierLevel);
}
