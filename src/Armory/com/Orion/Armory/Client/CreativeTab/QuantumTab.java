package com.Orion.Armory.Client.CreativeTab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class QuantumTab extends CreativeTabs{
    private ItemStack tIconStack;

	public QuantumTab() {
		super(CreativeTabs.getNextID(), "Armory - Quantum Armor Tab");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getIconItemStack()
    {
        if (tIconStack == null)
        {
            //HashMap<MLAAddon, Integer> tUgrades = new HashMap<MLAAddon, Integer>();
            //tUgrades.put(ARegistry.iInstance.getUpgrade(References.InternalNames.Upgrades.Helmet.TOP + "-" + References.InternalNames.Materials.ModMaterials.TinkersConstruct.ARDITE), 1);
            //tUgrades.put(ARegistry.iInstance.getUpgrade(References.InternalNames.Upgrades.Helmet.LEFT + "-" + References.InternalNames.Materials.ModMaterials.TinkersConstruct.COBALT), 1);
            //tUgrades.put(ARegistry.iInstance.getUpgrade(References.InternalNames.Upgrades.Helmet.RIGHT + "-" + References.InternalNames.Materials.ModMaterials.TinkersConstruct.COBALT), 1);

//            tIconStack = MedievalArmorFactory.getInstance().buildNewMLAArmor(ARegistry.iInstance.getArmorMapping(References.InternalNames.Armor.MEDIEVALHELMET), tUgrades, 100, References.InternalNames.Materials.ModMaterials.TinkersConstruct.MANYULLUN);
  //          tIconStack.setItemDamage(0);

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
