package com.smithsmodding.armory.common.helpers;

import com.smithsmodding.armory.api.helpers.IMaterialConstructionHelper;
import com.smithsmodding.armory.api.materials.IArmorMaterial;
import com.smithsmodding.armory.common.material.ArmorMaterial;
import net.minecraft.item.ItemStack;

/**
 * Author Orion (Created on: 07.07.2016)
 */
public class MaterialConstructionHelper implements IMaterialConstructionHelper {

    private static MaterialConstructionHelper instance = new MaterialConstructionHelper();

    private MaterialConstructionHelper() {
    }

    public static MaterialConstructionHelper getInstance() {
        return instance;
    }

    @Override
    public IArmorMaterial generateArmorMaterial(String uniqueIdentifier, String oreDictionaryIdentifier, boolean isBaseMaterial, float meltingPoint, int meltingTime, float heatCoefficient, ItemStack baseStack) {
        return new ArmorMaterial(uniqueIdentifier, oreDictionaryIdentifier, isBaseMaterial, meltingPoint, meltingTime, heatCoefficient, baseStack);
    }
}
