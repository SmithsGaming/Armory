package com.smithsmodding.armory.common.registry;
/*
 *   GeneralRegistry
 *   Created by: Orion
 *   Created on: 24-9-2014
 */

import com.smithsmodding.armory.common.block.BlockBlackSmithsAnvil;
import com.smithsmodding.armory.common.block.BlockFirePit;
import com.smithsmodding.armory.common.block.BlockFirePlace;
import com.smithsmodding.armory.common.creativetabs.ArmorTab;
import com.smithsmodding.armory.common.creativetabs.BlocksTab;
import com.smithsmodding.armory.common.creativetabs.ComponentsTab;
import com.smithsmodding.armory.common.creativetabs.HeatedItemTab;
import com.smithsmodding.armory.common.fluid.FluidMoltenMetal;
import com.smithsmodding.armory.common.item.ItemArmorComponent;
import com.smithsmodding.armory.common.item.ItemHeatedItem;
import com.smithsmodding.armory.common.item.ItemSmithingsGuide;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;

import java.util.Properties;

public class GeneralRegistry {
    public static boolean isInDevEnvironment = false;
    protected static GeneralRegistry INSTANCE;
    private static ItemArmor.ArmorMaterial armorMaterial = EnumHelper.addArmorMaterial("armory-Dummy", "missingno", 100, new int[]{0, 0, 0, 0}, 0, SoundEvent.soundEventRegistry.getObject(new ResourceLocation("item.armor.equip_chain")));

    public GeneralRegistry() {
        Properties tSysProp = System.getProperties();
        isInDevEnvironment = Boolean.parseBoolean(tSysProp.getProperty("armory.Dev", "false"));
    }

    public static GeneralRegistry getInstance() {
        if (INSTANCE == null) INSTANCE = new GeneralRegistry();
        return INSTANCE;
    }

    public static final ItemArmor.ArmorMaterial getVanillaArmorDefitinition () {
        return armorMaterial;
    }

    public static class Items {
        public static ItemHeatedItem heatedItem;
        public static ItemArmorComponent armorComponent;
        public static ItemSmithingsGuide guide;
    }

    public static class Blocks {
        public static BlockFirePit blockFirePit;
        public static BlockBlackSmithsAnvil blockBlackSmithsAnvil;
        public static BlockFirePlace blockFirePlace;
    }

    public static class Fluids {
        public static FluidMoltenMetal moltenMetal;
    }

    public static class CreativeTabs {
        public static BlocksTab blocksTab = new BlocksTab();
        public static ComponentsTab componentsTab = new ComponentsTab();
        public static HeatedItemTab heatedItemTab = new HeatedItemTab();
        public static ArmorTab armorTab = new ArmorTab();
    }
}
