package com.smithsmodding.armory.common.creativetabs;

import com.smithsmodding.armory.api.armor.MLAAddon;
import com.smithsmodding.armory.api.util.client.TranslationKeys;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.factories.ArmorFactory;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashMap;

/**
 * Author Marc (Created on: 14.06.2016)
 */
public class ArmorTab extends CreativeTabs {

    public ArmorTab() {
        super(TranslationKeys.CreativeTabs.Armor);
    }

    @Nonnull
    @Override
    public String getTranslatedTabLabel() {
        return getTabLabel();
    }

    @Override
    public ItemStack getTabIconItem() {
        //TODO: UPDATE
        HashMap<MLAAddon, Integer> tHelmetAddons = new HashMap<MLAAddon, Integer>();
        tHelmetAddons.put(MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Helmet.TOP + "-" + References.InternalNames.Materials.Vanilla.OBSIDIAN), 1);
        tHelmetAddons.put(MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Helmet.RIGHT + "-" + References.InternalNames.Materials.Vanilla.OBSIDIAN), 1);
        tHelmetAddons.put(MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Helmet.LEFT + "-" + References.InternalNames.Materials.Vanilla.OBSIDIAN), 1);

        ItemStack stack = ArmorFactory.getInstance().buildNewMLAArmor(ArmorRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALHELMET), tHelmetAddons, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getBaseDurability(References.InternalNames.Armor.MEDIEVALHELMET), References.InternalNames.Materials.Vanilla.IRON);
        return stack;
    }
}
