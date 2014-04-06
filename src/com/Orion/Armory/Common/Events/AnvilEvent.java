package com.Orion.Armory.Common.Events;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.AnvilUpdateEvent;

import com.Orion.Armory.Common.Armor.Modifiers.ArmorModifier;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class AnvilEvent {

	//TODO register this event

	@SubscribeEvent
	public void onAnvilUpdate(AnvilUpdateEvent e){

		ItemStack armor = e.left;
		ItemStack modifier = e.right;
		ItemStack result = e.output;

		e.cost = 0;

		NBTTagCompound nbtArmor;
		NBTTagCompound nbtModifier;

		if(armor != null){
			if(armor.getItem() instanceof ArmorBase){
				if(armor.getTagCompound() != null)
					nbtArmor = armor.getTagCompound();
				else
					nbtArmor = new NBTTagCompound();
			}
		}

		if(modifier != null){
			if(modifier.getItem() instanceof ArmorModifier){
				if(modifier.getTagCompound() != null)
					nbtArmor = modifier.getTagCompound();
				else
					nbtModifier = new NBTTagCompound();
			}
		}

		result = new ItemStack(armor.getItem());
		NBTTagCompound nbt = new NBTTagCompound();

	}
}
