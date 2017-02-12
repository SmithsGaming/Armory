package com.smithsmodding.armory.common.helpers;

import com.smithsmodding.armory.api.common.armor.IMultiComponentArmor;
import com.smithsmodding.armory.api.common.armor.IMultiComponentArmorExtension;
import com.smithsmodding.armory.api.common.capability.armor.IArmorCapability;
import com.smithsmodding.armory.api.common.helpers.IMaterialConstructionHelper;
import com.smithsmodding.armory.api.common.material.anvil.IAnvilMaterial;
import com.smithsmodding.armory.api.common.material.armor.IAddonArmorMaterial;
import com.smithsmodding.armory.api.common.material.armor.IAddonArmorMaterialDataCallback;
import com.smithsmodding.armory.api.common.material.armor.ICoreArmorMaterialDataCallback;
import com.smithsmodding.armory.api.common.material.armor.ICoreArmorMaterial;
import com.smithsmodding.armory.common.material.MedievalAddonArmorMaterial;
import com.smithsmodding.armory.common.material.MedievalAnvilMaterial;
import com.smithsmodding.armory.common.material.MedievalCoreArmorMaterial;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;
import java.util.HashMap;

/**
 * Author Orion (Created on: 07.07.2016)
 */
public class MaterialConstructionHelper implements IMaterialConstructionHelper {

    @Nonnull
    private static MaterialConstructionHelper instance = new MaterialConstructionHelper();

    private MaterialConstructionHelper() {
    }

    @Nonnull
    public static MaterialConstructionHelper getInstance() {
        return instance;
    }

    @Nonnull
    @Override
    public IAnvilMaterial createMedievalAnvilMaterial(String translationKey, String textFormatting, String oreDictionaryIdentifier, Float meltingPoint, Float vaporizingPoint, Integer meltingTime, Integer vaporizingTime, Float heatCoefficient, Integer durability) {
        return new MedievalAnvilMaterial(translationKey, textFormatting, oreDictionaryIdentifier, meltingPoint, vaporizingPoint, meltingTime, vaporizingTime, heatCoefficient, durability);
    }

    @Nonnull
    @Override
    public ICoreArmorMaterial createMedievalCoreArmorMaterial(String translationKey, String textFormatting, String oreDictionaryIdentifier, Float meltingPoint, Float vaporizingPoint, Integer meltingTime, Integer vaporizingTime, Float heatCoefficient, ICoreArmorMaterialDataCallback callback) {
        return new MedievalCoreArmorMaterial(translationKey, textFormatting, oreDictionaryIdentifier, meltingPoint, vaporizingPoint, meltingTime, vaporizingTime, heatCoefficient) {
            @Nonnull
            @Override
            public Integer getBaseDurabilityForArmor(@Nonnull IMultiComponentArmor armor) {
                return callback.getBaseDurabilityForArmor(armor);
            }

            @Nonnull
            @Override
            public HashMap<Capability<? extends IArmorCapability>, Object> getOverrideCoreMaterialCapabilities(IMultiComponentArmor armor) {
                return callback.getOverrideCoreMaterialCapabilities(armor);
            }
        };
    }

    @Nonnull
    @Override
    public IAddonArmorMaterial createMedievalAddonArmorMaterial(String translationKey, String textFormatting, String oreDictionaryIdentifier, Float meltingPoint, Float vaporizingPoint, Integer meltingTime, Integer vaporizingTime, Float heatCoefficient, IAddonArmorMaterialDataCallback callback) {
        return new MedievalAddonArmorMaterial(translationKey, textFormatting, oreDictionaryIdentifier, meltingPoint, vaporizingPoint, meltingTime, vaporizingTime, heatCoefficient) {
            @Nonnull
            @Override
            public HashMap<Capability<? extends IArmorCapability>, Object> getOverrideAddonMaterialCapabilities(IMultiComponentArmorExtension extension) {
                return callback.getOverrideAddonMaterialCapabilities(extension);
            }
        };
    }
}
