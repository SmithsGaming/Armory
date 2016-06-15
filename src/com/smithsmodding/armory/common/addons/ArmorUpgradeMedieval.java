package com.smithsmodding.armory.common.addons;

import com.smithsmodding.armory.api.armor.MLAAddon;
import com.smithsmodding.armory.api.armor.MaterialDependentMLAAddon;
import com.smithsmodding.armory.api.materials.IArmorMaterial;
import com.smithsmodding.armory.common.material.MaterialRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

/**
 * Created by Orion on 27-3-2014.
 */
public class ArmorUpgradeMedieval extends MaterialDependentMLAAddon {
    private final String materialInternalName;
    private final String visibleName;
    private final TextFormatting visibleNameColor;
    private final float protection;
    private final int extraDurability;

    //Constructors
    public ArmorUpgradeMedieval(String internalName, String parentID, String armorPositionID, String materialInternalName, String visibleName, TextFormatting visibleNameColor, float protection, int extraDurability, int maxUpgrades, ResourceLocation itemTextureWhole, ResourceLocation modelTextureLocation) {
        super(internalName, materialInternalName, parentID, armorPositionID, maxUpgrades, itemTextureWhole, modelTextureLocation, 1);
        this.materialInternalName = materialInternalName;
        this.visibleName = visibleName;
        this.visibleNameColor = visibleNameColor;
        this.protection = protection;
        this.extraDurability = extraDurability;
    }

    @Override
    public String getDisplayName() {
        IArmorMaterial material = MaterialRegistry.getInstance().getMaterial(materialInternalName);
        return material.getNameColor() + I18n.translateToLocal(material.getTranslationKey()) + getVisibleNameColor() + I18n.translateToLocal(getVisibleName()) + TextFormatting.RESET;
    }

    @Override
    public boolean validateCrafting(String addonIdToCheckAgainst, boolean installed) {
        MLAAddon upgrade = MedievalAddonRegistry.getInstance().getUpgrade(addonIdToCheckAgainst);
        return !((this.getAddonPositionID() == upgrade.getAddonPositionID()) && (this.getUniqueID() != addonIdToCheckAgainst));
    }

    public String getMaterialInternalName() {
        return materialInternalName;
    }

    public String getVisibleName() {
        return visibleName;
    }

    public TextFormatting getVisibleNameColor() {
        return visibleNameColor;
    }

    public float getProtection() {
        return protection;
    }

    public int getExtraDurability() {
        return extraDurability;
    }
}




/*
Old validation code. Might still need it.

public boolean validateCraftingForThisUpgrade(ArrayList<ArmorUpgrade> pInstalledUpgrades, ArrayList<ArmorUpgrade> pNewUpgrades)
    {
        int tInstalledAmount = 0;
        for (ArmorUpgrade tUpgrade : pInstalledUpgrades)
        {
            if (tUpgrade.iType.equals(this.iType))
            {
                tInstalledAmount++;
            }
        }

        if (tInstalledAmount == iMaxUpgrades)
        {
            return false;
        }

        return true;
    }

 */
