package com.smithsmodding.armory.api.references;

import net.minecraft.item.ItemArmor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;

/**
 * Author Orion (Created on: 07.07.2016)
 */
public class ModArmorMaterials {
    private static ItemArmor.ArmorMaterial armorMaterial = EnumHelper.addArmorMaterial("armory-Dummy", "missingno", 100, new int[]{0, 0, 0, 0}, 0, SoundEvent.REGISTRY.getObject(new ResourceLocation("item.armor.equip_chain")), 0f);

    public static final ItemArmor.ArmorMaterial getVanillaArmorDefitinition () {
        return armorMaterial;
    }
}
