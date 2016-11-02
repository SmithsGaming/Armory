package com.smithsmodding.armory.common.helpers;

import com.smithsmodding.armory.api.armor.MLAAddon;
import com.smithsmodding.armory.api.helpers.IMedievalUpgradeConstructionHelper;
import com.smithsmodding.armory.common.addons.ArmorUpgradeMedieval;
import com.smithsmodding.armory.common.material.ChainLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import org.jetbrains.annotations.NotNull;

/**
 * Author Orion (Created on: 07.07.2016)
 */
public class MedievalUpgradeConstructionHelper implements IMedievalUpgradeConstructionHelper {

    @NotNull
    private static MedievalUpgradeConstructionHelper instance = new MedievalUpgradeConstructionHelper();

    private MedievalUpgradeConstructionHelper() {
        super();
    }

    @NotNull
    public static MedievalUpgradeConstructionHelper getInstance() {
        return instance;
    }

    @NotNull
    @Override
    public MLAAddon generateBaseChainLayer(String internalName, String parentName, String addonPositionId, String materialId, ResourceLocation itemTextureWhole, ResourceLocation modelTextureLocation) {
        return new ChainLayer(internalName, parentName, addonPositionId, materialId, itemTextureWhole, modelTextureLocation);
    }

    @NotNull
    @Override
    public MLAAddon generateMedievalUpdate(String internalName, String parentID, String armorPositionID, String materialInternalName, String visibleName, TextFormatting visibleNameColor, float protection, int extraDurability, int maxUpgrades, ResourceLocation itemTextureWhole, ResourceLocation modelTextureLocation) {
        return new ArmorUpgradeMedieval(internalName, parentID, armorPositionID, materialInternalName, visibleName, visibleNameColor, protection, extraDurability, maxUpgrades, itemTextureWhole, modelTextureLocation);
    }
}
