package com.Orion.Armory.Common.Config;

import com.Orion.Armory.API.Materials.IArmorConfigurator;
import com.Orion.Armory.API.Materials.IArmorMaterial;
import com.Orion.Armory.Common.Material.MaterialRegistry;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Orion
 * Created on 09.06.2015
 * 15:11
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ArmorDataConfigHandler implements IArmorConfigurator
{
    private static File iArmoryConfigFile;

    public static void init(File pArmoryConfigFile)
    {
        iArmoryConfigFile = pArmoryConfigFile;
    }

    @Override
    public void loadIsBaseArmorMaterial() {
        for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values())
        {
            Configuration tGlobalMaterialConfig = new Configuration(new File(iArmoryConfigFile.getParentFile().getAbsolutePath() + "\\Armory\\Material Configuration\\" + tMaterial.getInternalMaterialName() + "\\Global.cfg"), true);

            tMaterial.setIsBaseArmorMaterial(tGlobalMaterialConfig.getBoolean("IsBaseArmorMaterial", "Armor", tMaterial.getIsBaseArmorMaterial(), "Marks this material as a material out of which the base layer can be crafted."));

            if (tGlobalMaterialConfig.hasChanged())
                tGlobalMaterialConfig.save();
        }
    }

    @Override
    public void loadActiveParts() {
        for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values())
        {
            Configuration tActivePartConfiguration = new Configuration(new File(iArmoryConfigFile.getParentFile().getAbsolutePath() + "\\Armory\\Material Configuration\\" + tMaterial.getInternalMaterialName() + "\\PartStates.cfg"), true);

            Iterator tIter = tMaterial.getAllPartStates().entrySet().iterator();
            while(tIter.hasNext())
            {
                Map.Entry<String, Boolean> tEntry = (Map.Entry) tIter.next();

                tMaterial.modifyPartState(tEntry.getKey(), tActivePartConfiguration.getBoolean(tEntry.getKey(), "PartStates", tEntry.getValue(), "Sets this material to be allowed to be crafted into a: " + tEntry.getKey()));
            }


            if (tActivePartConfiguration.hasChanged())
                tActivePartConfiguration.save();
        }
    }

    @Override
    public void loadBaseDamageAbsorptions() {
        for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values())
        {
            Configuration tDamageAbsorbtionConfiguration = new Configuration(new File(iArmoryConfigFile.getParentFile().getAbsolutePath() + "\\Armory\\Material Configuration\\" + tMaterial.getInternalMaterialName() + "\\DamageAbsorption.cfg"), true);

            Iterator tIter = tMaterial.getAllBaseDamageAbsorbtionValues().entrySet().iterator();
            while(tIter.hasNext())
            {
                Map.Entry<String, Float> tEntry = (Map.Entry) tIter.next();

                tMaterial.setBaseDamageAbsorption(tEntry.getKey(), tDamageAbsorbtionConfiguration.getFloat(tEntry.getKey(), "Damage absorption ratios", tEntry.getValue(), 0F, 10F, "Determines the amount of damage absorbed by: " + tEntry.getKey()));
            }

            if (tDamageAbsorbtionConfiguration.hasChanged())
                tDamageAbsorbtionConfiguration.save();
        }
    }

    @Override
    public void loadPartModifiers() {
        for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values())
        {
            Configuration tMaxModifiersPerPartConfig = new Configuration(new File(iArmoryConfigFile.getParentFile().getAbsolutePath() + "\\Armory\\Material Configuration\\" + tMaterial.getInternalMaterialName() + "\\Modifiers.cfg"), true);

            Iterator tIter = tMaterial.getAllMaxModifiersAmounts().entrySet().iterator();
            while(tIter.hasNext())
            {
                Map.Entry<String, Integer> tEntry = (Map.Entry) tIter.next();

                tMaterial.setMaxModifiersOnPart(tEntry.getKey(), tMaxModifiersPerPartConfig.getInt(tEntry.getKey(), "Amount of modifiers (Second Tier)", tEntry.getValue(), 0, 6, "Amount of modifiers (Second Tier) on :" + tEntry.getKey()));
            }

            if (tMaxModifiersPerPartConfig.hasChanged())
                tMaxModifiersPerPartConfig.save();
        }
    }

    @Override
    public void loadBaseDurability() {
        for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values())
        {
            Configuration tBaseDurabilityConfig = new Configuration(new File(iArmoryConfigFile.getParentFile().getAbsolutePath() + "\\Armory\\Material Configuration\\" + tMaterial.getInternalMaterialName() + "\\Durability.cfg"), true);

            Iterator tIter = tMaterial.getAllBaseDurabilityValues().entrySet().iterator();
            while(tIter.hasNext())
            {
                Map.Entry<String, Integer> tEntry = (Map.Entry) tIter.next();

                tMaterial.setBaseDurability(tEntry.getKey(), tBaseDurabilityConfig.getInt(tEntry.getKey(), "Durability", tEntry.getValue(), 0, 80, "Amount of modifiers (Second Tier) on :" + tEntry.getKey()));
            }

            if (tBaseDurabilityConfig.hasChanged())
                tBaseDurabilityConfig.save();
        }
    }

    @Override
    public void loadTemperatureCoefficient() {
        for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
            Configuration tGlobalMaterialConfig = new Configuration(new File(iArmoryConfigFile.getParentFile().getAbsolutePath() + "\\Armory\\Material Configuration\\" + tMaterial.getInternalMaterialName() + "\\Global.cfg"), true);

            tMaterial.setHeatCoefficient(tGlobalMaterialConfig.getFloat("HeatCoefficient", "Global", tMaterial.getHeatCoefficient(), -10F, 10F, "Determines how fast a metal can heat up. Higher is better."));

            if (tGlobalMaterialConfig.hasChanged())
                tGlobalMaterialConfig.save();
        }
    }

    @Override
    public void loadMeltingPoint() {
        for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values())
        {
            Configuration tGlobalMaterialConfig = new Configuration(new File(iArmoryConfigFile.getParentFile().getAbsolutePath() + "\\Armory\\Material Configuration\\" + tMaterial.getInternalMaterialName() + "\\Global.cfg"), true);

            tMaterial.setMeltingPoint(tGlobalMaterialConfig.getFloat("MeltingPoint", "Global", tMaterial.getMeltingPoint(), 20F, 5000F, "Determines the melting point of the metal"));

            if (tGlobalMaterialConfig.hasChanged())
                tGlobalMaterialConfig.save();
        }
    }
}
