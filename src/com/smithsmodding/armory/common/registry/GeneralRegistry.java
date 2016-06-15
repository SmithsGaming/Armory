package com.smithsmodding.armory.common.registry;
/*
 *   GeneralRegistry
 *   Created by: Orion
 *   Created on: 24-9-2014
 */

import com.smithsmodding.armory.common.block.BlockBlackSmithsAnvil;
import com.smithsmodding.armory.common.block.BlockFirePit;
import com.smithsmodding.armory.common.block.BlockFirePlace;
import com.smithsmodding.armory.common.fluid.FluidMoltenMetal;
import com.smithsmodding.armory.common.item.ItemHeatedItem;
import com.smithsmodding.armory.common.item.knowledge.ItemBlueprint;
import com.smithsmodding.armory.common.item.knowledge.ItemSmithingsGuide;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

import java.util.Properties;

public class GeneralRegistry {
    public static boolean isInDevEnvironment = false;
    protected static GeneralRegistry INSTANCE;
    private static ItemArmor.ArmorMaterial armorMaterial = EnumHelper.addArmorMaterial("armory-Dummy", "missingno", 100, new int[]{0, 0, 0, 0}, 0);

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
        public static ItemBlueprint blueprint;
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
}
