package com.smithsmodding.armory.common.registry;

import com.smithsmodding.armory.api.armor.MultiLayeredArmor;
import com.smithsmodding.armory.api.registries.IArmorRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * Author Orion (Created on: 07.07.2016)
 */
public class ArmorRegistry implements IArmorRegistry {
    private static ArmorRegistry instance;

    //Hashmap for storing all the basic armor mappings
    @NotNull
    protected HashMap<String, MultiLayeredArmor> mappings = new HashMap<String, MultiLayeredArmor>();

    public static ArmorRegistry getInstance() {
        if (instance == null) {
            instance = new ArmorRegistry();
        }

        return instance;
    }


    //ArmorMappings
    @NotNull
    public HashMap<String, MultiLayeredArmor> getAllRegisteredArmors() {
        return mappings;
    }

    public void registerNewArmor(@NotNull MultiLayeredArmor armor) {
        mappings.put(armor.getUniqueID(), armor);
    }

    public MultiLayeredArmor getArmor(String uniqueId) {
        return mappings.get(uniqueId);
    }
}
