package com.smithsmodding.armory.common.creativetabs;

import com.smithsmodding.armory.api.armor.MLAAddon;
import com.smithsmodding.armory.api.references.References;
import com.smithsmodding.armory.common.factory.MedievalArmorFactory;
import com.smithsmodding.armory.common.registry.ArmorRegistry;
import com.smithsmodding.armory.common.registry.MaterialRegistry;
import com.smithsmodding.armory.common.registry.MedievalAddonRegistry;
import com.smithsmodding.armory.util.client.TranslationKeys;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

/**
 * Author Marc (Created on: 14.06.2016)
 */
public class ArmorTab extends CreativeTabs {

    public ArmorTab() {
        super(I18n.format(TranslationKeys.CreativeTabs.Armor));
    }

    @Override
    public String getTranslatedTabLabel() {
        return getTabLabel();
    }

    @Override
    public ItemStack getIconItemStack() {
        HashMap<MLAAddon, Integer> tHelmetAddons = new HashMap<MLAAddon, Integer>();
        tHelmetAddons.put(MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Helmet.TOP + "-" + References.InternalNames.Materials.Vanilla.OBSIDIAN), 1);
        tHelmetAddons.put(MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Helmet.RIGHT + "-" + References.InternalNames.Materials.Vanilla.OBSIDIAN), 1);
        tHelmetAddons.put(MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Helmet.LEFT + "-" + References.InternalNames.Materials.Vanilla.OBSIDIAN), 1);

        ItemStack stack = MedievalArmorFactory.getInstance().buildNewMLAArmor(ArmorRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALHELMET), tHelmetAddons, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getBaseDurability(References.InternalNames.Armor.MEDIEVALHELMET), References.InternalNames.Materials.Vanilla.IRON);
        return stack;
    }

    @Override
    public Item getTabIconItem() {
        return ArmorRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALHELMET);
    }
}
