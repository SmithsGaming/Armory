package com.smithsmodding.armory.api.common.helpers;

import com.smithsmodding.armory.api.common.material.anvil.IAnvilMaterial;
import com.smithsmodding.armory.api.common.material.armor.IAddonArmorMaterial;
import com.smithsmodding.armory.api.common.material.armor.IAddonArmorMaterialDataCallback;
import com.smithsmodding.armory.api.common.material.armor.ICoreArmorMaterialDataCallback;
import com.smithsmodding.armory.api.common.material.armor.ICoreArmorMaterial;

import javax.annotation.Nonnull;

/**
 * Author Orion (Created on: 07.07.2016)
 */
public interface IMaterialConstructionHelper {

    @Nonnull
    IAnvilMaterial createMedievalAnvilMaterial(String translationKey, String textFormatting, String oreDictionaryIdentifier, Float meltingPoint, Float vaporizingPoint, Integer meltingTime, Integer vaporizingTime, Float heatCoefficient, Integer durability);

    @Nonnull
    ICoreArmorMaterial createMedievalCoreArmorMaterial(String translationKey, String textFormatting, String oreDictionaryIdentifier, Float meltingPoint, Float vaporizingPoint, Integer meltingTime, Integer vaporizingTime, Float heatCoefficient, ICoreArmorMaterialDataCallback callback);

    @Nonnull
    IAddonArmorMaterial createMedievalAddonArmorMaterial(String translationKey, String textFormatting, String oreDictionaryIdentifier, Float meltingPoint, Float vaporizingPoint, Integer meltingTime, Integer vaporizingTime, Float heatCoefficient, IAddonArmorMaterialDataCallback callback);
}
