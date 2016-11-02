package com.smithsmodding.armory.api.helpers;

import com.smithsmodding.armory.api.armor.MLAAddon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import org.jetbrains.annotations.NotNull;

/**
 * Author Orion (Created on: 07.07.2016)
 */
public interface IMedievalUpgradeConstructionHelper {

    @NotNull MLAAddon generateBaseChainLayer(String internalName, String parentName, String addonPositionId, String materialId, ResourceLocation itemTextureWhole, ResourceLocation modelTextureLocation);

    @NotNull MLAAddon generateMedievalUpdate(String internalName, String parentID, String armorPositionID, String materialInternalName, String visibleName, TextFormatting visibleNameColor, float protection, int extraDurability, int maxUpgrades, ResourceLocation itemTextureWhole, ResourceLocation modelTextureLocation);
}
