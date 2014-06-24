package com.Orion.Armory.Client.CreativeTab;

import com.Orion.Armory.Common.ARegistry;
import com.Orion.Armory.Common.Armor.ArmorCore;
import com.Orion.Armory.Common.Armor.ArmorUpgrade;
import com.Orion.Armory.Common.Armor.Modifiers.ArmorModifier;
import com.Orion.Armory.Common.Logic.ArmorBuilder;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class ArmorTab extends CreativeTabs{
    private ItemStack tIconStack;

	public ArmorTab() {
		super(CreativeTabs.getNextID(), "Armory - Armor Tab");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getIconItemStack()
    {
        if (tIconStack == null)
        {
            ArrayList<ArmorUpgrade> tUgrades = new ArrayList<ArmorUpgrade>();
            tUgrades.add(ARegistry.iInstance.getUpgrade(2));
            tUgrades.add(ARegistry.iInstance.getUpgrade(10));
            tUgrades.add(ARegistry.iInstance.getUpgrade(19));

            tIconStack = ArmorBuilder.instance.buildArmor(ArmorBuilder.instance.createInitialArmor(4, 0), tUgrades, new ArrayList<ArmorModifier>());
            tIconStack.setItemDamage(0);
        }

        return tIconStack;
    }

	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel() {
		return super.getTranslatedTabLabel();
	}

    @Override
    public Item getTabIconItem() {
        return null;
    }
}
