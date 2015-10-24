package com.SmithsModding.Armory.Common.Knowledge.Blueprint;

import com.SmithsModding.Armory.API.Knowledge.IBlueprint;
import com.SmithsModding.Armory.API.Materials.IArmorMaterial;
import com.SmithsModding.Armory.Common.Config.ArmoryConfig;
import com.SmithsModding.Armory.Util.Client.TranslationKeys;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

/**
 * Created by marcf on 10/2/2015.
 */
public class MaterialBlueprint implements IBlueprint {

    IArmorMaterial iMaterial;

    public MaterialBlueprint(IArmorMaterial pMaterial) {
        iMaterial = pMaterial;
    }

    @Override
    public String getID() {
        return iMaterial.getInternalMaterialName();
    }

    @Override
    public String getRecipeID() {
        return "";
    }

    @Override
    public float getMaxFloatValue() {
        return 1F;
    }

    @Override
    public float getMinFloatValue() {
        return 0;
    }

    @Override
    public String getTranslatedQuality(float pFloatValue) {
        if (pFloatValue > 1F)
            return EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal(TranslationKeys.Items.Blueprint.CreativeTier) + EnumChatFormatting.RESET;

        if (pFloatValue <= 0F)
            return EnumChatFormatting.RED + StatCollector.translateToLocal(TranslationKeys.Items.Blueprint.Tier1) + EnumChatFormatting.RESET;

        if (pFloatValue <= 0.45F)
            return EnumChatFormatting.GOLD + StatCollector.translateToLocal(TranslationKeys.Items.Blueprint.Tier2) + EnumChatFormatting.RESET;

        if (pFloatValue <= 0.65F)
            return EnumChatFormatting.GREEN + StatCollector.translateToLocal(TranslationKeys.Items.Blueprint.Tier3) + EnumChatFormatting.RESET;

        if (pFloatValue <= 0.85F)
            return EnumChatFormatting.BLUE + StatCollector.translateToLocal(TranslationKeys.Items.Blueprint.Tier4) + EnumChatFormatting.RESET;

        if (pFloatValue <= 0.95F)
            return EnumChatFormatting.DARK_AQUA + StatCollector.translateToLocal(TranslationKeys.Items.Blueprint.Tier5) + EnumChatFormatting.RESET;

        return getTranslatedQuality(1.1F);

    }

    @Override
    public int handleRecipeResultFromItemStackForPlayer(EntityPlayer pPlayer, ItemStack pRecipeResult, float pBlueprintQuality) {
        return pRecipeResult.stackSize;
    }

    @Override
    public int handleRecipeResultFromItemStack(ItemStack pRecipeResult, float pBlueprintQuality) {
        return pRecipeResult.stackSize;
    }

    @Override
    public float getQualityDecrementOnTick(boolean pInGuide) {
        if (pInGuide)
            return ArmoryConfig.materialBlueprintDeteriation;

        return ArmoryConfig.materialBlueprintDeteriationinInventory;
    }

    @Override
    public String getProductionInfoLine(ItemStack pStack) {
        return StatCollector.translateToLocal(TranslationKeys.Items.Blueprint.Material1) + iMaterial.getVisibleNameColor() + " " + StatCollector.translateToLocal(iMaterial.getVisibleName()) + EnumChatFormatting.RESET + EnumChatFormatting.GRAY + StatCollector.translateToLocal(TranslationKeys.Items.Blueprint.Material2);
    }
}
