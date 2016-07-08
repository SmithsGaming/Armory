package com.smithsmodding.armory.api.helpers;

import com.smithsmodding.armory.api.materials.IArmorMaterial;
import net.minecraft.item.ItemStack;

/**
 * Author Orion (Created on: 07.07.2016)
 */
public interface IMaterialConstructionHelper {

    IArmorMaterial generateArmorMaterial(String uniqueIdentifier, String oreDictionaryIdentifier, boolean isBaseMaterial, float meltingPoint, int meltingTime, float heatCoefficient, ItemStack baseStack);
}
