/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.armory.common.item;

import com.smithsmodding.armory.api.materials.IArmorMaterial;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.registry.MaterialRegistry;
import com.smithsmodding.smithscore.client.proxy.CoreClientProxy;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public abstract class ItemResource extends Item {

    @Override
    public FontRenderer getFontRenderer(ItemStack stack) {
        return CoreClientProxy.getMultiColoredFontRenderer();
    }

    @Override
    public String getItemStackDisplayName(ItemStack pStack) {
        String tMaterialID = "";

        if (pStack.getTagCompound() == null) {
            if (pStack.getItemDamage() == 0)
                pStack.setItemDamage(1);

            for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                if (tMaterial.getItemDamageMaterialIndex() == pStack.getItemDamage()) {
                    tMaterialID = tMaterial.getUniqueID();
                    break;
                }
            }
        } else {
            tMaterialID = pStack.getTagCompound().getString(References.NBTTagCompoundData.Material);
        }

        IArmorMaterial tMaterial = MaterialRegistry.getInstance().getMaterial(tMaterialID);

        return tMaterial.getNameColor() + I18n.format(tMaterial.getTranslationKey()) + " " + TextFormatting.RESET + I18n.format(this.getUnlocalizedName() + ".name");
    }

}
