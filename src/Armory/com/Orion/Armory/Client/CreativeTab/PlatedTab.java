package com.Orion.Armory.Client.CreativeTab;

import com.Orion.Armory.Common.Item.Armor.Core.MLAAddon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class PlatedTab extends CreativeTabs{
    private ItemStack tIconStack;

	public PlatedTab() {
		super(CreativeTabs.getNextID(), "Armory - Plated Armor Tab");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getIconItemStack()
    {
        if (tIconStack == null)
        {
            HashMap<MLAAddon, Integer> tUgrades = new HashMap<MLAAddon, Integer>();
            //tUgrades.put(ARegistry.iInstance.getUpgrade(References.InternalNames.Upgrades.Helmet.TOP + "-" + References.InternalNames.Materials.ModMaterials.TinkersConstruct.ARDITE), 1);
            //tUgrades.put(ARegistry.iInstance.getUpgrade(References.InternalNames.Upgrades.Helmet.LEFT + "-" + References.InternalNames.Materials.ModMaterials.TinkersConstruct.COBALT), 1);
            //tUgrades.put(ARegistry.iInstance.getUpgrade(References.InternalNames.Upgrades.Helmet.RIGHT + "-" + References.InternalNames.Materials.ModMaterials.TinkersConstruct.COBALT), 1);

            //tIconStack = MedievalArmorFactory.getInstance().buildNewMLAArmor(ARegistry.iInstance.getArmorMapping(References.InternalNames.Armor.MEDIEVALHELMET), tUgrades, 100, References.InternalNames.Materials.ModMaterials.TinkersConstruct.MANYULLUN);
            //tIconStack.setItemDamage(0);
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
