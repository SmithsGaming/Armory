package com.SmithsModding.Armory.Common.Registry;
/*
 *   GeneralRegistry
 *   Created by: Orion
 *   Created on: 24-9-2014
 */

import com.SmithsModding.Armory.Common.Block.*;
import com.SmithsModding.Armory.Common.Fluid.*;
import com.SmithsModding.Armory.Common.Item.*;
import com.SmithsModding.Armory.Common.Item.Knowledge.*;
import net.minecraft.item.*;
import net.minecraftforge.common.util.*;

import java.util.*;

public class GeneralRegistry {
    public static boolean isInDevEnvironment = false;
    protected static GeneralRegistry INSTANCE;
    private static ItemArmor.ArmorMaterial armorMaterial = EnumHelper.addArmorMaterial("Armory-Dummy", "missingno", 100, new int[]{0, 0, 0, 0}, 0);

    public GeneralRegistry() {
        Properties tSysProp = System.getProperties();
        isInDevEnvironment = Boolean.parseBoolean(tSysProp.getProperty("Armory.Dev", "false"));
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
    }

    public static class Fluids {
        public static FluidMoltenMetal moltenMetal;
    }
}
