package com.Orion.Armory.Common.Armor.Modifiers;


import com.Orion.Armory.Common.ARegistry;
import com.Orion.Armory.Common.Armor.ArmorCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

/**
 * Created by Orion on 27-3-2014.
 */
public abstract class ArmorModifier
{
    public String iInternalName;
    public String iVisibleName;
    public String iVisibleNameColor;
    public int iTargetArmorID;
    public Item iBaseItem;

    //Constructor
    public ArmorModifier(String pInternalName, String pVisibleName, String pVisibleNameColor, int pTargetArmorID, Item pBaseItem)
    {
        iInternalName = pInternalName;
        iVisibleName = pVisibleName;
        iVisibleNameColor = pVisibleNameColor;
        iTargetArmorID = pTargetArmorID;
        iBaseItem = pBaseItem;
    }

    public void registerModifier()
    {
        ARegistry.instance.registerModifier(this);
    }


    //Abstract functions and properties all modifiers need to have:
    public abstract void applyEffectToArmorPiece(ArmorCore pArmorPiece);
    public abstract void applyEffectToPlayer(EntityPlayer pPlayer, int pModifierLevel);
}
