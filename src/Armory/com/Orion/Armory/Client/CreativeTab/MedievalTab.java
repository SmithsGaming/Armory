package com.Orion.Armory.Client.CreativeTab;

import com.Orion.Armory.Common.Factory.MedievalArmorFactory;
import com.Orion.Armory.API.Armor.MLAAddon;
import com.Orion.Armory.Common.Registry.MedievalRegistry;
import com.Orion.Armory.Util.References;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class MedievalTab extends CreativeTabs{
    private ItemStack tIconStack;

	public MedievalTab() {
		super(CreativeTabs.getNextID(), "Armory - Medieval Armor Tab");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getIconItemStack()
    {
        if (tIconStack == null)
        {
            HashMap<MLAAddon, Integer> tUpgrades = new HashMap<MLAAddon, Integer>();
            tUpgrades.put(MedievalRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Helmet.TOP + "-" + References.InternalNames.Materials.Vanilla.OBSIDIAN), 1);
            tUpgrades.put(MedievalRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Helmet.LEFT + "-" + References.InternalNames.Materials.Vanilla.OBSIDIAN), 1);
            tUpgrades.put(MedievalRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Helmet.RIGHT + "-" + References.InternalNames.Materials.Vanilla.OBSIDIAN), 1);

            tIconStack = MedievalArmorFactory.getInstance().buildNewMLAArmor(MedievalRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALHELMET), tUpgrades, 100, References.InternalNames.Materials.Vanilla.IRON);
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
