/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Common.Item.Knowledge;

import com.Orion.Armory.API.Knowledge.BlueprintRegistry;
import com.Orion.Armory.API.Knowledge.IBluePrintItem;
import com.Orion.Armory.API.Knowledge.IBlueprint;
import com.Orion.Armory.Common.Item.ItemResource;
import com.Orion.Armory.Common.Registry.GeneralRegistry;
import com.Orion.Armory.Util.Client.CustomResource;
import com.Orion.Armory.Util.Client.Textures;
import com.Orion.Armory.Util.Client.TranslationKeys;
import com.Orion.Armory.Util.References;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ItemBlueprint extends ItemResource implements IBluePrintItem {
    public ItemBlueprint() {
        this.setMaxStackSize(1);
        this.setCreativeTab(GeneralRegistry.iTabBlueprints);
        this.setUnlocalizedName(References.InternalNames.Items.ItemBlueprint);

        registerResource(Textures.Items.Blueprint);
    }

    @Override
    public String getItemStackDisplayName(ItemStack pStack) {
        return StatCollector.translateToLocal(getUnlocalizedName() + ".name");
    }

    @Override
    public boolean getHasSubtypes() {
        return true;
    }


    @Override
    public CustomResource getResource(ItemStack pStack) {
        return getResource(References.InternalNames.Items.ItemBlueprint);
    }

    @Override
    public void getSubItems(Item pRing, CreativeTabs pCreativeTab, List pItemStacks) {
        for (IBlueprint pPrint : BlueprintRegistry.getInstance().getBlueprints()) {
            ItemStack pStack = new ItemStack(GeneralRegistry.Items.iBlueprints, 1);
            NBTTagCompound tCompound = new NBTTagCompound();
            tCompound.setDouble(References.NBTTagCompoundData.Item.Blueprints.FLOATVALUE, pPrint.getMaxFloatValue() + 1F);
            tCompound.setString(References.NBTTagCompoundData.Item.Blueprints.BLUEPRINTID, pPrint.getID());

            pStack.setTagCompound(tCompound);
            pItemStacks.add(pStack);
        }
    }

    @Override
    public String getBlueprintID(ItemStack pStack) {
        if (pStack.getTagCompound() == null)
            return null;

        return pStack.getTagCompound().getString(References.NBTTagCompoundData.Item.Blueprints.BLUEPRINTID);
    }

    @Override
    public float getBluePrintQuality(ItemStack pStack) {
        if (pStack.getTagCompound() == null)
            return -1F;

        return pStack.getTagCompound().getFloat(References.NBTTagCompoundData.Item.Blueprints.FLOATVALUE);
    }

    @Override
    public String getTranslatedBluePrintQuality(ItemStack pStack) {
        IBlueprint tPrint = BlueprintRegistry.getInstance().getBlueprint(getBlueprintID(pStack));

        if (tPrint == null)
            return EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.ITALIC + "UNKNOWN" + EnumChatFormatting.RESET;

        return tPrint.getTranslatedQuality(getBluePrintQuality(pStack));
    }

    @Override
    public void addInformation(ItemStack pStack, EntityPlayer pPlayer, List pTags, boolean pAdvancedTooltip) {
        super.addInformation(pStack, pPlayer, pTags, pAdvancedTooltip);

        pTags.add(StatCollector.translateToLocal(TranslationKeys.Knowledge.Blueprint.Quality) + " " + getTranslatedBluePrintQuality(pStack));
    }
}
