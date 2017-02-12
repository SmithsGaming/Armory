package com.smithsmodding.armory.api.util.references;

import com.smithsmodding.armory.api.common.material.anvil.IAnvilMaterial;
import com.smithsmodding.armory.api.common.material.armor.IAddonArmorMaterial;
import com.smithsmodding.armory.api.common.material.armor.ICoreArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;

import javax.annotation.Nonnull;

/**
 * Author Orion (Created on: 07.07.2016)
 */
public class ModMaterials {



    public static final class Armor {
        private static ItemArmor.ArmorMaterial armorMaterial = EnumHelper.addArmorMaterial("armory-Dummy", "missingno", 100, new int[]{0, 0, 0, 0}, 0, SoundEvent.REGISTRY.getObject(new ResourceLocation("item.armor.equip_chain")), 0f);

        public static final ItemArmor.ArmorMaterial getVanillaArmorDefitinition () {
            return armorMaterial;
        }

        public static final class Core {

            @Nonnull
            public static ICoreArmorMaterial IRON;

            @Nonnull
            public static ICoreArmorMaterial OBSIDIAN;

            @Nonnull
            public static ICoreArmorMaterial STEEL;

            @Nonnull
            public static ICoreArmorMaterial GOLD;
        }

        public static final class Addon {

            @Nonnull
            public static IAddonArmorMaterial IRON;

            @Nonnull
            public static IAddonArmorMaterial OBSIDIAN;

            @Nonnull
            public static IAddonArmorMaterial STEEL;

            @Nonnull
            public static IAddonArmorMaterial GOLD;
        }
    }

    public static final class Anvil {

        @Nonnull
        public static IAnvilMaterial IRON;

        @Nonnull
        public static IAnvilMaterial OBSIDIAN;

        @Nonnull
        public static IAnvilMaterial STONE;
    }
}
