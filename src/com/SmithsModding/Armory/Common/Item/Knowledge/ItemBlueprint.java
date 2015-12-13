/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.Armory.Common.Item.Knowledge;

import com.SmithsModding.Armory.API.Knowledge.*;
import com.SmithsModding.Armory.Common.Registry.*;
import com.SmithsModding.Armory.Util.Client.*;
import com.SmithsModding.Armory.Util.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

import java.util.*;

public class ItemBlueprint extends Item implements IBluePrintItem {

    public static final String UNKNOWN = EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.ITALIC + "UNKNOWN" + EnumChatFormatting.RESET;

    public ItemBlueprint () {
        this.setMaxStackSize(1);
        //this.setCreativeTab(GeneralRegistry.iTabBlueprints);
        this.setUnlocalizedName(References.InternalNames.Items.ItemBlueprint);
    }

    @Override
    public String getItemStackDisplayName (ItemStack pStack) {
        return StatCollector.translateToLocal(getUnlocalizedName() + ".name");
    }

    @Override
    public boolean getHasSubtypes () {
        return true;
    }

    @Override
    public void getSubItems (Item pItem, CreativeTabs pCreativeTab, List pItemStacks) {
        for (IBlueprint pPrint : BlueprintRegistry.getInstance().getBlueprints()) {
            ItemStack pStack = new ItemStack(GeneralRegistry.Items.blueprint, 1);
            NBTTagCompound tCompound = new NBTTagCompound();
            tCompound.setDouble(References.NBTTagCompoundData.Item.Blueprints.FLOATVALUE, pPrint.getMaxFloatValue() + 1F);
            tCompound.setString(References.NBTTagCompoundData.Item.Blueprints.BLUEPRINTID, pPrint.getID());

            pStack.setTagCompound(tCompound);
            pItemStacks.add(pStack);
        }
    }

    @Override
    public String getBlueprintID (ItemStack pStack) {
        if (pStack.getTagCompound() == null)
            return null;

        return pStack.getTagCompound().getString(References.NBTTagCompoundData.Item.Blueprints.BLUEPRINTID);
    }

    @Override
    public float getBluePrintQuality (ItemStack pStack) {
        if (pStack.getTagCompound() == null)
            return -1F;

        return pStack.getTagCompound().getFloat(References.NBTTagCompoundData.Item.Blueprints.FLOATVALUE);
    }

    @Override
    public void setBluePrintQuality (ItemStack pStack, float pNewQuality) {
        NBTTagCompound tCompound = pStack.getTagCompound();

        if (tCompound == null)
            tCompound = new NBTTagCompound();

        tCompound.setFloat(References.NBTTagCompoundData.Item.Blueprints.FLOATVALUE, pNewQuality);

        pStack.setTagCompound(tCompound);
    }

    @Override
    public void onUpdate (ItemStack pStack, World pWorld, Entity pEntity, int pMetaData, boolean pSelected) {
        String tBlueprintID = getBlueprintID(pStack);

        if (tBlueprintID.equals(""))
            return;

        IBlueprint tBlueprint = BlueprintRegistry.getInstance().getBlueprint(tBlueprintID);

        if (tBlueprint == null)
            return;

        float tNewBlueprintQuality = getBluePrintQuality(pStack) - tBlueprint.getQualityDecrementOnTick(false);

        if (getBluePrintQuality(pStack) >= tBlueprint.getMinFloatValue() && getBluePrintQuality(pStack) <= tBlueprint.getMaxFloatValue() && tNewBlueprintQuality >= tBlueprint.getMinFloatValue() && tNewBlueprintQuality <= tBlueprint.getMaxFloatValue())
            setBluePrintQuality(pStack, tNewBlueprintQuality);
    }

    @Override
    public String getTranslatedBluePrintQuality (ItemStack pStack) {
        IBlueprint tPrint = BlueprintRegistry.getInstance().getBlueprint(getBlueprintID(pStack));

        if (tPrint == null)
            return UNKNOWN;

        return tPrint.getTranslatedQuality(getBluePrintQuality(pStack));
    }

    @Override
    public void addInformation (ItemStack pStack, EntityPlayer pPlayer, List pTags, boolean pAdvancedTooltip) {
        super.addInformation(pStack, pPlayer, pTags, pAdvancedTooltip);

        pTags.add(StatCollector.translateToLocal(TranslationKeys.Items.Blueprint.Quality) + " " + getTranslatedBluePrintQuality(pStack));

        IBlueprint tPrint = BlueprintRegistry.getInstance().getBlueprint(getBlueprintID(pStack));
        if (tPrint == null) {
            pTags.add(StatCollector.translateToLocal(TranslationKeys.Items.Blueprint.Produces) + " " + UNKNOWN + " (" + getBlueprintID(pStack) + ")");
        } else {
            pTags.add(tPrint.getProductionInfoLine(pStack));
        }

    }
}
