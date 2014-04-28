package com.Orion.Armory.Client.CreativeTab;

import com.Orion.Armory.Common.Armor.ArmorCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ArmorTab extends CreativeTabs{

	public ArmorTab() {
		super(CreativeTabs.getNextID(), "Armory - Armor Tab");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		return new ArmorCore("armory.Dummy.Display.ArmorTab", 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel() {
		return super.getTranslatedTabLabel();
	}
}
