package com.Orion.Armory.Client.CreativeTab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ArmorTab extends CreativeTabs{

	public ArmorTab() {
		super(CreativeTabs.getNextID(), "Armor Tab");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel() {
		return super.getTranslatedTabLabel();
	}
}
