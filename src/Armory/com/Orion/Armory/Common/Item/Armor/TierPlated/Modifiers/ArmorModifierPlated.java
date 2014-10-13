package com.Orion.Armory.Common.Item.Armor.TierPlated.Modifiers;


import com.Orion.Armory.Common.Item.Armor.Core.MLAAddon;
import com.Orion.Armory.Common.Item.Armor.TierPlated.ArmorPlated;
import com.Orion.Armory.Util.Client.CustomResource;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;

/**
 * Created by Orion on 27-3-2014.
 */
public abstract class ArmorModifierPlated extends MLAAddon
{
    public String iVisibleName;
    public String iVisibleNameColor;
    public CustomResource iResource;

    public ArrayList<String> iRequiredModifiers;
    public ArrayList<String> iModifierBlackList;

    //Constructors    
    public ArmorModifierPlated(String pInternalName, String pParentID, String pArmorPositionID, String pVisibleName, String pVisibleNameColor, ArrayList<String> pRequiredModifiers, ArrayList<String> pModifierBlacklist, int pMaxModifications)
    {
        super(pInternalName, pParentID, pArmorPositionID, pMaxModifications);
        this.iVisibleName = pVisibleName;
        this.iVisibleNameColor = pVisibleNameColor;
        this.iRequiredModifiers = pRequiredModifiers;
        this.iModifierBlackList = pModifierBlacklist;
    }

    //Allows to add modifiers to the requirement list after init.
    public void addModifierToRequirements(String pModifierInternalName)
    {
        iRequiredModifiers.add(pModifierInternalName);
    }

    //Allows to add modifiers to the blacklist after init
    public void addModifierToBlacklist(String pModifierInternalName, Integer pMaximumInstalledAmount)
    {
        iModifierBlackList.add(pModifierInternalName);
    }

    public void removeModifierFromRequirements(String pModifierInternalName) { iRequiredModifiers.remove(pModifierInternalName);}

    public void removeModifierFromBlacklist(String pModiferInternalName) { iModifierBlackList.remove(pModiferInternalName);}

    //Validates the modifiers for crafting. This is done here for more flexible use of modifiers.
    public boolean validateCrafting(String pAddonIDToCheckAgainst, boolean pInstalled)
    {
        if(pInstalled)
        {
            return !iModifierBlackList.contains(pAddonIDToCheckAgainst);
        }

        return (iRequiredModifiers.contains(pAddonIDToCheckAgainst) && !iModifierBlackList.contains(pAddonIDToCheckAgainst));
    }

    //Abstract functions and properties all modifiers need to have:
    public abstract void applyEffectToArmorPiece(ArmorPlated pArmorPiece, int pModifierLevel);
    public abstract void applyEffectToPlayer(EntityPlayer pPlayer, int pModifierLevel);
}
