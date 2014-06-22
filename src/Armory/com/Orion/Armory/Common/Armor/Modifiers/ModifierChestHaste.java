package com.Orion.Armory.Common.Armor.Modifiers;
/*
*   ModifierRedstone
*   Created by: Orion
*   Created on: 6-4-2014
*/

import com.Orion.Armory.Common.Armor.ArmorCore;
import com.Orion.OrionsBelt.Client.CustomResource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.util.ArrayList;

public class ModifierChestHaste extends ArmorModifier {

    public ModifierChestHaste() {
        super("Armor.Chest.Redstone", "Haste", "", new CustomResource("armory.Modifiers.Haste", "tconstruct-armory:multiarmor/modifiers/Chest_Haste"), 1, 500, 100, Items.redstone, new ArrayList<Integer>(), new ArrayList<Integer>());
    }

    @Override
    public void applyEffectToArmorPiece(ArmorCore pArmorPiece, int pModifierLevel) {
        //For this modifier this does nothing as it gives an effect to the player
        return;
    }

    @Override
    public void applyEffectToPlayer(EntityPlayer pPlayer, int pModifierLevel)
    {
        pPlayer.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 2, pModifierLevel));
    }
}
