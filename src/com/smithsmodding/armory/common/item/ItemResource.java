/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.armory.common.item;

import com.smithsmodding.armory.api.materials.IArmorMaterial;
import com.smithsmodding.armory.common.material.MaterialRegistry;
import com.smithsmodding.armory.util.References;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

public abstract class ItemResource extends Item {

    @Override
    public String getItemStackDisplayName(ItemStack pStack) {
        String tMaterialID = "";

        if (pStack.getTagCompound() == null) {
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

        return tMaterial.getNameColor() + I18n.translateToLocal(tMaterial.getTranslationKey()) + " " + TextFormatting.RESET + I18n.translateToLocal(this.getUnlocalizedName() + ".name");
    }

}
