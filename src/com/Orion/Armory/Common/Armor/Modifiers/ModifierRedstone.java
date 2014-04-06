package com.Orion.Armory.Common.Armor.Modifiers;
/*
*   ModifierRedstone
*   Created by: Orion
*   Created on: 6-4-2014
*/

import com.Orion.Armory.Common.Armor.ArmorCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class ModifierRedstone extends ArmorModifier {
    public ModifierRedstone() {
        super("Armor.Chest.Redstone", "Haste", "", 1, Items.redstone);
    }

    @Override
    public void applyEffectToArmorPiece(ArmorCore pArmorPiece) {
        //For this modifier this does nothing as it gives an effect to the player
        return;
    }

    @Override
    public void applyEffectToPlayer(EntityPlayer pPlayer, int pModifierLevel)
    {
        pPlayer.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 1, pModifierLevel));
    }
}
