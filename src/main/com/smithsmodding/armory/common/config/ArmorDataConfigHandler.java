package com.smithsmodding.armory.common.config;

import com.smithsmodding.armory.api.armor.MLAAddon;
import com.smithsmodding.armory.api.materials.IArmorConfigurator;
import com.smithsmodding.armory.api.materials.IArmorMaterial;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Orion
 * Created on 09.06.2015
 * 15:11
 * <p>
 * Copyrighted according to Project specific license
 */
public class ArmorDataConfigHandler implements IArmorConfigurator {
    private static File armoryConfigFile;

    public static void init(File armoryGlobalConfigFile) {
        armoryConfigFile = armoryGlobalConfigFile;
    }

    @Override
    public void loadIDs() {
        for (IArmorMaterial material : MaterialRegistry.getInstance().getArmorMaterials().values()) {
            Configuration globalMaterialConfig = new Configuration(new File(armoryConfigFile.getParentFile().getAbsolutePath() + "/CoreMaterial Configuration/" + material.getUniqueID() + "/Global.cfg"), true);

            material.setItemDamageMaterialIndex(globalMaterialConfig.getInt("CORE_MATERIAL", "Global", material.getItemDamageMaterialIndex(), 0, Short.MAX_VALUE, "The internalID of the CoreMaterial. NO TWO MATERIALS SHOULD HAVE THE SAME ID!."));

            if (globalMaterialConfig.hasChanged())
                globalMaterialConfig.save();
        }
    }

    @Override
    public void loadIsBaseArmorMaterial() {
        for (IArmorMaterial material : MaterialRegistry.getInstance().getArmorMaterials().values()) {
            Configuration globalMaterialConfig = new Configuration(new File(armoryConfigFile.getParentFile().getAbsolutePath() + "/CoreMaterial Configuration/" + material.getUniqueID() + "/Global.cfg"), true);

            material.setIsBaseArmorMaterial(globalMaterialConfig.getBoolean("IsBaseArmorMaterial", "Armor", material.getIsBaseArmorMaterial(), "Marks this material as a material out of which the base layer can be crafted."));

            if (globalMaterialConfig.hasChanged())
                globalMaterialConfig.save();
        }
    }

    @Override
    public void loadActiveParts() {
        for (IArmorMaterial material : MaterialRegistry.getInstance().getArmorMaterials().values()) {
            Configuration activePartConfiguration = new Configuration(new File(armoryConfigFile.getParentFile().getAbsolutePath() + "/CoreMaterial Configuration/" + material.getUniqueID() + "/PartStates.cfg"), true);

            Iterator iter = MedievalAddonRegistry.getInstance().getPartStatesForMaterial(material).entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<MLAAddon, Boolean> entry = (Map.Entry<MLAAddon, Boolean>) iter.next();

                MedievalAddonRegistry.getInstance().setPartStateForMaterial(material, entry.getKey(), activePartConfiguration.getBoolean(entry.getKey().getUniqueID(), "PartStates", entry.getValue(), "Sets this material to be allowed to be crafted into a: " + entry.getKey().getUniqueID()));
            }

            if (activePartConfiguration.hasChanged())
                activePartConfiguration.save();
        }
    }

    @Override
    public void loadBaseDamageAbsorptions() {
        for (IArmorMaterial material : MaterialRegistry.getInstance().getArmorMaterials().values()) {
            Configuration damageAbsorbtionConfiguration = new Configuration(new File(armoryConfigFile.getParentFile().getAbsolutePath() + "/CoreMaterial Configuration/" + material.getUniqueID() + "/DamageAbsorption.cfg"), true);

            Iterator iter = material.getAllBaseDamageAbsorptionValues().entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, Float> entry = (Map.Entry) iter.next();

                material.setBaseDamageAbsorption(entry.getKey(), damageAbsorbtionConfiguration.getFloat(entry.getKey(), "Damage absorption ratios", entry.getValue(), 0F, 10F, "Determines the amount of damage absorbed by: " + entry.getKey()));
            }

            if (damageAbsorbtionConfiguration.hasChanged())
                damageAbsorbtionConfiguration.save();
        }
    }

    @Override
    public void loadPartModifiers() {
        for (IArmorMaterial material : MaterialRegistry.getInstance().getArmorMaterials().values()) {
            Configuration tMaxModifiersPerPartConfig = new Configuration(new File(armoryConfigFile.getParentFile().getAbsolutePath() + "/CoreMaterial Configuration/" + material.getUniqueID() + "/Modifiers.cfg"), true);

            Iterator tIter = material.getAllMaxModifiersAmounts().entrySet().iterator();
            while (tIter.hasNext()) {
                Map.Entry<String, Integer> tEntry = (Map.Entry) tIter.next();

                material.setMaxModifiersOnPart(tEntry.getKey(), tMaxModifiersPerPartConfig.getInt(tEntry.getKey(), "Amount of modifiers (Second Tier)", tEntry.getValue(), 0, 6, "Amount of modifiers (Second Tier) on :" + tEntry.getKey()));
            }

            if (tMaxModifiersPerPartConfig.hasChanged())
                tMaxModifiersPerPartConfig.save();
        }
    }

    @Override
    public void loadBaseDurability() {
        for (IArmorMaterial material : MaterialRegistry.getInstance().getArmorMaterials().values()) {
            Configuration baseDurabilityConfig = new Configuration(new File(armoryConfigFile.getParentFile().getAbsolutePath() + "/CoreMaterial Configuration/" + material.getUniqueID() + "/Durability.cfg"), true);

            Iterator iter = material.getAllBaseDurabilityValues().entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, Integer> tEntry = (Map.Entry) iter.next();

                material.setBaseDurability(tEntry.getKey(), baseDurabilityConfig.getInt(tEntry.getKey(), "Durability", tEntry.getValue(), 0, 80, "Amount of modifiers (Second Tier) on :" + tEntry.getKey()));
            }

            if (baseDurabilityConfig.hasChanged())
                baseDurabilityConfig.save();
        }
    }

    @Override
    public void loadTemperatureCoefficient() {
        for (IArmorMaterial material : MaterialRegistry.getInstance().getArmorMaterials().values()) {
            Configuration globalMaterialConfig = new Configuration(new File(armoryConfigFile.getParentFile().getAbsolutePath() + "/CoreMaterial Configuration/" + material.getUniqueID() + "/Global.cfg"), true);

            material.setHeatCoefficient(globalMaterialConfig.getFloat("HeatCoefficient", "Global", material.getHeatCoefficient(), -10F, 10F, "Determines how fast a metal can heat up. Higher is better."));

            if (globalMaterialConfig.hasChanged())
                globalMaterialConfig.save();
        }
    }

    @Override
    public void loadMeltingPoint() {
        for (IArmorMaterial material : MaterialRegistry.getInstance().getArmorMaterials().values()) {
            Configuration globalMaterialConfig = new Configuration(new File(armoryConfigFile.getParentFile().getAbsolutePath() + "/CoreMaterial Configuration/" + material.getUniqueID() + "/Global.cfg"), true);

            material.setMeltingPoint(globalMaterialConfig.getFloat("MeltingPoint", "Global", material.getMeltingPoint(), 20F, 5000F, "Determines the melting point of the metal"));

            if (globalMaterialConfig.hasChanged())
                globalMaterialConfig.save();
        }
    }
}
