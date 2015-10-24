package com.SmithsModding.Armory.Client.CreativeTab;

import com.SmithsModding.Armory.API.Armor.MLAAddon;
import com.SmithsModding.Armory.Common.Addons.MedievalAddonRegistry;
import com.SmithsModding.Armory.Common.Factory.MedievalArmorFactory;
import com.SmithsModding.Armory.Common.Material.MaterialRegistry;
import com.SmithsModding.Armory.Util.Client.TranslationKeys;
import com.SmithsModding.Armory.Util.References;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.HashMap;

public class MedievalTab extends CreativeTabs {
    private ItemStack tIconStack;

    public MedievalTab() {
        super(CreativeTabs.getNextID(), "Armory - Medieval Armor Tab");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getIconItemStack() {
        if (tIconStack == null) {
            HashMap<MLAAddon, Integer> tUpgrades = new HashMap<MLAAddon, Integer>();
            tUpgrades.put(MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Helmet.TOP + "-" + References.InternalNames.Materials.Vanilla.OBSIDIAN), 1);
            tUpgrades.put(MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Helmet.LEFT + "-" + References.InternalNames.Materials.Vanilla.OBSIDIAN), 1);
            tUpgrades.put(MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Helmet.RIGHT + "-" + References.InternalNames.Materials.Vanilla.OBSIDIAN), 1);

            tIconStack = MedievalArmorFactory.getInstance().buildNewMLAArmor(MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALHELMET), tUpgrades, 100, References.InternalNames.Materials.Vanilla.IRON);
            tIconStack.setItemDamage(0);
        }

        return tIconStack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getTranslatedTabLabel() {
        return StatCollector.translateToLocal(TranslationKeys.CreativeTabs.Medieval);
    }

    @Override
    public Item getTabIconItem() {
        return null;
    }
}
