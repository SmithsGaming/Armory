package com.Orion.Armory.Client.CreativeTab;

import com.Orion.Armory.Common.ARegistry;
import com.Orion.Armory.Util.References;
import com.Orion.Armory.Common.Armor.Core.MLAAddon;
import com.Orion.Armory.Common.Factory.StandardMLAFactory;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

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
            HashMap<MLAAddon, Integer> tUgrades = new HashMap<MLAAddon, Integer>();
            tUgrades.put(ARegistry.iInstance.getUpgrade(References.InternalNames.Upgrades.Helmet.TOP + "-" + References.InternalNames.Materials.ModMaterials.TinkersConstruct.ARDITE), 1);
            tUgrades.put(ARegistry.iInstance.getUpgrade(References.InternalNames.Upgrades.Helmet.LEFT + "-" + References.InternalNames.Materials.ModMaterials.TinkersConstruct.COBALT), 1);
            tUgrades.put(ARegistry.iInstance.getUpgrade(References.InternalNames.Upgrades.Helmet.RIGHT + "-" + References.InternalNames.Materials.ModMaterials.TinkersConstruct.COBALT), 1);

            tIconStack = StandardMLAFactory.iInstance.buildNewMLAArmor(ARegistry.iInstance.getArmorMapping(References.InternalNames.Armor.HELMET), tUgrades, 100, References.InternalNames.Materials.ModMaterials.TinkersConstruct.MANYULLUN);
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
